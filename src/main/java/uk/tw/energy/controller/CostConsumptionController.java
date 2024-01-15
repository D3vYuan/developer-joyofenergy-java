package uk.tw.energy.controller;

import java.math.BigDecimal;
import java.util.List;

import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.HistoricalCost;
import uk.tw.energy.domain.PricePlan;
import uk.tw.energy.service.AccountService;
import uk.tw.energy.service.MeterReadingService;
import uk.tw.energy.service.PricePlanService;

public class CostConsumptionController {

    private AccountService accountService;
    private MeterReadingService meterReadingService;
    private PricePlanService pricePlanService;

    public CostConsumptionController(AccountService accountService, MeterReadingService meterReadingService,
            PricePlanService pricePlanService) {
        this.accountService = accountService;
        this.meterReadingService = meterReadingService;
        this.pricePlanService = pricePlanService;
    }

    public HistoricalCost computeHistoricalCost(PricePlan pricePlan, List<ElectricityReading> readings) {
        BigDecimal consumptionCost = pricePlanService.calculateCost(readings, pricePlan);
        return new HistoricalCost(pricePlan, null, null, consumptionCost);
    }

}
