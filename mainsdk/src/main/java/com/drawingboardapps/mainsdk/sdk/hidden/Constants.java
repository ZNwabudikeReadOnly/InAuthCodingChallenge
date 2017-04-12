package com.drawingboardapps.mainsdk.sdk.hidden;

/**
 * Created by Zach on 4/10/2017.
 */

public class Constants {

    public static final boolean DEBUG = true;

    public class EventBus{
        public static final String GET_API = "api";
        public static final String GET_GPS = "gps";
        public static final String GET_APPS = "apps";

        public class Status{
            public static final int OK = 1;
            public static final int ERROR = 0;
        }

    }

    public class Http {
        public final static String BASE_URL = "https://data.lacity.org/api/views/i4ke-p6yq/";
        public static final String QUERY_PARAM = "DOWNLOAD";
        public static final long READ_TIMEOUT = 15;
        public static final long CONNECT_TIMEOUT = 30;
    }
}
