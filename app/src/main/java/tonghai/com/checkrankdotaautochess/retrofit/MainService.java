package tonghai.com.checkrankdotaautochess.retrofit;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import tonghai.com.checkrankdotaautochess.entity.Profile;
import tonghai.com.checkrankdotaautochess.utils.Constants;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Tong Hai on 7/27/2017.
 * Email: haitongbg@gmail.com
 * Phone: +841688326555
 */

public interface MainService {
    @FormUrlEncoded
    @POST(Constants.Url.LOOKUP_STEAMID)
    Call<ResponseBody> lookupSteamId(@Field(Constants.Key.ID) String id);

    @POST(Constants.Url.REQUEST_FETCH)
    Call<BaseResponse> requestFetch(@Path(Constants.Key.STEAM_ID_64) String steamId64);

    @GET(Constants.Url.GET_PROFILE)
    Call<Profile> getProfile(@Path(Constants.Key.STEAM_ID_64) String steamId64);

    @GET(Constants.Url.GET_FRIENDS)
    Call<ArrayList<Profile>> getFriends(@Path(Constants.Key.STEAM_ID_64) String steamId64);



    //Factory
    class Factory {
        public static MainService create() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            OkHttpClient client = httpClient
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.Url.DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(MainService.class);
        }
    }
}
