package net.sproutlab.kmufood.datamod;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

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

    public void saveMenu(String[][] parsedData){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor mPrefEditor = mPref.edit();
        for(int i=0; i<3; i++){
            for(int j=0; j<7; j++){
                mPrefEditor.putString("dorm-"+Rule1[j]+"-"+Rule0[i],parsedData[i][j].replace(" ", "\n"));
            }
        }
        for(int j=0; j<5; j++) mPrefEditor.putString("dorm-"+Rule1[j]+"-"+Rule0[3],parsedData[3][j].replace(" ", "\n"));
        mPrefEditor.commit();
    }

    public String[][] getMenu(){
        String[][] returnMenu = new String[7][4];
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        for(int i=0; i<7; i++){
            for(int j=0; j<4; j++){
                returnMenu[i][j] = mPref.getString("dorm-"+Rule1[i]+"-"+Rule0[j],"");
            }
        }
        return returnMenu;
    }
}
