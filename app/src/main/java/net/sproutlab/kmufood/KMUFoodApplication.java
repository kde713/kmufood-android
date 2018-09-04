package net.sproutlab.kmufood;

import android.support.multidex.MultiDexApplication;

/**
 * Created by kde713 on 2016. 9. 12..
 */
public class KMUFoodApplication extends MultiDexApplication {
    private boolean isUpdateChecked = false;

    public boolean isUpdateChecked() {
        return isUpdateChecked;
    }

    public void setUpdateChecked(boolean updateChecked) {
        isUpdateChecked = updateChecked;
    }
}
