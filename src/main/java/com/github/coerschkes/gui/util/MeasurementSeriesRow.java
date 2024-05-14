package com.github.coerschkes.gui.util;

import com.github.coerschkes.business.model.MeasurementSeries;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

@SuppressWarnings("unused")
public class MeasurementSeriesRow {
    private final SimpleIntegerProperty identNumber;
    private final SimpleIntegerProperty timeInterval;
    private final SimpleStringProperty consumer;
    private final SimpleStringProperty measurementSize;
    private final SimpleStringProperty measurements;

    private MeasurementSeriesRow(final int identNumber, final int timeInterval, final String consumer, final String measurementSize, final String measurements) {
        this.identNumber = new SimpleIntegerProperty(identNumber);
        this.timeInterval = new SimpleIntegerProperty(timeInterval);
        this.consumer = new SimpleStringProperty(consumer);
        this.measurementSize = new SimpleStringProperty(measurementSize);
        this.measurements = new SimpleStringProperty(measurements);
    }

    public static MeasurementSeriesRow of(final MeasurementSeries measurementSeries) {
        return new MeasurementSeriesRow(
                measurementSeries.getMeasurementSeriesId(),
                measurementSeries.getTimeMillis(),
                measurementSeries.getConsumer(),
                measurementSeries.getMeasurementSize(),
                measurementSeries.measurementsStringRepresentation()
        );
    }

    public int getIdentNumber() {
        return identNumber.get();
    }

    public SimpleIntegerProperty identNumberProperty() {
        return identNumber;
    }

    public int getTimeInterval() {
        return timeInterval.get();
    }

    public SimpleIntegerProperty timeIntervalProperty() {
        return timeInterval;
    }

    public String getConsumer() {
        return consumer.get();
    }

    public SimpleStringProperty consumerProperty() {
        return consumer;
    }

    public String getMeasurementSize() {
        return measurementSize.get();
    }

    public SimpleStringProperty measurementSizeProperty() {
        return measurementSize;
    }

    public String getMeasurements() {
        return measurements.get();
    }

    public SimpleStringProperty measurementsProperty() {
        return measurements;
    }
}
