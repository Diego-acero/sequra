package com.sequra.challenge.domain.payments.utils;

import lombok.Data;

@Data
public class PageFilter {
    private Long pageNumber;
    private Long resultsPerPage;
    private Long totalResults;
    private Long totalPages;

    public PageFilter(Long resultsPerPage, Long totalResults) {
        this.pageNumber = 0L;
        this.resultsPerPage = resultsPerPage;
        this.totalResults = totalResults;
        this.totalPages = (long) Math.ceil(totalResults.doubleValue() / resultsPerPage.doubleValue());
    }

    public void nextPage() {
        pageNumber++;
    }

    public Long getCurrentPageResults() {
        if(totalPages.equals(pageNumber + 1)) {
            return totalResults - (pageNumber * resultsPerPage);
        }
        if(pageNumber >= totalPages){
            return 0L;
        }
        return resultsPerPage;
    }
}
