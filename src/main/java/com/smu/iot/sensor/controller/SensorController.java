package com.smu.iot.sensor.controller;

import com.smu.iot.sensor.entity.TemperatureSensor;
import com.smu.iot.sensor.service.TemperatureSensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sensor")
@RequiredArgsConstructor
public class SensorController {

    private final TemperatureSensorService temperatureSensorService;

    @GetMapping
    public String index(Model model) {
        List<TemperatureSensor> recentData = temperatureSensorService.getRecentData();
        model.addAttribute("sensor_value_list", recentData);
        return "sensor/index";
    }

    @GetMapping("/getTemp/{cnt}")
    @ResponseBody
    public ResponseEntity<List<TemperatureSensor>> getTemp(@PathVariable int cnt) {
        List<TemperatureSensor> data = temperatureSensorService.getData(cnt);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/setTemp")
    @ResponseBody
    public ResponseEntity<Map<String, String>> setTemp(@RequestParam Double value) {
        try {
            temperatureSensorService.saveData(value);
            Map<String, String> response = new HashMap<>();
            response.put("message", "OK");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "KEY_ERROR");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}