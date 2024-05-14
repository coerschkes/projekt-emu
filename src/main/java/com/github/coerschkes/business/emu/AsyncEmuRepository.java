package com.github.coerschkes.business.emu;

import com.github.coerschkes.business.model.Measurement;

import java.util.concurrent.CompletableFuture;

public interface AsyncEmuRepository {

    /**
     * Returns a new read {@link Measurement}
     *
     * @return - {@link Measurement}
     */
    CompletableFuture<Measurement> readMeasurement();
}
