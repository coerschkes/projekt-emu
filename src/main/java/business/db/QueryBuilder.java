package business.db;

import business.Measurement;
import business.MeasurementSeries;

public class QueryBuilder {
    private static final String QUERY_SELECT_MEASUREMENT_WITH_SERIES_ID = "SELECT * FROM measurement WHERE measurementSeriesId = %s";
    private static final String QUERY_INSERT_INTO_MEASUREMENT = "INSERT INTO measurement (measurementId, measurementValue, timeMillis, measurementSeriesId) VALUES(%s, %s, %s, %s)";
    private static final String QUERY_SELECT_ALL_MEASUREMENT = "SELECT * FROM measurement";
    private static final String QUERY_INSERT_INTO_MEASUREMENT_SERIES = "INSERT INTO measurementSeries (measurementSeriesId, timeInterval, consumer, measurementSize) VALUES(%s, %s, %s, %s)";


    static String buildSelectMeasurementWithSeriesId(final int measurementSeriesId) {
        return String.format(QUERY_SELECT_MEASUREMENT_WITH_SERIES_ID, measurementSeriesId);
    }

    static String buildInsertIntoMeasurement(final Measurement measurement, final int measurementSeriesId) {
        return String.format(QUERY_INSERT_INTO_MEASUREMENT, measurement.getMeasurementId(), measurement.getMeasurementValue(), measurement.getTimeMillis(), measurementSeriesId);
    }

    static String buildSelectAllMeasurements() {
        return QUERY_SELECT_ALL_MEASUREMENT;
    }

    static String buildInsertIntoMeasurementSeries(final MeasurementSeries measurementSeries) {
        return String.format(QUERY_INSERT_INTO_MEASUREMENT_SERIES, measurementSeries.getMeasurementSeriesId(), measurementSeries.getTimeMillis(), measurementSeries.getConsumer(), measurementSeries.getMeasurementSize());
    }
}
