package info.vourja.airline.NetService;

import android.util.Base64;

/**
 * Created by vourja on 2016/06/21.
 */
public class util {

    public static String getCredentials(String token, String secret) {
        String credentials = token + ":" + secret;
        String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        return basic;
    }
}
