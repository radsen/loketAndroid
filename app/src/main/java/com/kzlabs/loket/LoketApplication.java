package com.kzlabs.loket;

import android.app.Application;

import com.digits.sdk.android.Digits;
import com.kzlabs.loket.di.components.DaggerLoketComponent;
import com.kzlabs.loket.di.components.DaggerNetComponent;
import com.kzlabs.loket.di.components.LoketComponent;
import com.kzlabs.loket.di.components.NetComponent;
import com.kzlabs.loket.di.modules.AppModule;
import com.kzlabs.loket.di.modules.LoketModule;
import com.kzlabs.loket.di.modules.NetModule;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

/**
 * Created by radsen on 10/18/16.
 */

public class LoketApplication extends Application {
    private static final String TAG = LoketApplication.class.getSimpleName();
    private static LoketApplication mInstance;

    private NetComponent mNetComponent;
    private LoketComponent loketComponent;

    public LoketApplication(){
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig =  new TwitterAuthConfig(getString(R.string.dgts_consumer_key),
                getString(R.string.dgts_consumer_secret));
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());

        mNetComponent = DaggerNetComponent
                .builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(getString(R.string.base_url)))
                .build();

        loketComponent = DaggerLoketComponent
                .builder()
                .netComponent(mNetComponent)
                .loketModule(new LoketModule())
                .build();
    }

    public NetComponent getComponent() {
        return mNetComponent;
    }

    public static LoketApplication getInstance() {
        return mInstance;
    }

    public LoketComponent getLoketComponent() {
        return loketComponent;
    }
}
