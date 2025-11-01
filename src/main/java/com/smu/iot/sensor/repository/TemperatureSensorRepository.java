package com.smu.iot.sensor.repository;

import com.smu.iot.sensor.entity.TemperatureSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemperatureSensorRepository extends JpaRepository<TemperatureSensor, Long> {
    List<TemperatureSensor> findTop5ByOrderByRegDateDesc();
    List<TemperatureSensor> findAllByOrderByRegDateDesc();
}