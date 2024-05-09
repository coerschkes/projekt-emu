package business.db;

import business.model.Measurement;
import business.model.MeasurementSeries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

class ResultTransformer {
    static Measurement[] toMeasurements(final ResultSet resultSet) {
        try {
            final Vector<Measurement> measurements = new Vector<>();
            while (resultSet.next()) {
                measurements.add(measurementFrom(resultSet));
            }
            resultSet.close();
            return measurements.toArray(new Measurement[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static MeasurementSeries[] toMeasurementSeries(final ResultSet resultSet) {
        try {
            ArrayList<MeasurementSeries> allMeasurementSeries = new ArrayList<>();
            while (resultSet.next()) {
                allMeasurementSeries.add(measurementSeriesFrom(resultSet));
            }
            resultSet.close();
            return allMeasurementSeries.toArray(new MeasurementSeries[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Measurement measurementFrom(final ResultSet resultSet) throws SQLException {
        return new Measurement(resultSet.getInt(1), resultSet.getDouble(2), resultSet.getLong(3));
    }

    private static MeasurementSeries measurementSeriesFrom(final ResultSet resultSet) throws SQLException {
        return new MeasurementSeries(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4));
    }
}
