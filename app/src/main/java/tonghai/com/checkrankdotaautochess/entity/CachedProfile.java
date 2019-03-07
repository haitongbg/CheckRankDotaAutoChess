package tonghai.com.checkrankdotaautochess.entity;

import com.google.gson.annotations.SerializedName;

public class CachedProfile {
    @SerializedName("steamId")
    private String steamId;
    @SerializedName("personaName")
    private String personaName;

    public CachedProfile(String steamId, String personaName) {
        this.steamId = steamId;
        this.personaName = personaName;
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public String getPersonaName() {
        return personaName;
    }

    public void setPersonaName(String personaName) {
        this.personaName = personaName;
    }
}
