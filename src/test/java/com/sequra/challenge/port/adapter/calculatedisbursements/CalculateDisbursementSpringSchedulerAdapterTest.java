package com.sequra.challenge.port.adapter.calculatedisbursements;

import com.sequra.challenge.domain.payments.DisbursementsRepository;
import com.sequra.challenge.domain.payments.MerchantRepository;
import com.sequra.challenge.domain.payments.OrderRepository;
import com.sequra.challenge.domain.payments.ShopperRepository;
import com.sequra.challenge.domain.payments.entity.Disbursement;
import com.sequra.challenge.domain.payments.entity.Merchant;
import com.sequra.challenge.domain.payments.entity.Order;
import com.sequra.challenge.domain.payments.entity.Shopper;
import com.sequra.challenge.domain.payments.entity.common.OrderStatus;
import com.sequra.challenge.domain.payments.entity.common.ParticipantBasicData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@SpringBootTest
class CalculateDisbursementSpringSchedulerAdapterTest {

    private static final BigDecimal[] ORDER_AMOUNTS = {BigDecimal.valueOf(40), BigDecimal.valueOf(200L), BigDecimal.valueOf(500L)};
    private static final BigDecimal[] DISBURSEMENTS_AMOUNTS = {BigDecimal.valueOf(0.4), BigDecimal.valueOf(1.9), BigDecimal.valueOf(4.25)};
    private static final String[] TABLES_TO_DELETE = {"SQ_DISBURSEMENT", "SQ_ORDER", "SQ_SHOPPER", "SQ_MERCHANT"};
    public static final int TWO = 2;
    public static final int ONE = 1;
    public static final int ZERO = 0;

    @Resource
    private DataSource dataSource;

    @Resource
    private CalculateDisbursementSpringSchedulerAdapter calculateDisbursementSpringSchedulerAdapter;

    @Resource
    private MerchantRepository merchantRepository;

    @Resource
    private ShopperRepository shopperRepository;

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private DisbursementsRepository disbursementsRepository;

    private Merchant merchant;
    private Shopper shopper;
    private Order order;

    private int year;
    private int week;

    @BeforeEach
    public void setUpEach() {
        setYearAndDate();

        shopper = new Shopper();
        shopper.setBasicData(new ParticipantBasicData("::TestShopper::", "::email::"));
        shopper.setNif("::NIF::");
        shopper = shopperRepository.save(shopper);

        merchant = buildMerchant();
        merchant = merchantRepository.save(merchant);

        order = buildOrder(merchant, ORDER_AMOUNTS[ZERO]);
        order = orderRepository.save(order);
    }

    @Test
    public void calculateDisbursementOfOneMerchant() {
        calculateDisbursementSpringSchedulerAdapter.calculateDisbursements();

        List<Disbursement> disbursements = disbursementsRepository.findAll();
        Assertions.assertEquals(disbursements.size(), ONE);
        validateDisbursement(disbursements.get(ZERO), merchant, DISBURSEMENTS_AMOUNTS[ZERO]);
    }

    @Test
    public void calculateDisbursementNoCompletedOrders() {
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);

        calculateDisbursementSpringSchedulerAdapter.calculateDisbursements();

