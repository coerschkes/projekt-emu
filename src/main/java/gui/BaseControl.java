package gui;

import business.*;
import business.db.DbModel;

import java.sql.SQLException;
import javafx.stage.Stage;

public class BaseControl {

	private DbModel dbModel;
	private BaseView baseView;
	  
	public BaseControl(Stage primaryStage){
		this.dbModel = DbModel.getInstance();
		this.baseView = new BaseView(this, primaryStage, this.dbModel);
		primaryStage.show();
	}
		
	public Measurement[] readMeasurements(String measurementSeriesId){
		Measurement[] result = null;
		int parsedMeasurementSeriesId = -1;
		try{
 			parsedMeasurementSeriesId = Integer.parseInt(measurementSeriesId);
 		}
 		catch(NumberFormatException nfExc){
 			baseView.showErrorMessage(
 				"Das Format der eingegebenen MessreihenId ist nicht korrekt.");
 		}
 		try{
 			result = this.dbModel.readMeasurementsFromDb(parsedMeasurementSeriesId);
 		}
 		catch(ClassNotFoundException cnfExc){
 			baseView.showErrorMessage(
 				"Fehler bei der Verbindungerstellung zur Datenbank.");
 		}
 		catch(SQLException sqlExc){
 			baseView.showErrorMessage(
 				"Fehler beim Zugriff auf die Datenbank.");
 			sqlExc.printStackTrace();
 		}
 		return result;
	} 
 	  
  	private void saveMeasurement(int measurementSeriesId, Measurement measurement){
 		try{
 			this.dbModel.saveMeasurement(measurementSeriesId, measurement);
 		}
 		catch(ClassNotFoundException cnfExc){
 			baseView.showErrorMessage(
 				"Fehler bei der Verbindungerstellung zur Datenbank.");
 		}
 		catch(SQLException sqlExc){
 			baseView.showErrorMessage(
 				"Fehler beim Zugriff auf die Datenbank.");
 		}
	} 
  	
	public Measurement readMeasurementFromEmu(String measurementSeriesId, String measurementId){
 		Measurement result = null;
		int parsedMeasurementSeriesId = Integer.parseInt(measurementSeriesId);
		int lfdNr = Integer.parseInt(measurementId);

		// Dummy-Messung-Objekt, muss ersetzt werden !!!
 		result = new Measurement(lfdNr, 0.345, System.currentTimeMillis());
 		
 		this.saveMeasurement(parsedMeasurementSeriesId, result);
 		return result;
 	}

}
