package com.kzlabs.loket.views;

import com.digits.sdk.android.AuthCallback;

/**
 * Created by Alejandro on 19/10/2016.
 */

public interface LoginView extends AuthCallback {

  void showProgress();

  void hideProgress();

  void navigateToPockets();
}
