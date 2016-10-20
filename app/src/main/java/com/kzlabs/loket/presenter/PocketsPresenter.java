package com.kzlabs.loket.presenter;

import com.kzlabs.loket.models.User;

/**
 * Created by Alejandro on 20/10/2016.
 */

public interface PocketsPresenter {

  void showDialogToCreatePocket(User user);

  void onDestroy();
}
