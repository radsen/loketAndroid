package com.kzlabs.loket.ui.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.birbit.android.jobqueue.JobManager;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsOAuthSigning;
import com.digits.sdk.android.DigitsSession;
import com.kzlabs.loket.LoketApplication;
import com.kzlabs.loket.R;
import com.kzlabs.loket.authentication.AuthConstants;
import com.kzlabs.loket.base.BaseActivity;
import com.kzlabs.loket.events.AuthenticationEvent;
import com.kzlabs.loket.interfaces.LoketInterface;
import com.kzlabs.loket.jobs.LoginJob;
import com.kzlabs.loket.views.LoginView;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;

import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

    public static final String TAG = LoginActivity.class.getSimpleName();

//    @BindView(R.id.btn_login)
    DigitsAuthButton loginDab;
    @BindView(R.id.pb_progress)
    ProgressBar progressBar;
    @BindView(R.id.ll_login)
    View loginView;

    @Inject
    JobManager jobManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoketApplication.getInstance().getLoketComponent().inject(this);
        ButterKnife.bind(this);

        loginDab = (DigitsAuthButton) findViewById(R.id.btn_login);
        loginDab.setCallback((AuthCallback) this);
        loginDab.setAuthTheme(android.R.style.Theme_Material);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void navigateToPockets() {
        Intent navigateToPocketsIntent = new Intent(this, PocketsActivity.class);
        startActivity(navigateToPocketsIntent);
        finish();
    }

    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        loginDab.setCallback((AuthCallback) this);
    }

    @Override
    public void success(DigitsSession session, String phoneNumber) {
        TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
        TwitterAuthToken authToken = session.getAuthToken();
        DigitsOAuthSigning oauthSigning = new DigitsOAuthSigning(authConfig, authToken);
        Map<String, String> authHeaders = oauthSigning.getOAuthEchoHeadersForVerifyCredentials();
        jobManager.addJobInBackground(new LoginJob(authHeaders.get(AuthConstants.PROVIDER),
            authHeaders.get(AuthConstants.CREDENTIALS)));
    }

    @Override
    public void failure(DigitsException error) {
        Snackbar.make(loginView, getResources().getString(R.string.error_auth),
                      Snackbar.LENGTH_LONG).show();
    }

    @Subscribe
    public void onEvent(AuthenticationEvent event){
        Log.d(TAG, "Reaching this point");

        AccountManager accountManager = AccountManager.get(this);
        Account account = new Account(AuthConstants.ACCOUNT_NAME,
            AuthConstants.ACCOUNT_TYPE);
        accountManager.addAccountExplicitly(account, event.getUser().getToken(), null);
        accountManager.setAuthToken(account, AuthConstants.ACCOUNT_TYPE,
            event.getUser().getToken());

        Intent intent = new Intent(this, PocketsActivity.class);
        intent.putExtra(LoketInterface.USER_KEY, event.getUser());
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
