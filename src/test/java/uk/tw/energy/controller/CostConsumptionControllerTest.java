package uk.tw.energy.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

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
    private static final String SMART_METER_ID_WITH_PLAN = "smart-meter-id-with-plan";
    private static final String SMART_METER_ID_WITHOUT_PLAN = "smart-meter-id-without-plan";

    private PricePlan pricePlan1;
    private PricePlan pricePlan2;

    @BeforeEach
    void setup() {
        meterReadingService = new MeterReadingService(new HashMap<>());
        pricePlan1 = new PricePlan(PRICE_PLAN_1_ID, null, BigDecimal.TEN, null);
        pricePlan2 = new PricePlan(PRICE_PLAN_2_ID, null, BigDecimal.ONE, null);

        Map<String, String> smartMeterToPricePlan = Map.of(SMART_METER_ID_WITH_PLAN, PRICE_PLAN_1_ID);
        accountService = new AccountService(smartMeterToPricePlan);

        List<PricePlan> pricePlans = List.of(pricePlan1, pricePlan2);
        pricePlanService = new PricePlanService(pricePlans, meterReadingService);

        controller = new CostConsumptionController(pricePlans, accountService, meterReadingService, pricePlanService);
    }

    @Test
    public void shouldReturnZeroCostGivenThisWeekSingleHourReadingWithPricePlan1() {
        // Formula:
        // Average Meter Readings (KW): (15 + 5) / 2 = 10
        // Unit of Time (hr): 1
        // Unit of Energy Consumed (KwH): 10 / 1 = 10
        // Unit of Tariff = 10 * 10 = 100

        ElectricityReading electricityReading = new ElectricityReading(Instant.now().minusSeconds(60 * 60),
                BigDecimal.valueOf(15.0));
        ElectricityReading electricityReading2 = new ElectricityReading(Instant.now(), BigDecimal.valueOf(5.0));
        List<ElectricityReading> readings = List.of(electricityReading, electricityReading2);
        meterReadingService.storeReadings(SMART_METER_ID_WITH_PLAN, readings);

        ResponseEntity<HistoricalCost> historicalCost = controller.computeHistoricalCost(SMART_METER_ID_WITH_PLAN);
        assertThat(historicalCost.getBody()).isNotNull();
        assertThat(historicalCost.getBody().getAmount()).isCloseTo(BigDecimal.valueOf(0),
                Percentage.withPercentage(1));
    }

    @Test
    public void shouldReturnZeroCostGivenThisWeekTwoHourReadingWithPricePlan1() {
        // Formula:
        // Average Meter Readings (KW): (15 + 5) / 2 = 10
        // Unit of Time (hr): 2
        // Unit of Energy Consumed (KwH): 10 / 2 = 5
        // Unit of Tariff = 5 * 10 = 50

        ElectricityReading electricityReading = new ElectricityReading(Instant.now().minusSeconds(60 * 60 * 2),
                BigDecimal.valueOf(15.0));
        ElectricityReading electricityReading2 = new ElectricityReading(Instant.now(), BigDecimal.valueOf(5.0));
        List<ElectricityReading> readings = List.of(electricityReading, electricityReading2);
        meterReadingService.storeReadings(SMART_METER_ID_WITH_PLAN, readings);

        ResponseEntity<HistoricalCost> historicalCost = controller.computeHistoricalCost(SMART_METER_ID_WITH_PLAN);
        assertThat(historicalCost.getBody()).isNotNull();
        assertThat(historicalCost.getBody().getAmount()).isCloseTo(BigDecimal.valueOf(0),
                Percentage.withPercentage(1));
    }

    @Test
    public void shouldReturnCorrectCostGivenLastWeekSingleHourReadingWithPricePlan1() {
        // Formula:
        // Average Meter Readings (KW): (15 + 5) / 2 = 10
        // Unit of Time (hr): 1
        // Unit of Energy Consumed (KwH): 10 / 1 = 10
        // Unit of Tariff = 10 * 10 = 100

        ElectricityReading electricityReading = new ElectricityReading(
                Instant.now().minus(7, ChronoUnit.DAYS).minusSeconds(60 * 60 * 8),
                BigDecimal.valueOf(15.0));
        ElectricityReading electricityReading2 = new ElectricityReading(
                Instant.now().minus(7, ChronoUnit.DAYS).minusSeconds(60 * 60 * 7 + 30),
                BigDecimal.valueOf(5.0));
        List<ElectricityReading> readings = List.of(electricityReading, electricityReading2);
        meterReadingService.storeReadings(SMART_METER_ID_WITH_PLAN, readings);

        ResponseEntity<HistoricalCost> historicalCost = controller.computeHistoricalCost(SMART_METER_ID_WITH_PLAN);
        assertThat(historicalCost.getBody()).isNotNull();
        assertThat(historicalCost.getBody().getAmount()).isCloseTo(BigDecimal.valueOf(100),
                Percentage.withPercentage(1));
    }

    @Test
    public void shouldReturnCorrectCostGivenLastWeekTwoHourReadingWithPricePlan1() {
        // Formula:
        // Average Meter Readings (KW): (15 + 5) / 2 = 10
        // Unit of Time (hr): 1
        // Unit of Energy Consumed (KwH): 10 / 2 = 5
        // Unit of Tariff = 5 * 10 = 100

        ElectricityReading electricityReading = new ElectricityReading(
                Instant.now().minus(7, ChronoUnit.DAYS).minusSeconds(60 * 60 * 8),
                BigDecimal.valueOf(15.0));
        ElectricityReading electricityReading2 = new ElectricityReading(
                Instant.now().minus(7, ChronoUnit.DAYS).minusSeconds(60 * 60 * 6),
                BigDecimal.valueOf(5.0));
        List<ElectricityReading> readings = List.of(electricityReading, electricityReading2);
        meterReadingService.storeReadings(SMART_METER_ID_WITH_PLAN, readings);

        ResponseEntity<HistoricalCost> historicalCost = controller.computeHistoricalCost(SMART_METER_ID_WITH_PLAN);
        assertThat(historicalCost.getBody()).isNotNull();
        assertThat(historicalCost.getBody().getAmount()).isCloseTo(BigDecimal.valueOf(50),
                Percentage.withPercentage(1));
    }

    @Test
    public void shouldReturnErrorGivenSmartMeterWithoutPricePlan() {
        ElectricityReading electricityReading = new ElectricityReading(Instant.now().minusSeconds(60 * 60),
                BigDecimal.valueOf(15.0));
        ElectricityReading electricityReading2 = new ElectricityReading(Instant.now(), BigDecimal.valueOf(5.0));
        List<ElectricityReading> readings = List.of(electricityReading, electricityReading2);
        meterReadingService.storeReadings(SMART_METER_ID_WITHOUT_PLAN, readings);

        ResponseEntity<HistoricalCost> historicalCost = controller.computeHistoricalCost(SMART_METER_ID_WITHOUT_PLAN);
        assertThat(historicalCost.getBody()).isNull();
        assertThat(historicalCost.getStatusCode().is4xxClientError()).isTrue();
        assertThat(historicalCost.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
