package com.kzlabs.loket.di.components;

import com.kzlabs.loket.authentication.AccountAuthenticator;
import com.kzlabs.loket.jobs.PocketJob;
import com.kzlabs.loket.ui.activities.MainLoketActivity;
import com.kzlabs.loket.di.modules.LoketModule;
import com.kzlabs.loket.di.scopes.UserScope;
import com.kzlabs.loket.jobs.LoginJob;
import com.kzlabs.loket.ui.activities.PocketActivity;
import com.kzlabs.loket.ui.fragments.LoginFragment;

import dagger.Component;

/**
 * Created by radsen on 10/19/16.
 */

@UserScope
@Component(dependencies = NetComponent.class, modules = LoketModule.class)
public interface LoketComponent {
    void inject(LoginJob job);

    void inject(LoginFragment fragment);

    void inject(MainLoketActivity mainLoketActivity);

    void inject(PocketActivity pocketActivity);

    void inject(AccountAuthenticator accountAuthenticator);

    void inject(PocketJob pocketJob);
}
