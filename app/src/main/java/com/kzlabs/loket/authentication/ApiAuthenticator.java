package com.kzlabs.loket.authentication;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.support.test.espresso.core.deps.guava.net.HttpHeaders;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by radsen on 10/19/16.
 */

public class ApiAuthenticator implements Authenticator {

    private static final String TAG = ApiAuthenticator.class.getSimpleName();

    private final Application application;
    private final AccountManager accountManager;

    @Inject
    public ApiAuthenticator(Application application, AccountManager accountManager) {
        this.application = application;
        this.accountManager = accountManager;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        Log.d(TAG, "authenticate");

        Request request = null;

        if (ActivityCompat.checkSelfPermission(application, Manifest.permission.GET_ACCOUNTS) !=
                PackageManager.PERMISSION_GRANTED) {

            Account[] accounts = accountManager.getAccountsByType(AuthenticationConstants.ACCOUNT_TYPE);

            if(accounts.length != 0){
                String token = accountManager.peekAuthToken(accounts[0],
                        AuthenticationConstants.ACCOUNT_TYPE);
                if(token != null){
                    request = response.request().newBuilder()
                            .addHeader(HttpHeaders.ACCEPT, "application/json")
                            .addHeader(HttpHeaders.AUTHORIZATION, token)
                            .build();
                }
            }
        }

        return request;
    }


}
