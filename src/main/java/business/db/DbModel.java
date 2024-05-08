package business.db;

import javafx.collections.*;

import java.sql.*;

import business.MeasurementSeries;
import business.Measurement;

public final class DbModel {
	
	private static DbModel basisModel;
	
	public static DbModel getInstance(){
		if (basisModel == null){
			basisModel = new DbModel();
		}
		return basisModel;
	}
	
	private DbModel(){		
	}
	
	private DbActions dbActions = new DbActions();
	
	// wird zukuenftig noch instanziiert
	private ObservableList<MeasurementSeries> measurementSeriesList = null;
	
	public Measurement[] readMeasurementsFromDb(int measurementSeriesId)
		throws ClassNotFoundException, SQLException{
		Measurement[] result = null;
		this.dbActions.connectDb();
		result = this.dbActions.readMeasurement(measurementSeriesId);
		this.dbActions.closeDb();
		return result;
	} 
	
	public void saveMeasurement(int measurementSeriesId, Measurement measurement)
		throws ClassNotFoundException, SQLException{
		this.dbActions.connectDb();
		this.dbActions.addMeasurement(measurementSeriesId, measurement);
		this.dbActions.closeDb();
	} 
	
	public void readAllMeasurementSeries()
		throws ClassNotFoundException, SQLException{
		this.dbActions.connectDb();
		MeasurementSeries[] allMeasurementSeries
		    = this.dbActions.readAllMeasurementSeries();
		this.dbActions.closeDb();
		int count = this.measurementSeriesList.size();
		for(int i = 0; i < count; i++){
		    this.measurementSeriesList.remove(0);
		}
		for(int i = 0; i < allMeasurementSeries.length; i++){
			this.measurementSeriesList.add(allMeasurementSeries[i]);
		} 
	}
		  
	public void saveMeasurementSeries(MeasurementSeries measurementSeries)
	  	throws ClassNotFoundException, SQLException{
	  	this.dbActions.connectDb();
	  	this.dbActions.addMeasurementSeries(measurementSeries);
	  	this.dbActions.closeDb();
	  	this.measurementSeriesList.add(measurementSeries);
	} 	
 
 }
