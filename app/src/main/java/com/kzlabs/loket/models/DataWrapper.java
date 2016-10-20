package com.kzlabs.loket.models;

import java.util.List;

/**
 * Created by radsen on 10/19/16.
 */
public class DataWrapper {
    private final String errorMessage;
    private List<Pocket> pockets;

    public DataWrapper(String message) {
        this.errorMessage = message;
    }

    public List<Pocket> getPockets() {
        return pockets;
    }

    public void setPockets(List<Pocket> pockets) {
        this.pockets = pockets;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
