package tonghai.com.checkrankdotaautochess.utils;

public class Constants {
    public static class Url {
        // DOMAIN
        public static final String DOMAIN = "http://www.autochess-stats.com/backend/api/dacprofiles/";
        public static final String GET_PROFILE = "{steamId64}";
        public static final String REQUEST_FETCH = "{steamId64}/requestfetch/";
        public static final String GET_FRIENDS = "{steamId64}/friends";
        public static final String LOOKUP_STEAMID = "https://steamid.xyz/q";
    }

    public static class Key {
        public static final String STEAM_ID_64 = "steamId64";
        public static final String DEVICE_ID = "deviceId";
        public static final String DEVICE_TOKEN = "deviceToken";
        public static final String CACHED_PROFILE = "cachedProfile";
        public static final String ID = "id";
    }

    public static class Rank {
        public static final String RANK_UNRANK = "unrank";
        public static final String RANK_PAWN = "pawn";
        public static final String RANK_KNIGHT = "knight";
        public static final String RANK_BISHOP = "bishop";
        public static final String RANK_ROCK = "rock";
        public static final String RANK_KING = "king";
        public static final String RANK_QUEEN = "queen";
    }

    public static class DataNotify {

    }
}
