package net.sproutlab.kmufood.datamod;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import net.sproutlab.kmufood.parsemod.JSONParse;

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

    public void saveData(JSONParse.returnUnit parsedData){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor mPrefEditor = mPref.edit();
        String[][] mFood = parsedData.mFood;
        String[][] mPrice = parsedData.mPrice;
        for(int i=0; i<6; i++){
            for(int j=0; j<8; j++){
                mPrefEditor.putString("chungfood-"+Rule1[i]+"-"+Rule0[j], mFood[i][j]);
                mPrefEditor.putString("chungprice-"+Rule1[i]+"-"+Rule0[j], mPrice[i][j]);
            }
        }
        mPrefEditor.commit();
    }

    public String[][] loadMenu(){
        String[][] returnMenu = new String[6][8];
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        for(int i=0; i<6; i++){
            for(int j=0; j<8; j++){
                returnMenu[i][j] = mPref.getString("chungfood-"+Rule1[i]+"-"+Rule0[j],"");
            }
        }
        return returnMenu;
    }

    public String[][] loadPrice(){
        String[][] returnPrice = new String[6][8];
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        for(int i=0; i<6; i++){
            for(int j=0; j<8; j++){
                returnPrice[i][j] = mPref.getString("chungprice-"+Rule1[i]+"-"+Rule0[j],"");
            }
        }
        return returnPrice;
    }
}