        List<Disbursement> disbursements = disbursementsRepository.findAll();
        Assertions.assertEquals(disbursements.size(), ZERO);
    }

    @Test
    public void calculateDisbursementOfMultipleMerchantsAndOrders() {
        Merchant secondMerchant = buildMerchant();
        secondMerchant = merchantRepository.save(secondMerchant);
        List<Order> secondMerchantOrders = new LinkedList<>();
        secondMerchantOrders.add(buildOrder(secondMerchant, ORDER_AMOUNTS[TWO]));
        secondMerchantOrders.add(buildOrder(secondMerchant, ORDER_AMOUNTS[ONE]));
        orderRepository.saveAll(secondMerchantOrders);

        calculateDisbursementSpringSchedulerAdapter.calculateDisbursements();

        List<Disbursement> disbursements = disbursementsRepository.findAll();
        Assertions.assertEquals(disbursements.size(), TWO);

        Merchant finalSecondMerchant = secondMerchant;
        BigDecimal secondMerchantExpectedDisbursementAmount = DISBURSEMENTS_AMOUNTS[TWO].add(DISBURSEMENTS_AMOUNTS[ONE]);
        disbursements.stream()
                .filter(disbursementItem -> disbursementItem.getMerchant().getId().equals(finalSecondMerchant.getId()))
                .forEach(disbursement -> validateDisbursement(disbursement, finalSecondMerchant, secondMerchantExpectedDisbursementAmount));
        disbursements.stream()
                .filter(disbursementItem -> disbursementItem.getId().equals(merchant.getId()))
                .forEach(disbursement -> validateDisbursement(disbursement, merchant, DISBURSEMENTS_AMOUNTS[ZERO]));
    }

    @Test
    public void calculateDisbursementWithMultiplePages() {
        BigDecimal expectedAmount = BigDecimal.ZERO;
        List<Order> orders = new LinkedList<>();
        for(int i = 0; i < 42; i++) {
            orders.add(buildOrder(merchant, ORDER_AMOUNTS[ONE]));
            expectedAmount = expectedAmount.add(DISBURSEMENTS_AMOUNTS[ONE]);
        }
        orderRepository.saveAll(orders);

        calculateDisbursementSpringSchedulerAdapter.calculateDisbursements();

        List<Disbursement> disbursements = disbursementsRepository.findAll();
        Assertions.assertEquals(disbursements.size(), ONE);
        BigDecimal finalExpectedAmount = expectedAmount;
        disbursements.stream()
                .filter(disbursementItem -> disbursementItem.getId().equals(merchant.getId()))
                .forEach(disbursement -> validateDisbursement(disbursement, merchant, finalExpectedAmount));
    }

    @Test
    public void calculateDisbursementWithDifferentDaysOrders() {
        order.setCompletedAt(ZonedDateTime.now().minus(2, ChronoUnit.WEEKS));
        order = orderRepository.save(order);
        Order correctOrder = buildOrder(merchant, ORDER_AMOUNTS[TWO]);
        Order correctOrder2 = buildOrder(merchant, ORDER_AMOUNTS[ONE]);
        correctOrder2.setCompletedAt(correctOrder2.getCompletedAt().minusDays(4));
        orderRepository.save(correctOrder);
        orderRepository.save(correctOrder2);
        BigDecimal expectedAmount = DISBURSEMENTS_AMOUNTS[TWO].add(DISBURSEMENTS_AMOUNTS[ONE]);

        calculateDisbursementSpringSchedulerAdapter.calculateDisbursements();

        List<Disbursement> disbursements = disbursementsRepository.findAll();
        Assertions.assertEquals(disbursements.size(), ONE);
        disbursements.stream()
                .filter(disbursementItem -> disbursementItem.getId().equals(merchant.getId()))
                .forEach(disbursement -> validateDisbursement(disbursement, merchant, expectedAmount));
    }

    @AfterEach
    public void afterEach() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TABLES_TO_DELETE);
    }

    private void setYearAndDate() {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate date = LocalDate.now();
        year = date.get(weekFields.weekBasedYear());
        week = date.get(weekFields.weekOfWeekBasedYear());
    }
    private void validateDisbursement(Disbursement disbursements, Merchant merchant, BigDecimal expectedAmount) {
        Assertions.assertEquals(disbursements.getMerchant(), merchant);
        Assertions.assertEquals(disbursements.getAmount(), expectedAmount);
        Assertions.assertEquals(disbursements.getWeek(), week);
        Assertions.assertEquals(disbursements.getYear(), year);
    }

    private Order buildOrder(Merchant merchant, BigDecimal amount) {
        Order order = new Order();
        order.setMerchant(merchant);
        order.setAmount(amount);
        order.setShopper(shopper);
        order.setCompletedAt(ZonedDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault()));
        order.setCreatedAt(ZonedDateTime.now());
        order.setStatus(OrderStatus.COMPLETED);
        return order;
    }

    private Merchant buildMerchant() {
        Merchant merchant = new Merchant();
        merchant.setParticipantBasicData(new ParticipantBasicData("::Test::", "::email::"));
        merchant.setCif("::CIF::");
        return merchant;
    }
}