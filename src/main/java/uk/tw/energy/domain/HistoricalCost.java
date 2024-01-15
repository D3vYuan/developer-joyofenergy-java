package uk.tw.energy.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class HistoricalCost {

    private String pricePlanId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal amount;

    public HistoricalCost(String pricePlanId, Instant startDateInstant, Instant endDateInstant, BigDecimal amount) {
        this.pricePlanId = pricePlanId;
        this.startDate = LocalDate.ofInstant(startDateInstant, ZoneId.systemDefault());
        this.endDate = LocalDate.ofInstant(endDateInstant, ZoneId.systemDefault());
        this.amount = amount;
    }

    public String getPricePlanId() {
        return this.pricePlanId;
    }

    public void setPricePlanId(String pricePlanId) {
        this.pricePlanId = pricePlanId;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
