package net.sproutlab.kmufood.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import net.sproutlab.kmufood.api.MenuJSONParse;

/**
 * Created by kde713 on 2016. 5. 1..
 */

public class stuFooddata {

    private final String PREF_NAME = "net.sproutlab.kmufood";

    static Context mContext;

    private String[] Rule0 = {"mor", "lun-gama", "lun-nood", "lun-cafe", "lun-inter", "lun-bap ", "din-gama", "din-inter", "din-bap", "chi", "chisp"};

    private String[] Rule1 = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};

    public stuFooddata(Context c) {
        mContext = c;
    }

    public void saveData(MenuJSONParse.returnUnit parsedData) {
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor mPrefEditor = mPref.edit();
        String[][] mFood = parsedData.mFood;
        String[][] mPrice = parsedData.mPrice;
        for (int i = 0; i < Rule1.length; i++) {
            for (int j = 0; j < Rule0.length; j++) {
                mPrefEditor.putString("stufood-" + Rule1[i] + "-" + Rule0[j], mFood[i][j]);
                mPrefEditor.putString("stuprice-" + Rule1[i] + "-" + Rule0[j], mPrice[i][j]);
            }
        }
        mPrefEditor.commit();
    }

    public String[][] loadMenu() {
        String[][] returnMenu = new String[Rule1.length][Rule0.length];
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        for (int i = 0; i < Rule1.length; i++) {
            for (int j = 0; j < Rule0.length; j++) {
                returnMenu[i][j] = mPref.getString("stufood-" + Rule1[i] + "-" + Rule0[j], "");
            }
        }
        return returnMenu;
    }

    public String[][] loadPrice() {
        String[][] returnPrice = new String[Rule1.length][Rule0.length];
        SharedPreferences mPref = mContext.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        for (int i = 0; i < Rule1.length; i++) {
            for (int j = 0; j < Rule0.length; j++) {
                returnPrice[i][j] = mPref.getString("stuprice-" + Rule1[i] + "-" + Rule0[j], "");
            }
        }
        return returnPrice;
    }

    public int getFoodCount() {
        return Rule0.length;
    }
}
