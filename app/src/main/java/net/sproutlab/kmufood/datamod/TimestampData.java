package net.sproutlab.kmufood.datamod;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kde713 on 2016. 5. 13..
 */
public class Timestampdata {

    private final String PREF_NAME = "net.sproutlab.kmufood";

    static Context mContext;

    SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy.MM.dd");

    private final int specialKey = 2010105;

    public Timestampdata(Context c){
        mContext = c;
    }


    public void updateTS(){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor mPrefEditor = mPref.edit();
        Date mDate = new Date(System.currentTimeMillis());
        mPrefEditor.putString("lastupdate",mDateFormat.format(mDate));
        mPrefEditor.commit();
    }

    public String getTS(){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SimpleDateFormat returnFormat = new SimpleDateFormat("yyyy. MM. dd");
        try {
            Date lastup_cv = mDateFormat.parse(mPref.getString("lastupdate", "Error"));
            return returnFormat.format(lastup_cv);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public Boolean checkTS(){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        String update = mPref.getString("lastupdate", null);
        Calendar curCal = Calendar.getInstance();
        Calendar recCal = Calendar.getInstance();
        if(update == null) return true;
        try {
            recCal.setTime(mDateFormat.parse(update));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int[] chkvar = {curCal.get(Calendar.WEEK_OF_YEAR) - recCal.get(Calendar.WEEK_OF_YEAR), curCal.get(Calendar.DAY_OF_WEEK)};

        if(chkvar[0] > 1 || (chkvar[0] == 1 && chkvar[1] != 1)) return true;
        else return false;
    }

    public Boolean FirstRun(){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        return mPref.getBoolean("firstrun", true);
    }

    public Boolean checkKey(){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        if(mPref.getInt("specialkey", 0) != specialKey) return false;
        else return true;
    }

    public void logRun(){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor mPrefEditor = mPref.edit();
        mPrefEditor.putBoolean("firstrun",false);
        mPrefEditor.commit();
    }

    public void patchKey(){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor mPrefEditor = mPref.edit();
        mPrefEditor.putInt("specialkey", specialKey);
        mPrefEditor.commit();
    }

    public void setShowMsg(Boolean opt){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor mPrefEditor = mPref.edit();
        mPrefEditor.putBoolean("showcustommsg", opt);
        mPrefEditor.commit();
    }

    public Boolean isShowMsg(){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        return mPref.getBoolean("showcustommsg", true);
    }
}
