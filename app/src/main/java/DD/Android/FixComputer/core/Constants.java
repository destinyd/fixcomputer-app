

package DD.Android.FixComputer.core;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Bootstrap constants
 */
public class Constants {

    public static class Auth {
        private Auth() {
        }

//        /**
//         * Account type id
//         */
//        public static final String BOOTSTRAP_ACCOUNT_TYPE = "DD.Android.FixComputer";
//
//        /**
//         * Account name
//         */
//        public static final String BOOTSTRAP_ACCOUNT_NAME = "FixComputer";
//
//        /**
//         * Provider id
//         */
//        public static final String BOOTSTRAP_PROVIDER_AUTHORITY = "DD.Android.FixComputer.sync";
//
//        /**
//         * Auth token type
//         */
//        public static final String AUTHTOKEN_TYPE = BOOTSTRAP_ACCOUNT_TYPE;

        public static final String FIXCOMPUTER_ACCOUNT_TYPE = "DD.Android.FixComputer";

        /**
         * Account name
         */
        public static final String FIXCOMPUTER_ACCOUNT_NAME = "FixComputer";

        /**
         * Provider id
         */
        public static final String FIXCOMPUTER_PROVIDER_AUTHORITY = "DD.Android.FixComputer.sync";

        /**
         * Auth token type
         */
        public static final String AUTHTOKEN_TYPE = FIXCOMPUTER_ACCOUNT_TYPE;
    }

    /**
     * All HTTP is done through a REST style API built for demonstration purposes on Parse.com
     * Thanks to the nice people at Parse for creating such a nice system for us to use for fixcomputer!
     */
    public static class Http {
        private Http() {
        }


        public static final String URL_BASE = "http://192.168.1.4:3003";

        public static final String URL_PROBLEMS = URL_BASE + "/android.json";

        public static final String FORMAT_URL_PROBLEM = URL_BASE + "/android/%s.json";

        public static final String FORMAT_URL_PROBLEM_WITH_UUID = URL_BASE + "/android/%s.json?uuid=%s";

        public static final String FORMAT_URL_PROBLEM_WITH_UUID_STATUS = URL_BASE + "/android/%s.json?uuid=%s&problem[status]=%s&proble[status_plus]=%s";

        public static final String FORMAT_URL_STATUS = URL_BASE + "/status.json?uuid=%s";

