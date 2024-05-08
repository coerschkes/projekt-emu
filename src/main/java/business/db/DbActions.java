package business.db;

import business.Measurement;
import business.MeasurementSeries;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

@SuppressWarnings("SqlSourceToSinkFlow")
public class DbActions {
    Statement statement;
    Connection con;

    public Measurement[] readMeasurement(int measurementSeriesId)
            throws SQLException {
        ResultSet result;
        result = this.statement.executeQuery(QueryBuilder.buildSelectMeasurementWithSeriesId(measurementSeriesId));
        Vector<Measurement> measurements = new Vector<Measurement>();
        while (result.next()) {
            measurements.add(
                    new Measurement(
                            Integer.parseInt(result.getString(1)),
                            Double.parseDouble(result.getString(2)),
                            Long.parseLong(result.getString(3))));
            ;
        }
        result.close();
        return measurements.toArray(new Measurement[0]);
    }

    public void addMeasurement(int measurementSeriesId, Measurement measurement)
            throws SQLException {
        String insertMeasurementStatement = String.format(QueryBuilder.buildInsertIntoMeasurement(measurement, measurementSeriesId));
        System.out.println(insertMeasurementStatement);
        this.statement.executeUpdate(insertMeasurementStatement);
    }

    public MeasurementSeries[] readAllMeasurementSeries()
            throws SQLException {
        ResultSet result;
        result = this.statement.executeQuery(QueryBuilder.buildSelectAllMeasurements());
        ArrayList<MeasurementSeries> allMeasurementSeries = new ArrayList<MeasurementSeries>();
        while (result.next()) {
            allMeasurementSeries.add(
                    new MeasurementSeries(Integer.parseInt(result.getString(1)),
                            Integer.parseInt(result.getString(2)),
                            result.getString(3), result.getString(4)));
        }
        for (int i = 0; i < allMeasurementSeries.size(); i++) {
            allMeasurementSeries.get(i).setMeasurements(
                    this.readMeasurement(allMeasurementSeries.get(i).getMeasurementSeriesId()));
        }
        result.close();
        return allMeasurementSeries.toArray(new MeasurementSeries[0]);
    }

    public void addMeasurementSeries(MeasurementSeries measurementSeries)
            throws SQLException {
        String insertMeasurementSeriesStatement = QueryBuilder.buildInsertIntoMeasurementSeries(measurementSeries);
        System.out.println(insertMeasurementSeriesStatement);
        this.statement.executeUpdate(insertMeasurementSeriesStatement);
    }


    public void connectDb()
            throws ClassNotFoundException, SQLException {
//		todo: add right dependency here
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/emu?"
                        + "zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC",
                "root", "root");
        this.statement = this.con.createStatement();
    }

    public void closeDb()
            throws SQLException {
        this.con.close();
    }
}    

