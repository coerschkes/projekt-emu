package com.github.coerschkes.business.model;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MeasurementTest {

    @ParameterizedTest
    @ValueSource(doubles = {0, 1.0, 1.3945, 0.0})
    public void concatAttributes_should_return_measurement_set_in_constructor(double value) {
        final Measurement measurement = new Measurement(0, 0, value, 0);
        assertEquals("0: " + value, measurement.concatAttributes());
    }


}