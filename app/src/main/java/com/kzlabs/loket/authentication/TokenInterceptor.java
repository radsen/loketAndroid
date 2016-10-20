package com.kzlabs.loket.authentication;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.support.test.espresso.core.deps.guava.net.HttpHeaders;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by radsen on 10/19/16.
 */

public class TokenInterceptor implements Interceptor {
    private static final String TAG = TokenInterceptor.class.getSimpleName();
    private String token;

    @Inject
    public TokenInterceptor(Application application, AccountManager manager) {
        Account[] account = manager.getAccountsByType(AuthConstants.ACCOUNT_TYPE);
        if(account != null && account.length > 0){
            token = manager.peekAuthToken(account[0], AuthConstants.ACCOUNT_TYPE);
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d(TAG, "intercept");
        Request originalRequest = chain.request();

        // Nothing to add to intercepted request if:
        // a) Authorization value is empty because user is not logged in yet
        // b) There is already a header with updated Authorization value
        if (authorizationTokenIsEmpty() || alreadyHasAuthorizationHeader(originalRequest)) {
            Log.d(TAG, "No Inject Authorization");
            return chain.proceed(originalRequest);
        }

        Log.d(TAG, "Inject Authorization");
        // Add authorization header with updated authorization value to  intercepted request
        Request authorisedRequest = originalRequest.newBuilder()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
        return chain.proceed(authorisedRequest);
    }

    private boolean alreadyHasAuthorizationHeader(Request originalRequest) {
        return !TextUtils.isEmpty(originalRequest.header(HttpHeaders.AUTHORIZATION));
    }

    private boolean authorizationTokenIsEmpty() {
        return TextUtils.isEmpty(token);
    }
}
