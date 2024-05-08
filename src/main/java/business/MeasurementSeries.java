package business;


public class MeasurementSeries {
	
	private int measurementSeriesId;
	private int timeMillis;
	private String consumer;
	private String measurementSize;
	private Measurement[] measurements;
	
	public MeasurementSeries(int measurementSeriesId, int timeMillis,
							 String consumer, String measurementSize) {
		super();
		this.measurementSeriesId = measurementSeriesId;
		this.timeMillis = timeMillis;
		this.consumer = consumer;
		this.measurementSize = measurementSize;
	}

	public int getMeasurementSeriesId() {
		return measurementSeriesId;
	}
	
	public void setMeasurementSeriesId(int measurementSeriesId) {
		this.measurementSeriesId = measurementSeriesId;
	}
	
	public int getTimeMillis() {
		return timeMillis;
	}
	
	public void setTimeMillis(int timeMillis) {
		this.timeMillis = timeMillis;
	}
	public String getConsumer() {
		return consumer;
	}
	
	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}
	
	public String getMeasurementSize() {
		return measurementSize;
	}
	public void setMeasurementSize(String measurementSize) {
		this.measurementSize = measurementSize;
	}
	
	public Measurement[] getMeasurements() {
		return measurements;
	}
	
	public void setMeasurements(Measurement[] measurements) {
		this.measurements = measurements;
	}
	
	public String gibAttributeAus(){
		return (this.measurementSeriesId + " "
		    + this.timeMillis + " " + this.consumer + " "
			+ this.measurementSize);
	}

}
