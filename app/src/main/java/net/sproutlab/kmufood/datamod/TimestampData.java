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

        if(chkvar[0] > 1 || (chkvar[0] == 1 && chkvar[1] != 1 && chkvar[1] != 7)) return true;
        else return false;
    }
}
