package com.kzlabs.loket.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.kzlabs.loket.LoketApplication;
import com.kzlabs.loket.events.PocketEvent;
import com.kzlabs.loket.interfaces.LoketApi;
import com.kzlabs.loket.interfaces.Priority;
import com.kzlabs.loket.models.DataWrapper;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import retrofit2.Response;

/**
 * Created by radsen on 10/19/16.
 */
public class PocketJob extends Job {

    @Inject
    EventBus bus;

    @Inject
    LoketApi api;

    public PocketJob() {
        super(new Params(Priority.LOW).requireNetwork());
        LoketApplication.getInstance().getLoketComponent().inject(this);
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        Response response = api.getPockets().execute();

        DataWrapper wrapper = null;
        if(response.isSuccessful()){
            wrapper = (DataWrapper) response.body();
        } else {
            wrapper = new DataWrapper(response.message());
        }

        bus.post(new PocketEvent(wrapper));
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
