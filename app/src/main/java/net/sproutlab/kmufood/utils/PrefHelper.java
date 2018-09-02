package net.sproutlab.kmufood.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class PrefHelper {

    private final String PREF_NAME = "net.sproutlab.kmufood";
    private final int uniqueKey = 180903030;

    private final String KEY_LASTUPDATE = "last_update";
    private final String KEY_UNIQUEKEY = "key";
    private final String KEY_PREFERFOOD = "preferfood";

    private final Context prefContext;

    public PrefHelper(Context c) {
        prefContext = c;
    }

    public void updateLastUpdate() {
        SharedPreferences preferences = prefContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = preferences.edit();
        Date currentDate = new Date(System.currentTimeMillis());
        prefEditor.putString(KEY_LASTUPDATE, DateUtil.getStringFromDate(currentDate));
        prefEditor.apply();
    }

    public String getLastUpdate() {
        SharedPreferences preferences = prefContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        try {
            Date lastUpdate = DateUtil.getDateFromString(preferences.getString(KEY_LASTUPDATE, "Error"));
            return DateUtil.getStringFromDate(lastUpdate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public Boolean needUpdate() {
        SharedPreferences preferences = prefContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        try {
            Date lastUpdate = DateUtil.getDateFromString(preferences.getString(KEY_LASTUPDATE, "Error"));
            Calendar todayCal = Calendar.getInstance();
            Calendar diffCal = Calendar.getInstance();
            diffCal.setTime(lastUpdate);

            int[] flags = {
                    todayCal.get(Calendar.WEEK_OF_YEAR) - diffCal.get(Calendar.WEEK_OF_YEAR),
                    todayCal.get(Calendar.DAY_OF_WEEK)
            };
            return flags[0] > 1 || (flags[0] == 1 && flags[1] != 1);
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }

    public Boolean checkUniqueKey() {
        SharedPreferences preferences = prefContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        int savedKey = preferences.getInt(KEY_UNIQUEKEY, 0);
        Log.d("UniqueKey", String.format("saved=%d, toBe=%d", savedKey, uniqueKey));
        return uniqueKey == savedKey;
    }

    public void updateKey() {
        SharedPreferences preferences = prefContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = preferences.edit();
        prefEditor.putInt(KEY_UNIQUEKEY, uniqueKey);
        prefEditor.apply();
    }

    public void setPreferFood(String preferFood) {
        SharedPreferences preferences = prefContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = preferences.edit();
        prefEditor.putString(KEY_PREFERFOOD, preferFood);
        prefEditor.apply();
        FirebaseAnalytics.getInstance(prefContext).setUserProperty("prefer_food", preferFood);
    }

    public String getPreferFood() {
        SharedPreferences preferences = prefContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        String preferFood = preferences.getString(KEY_PREFERFOOD, "");
        Log.d("PreferFood", String.format("prefer=%s", preferFood));
        return preferFood;
    }

}
