package com.kzlabs.loket;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kzlabs.loket.models.Pocket;

import java.util.List;

/**
 * Created by radsen on 10/19/16.
 */

public class PocketAdapter extends RecyclerView.Adapter<PocketAdapter.ViewHolder> {

    private final List<Pocket> pocketList;
    private final LayoutInflater inflater;

    public PocketAdapter(Context context, List<Pocket> pocketList){
        this.pocketList = pocketList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PocketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = inflater.inflate(R.layout.pocket_view, null);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(PocketAdapter.ViewHolder holder, int position) {
        Pocket pocket = pocketList.get(position);

        int status = 0;
        switch (pocket.getStatus()){
            case 0:
                status = 0;
                break;
            case 1:
                status = 0;
                break;
            case 2:
                status = 0;
                break;
        }
        holder.ivStatus.setImageResource(status);
        int pocketValue = (pocket.getValue() > 0)?R.mipmap.ic_launcher:0;
        holder.ivPocket.setImageResource(pocketValue);
        holder.tvDescription.setText(pocket.getDescription());
    }

    @Override
    public int getItemCount() {
        return pocketList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivStatus;
        ImageView ivPocket;
        TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            ivStatus = (ImageView) itemView.findViewById(R.id.ivStatus);
            ivPocket = (ImageView) itemView.findViewById(R.id.ivPocket);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
        }
    }
}
