package com.kzlabs.loket.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.kzlabs.loket.LoketApplication;
import com.kzlabs.loket.events.PocketPostEvent;
import com.kzlabs.loket.interfaces.LoketApi;
import com.kzlabs.loket.interfaces.Priority;
import com.kzlabs.loket.models.Pocket;
import com.kzlabs.loket.models.ResponsePocket;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import retrofit2.Response;

/**
 * Created by radsen on 10/20/16.
 */

public class PocketPostJob extends Job {

    private static final String TAG = PocketPostJob.class.getSimpleName();

    private final String description;
    private final float value;
    private final String destination;

    @Inject
    EventBus bus;

    @Inject
    LoketApi api;

    public PocketPostJob(String destination, float value, String description) {
        super(new Params(Priority.LOW).requireNetwork());
        LoketApplication.getInstance().getLoketComponent().inject(this);
        this.destination = destination;
        this.value = value;
        this.description = description;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        Response response = api.createPocket(destination, value, description).execute();

        if(response.isSuccessful()){
            Pocket pocket = ((ResponsePocket) response.body()).getPocket();
            if(pocket != null){
                bus.post(new PocketPostEvent(pocket));
            }
        } else {
            bus.post(new PocketPostEvent(response.message()));
        }
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Log.d(TAG, "Reason: " + cancelReason + " Message: " + throwable.getMessage());
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}