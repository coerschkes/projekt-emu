package com.github.coerschkes.gui;

import com.github.coerschkes.business.db.AsyncDatabaseModel;
import com.github.coerschkes.business.emu.AsyncEmuModel;
import com.github.coerschkes.business.model.Measurement;
import com.github.coerschkes.business.model.MeasurementSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class ReactiveControl {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveControl.class);
    private final AsyncDatabaseModel asyncDatabaseModel;
    private final AsyncEmuModel asyncEmuModel;
    private final Consumer<Throwable> errorHandler;

    public ReactiveControl(final Consumer<Throwable> errorHandler) {
        this.asyncDatabaseModel = AsyncDatabaseModel.getInstance();
        this.asyncEmuModel = AsyncEmuModel.getInstance();
        this.errorHandler = errorHandler;
    }

    public void readMeasurementSeries(final Consumer<MeasurementSeries[]> callbackOnCompletion) {
        asyncDatabaseModel.readAllMeasurementSeries().whenComplete((measurementSeries, throwable) -> {
            if (throwable != null) {
                handleThrowable(throwable);
            } else {
                callbackOnCompletion.accept(measurementSeries);
            }
        });
    }

    public void saveMeasurement(final Measurement measurement, final Runnable callbackOnCompletion) {
        asyncDatabaseModel.saveMeasurement(measurement).whenComplete((unused, throwable) -> {
            if (throwable != null) {
                handleThrowable(throwable);
            } else {
                callbackOnCompletion.run();
            }
        });
    }

    public void saveMeasurementSeries(final MeasurementSeries measurementSeries, final Runnable callbackOnCompletion) {
        asyncDatabaseModel.saveMeasurementSeries(measurementSeries).whenComplete((unused, throwable) -> {
            if (throwable != null) {
                handleThrowable(throwable);
            } else {
                callbackOnCompletion.run();
            }
        });

    }

    public void readMeasurement(final Consumer<Measurement> callbackOnCompletion) {
        asyncEmuModel.readMeasurement().whenComplete((measurement, throwable) -> {
            if (throwable != null) {
                handleThrowable(throwable);
            } else {
                callbackOnCompletion.accept(measurement);
            }
        });
    }

    private void handleThrowable(final Throwable throwable) {
        LOGGER.error("Error during async execution: ", throwable);
        errorHandler.accept(throwable);
    }
}
