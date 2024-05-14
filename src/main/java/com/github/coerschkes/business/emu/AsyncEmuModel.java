package com.github.coerschkes.business.emu;

import com.github.coerschkes.business.model.Measurement;

import java.util.concurrent.CompletableFuture;

public class AsyncEmuModel {
    private static AsyncEmuModel modelInstance;
    private final AsyncEmuRepository repository;

    public static AsyncEmuModel getInstance() {
        if (modelInstance == null) {
            modelInstance = new AsyncEmuModel();
        }
        return modelInstance;
    }

    private AsyncEmuModel() {
        repository = new AsyncEmuRemoteRepository();
    }

    public CompletableFuture<Measurement> readMeasurement() {
        return this.repository.readMeasurement();
    }
}
