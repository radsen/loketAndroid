package com.kzlabs.loket.presenter;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.kzlabs.loket.R;
import com.kzlabs.loket.models.Pocket;
import com.kzlabs.loket.models.User;
import com.kzlabs.loket.ui.activities.PocketsActivity;
import com.kzlabs.loket.views.PocketsView;

import utils.DialogCreatorHelper;

/**
 * Created by Alejandro on 20/10/2016.
 */

public class PocketsPresenterImpl implements PocketsPresenter {

  public static final String TAG = PocketsActivity.class.getSimpleName();

  private PocketsView mPocketsView;

  public PocketsPresenterImpl(PocketsView pocketsView) {
    this.mPocketsView = pocketsView;
  }

  @Override
  public void showDialogToCreatePocket(final User user) {
    if (mPocketsView != null) {
      View createPocketView = getViewToPopulate(R.layout.view_create_pocket);
      final AppCompatEditText addPocketDestination = (AppCompatEditText) createPocketView
          .findViewById(R.id.et_destination);
      final AppCompatEditText addPocketValue = (AppCompatEditText) createPocketView
          .findViewById(R.id.et_value);
      final AppCompatEditText addPocketDescription = (AppCompatEditText) createPocketView
          .findViewById(R.id.et_description);
      AppCompatButton addPocketButton = (AppCompatButton) createPocketView
          .findViewById(R.id.btn_add);
      addPocketButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          String destination = addPocketDestination.getText().toString();
          User destinationUser = new User(destination);
          String valueAsString = addPocketValue.getText().toString();
          float value = Float.parseFloat(valueAsString);
          String description = addPocketDescription.getText().toString();
          Pocket pocket = new Pocket(value, user, destinationUser, description, 0);
          Log.i(TAG, "Pocket to save: " + pocket.toString());
          mPocketsView.createPocket(pocket);
        }
      });

      DialogFragment pocketDialog = DialogCreatorHelper.newInstance(0, 0, 0, 0, createPocketView);
      mPocketsView.showPocketDialog(pocketDialog);
    }
  }

  public View getViewToPopulate(int layoutId) {
    LayoutInflater inflater = LayoutInflater.from(mPocketsView.getActivity());
    return inflater.inflate(layoutId, null);
  }

  @Override
  public void onDestroy() {
    mPocketsView = null;
  }
}
