package tonghai.com.checkrankdotaautochess.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tonghai.com.checkrankdotaautochess.R;
import tonghai.com.checkrankdotaautochess.entity.CachedProfile;

public class CachedProfileAdater extends ArrayAdapter<String> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<CachedProfile> mCachedProfiles;
    private final int mResource;

    public CachedProfileAdater(Context context, int resource, List cachedProfiles) {
        super(context, resource, 0, cachedProfiles);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        mCachedProfiles = cachedProfiles;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(mResource, parent, false);
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvSteamID64 = view.findViewById(R.id.tv_steamid64);
        View vUnderline = view.findViewById(R.id.v_underline);
        CachedProfile cachedProfile = mCachedProfiles.get(position);
        if (cachedProfile != null) {
            tvName.setText(cachedProfile.getPersonaName());
            tvSteamID64.setText(cachedProfile.getSteamId());
            vUnderline.setVisibility((position == 0) ? View.GONE : View.VISIBLE);
        }
        return view;
    }
}
