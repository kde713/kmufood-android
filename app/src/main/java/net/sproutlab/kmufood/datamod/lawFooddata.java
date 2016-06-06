package net.sproutlab.kmufood.datamod;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import net.sproutlab.kmufood.parsemod.JSONParse;

/**
 * Created by kde713 on 2016. 5. 1..
 */
public class lawFooddata {

    private final String PREF_NAME = "net.sproutlab.kmufood";

    static Context mContext;

    private String[] Rule0 = {"baro1","baro2","nood","bap1","bap2","fire1","fire2"};
    private String[] Rule1 = {"mon", "tue", "wed", "thu", "fri"};

    public lawFooddata(Context c){
        mContext = c;
    }

    public void saveData(JSONParse.returnUnit parsedData){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor mPrefEditor = mPref.edit();
        String[][] mFood = parsedData.mFood;
        String[][] mPrice = parsedData.mPrice;
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                mPrefEditor.putString("lawfood-"+Rule1[i]+"-"+Rule0[j], mFood[i][j]);
                mPrefEditor.putString("lawprice-"+Rule1[i]+"-"+Rule0[j], mPrice[i][j]);
            }
        }
        mPrefEditor.commit();
    }

    public String[][] loadMenu(){
        String[][] returnMenu = new String[5][7];
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                returnMenu[i][j] = mPref.getString("lawfood-"+Rule1[i]+"-"+Rule0[j],"");
            }
        }
        return returnMenu;
    }

    public String[][] loadPrice(){
        String[][] returnPrice = new String[5][7];
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        for(int i=0; i<5; i++){
            for(int j=0; j<7; j++){
                returnPrice[i][j] = mPref.getString("lawprice-"+Rule1[i]+"-"+Rule0[j],"");
            }
        }
        return returnPrice;
    }
}
