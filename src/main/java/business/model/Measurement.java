package business.model;

public class Measurement {

    private final int measurementId;
    private final double measurementValue;
    private final long timeMillis;

    public Measurement(final int measurementId, final double measurementValue, final long timeMillis) {
        super();
        this.measurementId = measurementId;
        this.measurementValue = measurementValue;
        this.timeMillis = timeMillis;
    }

    public int getMeasurementId() {
        return measurementId;
    }


    public double getMeasurementValue() {
        return measurementValue;
    }


    public String getAttributes() {
        return this.measurementId + ": " + this.measurementValue;
    }

    public long getTimeMillis() {
        return timeMillis;
    }
}
