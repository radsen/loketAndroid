package com.kzlabs.loket.events;

import com.kzlabs.loket.models.Pocket;

/**
 * Created by radsen on 10/20/16.
 */
public class PocketPutEvent {
    private Pocket pocket;
    private String message;

    public PocketPutEvent(String message) {
        this.message = message;
    }

    public PocketPutEvent(Pocket pocket) {
        this.pocket = pocket;
    }

    public Pocket getPocket() {
        return pocket;
    }

    public String getMessage() {
        return message;
    }
}
