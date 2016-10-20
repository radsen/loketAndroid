package com.kzlabs.loket.ui.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kzlabs.loket.R;
import com.kzlabs.loket.models.Pocket;

import java.util.List;

/**
 * Created by Alejandro on 19/10/2016.
 */

public class PocketAdapter extends BaseAdapter {

  private Context mContext;
  private List<Pocket> mPocketList;

  public PocketAdapter(Context context, List<Pocket> pocketList) {
    mContext = context;
    mPocketList = pocketList;
  }

  @Override
  public int getCount() {
    return mPocketList != null ? mPocketList.size() : 0;
  }

  /**
   * Returns the actual object at the specified position in the adapter.
   *
   * @param position The position clicked
   * @return pocket
   */
  @Override
  public Object getItem(int position) {
    return mPocketList != null ? mPocketList.get(position) : null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View pocketView = getViewToPopulate(R.layout.view_pocket);

    CardView pocketCardView = (CardView) pocketView.findViewById(R.id.cv_pocket_card);
    AppCompatImageView pocketImgView = (AppCompatImageView) pocketCardView
        .findViewById(R.id.iv_pocket_category);
    AppCompatTextView pocketValueTextView = (AppCompatTextView) pocketCardView
        .findViewById(R.id.tv_pocket_value);
    AppCompatTextView pocketDescriptionTextView = (AppCompatTextView) pocketCardView
        .findViewById(R.id.tv_pocket_description) ;
    AppCompatTextView pocketDestinationTextView = (AppCompatTextView) pocketCardView
        .findViewById(R.id.tv_pocket_destination) ;

    if (mPocketList != null) {
      Pocket currentPocket = mPocketList.get(position);
      if (currentPocket.getStatus() == 1) {
        // The pocket is unblocked.
        pocketImgView.setImageResource(R.mipmap.ic_lock_open_white_36dp);
        pocketValueTextView.setTextColor(ContextCompat.getColor(mContext, R.color.green));
      }
      pocketValueTextView.setText("$" + Float.toString(currentPocket.getValue()));
      pocketDescriptionTextView.setText(currentPocket.getDescription());
      pocketDestinationTextView.setText(currentPocket.getDestination().getPhoneNumber());
    }

    return pocketView;
  }

  public View getViewToPopulate(int layoutId) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    return inflater.inflate(layoutId, null);
  }
}
