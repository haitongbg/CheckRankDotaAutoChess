package tonghai.com.checkrankdotaautochess.entity;

import com.google.gson.annotations.SerializedName;

public class Profile {
    @SerializedName("firstFetched")
    private String firstFetched;
    @SerializedName("dacProfile")
    private DacProfile dacProfile;
    @SerializedName("recentlyPlayedDota")
    private boolean recentlyPlayedDota;
    @SerializedName("lastFetched")
    private String lastFetched;
    @SerializedName("isPublic")
    private boolean isPublic;
    @SerializedName("profileUrl")
    private String profileUrl;
    @SerializedName("iconUrl")
    private String iconUrl;
    @SerializedName("country")
    private String country;
    @SerializedName("realName")
    private String realName;
    @SerializedName("personaName")
    private String personaName;
    @SerializedName("steamId")
    private String steamId;

    public Profile() {
    }

    public String getFirstFetched() {
        return firstFetched;
    }

    public void setFirstFetched(String firstFetched) {
        this.firstFetched = firstFetched;
    }

    public DacProfile getDacProfile() {
        return dacProfile;
    }

    public void setDacProfile(DacProfile dacProfile) {
        this.dacProfile = dacProfile;
    }

    public boolean isRecentlyPlayedDota() {
        return recentlyPlayedDota;
    }

    public void setRecentlyPlayedDota(boolean recentlyPlayedDota) {
        this.recentlyPlayedDota = recentlyPlayedDota;
    }

    public String getLastFetched() {
        return lastFetched;
    }

    public void setLastFetched(String lastFetched) {
        this.lastFetched = lastFetched;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPersonaName() {
        return personaName;
    }

    public void setPersonaName(String personaName) {
        this.personaName = personaName;
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }
}
