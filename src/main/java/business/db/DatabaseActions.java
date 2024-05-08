package business.db;

import business.Measurement;
import business.MeasurementSeries;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;

public class DatabaseActions {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseActions.class);
    private final DatabaseConnection connection;

    public DatabaseActions() {
        connection = new DatabaseConnection();
    }

    public Measurement[] readMeasurement(int measurementSeriesId) {
        return this.executeQuery(QueryBuilder.buildSelectMeasurementWithSeriesId(measurementSeriesId),
                ResultTransformer::toMeasurements,
                "readMeasurement");
    }

    public void addMeasurement(int measurementSeriesId, Measurement measurement) {
        this.executeUpdate(QueryBuilder.buildInsertIntoMeasurement(measurement, measurementSeriesId), "addMeasurement");
    }

    public MeasurementSeries[] readAllMeasurementSeries() {
        return this.executeQuery(QueryBuilder.buildSelectAllMeasurements(),
                resultSet -> ResultTransformer.toMeasurementSeries(resultSet, series -> this.readMeasurement(series.getMeasurementSeriesId())),
                "readAllMeasurementSeries");
    }

    public void addMeasurementSeries(MeasurementSeries measurementSeries) {
        this.executeUpdate(QueryBuilder.buildInsertIntoMeasurementSeries(measurementSeries), "addMeasurementSeries");
    }

    private <T> T executeQuery(final String query, final Function<ResultSet, T> resultSetTransformer, final String method) {
        this.connectDatabase();
        try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery(query);
            return resultSetTransformer.apply(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Exception in " + method, e);
            throw new RuntimeException(e);
        } finally {
            this.connection.closeConnection();
        }
    }

    private void executeUpdate(final String query, final String method) {
        this.connectDatabase();
        try (final Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            LOGGER.error("Exception in " + method, e);
            throw new RuntimeException(e);
        } finally {
            this.connection.closeConnection();
        }
    }

    private void connectDatabase() {
        try {
            this.connection.connect();
        } catch (SQLException e) {
            LOGGER.error("Exception in addMeasurementSeries", e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Exception in loading the sql driver class", e);
            throw new RuntimeException(e);
        }
    }
}

