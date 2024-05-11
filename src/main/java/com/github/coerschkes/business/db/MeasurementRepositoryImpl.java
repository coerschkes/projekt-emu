package com.github.coerschkes.business.db;

import com.github.coerschkes.business.model.Measurement;
import com.github.coerschkes.business.model.MeasurementSeries;

import java.sql.SQLException;

class MeasurementRepositoryImpl implements MeasurementRepository {
    private final MysqlConnector mysqlConnector;

    MeasurementRepositoryImpl() {
        mysqlConnector = new MysqlConnector();
    }

    @Override
    public Measurement[] readMeasurementsFromSeries(final int measurementSeriesId) throws SQLException, ClassNotFoundException {
        return this.mysqlConnector.executeQuery(QueryBuilder.selectMeasurementWithSeriesId(measurementSeriesId), ResultTransformer::toMeasurements);
    }

    @Override
    public MeasurementSeries[] readAllMeasurementSeries() throws SQLException, ClassNotFoundException {
        final MeasurementSeries[] allMeasurementSeries = this.mysqlConnector.executeQuery(QueryBuilder.selectAllMeasurementSeries(), ResultTransformer::toMeasurementSeries);
        for (MeasurementSeries measurementSeries : allMeasurementSeries) {
            measurementSeries.setMeasurements(readMeasurementsFromSeries(measurementSeries.getMeasurementSeriesId()));
        }
        return allMeasurementSeries;
    }

    @Override
    public void saveMeasurement(final int measurementSeriesId, final Measurement measurement) throws SQLException, ClassNotFoundException {
        System.out.println("Executing update on db: " + QueryBuilder.insertIntoMeasurement(measurement, measurementSeriesId));
        this.mysqlConnector.executeUpdate(QueryBuilder.insertIntoMeasurement(measurement, measurementSeriesId));
    }

    @Override
    public void saveMeasurementSeries(final MeasurementSeries measurementSeries) throws SQLException, ClassNotFoundException {
        this.mysqlConnector.executeUpdate(QueryBuilder.insertIntoMeasurementSeries(measurementSeries));
    }
}
