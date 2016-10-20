package com.kzlabs.loket.events;

import com.kzlabs.loket.models.DataWrapper;
import com.kzlabs.loket.models.Pocket;

import java.util.List;

/**
 * Created by radsen on 10/19/16.
 */
public class PocketEvent {

    private final DataWrapper wrapper;

    public PocketEvent(DataWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public List<Pocket> getPockets(){
        return wrapper.getPockets();
    }

    public String errorMessage(){
        return wrapper.getErrorMessage();
    }
}
