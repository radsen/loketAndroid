package com.kzlabs.loket.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.birbit.android.jobqueue.JobManager;
import com.kzlabs.loket.LoketApplication;
import com.kzlabs.loket.R;
import com.kzlabs.loket.base.BaseActivity;
import com.kzlabs.loket.events.PocketEvent;
import com.kzlabs.loket.events.PocketPostEvent;
import com.kzlabs.loket.interfaces.LoketInterface;
import com.kzlabs.loket.jobs.PocketJob;
import com.kzlabs.loket.jobs.PocketPost;
import com.kzlabs.loket.models.Pocket;
import com.kzlabs.loket.models.User;
import com.kzlabs.loket.presenter.PocketsPresenter;
import com.kzlabs.loket.presenter.PocketsPresenterImpl;
import com.kzlabs.loket.ui.adapters.PocketAdapter;
import com.kzlabs.loket.views.PocketsView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PocketsActivity extends BaseActivity implements PocketsView {

  public static final String TAG = PocketsActivity.class.getSimpleName();

  @BindView(R.id.gv_pockets)
  GridView pocketsGridView;
  @BindView(R.id.fab_add_pocket)
  FloatingActionButton addPocketFab;

  private PocketAdapter mAdapter;
  private PocketsPresenter mPocketsPresenter;
  private DialogFragment mPocketDialog;
  private User user;
  private List<Pocket> mPocketList;

  @Inject
  JobManager jobManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LoketApplication.getInstance().getLoketComponent().inject(this);
    setContentView(R.layout.activity_pockets);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setIcon(R.mipmap.ic_drafts_white_24dp);
    getSupportActionBar().setTitle(" " + getResources().getString(R.string.pockets));
    ButterKnife.bind(this);

    user = getIntent().getParcelableExtra(LoketInterface.USER_KEY);
    mPocketList = new ArrayList<>();

    fillAdapter();
    pocketsGridView.setAdapter(mAdapter);
    setOnPocketClickListener();

    mPocketsPresenter = new PocketsPresenterImpl(this);
  }

  public void fillAdapter() {
    mAdapter = new PocketAdapter(this, mPocketList);
  }

  @Override
  protected void onStart() {
    super.onStart();
    jobManager.addJobInBackground(new PocketJob());
  }

  @Subscribe
  public void onEvent(final PocketEvent event){
    Log.d(TAG, "Loading pockets");

    if(event.errorMessage() != null){
      displayMessages(event.errorMessage());
    } else {
      if(mPocketList.size() > 0){
        mPocketList.clear();
      }

      Log.d(TAG, "Has pockets");
      if(event.getPockets().size() > 0){
        mPocketList.addAll(event.getPockets());
        refresh();
      }
    }
  }

  @Subscribe
  public void onEvent(PocketPostEvent event){
    Log.d(TAG, "Getting Post event");
    if(event.getMessage() != null){
      displayMessages(event.getMessage());
    } else {
      mPocketList.add(event.getPocket());
      refresh();
      hideDialog();
    }
  }

  private void displayMessages(final String message) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        Toast.makeText(getBaseContext(), message,
            Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void refresh() {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        refreshPockets();
      }
    });
  }

  private void hideDialog() {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
       hidePocketDialog();
      }
    });
  }

  public void setOnPocketClickListener() {
    pocketsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "Position clicked: " + position);
        Pocket pocket = mPocketList.get(position);
        if (pocket.getStatus() == 0) {
          // The transaction can be free set.
          Intent navigateToPocketIntent = new Intent(PocketsActivity.this, SetFreeActivity.class);
          navigateToPocketIntent.putExtra(LoketInterface.POCKET_KEY, pocket);
          startActivity(navigateToPocketIntent);
        }
      }
    });
  }

  @Override
  public Activity getActivity() {
    return this;
  }

  @OnClick(R.id.fab_add_pocket)
  public void onAddPocketClick() {
   mPocketsPresenter.showDialogToCreatePocket(user);
  }

  @Override
  public void showPocketDialog(DialogFragment pocketDialog) {
    mPocketDialog = pocketDialog;
    pocketDialog.show(getSupportFragmentManager(), TAG);
  }

  @Override
  public void hidePocketDialog() {
    if (mPocketDialog != null) {
      mPocketDialog.dismiss();
    }
  }

  @Override
  public void refreshPockets() {
    fillAdapter();
    mAdapter.notifyDataSetChanged();
  }

  @Override
  public void createPocket(Pocket pocket) {
    if (pocket != null) {
      jobManager.addJobInBackground(new PocketPost(pocket.getDestination().getPhoneNumber(),
          pocket.getValue(), pocket.getDescription()));
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    refreshPockets(); //TODO
  }

  @Override
  protected void onDestroy() {
    mPocketsPresenter.onDestroy();
    super.onDestroy();
  }
}
