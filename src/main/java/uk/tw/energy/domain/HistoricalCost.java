package uk.tw.energy.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HistoricalCost {
    // { price_plan: xxx,
    // start_date: xxx,
    // end_date: xxx,
    // amount: xxx }
    private PricePlan pricePlan;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal amount;

    public HistoricalCost(PricePlan pricePlan, LocalDate startDate, LocalDate endDate, BigDecimal amount) {
        this.pricePlan = pricePlan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
    }

    public PricePlan getPricePlan() {
        return this.pricePlan;
    }

    public void setPricePlan(PricePlan pricePlan) {
        this.pricePlan = pricePlan;
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
