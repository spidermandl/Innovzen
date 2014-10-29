
package com.innovzen.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PersistentUtil {

    /***************** obtainSharedPreferences ************************************/

    private static SharedPreferences obtainSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /***************** SETTERS ************************************/
    /**
     * Just so we don't have to call getSharedPRegerences and e.commit every time
     * 
     * @param context
     * @param strValue
     * @param prefKey
     * @return
     */
    public static boolean setString(Context context, String strValue, String prefKey) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putString(prefKey, strValue);
        return e.commit();
    }

    /**
     * Just so we don't have to call getSharedPRegerences and e.commit every time
     * 
     * @param context
     * @param strValue
     * @param prefKey
     * @return
     */
    public static boolean setBoolean(Context context, boolean value, String prefKey) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putBoolean(prefKey, value);
        return e.commit();
    }

    public static boolean setInt(Context context, int value, String prefKey) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putInt(prefKey, value);
        return e.commit();
    }

    public static boolean setLong(Context context, long value, String prefKey) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putLong(prefKey, value);
        return e.commit();
    }

    public static boolean setFloat(Context context, float value, String prefKey) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putFloat(prefKey, value);
        return e.commit();
    }

    public static boolean removeString(Context context, String prefKey) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.remove(prefKey);
        return e.commit();
    }

    /***************** GETTERS ************************************/
    /**
     * 
     * @param con
     * @param prefKey
     *            -shared prefereces key of the value stored
     * @return the value from shared preferences , false if is not set
     */
    public static boolean getBoolean(Context con, String prefKey) {
        return obtainSharedPreferences(con).getBoolean(prefKey, false);
    }

    /**
     * 
     * @param con
     * @param prefKey
     *            -shared prefereces key of the value stored
     * @param default
     * @return the value from shared preferences , false if is not set
     */
    public static boolean getBoolean(Context con, String prefKey, boolean defaultValue) {
        return obtainSharedPreferences(con).getBoolean(prefKey, defaultValue);
    }

    /**
     * 
     * @param con
     * @param prefKey
     *            -shared prefereces key of the value stored
     * @return the value from shared preferences , -1 if is not set
     */
    public static int getInt(Context con, String prefKey) {
        return obtainSharedPreferences(con).getInt(prefKey, -1);
    }

    /**
     * 
     * @param con
     * @param prefKey
     *            -shared prefereces key of the value stored
     * @param default
     * @return the value from shared preferences , -1 if is not set
     */
    public static int getInt(Context con, String prefKey, int defaultValue) {
        return obtainSharedPreferences(con).getInt(prefKey, defaultValue);
    }

    /**
     * 
     * @param con
     * @param prefKey
     *            -shared prefereces key of the value stored
     * @return the value from shared preferences , -1 if is not set
     */
    public static float getFloat(Context con, String prefKey) {
        return obtainSharedPreferences(con).getFloat(prefKey, -1);
    }

    /**
     * 
     * @param con
     * @param prefKey
     *            -shared prefereces key of the value stored
     * @return the value from shared preferences , -1 if is not set
     */
    public static long getLong(Context con, String prefKey) {
        return obtainSharedPreferences(con).getLong(prefKey, -1);
    }

    /**
     * 
     * @param con
     * @param prefKey
     *            -shared prefereces key of the value stored
     * @return the value from shared preferences , null if is not set
     */
    public static String getString(Context con, String prefKey) {
        return obtainSharedPreferences(con).getString(prefKey, null);
    }
}
