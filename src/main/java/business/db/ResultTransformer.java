package business.db;

import business.Measurement;
import business.MeasurementSeries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

class ResultTransformer {
    static Measurement[] toMeasurements(final ResultSet resultSet) {
        try {
            final Vector<Measurement> measurements = new Vector<Measurement>();
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

    private static Measurement measurementFrom(ResultSet resultSet) throws SQLException {
        return new Measurement(
                Integer.parseInt(resultSet.getString(1)),
                Double.parseDouble(resultSet.getString(2)),
                Long.parseLong(resultSet.getString(3)));
    }

    private static MeasurementSeries measurementSeriesFrom(ResultSet resultSet) throws SQLException {
        return new MeasurementSeries(Integer.parseInt(resultSet.getString(1)),
                Integer.parseInt(resultSet.getString(2)),
                resultSet.getString(3), resultSet.getString(4));
    }
}
