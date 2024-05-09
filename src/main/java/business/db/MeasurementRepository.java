package business.db;

import business.model.Measurement;
import business.model.MeasurementSeries;

import java.sql.SQLException;

interface MeasurementRepository {
    Measurement[] readMeasurementsFromSeries(final int measurementSeriesId) throws SQLException, ClassNotFoundException;

    MeasurementSeries[] readAllMeasurementSeries() throws SQLException, ClassNotFoundException;

    void saveMeasurement(final int measurementSeriesId, final Measurement measurement) throws SQLException, ClassNotFoundException;

    void saveMeasurementSeries(final MeasurementSeries measurementSeries) throws SQLException, ClassNotFoundException;
}
