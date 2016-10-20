package com.kzlabs.loket.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.kzlabs.loket.LoketApplication;
import com.kzlabs.loket.events.AuthenticationEvent;
import com.kzlabs.loket.interfaces.LoketApi;
import com.kzlabs.loket.interfaces.Priority;
import com.kzlabs.loket.models.Auth;
import com.kzlabs.loket.models.User;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import retrofit2.Response;

/**
 * Created by radsen on 10/18/16.
 */

public class LoginJob extends Job {

    private final String apiUrl;
    private final String credentials;

    @Inject
    EventBus bus;

    @Inject
    LoketApi api;

    public LoginJob(String apiUrl, String credentials) {
        super(new Params(Priority.LOW).requireNetwork());
        LoketApplication.getInstance().getLoketComponent().inject(this);
        this.apiUrl = apiUrl;
        this.credentials = credentials;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        Response response = api.authentication(apiUrl, credentials).execute();
        Auth authentication = (Auth)response.body();

        if(authentication != null){
            bus.post(new AuthenticationEvent(authentication.getUser()));
        }
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount,
                                                     int maxRunCount) {
        return null;
    }
}
