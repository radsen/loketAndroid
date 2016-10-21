package com.kzlabs.loket.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.kzlabs.loket.LoketApplication;
import com.kzlabs.loket.events.PocketPutEvent;
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

public class PocketPutJob extends Job {
    private static final String TAG = PocketPutJob.class.getSimpleName();

    private final String id;
    private String task;

    @Inject
    LoketApi api;

    @Inject
    EventBus bus;

    public PocketPutJob(String id, boolean isSender) {
        super(new Params(Priority.LOW).requireNetwork());
        LoketApplication.getInstance().getLoketComponent().inject(this);
        this.id = id;
        task = (isSender)?"confirm":"reject";
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        Response response = api.updatePocketTransaction(id, task).execute();
        if(response.isSuccessful()){
            Pocket pocket = ((ResponsePocket)response.body()).getPocket();
            if(pocket != null){
                bus.post(new PocketPutEvent(pocket));
            }
        } else {
            bus.post(new PocketPutEvent(response.message()));
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
