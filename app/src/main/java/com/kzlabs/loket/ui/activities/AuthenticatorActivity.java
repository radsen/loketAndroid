package com.kzlabs.loket.ui.activities;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.kzlabs.loket.authentication.AuthConstants;
import com.kzlabs.loket.base.BaseActivity;
import com.kzlabs.loket.interfaces.LoketInterface;

/**
 * Created by radsen on 10/19/16.
 */
public class AuthenticatorActivity extends BaseActivity {
    private boolean loggedIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AccountManager accountManager = AccountManager.get(this);

        Account[] accounts = accountManager.getAccountsByType(AuthConstants.ACCOUNT_TYPE);
        if (accounts.length == 0) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);

            finish();
        } else {
            loggedIn = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
