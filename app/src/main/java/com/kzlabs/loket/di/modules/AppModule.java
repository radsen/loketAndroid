package com.kzlabs.loket.di.modules;

import android.accounts.AccountManager;
import android.app.Application;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by radsen on 10/18/16.
 */

@Module
public class AppModule {

    private final Application mApplication;

    public AppModule(Application app){
        this.mApplication = app;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    AccountManager providesAccountManager() {
        return AccountManager.get(mApplication);
    }

    @Provides
    @Singleton
    Configuration provideConfiguration(){
        return new Configuration.Builder(mApplication).build();
    }

    @Provides
    @Singleton
    JobManager provideManager(Configuration configuration) {
        return new JobManager(configuration);
    }

    @Singleton
    @Provides
    EventBus provideEventBus(){
        return EventBus.getDefault();
    }
}
