package net.sproutlab.kmufood.datamod;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import net.sproutlab.kmufood.parsemod.JSONParse;

/**
 * Created by kde713 on 2016. 5. 1..
 */
public class dormFooddata {

    private final String PREF_NAME = "net.sproutlab.kmufood";

    static Context mContext;

    private String[] Rule0 = {"mor", "lun", "din", "lun2"};
    private String[] Rule1 = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};

    public dormFooddata(Context c){
        mContext = c;
    }

    public void saveData(JSONParse.returnUnit parsedData){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor mPrefEditor = mPref.edit();
        String[][] mFood = parsedData.mFood;
        String[][] mPrice = parsedData.mPrice;
        for(int i=0; i<7; i++){
            for(int j=0; j<4; j++){
                mPrefEditor.putString("dormfood-"+Rule1[i]+"-"+Rule0[j], mFood[i][j]);
                mPrefEditor.putString("dormprice-"+Rule1[i]+"-"+Rule0[j], mPrice[i][j]);
            }
        }
        mPrefEditor.commit();
    }

    public String[][] loadMenu(){
        String[][] returnMenu = new String[7][4];
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        for(int i=0; i<7; i++){
            for(int j=0; j<4; j++){
                returnMenu[i][j] = mPref.getString("dormfood-"+Rule1[i]+"-"+Rule0[j],"");
            }
        }
        return returnMenu;
    }

    public String[][] loadPrice(){
        String[][] returnPrice = new String[7][4];
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        for(int i=0; i<7; i++){
            for(int j=0; j<4; j++){
                returnPrice[i][j] = mPref.getString("dormprice-"+Rule1[i]+"-"+Rule0[j],"");
            }
        }
        return returnPrice;
    }
}
