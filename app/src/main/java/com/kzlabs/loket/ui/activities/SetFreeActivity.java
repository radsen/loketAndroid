package com.kzlabs.loket.ui.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.kzlabs.loket.R;
import com.kzlabs.loket.interfaces.LoketInterface;
import com.kzlabs.loket.models.Pocket;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetFreeActivity extends AppCompatActivity {

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_set_free);
    ButterKnife.bind(this);

    pocket = getIntent().getParcelableExtra(LoketInterface.POCKET_KEY);

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
    pocket.setStatus(1);
    pocketImgView.setImageResource(R.mipmap.ic_lock_open_white_36dp);
    pocketSetFreeButton.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
    pocketSetFreeButton.setText(getResources().getString(R.string.free_set));
    pocketSetFreeButton.setEnabled(false);
  }
}
