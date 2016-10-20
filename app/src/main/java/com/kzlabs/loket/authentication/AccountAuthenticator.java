package com.kzlabs.loket.authentication;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kzlabs.loket.LoketApplication;
import com.kzlabs.loket.interfaces.LoketApi;
import com.kzlabs.loket.ui.activities.AuthenticatorActivity;

import javax.inject.Inject;

/**
 * Created by radsen on 10/19/16.
 */

public class AccountAuthenticator extends AbstractAccountAuthenticator {

    private static final String TAG = AccountAuthenticator.class.getSimpleName();

    private final Context context;

    @Inject
    LoketApi apiService;

    public AccountAuthenticator(Context context) {
        super(context);

        this.context = context;
        ((LoketApplication)context.getApplicationContext()).getLoketComponent().inject(this);
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType,
                             String authTokenType, String[] strings, Bundle options) throws NetworkErrorException {
        final Intent intent = new Intent(context, AuthenticatorActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
        //intent.putExtra(LoginFragment.PARAM_AUTHTOKEN_TYPE, authTokenType);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account,
                               String authTokenType, Bundle options) throws NetworkErrorException {
        final Bundle bundle = new Bundle();

        // If the caller requested an authToken type we don't support, then
        // return an error
        if (!authTokenType.equals("")) {
            Log.d(TAG, "invalid authTokenType" + authTokenType);
            bundle.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
            return bundle;
        }

        // Otherwise... start the login intent
        Log.i(TAG, "Starting login activity");
        final Intent intent = new Intent(context, AuthenticatorActivity.class);
        //intent.putExtra(LoginFragment.PARAM_USERNAME, account.name);
        //intent.putExtra(LoginFragment.PARAM_AUTHTOKEN_TYPE, authTokenType);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return authTokenType.equals("") ? authTokenType : null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse,
                                    Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse,
                              Account account, String[] strings) throws NetworkErrorException {
        return null;
    }
}
