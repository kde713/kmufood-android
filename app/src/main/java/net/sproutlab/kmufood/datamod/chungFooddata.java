package net.sproutlab.kmufood.datamod;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kde713 on 2016. 5. 1..
 */
public class chungFooddata {

    private final String PREF_NAME = "net.sproutlab.kmufood";

    static Context mContext;

    private String[] Rule0 = {"wm1","wm2","wm3","wm4","wm5","fm1","fm2","fm3"};
    private String[] Rule1 = {"mon", "tue", "wed", "thu", "fri", "sat"};

    SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy.MM.dd");

    public chungFooddata(Context c){
        mContext = c;
    }

    public void saveMenu(String[][] parsedData){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor mPrefEditor = mPref.edit();
        for(int i=0; i<8; i++){
            for(int j=0; j<6; j++){
                mPrefEditor.putString("chung-"+Rule1[j]+"-"+Rule0[i],parsedData[i][j].replace(" ", "\n"));
                Log.d("chungFoodparser-Data", "Data for key "+"chung-"+Rule1[j]+"-"+Rule0[i]+" is "+parsedData[i][j]);
            }
        }
        mPrefEditor.commit();
    }

    public String[][] getMenu(){
        String[][] returnMenu = new String[6][8];
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        for(int i=0; i<6; i++){
            for(int j=0; j<8; j++){
                returnMenu[i][j] = mPref.getString("chung-"+Rule1[i]+"-"+Rule0[j],"blank");
                Log.d("chungFoodparser-Data", "returned Data for section "+Integer.toString(i)+","+Integer.toString(j)+"(chung-"+Rule1[i]+"-"+Rule0[j]+") is "+returnMenu[i][j]);
            }
        }
        return returnMenu;
    }

    public void updateTS(){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor mPrefEditor = mPref.edit();
        Date mDate = new Date(System.currentTimeMillis());
        mPrefEditor.putString("chung-update",mDateFormat.format(mDate));
        mPrefEditor.commit();
    }

    public Boolean checkTS(){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        String update = mPref.getString("chung-update", null);
        Calendar curCal = Calendar.getInstance();
        Calendar recCal = Calendar.getInstance();
        if(update == null) return true;
        try {
            recCal.setTime(mDateFormat.parse(update));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int[] chkvar = {curCal.get(Calendar.WEEK_OF_YEAR) - recCal.get(Calendar.WEEK_OF_YEAR)};

        if(chkvar[0] >= 1) return true;
        else return false;
    }
}
