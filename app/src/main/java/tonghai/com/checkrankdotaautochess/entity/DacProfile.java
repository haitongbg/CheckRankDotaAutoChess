package tonghai.com.checkrankdotaautochess.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DacProfile implements Parcelable {
    @SerializedName("totalRank")
    private int totalRank;
    @SerializedName("availableCouriers")
    private ArrayList<String> availableCouriers;
    @SerializedName("candies")
    private int candies;
    @SerializedName("onDutyCourier")
    private String onDutyCourier;
    @SerializedName("matchesPlayed")
    private int matchesPlayed;
    @SerializedName("score")
    private int score;
    @SerializedName("steamId")
    private String steamId;
    @SerializedName("queenRank")
    private int queenRank;
    @SerializedName("rank")
    private int rank;

    public DacProfile() {
    }

    protected DacProfile(Parcel in) {
        totalRank = in.readInt();
        availableCouriers = in.createStringArrayList();
        candies = in.readInt();
        onDutyCourier = in.readString();
        matchesPlayed = in.readInt();
        score = in.readInt();
        steamId = in.readString();
        queenRank = in.readInt();
        rank = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(totalRank);
        dest.writeStringList(availableCouriers);
        dest.writeInt(candies);
        dest.writeString(onDutyCourier);
        dest.writeInt(matchesPlayed);
        dest.writeInt(score);
        dest.writeString(steamId);
        dest.writeInt(queenRank);
        dest.writeInt(rank);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DacProfile> CREATOR = new Creator<DacProfile>() {
        @Override
        public DacProfile createFromParcel(Parcel in) {
            return new DacProfile(in);
        }

        @Override
        public DacProfile[] newArray(int size) {
            return new DacProfile[size];
        }
    };

    public int getTotalRank() {
        return totalRank;
    }

    public void setTotalRank(int totalRank) {
        this.totalRank = totalRank;
    }

    public ArrayList<String> getAvailableCouriers() {
        return availableCouriers;
    }

    public void setAvailableCouriers(ArrayList<String> availableCouriers) {
        this.availableCouriers = availableCouriers;
    }

    public int getCandies() {
        return candies;
    }

    public void setCandies(int candies) {
        this.candies = candies;
    }

    public String getOnDutyCourier() {
        return onDutyCourier;
    }

    public void setOnDutyCourier(String onDutyCourier) {
        this.onDutyCourier = onDutyCourier;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public int getQueenRank() {
        return queenRank;
    }

    public void setQueenRank(int queenRank) {
        this.queenRank = queenRank;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
