package net.sproutlab.kmufood.datamod;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kde713 on 2016. 5. 1..
 */
public class chungFooddata {

    private final String PREF_NAME = "net.sproutlab.kmufood";

    static Context mContext;

    private String[] Rule0 = {"wm1","wm2","wm3","wm4","wm5","fm1","fm2","fm3"};
    private String[] Rule1 = {"mon", "tue", "wed", "thu", "fri", "sat"};

    public chungFooddata(Context c){
        mContext = c;
    }

    public void saveMenu(String[][] parsedData){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor mPrefEditor = mPref.edit();
        Pattern mPattern = Pattern.compile("[ ][￦][ ]\\d{1,2}[,]\\d{3}");
        for(int i=0; i<8; i++){
            for(int j=0; j<6; j++){
                Matcher mMatcher = mPattern.matcher(parsedData[i][j]);
                String target_price = "";
                while(mMatcher.find()){
                    target_price = mMatcher.group(0).replace(" ","");
                }
                mPrefEditor.putString("chung-"+Rule1[j]+"-"+Rule0[i], mPattern.matcher(parsedData[i][j]).replaceAll(""));
                Log.d("chungFood-Data", "Data for key "+"chung-"+Rule1[j]+"-"+Rule0[i]+" is "+mPattern.matcher(parsedData[i][j]).replaceAll(""));
                mPrefEditor.putString("chung-"+Rule1[j]+"-"+Rule0[i]+"-price", target_price.replace(" ", ""));
                Log.d("chungFood-Data", "Data for key "+"chung-"+Rule1[j]+"-"+Rule0[i]+"-price is "+target_price.replace(" ", ""));
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
                returnMenu[i][j] = mPref.getString("chung-"+Rule1[i]+"-"+Rule0[j],"");
                Log.d("chungFood-Data", "returned Data for section "+Integer.toString(i)+","+Integer.toString(j)+"(chung-"+Rule1[i]+"-"+Rule0[j]+") is "+returnMenu[i][j]);
            }
        }
        return returnMenu;
    }

    public String[][] getPrice(){
        String[][] returnPrice = new String[6][8];
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        for(int i=0; i<6; i++){
            for(int j=0; j<8; j++){
                returnPrice[i][j] = mPref.getString("chung-"+Rule1[i]+"-"+Rule0[j]+"-price","");
                Log.d("chungFood-Data", "returned Data for section "+Integer.toString(i)+","+Integer.toString(j)+"(chung-"+Rule1[i]+"-"+Rule0[j]+"-price) is "+returnPrice[i][j]);
            }
        }
        return returnPrice;
    }
}
