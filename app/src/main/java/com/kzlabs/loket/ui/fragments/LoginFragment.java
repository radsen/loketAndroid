package com.kzlabs.loket.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.birbit.android.jobqueue.JobManager;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsOAuthSigning;
import com.digits.sdk.android.DigitsSession;
import com.kzlabs.loket.LoketApplication;
import com.kzlabs.loket.R;
import com.kzlabs.loket.base.BaseFragment;
import com.kzlabs.loket.jobs.LoginJob;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by radsen on 10/18/16.
 */

public class LoginFragment extends BaseFragment implements AuthCallback {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private static final String PROVIDER = "X-Auth-Service-Provider";
    private static final String CREDENTIALS = "X-Verify-Credentials-Authorization";

    private DigitsAuthButton digitsButton;

    @Inject
    JobManager jobManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoketApplication.getInstance().getLoketComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        digitsButton = (DigitsAuthButton) view.findViewById(R.id.auth_button);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        digitsButton.setCallback(this);
    }

    @Override
    public void success(DigitsSession session, String phoneNumber) {
        TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
        TwitterAuthToken authToken = session.getAuthToken();
        DigitsOAuthSigning oauthSigning = new DigitsOAuthSigning(authConfig, authToken);
        Map<String, String> authHeaders = oauthSigning.getOAuthEchoHeadersForVerifyCredentials();
        jobManager.addJobInBackground(new LoginJob(authHeaders.get(PROVIDER),
                authHeaders.get(CREDENTIALS)));
    }

    @Override
    public void failure(DigitsException error) {
        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
