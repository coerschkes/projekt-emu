package business;


public class MeasurementSeries {

    private final int measurementSeriesId;
    private final int timeMillis;
    private final String consumer;
    private final String measurementSize;
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

    public int getTimeMillis() {
        return timeMillis;
    }

    public String getConsumer() {
        return consumer;
    }

    public String getMeasurementSize() {
        return measurementSize;
    }

    public Measurement[] getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Measurement[] measurements) {
        this.measurements = measurements;
    }

    public String gibAttributeAus() {
        return (this.measurementSeriesId + " "
                + this.timeMillis + " " + this.consumer + " "
                + this.measurementSize);
    }

}
