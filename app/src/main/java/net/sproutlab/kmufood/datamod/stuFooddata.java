package net.sproutlab.kmufood.datamod;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kde713 on 2016. 5. 1..
 */

public class stuFooddata {

    private final String PREF_NAME = "net.sproutlab.kmufood";

    static Context mContext;

    private String[] Rule0 = {"mor","wif","han","bogl","boga","nood","cafe","din1","din2","din3","inst"};
    private String[] Rule1 = {"mon", "tue", "wed", "thu", "fri", "sat","sun"};

    public stuFooddata(Context c){
        mContext = c;
    }

    public void saveMenu(String[][] parsedData){
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor mPrefEditor = mPref.edit();
        for(int i=0; i<11; i++){
            for(int j=0; j<7; j++){
                mPrefEditor.putString("stu-"+Rule1[j]+"-"+Rule0[i],parsedData[i][j].replace("[ ","[").replace(" ]","]").replace(" ", "\n"));
            }
        }
        mPrefEditor.commit();
    }

    public String[][] getMenu(){
        String[][] returnMenu = new String[7][11];
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        for(int i=0; i<7; i++){
            for(int j=0; j<11; j++){
                returnMenu[i][j] = mPref.getString("stu-"+Rule1[i]+"-"+Rule0[j],"");
            }
        }
        return returnMenu;
    }
}
