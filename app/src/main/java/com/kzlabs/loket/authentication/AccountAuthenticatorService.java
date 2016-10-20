package com.kzlabs.loket.authentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by radsen on 10/19/16.
 */

public class AccountAuthenticatorService extends Service {
    private static AccountAuthenticator AUTHENTICATOR = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private AccountAuthenticator getAuthenticator() {
        if(AUTHENTICATOR == null){
            AUTHENTICATOR = new AccountAuthenticator(this);
        }

        return AUTHENTICATOR;
    }
}
