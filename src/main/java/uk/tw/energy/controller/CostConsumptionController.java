package uk.tw.energy.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.HistoricalCost;
import uk.tw.energy.domain.PricePlan;
import uk.tw.energy.service.AccountService;
import uk.tw.energy.service.MeterReadingService;
import uk.tw.energy.service.PricePlanService;

@RestController
@RequestMapping("/history")
public class CostConsumptionController {

    private final List<PricePlan> pricePlans;
    private AccountService accountService;
    private MeterReadingService meterReadingService;
    private PricePlanService pricePlanService;

    public CostConsumptionController(List<PricePlan> pricePlans, AccountService accountService,
            MeterReadingService meterReadingService,
            PricePlanService pricePlanService) {
        this.pricePlans = pricePlans;
        this.accountService = accountService;
        this.meterReadingService = meterReadingService;
        this.pricePlanService = pricePlanService;
    }

    @GetMapping("/usage/{meter-id}")
    public ResponseEntity<HistoricalCost> computeHistoricalCost(@PathVariable("meter-id") String meterId) {
        String pricePlanId = accountService.getPricePlanIdForSmartMeterId(meterId);
        Instant todayDate = Instant.now().truncatedTo(ChronoUnit.DAYS);

        Optional<PricePlan> pricePlan = this.pricePlans.stream().filter(plan -> plan.getPlanName().equals(pricePlanId))
                .findFirst();
        if (!pricePlan.isPresent()) {
            // return ResponseEntity.badRequest().body(String.format("No price plan found
            // for %s", meterId));
            return ResponseEntity.badRequest().body(null);
        }

        Instant lastWeekStartDateRange = todayDate.minus(7, ChronoUnit.DAYS);
        Instant lastWeekEndDateRange = todayDate.minus(1, ChronoUnit.DAYS);
        Optional<List<ElectricityReading>> readings = meterReadingService.getReadings(meterId);
        if (!readings.isPresent()) {
            HistoricalCost cost = new HistoricalCost(pricePlanId, lastWeekStartDateRange,
                    lastWeekEndDateRange,
                    BigDecimal.valueOf(0));
            return ResponseEntity.ok(cost);
        }

        List<ElectricityReading> lastWeekReadings = readings.get().stream()
                .filter(reading -> reading.time().isBefore(lastWeekEndDateRange)
                        && reading.time().isAfter(lastWeekStartDateRange))
                .collect(Collectors.toList());
        if (lastWeekReadings.isEmpty()) {
            HistoricalCost cost = new HistoricalCost(pricePlanId, lastWeekStartDateRange,
                    lastWeekEndDateRange,
                    BigDecimal.valueOf(0));
            return ResponseEntity.ok(cost);
        }

        BigDecimal consumptionCost = pricePlanService.calculateCost(readings.get(), pricePlan.get());
        // ElectricityReading first = readings.get().stream()
        // .min(Comparator.comparing(ElectricityReading::time))
        // .get();

        // ElectricityReading last = readings.get().stream()
        // .max(Comparator.comparing(ElectricityReading::time))
        // .get();

        HistoricalCost cost = new HistoricalCost(pricePlanId, lastWeekStartDateRange,
                lastWeekEndDateRange, consumptionCost);
        return ResponseEntity.ok(cost);
    }

}
