package com.github.coerschkes.business.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MeasurementSeriesTest {

    @MethodSource(value = "measurementSeriesValueProvider")
    @ParameterizedTest
    public void constructor_should_set_values_correctly(int measurementSeriesId, String measurementSize) {
        final MeasurementSeries measurementSeries = new MeasurementSeries(measurementSeriesId, 20, "LED", measurementSize);
        assertEquals(measurementSeriesId, measurementSeries.getMeasurementSeriesId());
        assertEquals(20, measurementSeries.getTimeMillis());
        assertEquals("LED", measurementSeries.getConsumer());
        assertEquals(measurementSize, measurementSeries.getMeasurementSize());
    }

    @Test
    public void constructor_with_minimal_configuration_should_set_values_correctly() {
        final MeasurementSeries measurementSeries = new MeasurementSeries(1, 20);
        assertEquals(1, measurementSeries.getMeasurementSeriesId());
        assertEquals(20, measurementSeries.getTimeMillis());
        assertEquals("DEFAULT", measurementSeries.getConsumer());
        assertEquals("Leistung", measurementSeries.getMeasurementSize());
    }

    @Test
    public void constructor_with_minimal_configuration_should_throw_IllegalArgument_Exception_if_timeMillis_too_small() {
        assertThrows(IllegalArgumentException.class, () -> new MeasurementSeries(1, 10), "Das Zeitintervall muss mindestens 15 Sekunden lang sein.");
    }

    @Test
    public void constructor_with_minimal_configuration_should_throw_IllegalArgument_Exception_if_timeMillis_too_big() {
        assertThrows(IllegalArgumentException.class, () -> new MeasurementSeries(1, 3700), "Das Zeitintervall darf hoechstens 3600 Sekunden lang sein.");
    }

    @Test
    public void testMeasurementsStringRepresentation() {
        final MeasurementSeries measurementSeries = Mockito.mock(MeasurementSeries.class);
        Mockito.when(measurementSeries.getMeasurements()).thenReturn(new Measurement[]{buildMeasurement(1.0, 0), buildMeasurement(4.0, 3000)});
        final Measurement[] measurements = measurementSeries.getMeasurements();

        final MeasurementSeries measurementSeriesUnderTest = new MeasurementSeries(0, 0, "LED", "Leistung");
        measurementSeriesUnderTest.setMeasurements(measurements);

        assertEquals(measurements[0].getMeasurementValue() + " " + measurements[0].getTimeMillis() + "/" +
                measurements[1].getMeasurementValue() + " " + measurements[1].getTimeMillis(), measurementSeriesUnderTest.measurementsStringRepresentation());
    }

    private static Stream<Arguments> measurementSeriesValueProvider() {
        return Stream.of(
                Arguments.of(1, "Leistung"),
                Arguments.of(2, "Arbeit")
        );
    }

    private Measurement buildMeasurement(double value, long timeMillis) {
        return new Measurement(0, 0, value, timeMillis);
    }

}