package com.kzlabs.loket.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.birbit.android.jobqueue.JobManager;
import com.kzlabs.loket.LoketApplication;
import com.kzlabs.loket.PocketAdapter;
import com.kzlabs.loket.R;
import com.kzlabs.loket.base.BaseActivity;
import com.kzlabs.loket.events.AuthenticationEvent;
import com.kzlabs.loket.events.PocketEvent;
import com.kzlabs.loket.events.PocketPostEvent;
import com.kzlabs.loket.interfaces.LoketInterface;
import com.kzlabs.loket.jobs.PocketJob;
import com.kzlabs.loket.jobs.PocketPost;
import com.kzlabs.loket.models.Pocket;
import com.kzlabs.loket.models.User;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by radsen on 10/19/16.
 */

public class PocketActivity extends BaseActivity implements LoketInterface, View.OnClickListener {

    private static final String TAG = PocketActivity.class.getSimpleName();

    @Inject
    JobManager jobManager;

    private User user;
    private RecyclerView rvPockets;
    private List<Pocket> pocketList;
    private GridLayoutManager lLayout;
    private PocketAdapter pocketAdapter;
    private Button btnCreate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoketApplication.getInstance().getLoketComponent().inject(this);
        setContentView(R.layout.loket_pocket_activity);

        user = getIntent().getParcelableExtra(USER_KEY);

        lLayout = new GridLayoutManager(this, 4);

        rvPockets = (RecyclerView) findViewById(R.id.rvPockets);
        rvPockets.setHasFixedSize(true);
        rvPockets.setLayoutManager(lLayout);

        pocketList = new ArrayList<>();
        pocketAdapter = new PocketAdapter(this, pocketList);
        rvPockets.setAdapter(pocketAdapter);

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);
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
            if(pocketList.size() > 0){
                pocketList.clear();
            }

            Log.d(TAG, "Has pockets");
            if(event.getPockets().size() > 0){
                pocketList.addAll(event.getPockets());
                refresh();
            }
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

    @Subscribe
    public void onEvent(PocketPostEvent event){
        Log.d(TAG, "Getting Post event");
        if(event.getMessage() != null){
            displayMessages(event.getMessage());
        } else {
            pocketList.add(event.getPocket());
            refresh();
        }
    }

    private void refresh() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pocketAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCreate:
                jobManager.addJobInBackground(new PocketPost("3005872026", 10000.0f, "Pocket creation test"));
                break;
        }
    }
}

