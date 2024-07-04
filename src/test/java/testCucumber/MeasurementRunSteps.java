package testCucumber;

import com.github.coerschkes.business.db.AsyncDatabaseModel;
import com.github.coerschkes.business.model.Measurement;
import com.github.coerschkes.business.model.MeasurementSeries;
import com.github.coerschkes.business.util.RequestFailedException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class MeasurementRunSteps {
    private static final double STATIC_MEASUREMENT_VALUE = 2.4;
    static final int[] MEASUREMENT_SERIES_IDS = {1000, 1001, 1002};
    private MeasurementSeries[] allMeasurementSeries;
    private MeasurementSeries measurementSeries;
    private List<Exception> exceptionRecord;

    @Before
    public void setUp() {
        this.allMeasurementSeries = null;
        this.measurementSeries = null;
        this.exceptionRecord = new ArrayList<>();
        AsyncDatabaseModel.getInstance().saveMeasurementSeries(new MeasurementSeries(MEASUREMENT_SERIES_IDS[0], 20000, "LED", "Leistung")).join();
        AsyncDatabaseModel.getInstance().saveMeasurementSeries(new MeasurementSeries(MEASUREMENT_SERIES_IDS[1], 10000, "Laptop", "Leistung")).join();
        AsyncDatabaseModel.getInstance().saveMeasurementSeries(new MeasurementSeries(MEASUREMENT_SERIES_IDS[2], 5000, "Ladegerät", "Leistung")).join();
        AsyncDatabaseModel.getInstance().saveMeasurement(new Measurement(0, MEASUREMENT_SERIES_IDS[1], 3.0, System.currentTimeMillis())).join();
        AsyncDatabaseModel.getInstance().saveMeasurement(new Measurement(0, MEASUREMENT_SERIES_IDS[2], 3.0, System.currentTimeMillis())).join();
        AsyncDatabaseModel.getInstance().saveMeasurement(new Measurement(0, MEASUREMENT_SERIES_IDS[2], 3.0, System.currentTimeMillis() + 6000)).join();
    }

    @After
    public void tearDown() {
        for (int id : MEASUREMENT_SERIES_IDS) {
            AsyncDatabaseModel.getInstance().deleteMeasurementSeries(id).join();
        }
    }

    @Given("Messreihen ansehen")
    public void loadAllMeasurementSeries() {
        this.allMeasurementSeries = AsyncDatabaseModel.getInstance().readAllMeasurementSeries().join();
    }

    @Given("^Messreihe mit MessreihenId \"(\\d+)\" aussuchen$")
    public void loadMeasurementSeries(int id) {
        Arrays.stream(this.allMeasurementSeries)
                .filter(measurementSeries -> measurementSeries.getMeasurementSeriesId() == id)
                .findFirst()
                .ifPresent(measurementSeries -> this.measurementSeries = measurementSeries);
        if (this.measurementSeries == null) {
            throw new IllegalArgumentException("No measurement series with id " + id + " found");
        }
    }

    @When("^zur aktuellen Messreihe weitere Messung durchfuehren$")
    public void addMeasurement() {
        if (this.measurementSeries == null) {
            throw new IllegalArgumentException("No measurement series selected");
        }
        AtomicLong millisBefore = new AtomicLong(0);
        Arrays.stream(this.measurementSeries.getMeasurements())
                .max(Comparator.comparing(Measurement::getMeasurementId))
                .ifPresent(measurement -> millisBefore.set(measurement.getTimeMillis()));
        if (millisBefore.get() == 0) {
            AsyncDatabaseModel.getInstance().saveMeasurement(new Measurement(0, this.measurementSeries.getMeasurementSeriesId(), STATIC_MEASUREMENT_VALUE, System.currentTimeMillis())).join();
        } else {
            if (millisBefore.get() + measurementSeries.getTimeMillis() > System.currentTimeMillis()) {
                AsyncDatabaseModel.getInstance().saveMeasurement(new Measurement(0, this.measurementSeries.getMeasurementSeriesId(), STATIC_MEASUREMENT_VALUE, millisBefore.get() + measurementSeries.getTimeMillis())).join();
            } else {
                AsyncDatabaseModel.getInstance().saveMeasurement(new Measurement(0, this.measurementSeries.getMeasurementSeriesId(), STATIC_MEASUREMENT_VALUE, System.currentTimeMillis())).join();
            }
        }
    }

    @When("^zur aktuellen Messreihe zu schnell weitere Messung durchfuehren$")
    public void addMeasurementTooFast() {
        if (this.measurementSeries == null) {
            throw new IllegalArgumentException("No measurement series selected");
        }
        if (this.measurementSeries.getMeasurements().length == 0) {
            throw new IllegalArgumentException("No measurements in series");
        }
        AtomicLong millisBefore = new AtomicLong(0);
        Arrays.stream(this.measurementSeries.getMeasurements())
                .max(Comparator.comparing(Measurement::getMeasurementId))
                .ifPresent(measurement -> millisBefore.set(measurement.getTimeMillis()));
        try {
            AsyncDatabaseModel.getInstance().saveMeasurement(new Measurement(0, this.measurementSeries.getMeasurementSeriesId(), STATIC_MEASUREMENT_VALUE, millisBefore.get() + (measurementSeries.getTimeMillis() - 1))).join();
        } catch (Exception e) {
            this.exceptionRecord.add((Exception) e.getCause());
        }
    }

    @Then("^Es wurde eine Messung zur aktuellen Messreihe an Position \"(\\d+)\" gespeichert$")
    public void checkLastMeasurementPosition(int position) {
        if (this.measurementSeries == null) {
            throw new IllegalArgumentException("No measurement series selected");
        }
        this.loadAllMeasurementSeries();
        this.loadMeasurementSeries(this.measurementSeries.getMeasurementSeriesId());
        Assert.assertThat(this.measurementSeries.getMeasurements().length, Matchers.equalTo(position));
    }

    @Then("^Exception zum Zeitintervall bei der Anlage einer Messung zur aktuellen Messreihe erhalten$")
    public void checkErrorOnAddMeasurement() {
        if (this.measurementSeries == null) {
            throw new IllegalArgumentException("No measurement series selected");
        }
        final Optional<Exception> foundException = this.exceptionRecord.stream().filter(e -> e instanceof RequestFailedException).findAny();
        if (!foundException.isPresent()) {
            Assert.fail("No exception found");
        } else {
            assertFalse(false);
        }
    }

}
