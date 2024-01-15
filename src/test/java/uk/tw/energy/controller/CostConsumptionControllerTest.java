package uk.tw.energy.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.HistoricalCost;
import uk.tw.energy.domain.PricePlan;
import uk.tw.energy.service.AccountService;
import uk.tw.energy.service.MeterReadingService;
import uk.tw.energy.service.PricePlanService;

public class CostConsumptionControllerTest {
    private CostConsumptionController controller;
    private AccountService accountService;
    private MeterReadingService meterReadingService;
    private PricePlanService pricePlanService;

    private static final String PRICE_PLAN_1_ID = "test-supplier-1";
    private static final String PRICE_PLAN_2_ID = "test-supplier-2";
    private static final String SMART_METER_ID = "smart-meter-id";

    private PricePlan pricePlan1;
    private PricePlan pricePlan2;

    @BeforeEach
    void setup() {
        meterReadingService = new MeterReadingService(new HashMap<>());
        pricePlan1 = new PricePlan(PRICE_PLAN_1_ID, null, BigDecimal.TEN, null);
        pricePlan2 = new PricePlan(PRICE_PLAN_2_ID, null, BigDecimal.ONE, null);

        List<PricePlan> pricePlans = List.of(pricePlan1, pricePlan2);
        pricePlanService = new PricePlanService(pricePlans, meterReadingService);

        controller = new CostConsumptionController(accountService, meterReadingService, pricePlanService);
    }

    @Test
    public void shouldReturnCostGivenSingleReadingWithPricePlan1() {
        ElectricityReading electricityReading = new ElectricityReading(Instant.now().minusSeconds(3600),
                BigDecimal.valueOf(15.0));
        ElectricityReading electricityReading2 = new ElectricityReading(Instant.now(), BigDecimal.valueOf(5.0));
        List<ElectricityReading> readings = List.of(electricityReading, electricityReading2);
        meterReadingService.storeReadings(SMART_METER_ID, readings);

        HistoricalCost historicalCost = controller.computeHistoricalCost(pricePlan1, readings);
        assertThat(historicalCost.getAmount()).isCloseTo(BigDecimal.valueOf(100), Percentage.withPercentage(1));
    }
}
