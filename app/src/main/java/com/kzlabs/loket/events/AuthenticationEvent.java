package com.kzlabs.loket.events;

import com.kzlabs.loket.models.User;

/**
 * Created by radsen on 10/18/16.
 */
public class AuthenticationEvent {

    private final User authentication;

    public AuthenticationEvent(User authentication){
        this.authentication = authentication;
    }

    public User getUser() {
        return authentication;
    }
}
