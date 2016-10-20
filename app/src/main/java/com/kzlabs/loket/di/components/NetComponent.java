package com.kzlabs.loket.di.components;

import android.accounts.AccountManager;

import com.birbit.android.jobqueue.JobManager;
import com.kzlabs.loket.di.modules.AppModule;
import com.kzlabs.loket.di.modules.NetModule;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by radsen on 10/19/16.
 */

@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {

    EventBus eventBus();
    JobManager jobManager();

    Retrofit retrofit();
    OkHttpClient okHttpClient();

}
