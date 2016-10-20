package com.kzlabs.loket.di.modules;

import com.kzlabs.loket.di.scopes.UserScope;
import com.kzlabs.loket.interfaces.LoketApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by radsen on 10/19/16.
 */

@Module
public class LoketModule {

    @Provides
    @UserScope
    public LoketApi provideLoketApi(Retrofit retrofit){
        return retrofit.create(LoketApi.class);
    }

}
