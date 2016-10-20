package com.kzlabs.loket.events;

import com.kzlabs.loket.models.Pocket;

/**
 * Created by radsen on 10/20/16.
 */
public class PocketPostEvent {
    private Pocket pocket;
    private String message;

    public PocketPostEvent(Pocket pocket) {
        this.pocket = pocket;
    }

    public PocketPostEvent(String message) {
        this.message = message;
    }

    public Pocket getPocket() {
        return pocket;
    }

    public String getMessage() {
        return message;
    }
}
