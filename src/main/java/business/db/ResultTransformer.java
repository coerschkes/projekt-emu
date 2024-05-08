package business.db;

import business.Measurement;
import business.MeasurementSeries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.function.Function;

//todo: refactor
public class ResultTransformer {
    public static Measurement[] toMeasurements(final ResultSet resultSet) {
        try {
            final Vector<Measurement> measurements = new Vector<Measurement>();
            while (resultSet.next()) {
                measurements.add(
                        new Measurement(
                                Integer.parseInt(resultSet.getString(1)),
                                Double.parseDouble(resultSet.getString(2)),
                                Long.parseLong(resultSet.getString(3))));
            }
            resultSet.close();
            return measurements.toArray(new Measurement[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static MeasurementSeries[] toMeasurementSeries(final ResultSet resultSet, final Function<MeasurementSeries, Measurement[]> callback) {
        try {
            ArrayList<MeasurementSeries> allMeasurementSeries = new ArrayList<MeasurementSeries>();
            while (resultSet.next()) {
                allMeasurementSeries.add(
                        new MeasurementSeries(Integer.parseInt(resultSet.getString(1)),
                                Integer.parseInt(resultSet.getString(2)),
                                resultSet.getString(3), resultSet.getString(4)));
            }
            for (MeasurementSeries measurementSeries : allMeasurementSeries) {
                measurementSeries.setMeasurements(callback.apply(measurementSeries));
            }
            resultSet.close();
            return allMeasurementSeries.toArray(new MeasurementSeries[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
