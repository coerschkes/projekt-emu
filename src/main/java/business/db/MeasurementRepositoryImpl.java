package business.db;

import business.model.Measurement;
import business.model.MeasurementSeries;

import java.sql.ResultSet;
import java.sql.SQLException;

class MeasurementRepositoryImpl implements MeasurementRepository {
    private final MysqlConnector mysqlConnector;

    MeasurementRepositoryImpl() {
        mysqlConnector = new MysqlConnector();
    }

    @Override
    public Measurement[] readMeasurementsFromSeries(final int measurementSeriesId) throws SQLException, ClassNotFoundException {
        final ResultSet resultSet = this.mysqlConnector.executeQuery(QueryBuilder.selectMeasurementWithSeriesId(measurementSeriesId));
        return ResultTransformer.toMeasurements(resultSet);
    }

    @Override
    public MeasurementSeries[] readAllMeasurementSeries() throws SQLException, ClassNotFoundException {
        final ResultSet resultSet = this.mysqlConnector.executeQuery(QueryBuilder.selectAllMeasurementSeries());
        final MeasurementSeries[] allMeasurementSeries = ResultTransformer.toMeasurementSeries(resultSet);
        for (MeasurementSeries measurementSeries : allMeasurementSeries) {
            measurementSeries.setMeasurements(readMeasurementsFromSeries(measurementSeries.getMeasurementSeriesId()));
        }
        return allMeasurementSeries;
    }

    @Override
    public void saveMeasurement(final int measurementSeriesId, final Measurement measurement) throws SQLException, ClassNotFoundException {
        this.mysqlConnector.executeUpdate(QueryBuilder.insertIntoMeasurement(measurement, measurementSeriesId));
    }

    @Override
    public void saveMeasurementSeries(MeasurementSeries measurementSeries) throws SQLException, ClassNotFoundException {
        this.mysqlConnector.executeUpdate(QueryBuilder.insertIntoMeasurementSeries(measurementSeries));
    }
}
