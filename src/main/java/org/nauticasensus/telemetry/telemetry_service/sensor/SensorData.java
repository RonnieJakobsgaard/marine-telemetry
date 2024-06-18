package org.nauticasensus.telemetry.telemetry_service.sensor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@Entity
@Table(name = "sensor_data")
public class SensorData {

    @Id
    private String sensorId;
    private String unitOfMeasurement;
    private Double value;
    private Instant time;

    public SensorData() {
    }

    public SensorData(String sensorId, String unitOfMeasurement, Double value, Instant time) {
        this.sensorId = sensorId;
        this.unitOfMeasurement = unitOfMeasurement;
        this.value = value;
        this.time = time;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
}

interface SensorDataRepository extends ListCrudRepository<SensorData, Long> {
}


@RestController
@RequestMapping("/ts/sensor")
class SensorController {

    private final SensorDataRepository sensorDataRepository;

    public SensorController(SensorDataRepository sensorRepository) {
        this.sensorDataRepository = sensorRepository;
    }

    @PostMapping
    public SensorData create(@RequestBody SensorData sensor) {
        sensor.setTime(Instant.now());
        return sensorDataRepository.save(sensor);
    }
}
