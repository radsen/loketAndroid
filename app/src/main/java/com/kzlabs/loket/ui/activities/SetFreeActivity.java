package com.kzlabs.loket.ui.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.birbit.android.jobqueue.JobManager;
import com.kzlabs.loket.LoketApplication;
import com.kzlabs.loket.R;
import com.kzlabs.loket.base.BaseActivity;
import com.kzlabs.loket.events.PocketPutEvent;
import com.kzlabs.loket.interfaces.LoketInterface;
import com.kzlabs.loket.jobs.PocketPutJob;
import com.kzlabs.loket.models.Pocket;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetFreeActivity extends BaseActivity {

  @BindView(R.id.iv_pocket_category)
  AppCompatImageView pocketImgView;
  @BindView(R.id.tv_pocket_value)
  AppCompatTextView pocketValueTextView;
  @BindView(R.id.tv_pocket_description)
  AppCompatTextView pocketDescriptionTextView;
  @BindView(R.id.tv_pocket_destination)
  AppCompatTextView pocketDestinationTextView;
  @BindView(R.id.btn_set_free)
  AppCompatButton pocketSetFreeButton;

  private Pocket pocket;

  @Inject
  JobManager jobManager;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LoketApplication.getInstance().getLoketComponent().inject(this);
    setContentView(R.layout.activity_set_free);
    ButterKnife.bind(this);

    pocket = getIntent().getParcelableExtra(LoketInterface.POCKET_KEY);

    int text = (pocket.isSender()) ? R.string.action_release : R.string.action_cancel;
    pocketSetFreeButton.setText(text);

    setPocketContent();
  }

  public void setPocketContent() {
    pocketValueTextView.setText("$" + Float.toString(pocket.getValue()));
    pocketDescriptionTextView.setText(pocket.getDescription());
    pocketDestinationTextView.setText(pocket.getDestination().getPhoneNumber());
  }

  @OnClick(R.id.btn_set_free)
  public void onSetFreeClick() {
    // The pocket was free set.

    int action = (pocket.isSender()) ? R.string.action_confirming : R.string.actionRejecting;

    pocketSetFreeButton.setText(getString(action));
    pocketSetFreeButton.setEnabled(false);
    jobManager.addJobInBackground(new PocketPutJob(pocket.getId(), pocket.isSender()));
  }

  @Subscribe
  public void onEvent(PocketPutEvent event){
    if(event.getMessage() != null){
      displayMessages(event.getMessage());
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          int text = (pocket.isSender()) ? R.string.action_release : R.string.action_cancel;
          pocketSetFreeButton.setText(text);
          pocketSetFreeButton.setEnabled(true);
        }
      });

    } else {
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          pocketImgView.setImageResource(R.mipmap.ic_lock_open_white_36dp);
          pocketSetFreeButton.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.green));
          pocketSetFreeButton.setText(getResources().getString(R.string.free_set));
        }
      });

    }
  }
}
