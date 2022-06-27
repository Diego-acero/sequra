package com.sequra.challenge.domain.payments.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

@NoArgsConstructor
@Data
@Table(name = "SQ_DISBURSEMENT")
@Entity
public class Disbursement {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ_SQ_DISBURSEMENT")
    @SequenceGenerator(name = "SEQ_SQ_DISBURSEMENT", sequenceName = "SEQ_SQ_DISBURSEMENT_ID", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "merchantId")
    private Merchant merchant;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer week;

    @Column(nullable = false)
    private BigDecimal amount;

    public void calculateYearAndWeek() {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate date = LocalDate.now();
        this.year = date.get(weekFields.weekBasedYear());
        this.week = date.get(weekFields.weekOfWeekBasedYear());
    }
}
