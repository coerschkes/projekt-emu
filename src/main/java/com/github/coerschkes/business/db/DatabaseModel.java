package com.github.coerschkes.business.db;

import com.github.coerschkes.business.model.Measurement;
import com.github.coerschkes.business.model.MeasurementSeries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Arrays;

public final class DatabaseModel {
    private static DatabaseModel modelInstance;
    private final MeasurementRepository repository;
    private final ObservableList<MeasurementSeries> measurementSeriesList = FXCollections.emptyObservableList();

    public static DatabaseModel getInstance() {
        if (modelInstance == null) {
            modelInstance = new DatabaseModel();
        }
        return modelInstance;
    }

    private DatabaseModel() {
        repository = new MeasurementRepositoryImpl();
    }

    public Measurement[] readMeasurementsFromDb(final int measurementSeriesId) throws SQLException, ClassNotFoundException {
        return repository.readMeasurementsFromSeries(measurementSeriesId);
    }

    public void saveMeasurement(final int measurementSeriesId, final Measurement measurement) throws SQLException, ClassNotFoundException {
        repository.saveMeasurement(measurementSeriesId, measurement);
    }

    public void readAllMeasurementSeries() throws SQLException, ClassNotFoundException {
        final MeasurementSeries[] allMeasurementSeries = repository.readAllMeasurementSeries();
        int count = this.measurementSeriesList.size();
        if (count > 0) {
            this.measurementSeriesList.subList(0, count).clear();
        }
        this.measurementSeriesList.addAll(Arrays.asList(allMeasurementSeries));
    }

    public void saveMeasurementSeries(final MeasurementSeries measurementSeries) throws SQLException, ClassNotFoundException {
        this.repository.saveMeasurementSeries(measurementSeries);
        this.measurementSeriesList.add(measurementSeries);
    }
}
