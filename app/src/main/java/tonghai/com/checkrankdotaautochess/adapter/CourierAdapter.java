package tonghai.com.checkrankdotaautochess.adapter;

import tonghai.com.checkrankdotaautochess.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CourierAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> mCouriers;
    private String mCurrentCourier;

    public CourierAdapter(Context context, ArrayList<String> couriers, String currentCourier) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mCouriers = couriers;
        this.mCurrentCourier = currentCourier;
    }

    @Override
    public int getCount() {
        return mCouriers.size();
    }

    @Override
    public Object getItem(int position) {
        return mCouriers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_courier, parent, false);
            holder = new ViewHolder();
            holder.ivCourier = convertView.findViewById(R.id.iv_courier);
            holder.ivChecked = convertView.findViewById(R.id.iv_checked);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String image = mCouriers.get(position);
        if (image != null) {
            Glide.with(mContext).load("http://chess.tsunaminori.com/assets/images/chess/" + image.replace("e000","png.png")).into(holder.ivCourier);
            if (image.equals(mCurrentCourier)) holder.ivChecked.setVisibility(View.VISIBLE);
            else holder.ivChecked.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView ivCourier, ivChecked;
    }
}
