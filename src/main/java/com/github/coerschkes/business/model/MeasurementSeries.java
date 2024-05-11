package com.github.coerschkes.business.model;


import java.util.Arrays;
import java.util.stream.Collectors;

public class MeasurementSeries {
    private final int measurementSeriesId;
    private final int timeMillis;
    private final String consumer;
    private final String measurementSize;
    private Measurement[] measurements;

    public MeasurementSeries(int measurementSeriesId, int timeMillis,
                             String consumer, String measurementSize) {
        super();
        this.measurementSeriesId = measurementSeriesId;
        this.timeMillis = timeMillis;
        this.consumer = consumer;
        this.measurementSize = measurementSize;
    }

    public int getMeasurementSeriesId() {
        return measurementSeriesId;
    }

    public int getTimeMillis() {
        return timeMillis;
    }

    public String getConsumer() {
        return consumer;
    }

    public String getMeasurementSize() {
        return measurementSize;
    }

    public Measurement[] getMeasurements() {
        return measurements;
    }

    public String getMeasurementsAsString() {
        return Arrays.stream(measurements)
                .map(Measurement::getMeasurementValue)
                .map(Object::toString)
                .collect(Collectors.joining(" / "));
    }

    public void setMeasurements(Measurement[] measurements) {
        this.measurements = measurements;
    }

    @Override
    public String toString() {
        return "MeasurementSeries{" +
                "measurementSeriesId=" + measurementSeriesId +
                ", timeMillis=" + timeMillis +
                ", consumer='" + consumer + '\'' +
                ", measurementSize='" + measurementSize + '\'' +
                ", measurements=" + Arrays.toString(measurements) +
                '}';
    }
}
