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
import android.widget.RelativeLayout;

import com.kzlabs.loket.R;
import com.kzlabs.loket.models.Pocket;

import java.util.List;

import static android.R.attr.id;

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
        .findViewById(R.id.tv_pocket_identification) ;
    RelativeLayout rlIdentificationColor = (RelativeLayout) pocketCardView
            .findViewById(R.id.rlIdentificationColor);
    AppCompatTextView tvPocketLabel = (AppCompatTextView) pocketCardView
            .findViewById(R.id.tv_pocket_label) ;


    if (mPocketList != null) {
      Pocket currentPocket = mPocketList.get(position);

      int icon = 0;
      int color = 0;
      switch (currentPocket.getStatus()){
        case 0:
          icon = R.drawable.ic_logo_nequi;
          color = R.color.light_grey;
          break;
        case 1:
          icon = R.drawable.ic_lock;
          color = (currentPocket.isSender()) ? R.color.light_blue : R.color.received;
          break;
        case 2:
          icon = R.drawable.ic_unlock;
          color = R.color.light_blue;
          break;
        case 3:
          icon = R.drawable.ic_unlock;
          color = R.color.rejected;
          break;
      }

      String label = (currentPocket.isSender()) ? mContext.getString(R.string.txt_destination) :
              mContext.getString(R.string.txt_origin);
      tvPocketLabel.setText(label);
      rlIdentificationColor.setBackgroundColor(mContext.getResources().getColor(color));
      pocketImgView.setImageResource(icon);

      pocketValueTextView.setText("$" + Float.toString(currentPocket.getValue()));
      pocketDescriptionTextView.setText(currentPocket.getDescription());

      String phoneNumber = (currentPocket.isSender()) ?
              currentPocket.getDestination().getPhoneNumber() :
              currentPocket.getOrigin().getPhoneNumber() ;
      pocketDestinationTextView.setText(phoneNumber);
    }

    return pocketView;
  }

  public View getViewToPopulate(int layoutId) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    return inflater.inflate(layoutId, null);
  }
}
