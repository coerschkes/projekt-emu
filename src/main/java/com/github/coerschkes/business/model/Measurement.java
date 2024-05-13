package com.github.coerschkes.business.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Measurement {

    private final int measurementId;
    private final int measurementSeriesId;
    private final double measurementValue;
    private final long timeMillis;

    public Measurement(final @JsonProperty("measurementId") int measurementId,
                       final @JsonProperty("measurementSeriesId") int measurementSeriesId,
                       final @JsonProperty("measurementValue") double measurementValue,
                       final @JsonProperty("timeMillis") long timeMillis) {
        super();
        this.measurementId = measurementId;
        this.measurementSeriesId = measurementSeriesId;
        this.measurementValue = measurementValue;
        this.timeMillis = timeMillis;
    }

    public int getMeasurementId() {
        return measurementId;
    }


    public double getMeasurementValue() {
        return measurementValue;
    }


    public String concatAttributes() {
        return this.measurementId + ": " + this.measurementValue;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "measurementId=" + measurementId +
                ", measurementValue=" + measurementValue +
                ", timeMillis=" + timeMillis +
                '}';
    }

    public int getMeasurementSeriesId() {
        return measurementSeriesId;
    }
}