        public static final String URL_PRICING = URL_BASE + "/pricing.json";
//        public static final String URL_BASE = "http://fixcomputer.com";

//        public static final String API_BASE = URL_BASE + "/api/v1";
        /**
         * Authentication URL
         */
//        public static final String URL_AUTH = URL_BASE + "/oauth/token";

//        public static final String URL_TEST = API_BASE + "/test";
//        public static final String URL_REG = API_BASE + "/reg";
//
//        /**
//         * List Users URL
//         */
//        public static final String URL_USERS = API_BASE + "/users.json";
//
//        public static final String FORMAT_URL_USERS = API_BASE + "/users.json?page=%d&";
//
//        public static final String FORMAT_URL_USER = API_BASE + "/users/%s.json";
//        /**
//         * List Friend URL
//         */
//        public static final String URL_FRIEND = API_BASE + "/users/friend.json";
//        public static final String FORMAT_URL_FRIEND = API_BASE + "/users/friend.json?page=%d&";
//
//        public static final String FORMAT_URL_USER_FOLLOW = API_BASE + "/users/%s/follow.json";
//
//        /**
//         * List Checkin's URL
//         */
//        public static final String URL_ACTIVITIES = API_BASE + "/activities.json";
//
//        public static final String FORMAT_URL_ACTIVITIES = API_BASE + "/activities.json?page=%d&";
//
//        public static final String URL_ME = API_BASE + "/me.json";
//
//        public static final String URL_NOTIFICATIONS = API_BASE + "/notifications.json";
//
//        public static final String URL_NOTIFICATIONS_STATUS = API_BASE + "/notifications/status.json";
//
//        public static final String FORMAT_URL_NOTIFICATION = API_BASE + "/notifications/%s.json";
//
//        public static final String FORMAT_URL_REPLY_NOTIFICATION = API_BASE + "/notifications/%s/%s.json";
//
//        public static final String FORMAT_URL_ACTIVITY = API_BASE + "/activities/%s.json";
//
//        public static final String FORMAT_URL_ACTIVITY_REQUEST = API_BASE + "/activities/%s/activity_requests.json";
//        public static final String FORMAT_URL_ACTIVITY_QUIT = API_BASE + "/activities/%s/quit.json";
//        public static final String FORMAT_URL_ACTIVITY_CLOSE = API_BASE + "/activities/%s/close.json";
//        public static final String FORMAT_URL_ACTIVITY_INVITE_FRIEND = API_BASE + "/activities/%s/invite.json";
//        public static final String FORMAT_ACCESS_TOKEN = "%s=%s";
//
//        public static final String PARSE_APP_ID = "78e00ce282ab3e3357af2e40c38050ef4e3fa85a8584790950efcfdb717786e3";
//        public static final String PARSE_REST_API_KEY = "976d0d4ab74708ae5c3b8ef0f5723a5fc72df5692c5f452c20d87c5673c1b6e4";
//        public static final String HEADER_PARSE_REST_API_KEY = "client_secret";
//            public static final String HEADER_PARSE_APP_ID = "client_id";
//        public static final String CONTENT_TYPE_JSON = "application/json";
//        public static final String USERNAME = "username";
//        public static final String PASSWORD = "password";
//        public static final String SESSION_TOKEN = "sessionToken";
//
//        public static final String HEADER_PARSE_GRANT_TYPE = "grant_type";
//        public static final String GRANT_TYPE = "password";
//        public static final String HEADER_PARSE_ACCESS_TOKEN = "access_token";


        /**
         * Base URL for all requests
         */
//        public static final String URL_BASE = "https://api.parse.com";

        /**
         * Authentication URL
         */
//        public static final String URL_AUTH = URL_BASE + "/1/login";

        /**
         * List Users URL
         */
//        public static final String URL_USERS = URL_BASE + "/1/users";
//
//        /**
//         * List News URL
//         */
//        public static final String URL_NEWS = URL_BASE + "/1/classes/News";
//
//        /**
//         * List Checkin's URL
//         */
//        public static final String URL_CHECKINS = URL_BASE + "/1/classes/Locations";
    }


    public static class Extra {
        private Extra() {
        }
        public static final String PROBLEM = "Problem";
        public static final String APIKEY = "APIKEY";

    }

    public static class Intent {
        private Intent() {
        }

        /**
         * Action prefix for all intents created
         */
        public static final String INTENT_PREFIX = "DD.Android.FixComputer.";

    }

    public static class Other {
        private Other() {
        }

        public static final SimpleDateFormat POST_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        public static final SimpleDateFormat TODAY_DATE_FORMAT = new SimpleDateFormat("HH:mm");
        public static final SimpleDateFormat THIS_MONTH_DATE_FORMAT = new SimpleDateFormat("M月d日");
        public static final SimpleDateFormat OTHER_DATE_FORMAT = new SimpleDateFormat("yy-MM-dd");

        public static final TimeZone CHINA_TIME_ZONE = TimeZone.getTimeZone("Asia/Shanghai");
        public static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");
        public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();

        public static class ActivityTaskStatus {
            private ActivityTaskStatus() {
            }

            public static final int JOIN = 0;
            public static final int QUIT = 1;
            public static final int INVITE = 2;
            public static final int CLOSE = 3;
        }

        public static int NOTIFICATION_ID = 11;
    }

    public static class Delay {
        private Delay() {
        }

        public static final int GET_STATUS = 5000;
//        public static final int GET_STATUS = 600000;
    }

    public static class ToolBar {
        private ToolBar() {
        }

        public static final int PRICING = 0;
        public static final int PROBLEM_FORM = 1;
        public static final int PROBLEMS = 2;
//        public static final int MENU = 3;
//        public static final int COUNT = 4;
        public static final int COUNT = 3;
    }

}


