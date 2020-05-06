package com.apm.request.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Ing. Oscar G. Medina Cruz on 28/08/17.
 *
 * Handle string actions and functions
 */

public class TokenUtils {

    /**
     * Save user token passed as parameter
     * @param context       application context
     * @param token         generated user token
     */
    public static void RegisterUserToken(Context context, String token) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        if (GetUserToken(context).equals("")) {
            editor.putString("token", token);
            editor.apply();
            Log.i("TOKEN", "Saved token: " + token);
        } else {
            String currentUserToken = GetUserToken(context);
            String lastUserToken = GetUserLastToken(context);

            if (!currentUserToken.equals(token) && !lastUserToken.equals(token)) {
                editor.putString("lastToken", currentUserToken);
                editor.putString("token", token);
                editor.apply();

                Log.i("LAST_TOKEN", "Saved last token: " + currentUserToken);
                Log.i("TOKEN", "Saved token: " + token);
            }

        }
    }

    /**
     * Return current user token
     *
     * @param context       application context
     * @return              current registered token
     */
    public static String GetUserToken(Context context) {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
        return shared.getString("token", "");
    }

    /**
     * Return last user token (registered to control last and previous token)
     *
     * @param context       application context
     * @return              last/previous registered token
     */
    public static String GetUserLastToken(Context context) {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
        return shared.getString("lastToken", "");
    }

    /**
     * Erase token
     *
     * @param context       application context
     */
    public static void ClearToken(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().clear().apply();
    }

}
