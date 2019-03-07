package tonghai.com.checkrankdotaautochess.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tonghai.com.checkrankdotaautochess.R;
import tonghai.com.checkrankdotaautochess.adapter.CachedProfileAdater;
import tonghai.com.checkrankdotaautochess.adapter.CourierAdapter;
import tonghai.com.checkrankdotaautochess.entity.CachedProfile;
import tonghai.com.checkrankdotaautochess.entity.DacProfile;
import tonghai.com.checkrankdotaautochess.entity.Profile;
import tonghai.com.checkrankdotaautochess.retrofit.BaseResponse;
import tonghai.com.checkrankdotaautochess.retrofit.MainService;
import tonghai.com.checkrankdotaautochess.utils.Constants;
import tonghai.com.checkrankdotaautochess.utils.Utils;
import tonghai.com.checkrankdotaautochess.view.ProgressWheel;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.text_steamid)
    TextView textSteamid64;
    @BindView(R.id.edt_steamid)
    EditText edtSteamid64;
    @BindView(R.id.btn_check)
    Button btnCheck;
    @BindView(R.id.layout_input)
    ConstraintLayout layoutInput;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.v_online_status)
    View vOnlineStatus;
    @BindView(R.id.tv_online_status)
    TextView tvOnlineStatus;
    @BindView(R.id.text_rank_name)
    TextView textRankName;
    @BindView(R.id.tv_rank_name)
    TextView tvRankName;
    @BindView(R.id.iv_rank_icon)
    ImageView ivRankIcon;
    @BindView(R.id.v_underline_rank_name)
    View vUnderlineRankName;
    @BindView(R.id.text_rank_point)
    TextView textRankPoint;
    @BindView(R.id.tv_rank_point)
    TextView tvRankPoint;
    @BindView(R.id.v_underline_rank_point)
    View vUnderlineRankPoint;
    @BindView(R.id.text_game_count)
    TextView textGameCount;
    @BindView(R.id.tv_game_count)
    TextView tvGameCount;
    @BindView(R.id.v_underline_game_count)
    View vUnderlineGameCount;
    @BindView(R.id.text_candy_count)
    TextView textCandyCount;
    @BindView(R.id.tv_candy_count)
    TextView tvCandyCount;
    @BindView(R.id.v_underline_candy_count)
    View vUnderlineCandyCount;
    @BindView(R.id.text_courier_list)
    TextView textCourierList;
    @BindView(R.id.grv_courier_list)
    GridView grvCourierList;
    @BindView(R.id.layout_profile)
    ConstraintLayout layoutProfile;
    @BindView(R.id.progress_loading)
    ProgressWheel progressLoading;
    @BindView(R.id.spinner_steamid64_cached)
    Spinner spinnerSteamid64Cached;
    private MainService mainService;
    private ArrayList<CachedProfile> mCachedProfiles;
    private CourierAdapter mCourierAdapter;
    private Profile mProfile;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainService = MyApplication.getInstance().getMainService();
        initUI();
    }

    private void initUI() {
        layoutProfile.setVisibility(View.GONE);
        progressLoading.setProgress(0.5f);
        progressLoading.setCallback(progress -> {
            if (progress == 0) {
                progressLoading.setProgress(1.0f);
            } else if (progress == 1.0f) {
                progressLoading.setProgress(0.0f);
            }
        });
        ivAvatar.setOnClickListener(v -> {
            if (mProfile != null && mProfile.getProfileUrl() != null)
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mProfile.getProfileUrl())));
        });
        btnCheck.setOnClickListener(v -> {
            String id = edtSteamid64.getText().toString();
            if (id.isEmpty())
                Toast.makeText(MainActivity.this, R.string.steam_id_empty, Toast.LENGTH_SHORT).show();
            else lookupSteamId(id);
        });
        getCachedProfile();
    }

    private void lookupSteamId(String id) {
        btnCheck.setVisibility(View.INVISIBLE);
        progressLoading.setVisibility(View.VISIBLE);
        Call<ResponseBody> lookupSteamId = mainService.lookupSteamId(id);
        lookupSteamId.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string().replaceAll("\n","").replaceAll(" ", "");
                    Log.e("response", result);
                    String startKey = "Steam64ID<input type=\"text\"onclick=\"this.select();\"value=\"";
                    String endKey = "\">Nick Name";
                    String id64 = "";
                    if (result.contains(startKey) && result.contains(endKey)) id64 = result.substring(result.indexOf(startKey) + startKey.length(), result.indexOf(endKey));
                    if (!id64.isEmpty()) {
                        Log.e("id64", id64);
                        requestFetch(id64);
                    }
                    else {
                        Toast.makeText(MainActivity.this, R.string.no_result, Toast.LENGTH_SHORT).show();
                        progressLoading.setVisibility(View.INVISIBLE);
                        btnCheck.setVisibility(View.VISIBLE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, R.string.no_result, Toast.LENGTH_SHORT).show();
                    progressLoading.setVisibility(View.INVISIBLE);
                    btnCheck.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, R.string.no_result, Toast.LENGTH_SHORT).show();
                progressLoading.setVisibility(View.INVISIBLE);
                btnCheck.setVisibility(View.VISIBLE);
            }
        });
    }

    private void requestFetch(String id) {
        Call<BaseResponse> requestFetch = mainService.requestFetch(id);
        requestFetch.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                getProfile(id);
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                t.printStackTrace();
                getProfile(id);
            }
        });
    }

    private void getProfile(String id) {
        Call<Profile> getProfile = mainService.getProfile(id);
        getProfile.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                progressLoading.setVisibility(View.INVISIBLE);
                btnCheck.setVisibility(View.VISIBLE);
                setData(response.body());
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, R.string.no_result, Toast.LENGTH_SHORT).show();
                progressLoading.setVisibility(View.INVISIBLE);
                btnCheck.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setData(Profile profile) {
        if (profile != null) {
            layoutProfile.setVisibility(View.VISIBLE);
            mProfile = profile;
            String avatar = mProfile.getIconUrl();
            if (avatar != null) Glide.with(this).load(avatar).into(ivAvatar);
            String name = mProfile.getPersonaName();
            if (name != null) tvName.setText(name);
            String location = mProfile.getCountry();
            if (location != null) tvLocation.setText(location);
            if (mProfile.isRecentlyPlayedDota()) {
                tvOnlineStatus.setText(R.string.online);
                vOnlineStatus.setBackgroundResource(R.drawable.bg_circle_green);
            } else {
                tvOnlineStatus.setText(R.string.offline);
                vOnlineStatus.setBackgroundResource(R.drawable.bg_circle_red);
            }
            DacProfile dacProfile = mProfile.getDacProfile();
            if (dacProfile != null) {
                //
                int rank = dacProfile.getRank();
                String rankName;
                int rankIcon;
                if (rank <= 0) {
                    rankName = Constants.Rank.RANK_UNRANK;
                    rankIcon = R.drawable.ic_ranking_unrank;
                } else if (rank <= 9) {
                    rankName = Constants.Rank.RANK_PAWN + "-" + rank;
                    rankIcon = R.drawable.ic_ranking_pawn;
                } else if (rank <= 18) {
                    rankName = Constants.Rank.RANK_KNIGHT + "-" + (rank - 9);
                    rankIcon = R.drawable.ic_ranking_knight;
                } else if (rank <= 27) {
                    rankName = Constants.Rank.RANK_BISHOP + "-" + (rank - 18);
                    rankIcon = R.drawable.ic_ranking_bishop;
                } else if (rank <= 36) {
                    rankName = Constants.Rank.RANK_ROCK + "-" + (rank - 27);
                    rankIcon = R.drawable.ic_ranking_rock;
                } else if (rank == 37) {
                    rankName = Constants.Rank.RANK_KING;
                    rankIcon = R.drawable.ic_ranking_knight;
                } else {
                    rankName = Constants.Rank.RANK_QUEEN;
                    rankIcon = R.drawable.ic_ranking_queen;
                }
                tvRankName.setText(rankName);
                ivRankIcon.setImageResource(rankIcon);
                //
                tvRankPoint.setText(String.valueOf(dacProfile.getScore()));
                tvGameCount.setText(String.valueOf(dacProfile.getMatchesPlayed()));
                tvCandyCount.setText(String.valueOf(dacProfile.getCandies()));
                ArrayList<String> couriers = dacProfile.getAvailableCouriers();
                if (couriers != null && !couriers.isEmpty()) {
                    mCourierAdapter = new CourierAdapter(this, couriers, dacProfile.getOnDutyCourier());
                    grvCourierList.setAdapter(mCourierAdapter);
                }
            }
            cacheProfile();
        } else {
            layoutProfile.setVisibility(View.GONE);
            Toast.makeText(this, R.string.no_result, Toast.LENGTH_SHORT).show();
        }
    }

    private void cacheProfile() {
        CachedProfile cachedProfile = new CachedProfile(mProfile.getSteamId(), mProfile.getPersonaName());
        for (int i = 0, z = mCachedProfiles.size(); i < z; i++) {
            if (mCachedProfiles.get(i).getSteamId().equals(cachedProfile.getSteamId())) {
                mCachedProfiles.remove(i);
                break;
            }
        }
        mCachedProfiles.add(0, cachedProfile);
        while (mCachedProfiles.size() > 10) mCachedProfiles.remove(10);
        Utils.saveString(MainActivity.this, Constants.Key.CACHED_PROFILE, gson.toJson(mCachedProfiles));
        setDataCachedProfiles();
    }

    private void getCachedProfile() {
        mCachedProfiles = new ArrayList<>();
        String cache = Utils.getString(this, Constants.Key.CACHED_PROFILE, "");
        if (!cache.isEmpty()) {
            try {
                JSONArray jsonArray = new JSONArray(cache);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    CachedProfile cachedProfile = gson.fromJson(object.toString(), CachedProfile.class);
                    if (cachedProfile != null) mCachedProfiles.add(cachedProfile);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setDataCachedProfiles();
    }

    private void setDataCachedProfiles() {
        if (!mCachedProfiles.isEmpty()) {
            edtSteamid64.setText(mCachedProfiles.get(0).getSteamId());
            CachedProfileAdater cachedProfileAdater = new CachedProfileAdater(this, R.layout.item_cached_profile, mCachedProfiles);
            spinnerSteamid64Cached.setAdapter(cachedProfileAdater);
            spinnerSteamid64Cached.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    String steamID = ((TextView) view.findViewById(R.id.tv_steamid64)).getText().toString();
                    edtSteamid64.setText(steamID);
                    edtSteamid64.setSelection(edtSteamid64.length());
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        edtSteamid64.setText("mixigaming");
    }
}
