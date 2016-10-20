package com.kzlabs.loket.ui.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kzlabs.loket.LoketApplication;
import com.kzlabs.loket.R;
import com.kzlabs.loket.authentication.AuthenticationConstants;
import com.kzlabs.loket.base.BaseActivity;
import com.kzlabs.loket.events.AuthenticationEvent;
import com.kzlabs.loket.interfaces.LoketInterface;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

public class MainLoketActivity extends BaseActivity implements LoketInterface {

    private static final String TAG = MainLoketActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoketApplication.getInstance().getLoketComponent().inject(this);
        setContentView(R.layout.loket_main_activity);
    }

    @Subscribe
    public void onEvent(AuthenticationEvent event){
        Log.d(TAG, "Reaching this point");

        AccountManager accountManager = AccountManager.get(this);
        Account account = new Account(AuthenticationConstants.ACCOUNT_NAME,
                AuthenticationConstants.ACCOUNT_TYPE);
        accountManager.addAccountExplicitly(account, event.getUser().getToken(), null);
        accountManager.setAuthToken(account, AuthenticationConstants.ACCOUNT_TYPE,
                event.getUser().getToken());

        Intent intent = new Intent(this, PocketActivity.class);
        intent.putExtra(USER_KEY, event.getUser());
        startActivity(intent);
    }
}
