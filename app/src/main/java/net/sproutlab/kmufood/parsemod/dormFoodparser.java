package net.sproutlab.kmufood.parsemod;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import net.sproutlab.kmufood.datamod.dormFooddata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by kde713 on 2016. 4. 30..
 */
public class dormFoodparser extends AsyncTask<String, Void, Boolean> {

    String TARGETURL;
    String[][] MealMenu = new String[3][7];

    private Context mContext;
    private Handler handler;

    private dormFooddata mFoodData;

    public dormFoodparser(Context c, Handler h){
        mContext = c;
        this.handler = h;
        mFoodData = new dormFooddata(mContext);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        TARGETURL = "http://kmucoop.kookmin.ac.kr/restaurant/restaurant.php?w=6";
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            Document doc = Jsoup.connect(TARGETURL).get();

            Element table0 = doc.select("table.ft1").get(1);
            Elements table = table0.getAllElements().select("table.ft1 > tbody > tr");

            for(int i = 1; i < 4; i++){
                Elements entry = table.get(i).getElementsByTag("td").select("td.ft_mn");
                for(int j = 0; j < 7; j++){
                    MealMenu[i-1][j] = entry.select("tbody > tr > td.ft1").get(j).text();
                }
            }

            Log.d("dormFoodparser-parse","Parse Completed!");

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("dormFoodparser-parse", "Parse Failed!");
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result){
        super.onPostExecute(result);
        handler.sendEmptyMessage(1);
        Message msg = handler.obtainMessage();
        if(result){
            msg.what = 1;
            Log.d("dormFoodparser-parse","Requesting Data Store");
            mFoodData.saveMenu(MealMenu);
            Log.d("dormFoodparser-parse","Requesting Timestamp Update");
            mFoodData.updateTS();
        } else{
            msg.what = -1;
        }
        Log.d("dormFoodparser-parse","Handler sended");
        handler.sendMessage(msg);
    }

}
