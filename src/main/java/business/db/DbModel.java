package business.db;

import business.Measurement;
import business.MeasurementSeries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;

public final class DbModel {
    private final DatabaseActions databaseActions = new DatabaseActions();
    private final ObservableList<MeasurementSeries> measurementSeriesList = FXCollections.emptyObservableList();
    private static DbModel basisModel;

    public static DbModel getInstance() {
        if (basisModel == null) {
            basisModel = new DbModel();
        }
        return basisModel;
    }

    private DbModel() {
    }

    public Measurement[] readMeasurementsFromDb(final int measurementSeriesId) {
        return this.databaseActions.readMeasurement(measurementSeriesId);
    }

    public void saveMeasurement(final int measurementSeriesId, final Measurement measurement) {
        this.databaseActions.addMeasurement(measurementSeriesId, measurement);
    }

    public void readAllMeasurementSeries() {
        MeasurementSeries[] allMeasurementSeries
                = this.databaseActions.readAllMeasurementSeries();
        int count = this.measurementSeriesList.size();
        if (count > 0) {
            this.measurementSeriesList.subList(0, count).clear();
        }
        this.measurementSeriesList.addAll(Arrays.asList(allMeasurementSeries));
    }

    public void saveMeasurementSeries(final MeasurementSeries measurementSeries) {
        this.databaseActions.addMeasurementSeries(measurementSeries);
        this.measurementSeriesList.add(measurementSeries);
    }
}
