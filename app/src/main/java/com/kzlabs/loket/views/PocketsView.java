package com.kzlabs.loket.views;

import android.app.Activity;
import android.support.v4.app.DialogFragment;

import com.kzlabs.loket.models.Pocket;

/**
 * Created by Alejandro on 20/10/2016.
 */

public interface PocketsView {

  Activity getActivity();

  void showPocketDialog(DialogFragment pocketsDialog);

  void hidePocketDialog();

  void refreshPockets();

  void createPocket(Pocket pocket);
}
