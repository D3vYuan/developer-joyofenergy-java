package uk.tw.energy.service;

<<<<<<< HEAD
=======
import org.springframework.stereotype.Service;
import uk.tw.energy.domain.ElectricityReading;

>>>>>>> 103d11b53412a1320da3d6bd72c43adb501a8312
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

<<<<<<< HEAD
import org.springframework.stereotype.Service;

import uk.tw.energy.domain.ElectricityReading;

=======
>>>>>>> 103d11b53412a1320da3d6bd72c43adb501a8312
@Service
public class MeterReadingService {

    private final Map<String, List<ElectricityReading>> meterAssociatedReadings;

    public MeterReadingService(Map<String, List<ElectricityReading>> meterAssociatedReadings) {
        this.meterAssociatedReadings = meterAssociatedReadings;
    }

    public Optional<List<ElectricityReading>> getReadings(String smartMeterId) {
        return Optional.ofNullable(meterAssociatedReadings.get(smartMeterId));
    }

    public void storeReadings(String smartMeterId, List<ElectricityReading> electricityReadings) {
        if (!meterAssociatedReadings.containsKey(smartMeterId)) {
            meterAssociatedReadings.put(smartMeterId, new ArrayList<>());
        }
        meterAssociatedReadings.get(smartMeterId).addAll(electricityReadings);
    }
}
