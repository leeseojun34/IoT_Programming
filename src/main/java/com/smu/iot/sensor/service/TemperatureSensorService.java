package com.smu.iot.sensor.service;

import com.smu.iot.sensor.entity.TemperatureSensor;
import com.smu.iot.sensor.repository.TemperatureSensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TemperatureSensorService {

    private final TemperatureSensorRepository temperatureSensorRepository;

    /**
     * 최근 5개의 온도 데이터 조회
     */
    public List<TemperatureSensor> getRecentData() {
        return temperatureSensorRepository.findTop5ByOrderByRegDateDesc();
    }

    /**
     * 최근 cnt개의 온도 데이터 조회 (역순 정렬)
     */
    public List<TemperatureSensor> getData(int cnt) {
        List<TemperatureSensor> allData = temperatureSensorRepository.findAllByOrderByRegDateDesc();

        // cnt개만큼 가져오고 역순으로 정렬
        return allData.stream()
            .limit(cnt)
            .collect(Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    java.util.Collections.reverse(list);
                    return list;
                }
            ));
    }

    /**
     * 온도 데이터 저장
     */
    @Transactional
    public TemperatureSensor saveData(Double value) {
        TemperatureSensor sensor = TemperatureSensor.builder()
            .sensorValue(value)
            .build();
        return temperatureSensorRepository.save(sensor);
    }
}