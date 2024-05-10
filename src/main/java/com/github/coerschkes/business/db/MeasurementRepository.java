package com.github.coerschkes.business.db;

import com.github.coerschkes.business.model.Measurement;
import com.github.coerschkes.business.model.MeasurementSeries;

import java.sql.SQLException;

interface MeasurementRepository {
    /**
     * Reads all {@link Measurement} from a {@link MeasurementSeries} from the database.
     *
     * @param measurementSeriesId - The id of the measurement series to read
     * @return - an array of {@link Measurement}
     * @throws SQLException           - In case sql problems occur during database interaction
     * @throws ClassNotFoundException - In case any problems with the sql driver occur
     */
    Measurement[] readMeasurementsFromSeries(final int measurementSeriesId) throws SQLException, ClassNotFoundException;

    /**
     * Reads all {@link MeasurementSeries} including its {@link Measurement} from the database.
     *
     * @return - an array of {@link MeasurementSeries}
     * @throws SQLException           - In case sql problems occur during database interaction
     * @throws ClassNotFoundException - In case any problems with the sql driver occur
     */
    MeasurementSeries[] readAllMeasurementSeries() throws SQLException, ClassNotFoundException;

    /**
     * Saves a {@link Measurement} with a connection to an existing {@link MeasurementSeries} to the database.
     *
     * @param measurementSeriesId - The id of an existing {@link MeasurementSeries}
     * @param measurement         - The {@link Measurement} to save to the database
     * @throws SQLException           - In case sql problems occur during database interaction
     * @throws ClassNotFoundException - In case any problems with the sql driver occur
     */
    void saveMeasurement(final int measurementSeriesId, final Measurement measurement) throws SQLException, ClassNotFoundException;

    /**
     * Saves a {@link MeasurementSeries} to the database.
     *
     * @param measurementSeries - The {@link MeasurementSeries} to save.
     * @throws SQLException           - In case sql problems occur during database interaction
     * @throws ClassNotFoundException - In case any problems with the sql driver occur
     */
    void saveMeasurementSeries(final MeasurementSeries measurementSeries) throws SQLException, ClassNotFoundException;
}
