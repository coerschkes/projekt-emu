package business;

public class Measurement {
	
	private int measurementId;
	private double measurementValue;
	private long timeMillis;
	
	public Measurement(int measurementId, double measurementValue, long timeMillis) {
		super();
		this.measurementId = measurementId;
		this.measurementValue = measurementValue;
		this.timeMillis = timeMillis;
	}

	public int getMeasurementId() {
		return measurementId;
	}
	
	public void setMeasurementId(int measurementId) {
		this.measurementId = measurementId;
	} 
	
	public double getMeasurementValue() {
		return measurementValue;
	}
	
	public void setMeasurementValue(double measurementValue) {
		this.measurementValue = measurementValue;
	}
	
	public String getAttributes(){
		return this.measurementId + ": " + this.measurementValue;
	}

	public long getTimeMillis() {
		return timeMillis;
	}

	public void setTimeMillis(long timeMillis) {
		this.timeMillis = timeMillis;
	}
}
