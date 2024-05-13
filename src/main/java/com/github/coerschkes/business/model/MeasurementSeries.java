package com.github.coerschkes.business.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class MeasurementSeries {
    private final int measurementSeriesId;
    private final int timeMillis;
    private final String consumer;
    private final String measurementSize;
    private Measurement[] measurements;

    public MeasurementSeries(final @JsonProperty("measurementSeriesId") int measurementSeriesId,
                             final @JsonProperty("timeMillis") int timeMillis,
                             final @JsonProperty("consumer") String consumer,
                             final @JsonProperty("measurementSize") String measurementSize) {
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

    public String measurementsStringRepresentation() {
        if (measurements == null) {
            return null;
        } else {
            return Arrays.stream(measurements)
                    .map(this::getMeasurementStringRepresentation)
                    .collect(Collectors.joining("/"));
        }
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

    private String getMeasurementStringRepresentation(final Measurement measurement) {
        if (isFirst(measurement)) {
            return measurement.getMeasurementValue() + " 0 ";
        } else {
            return measurement.getMeasurementValue() + " " + measurement.getTimeMillis() + " ";
        }
    }

    private boolean isFirst(final Measurement measurement) {
        Optional<Measurement> first = Arrays.stream(measurements).findFirst();
        return first.filter(measurement::equals).isPresent();
    }
}
