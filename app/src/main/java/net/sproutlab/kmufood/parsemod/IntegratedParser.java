package net.sproutlab.kmufood.parsemod;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import net.sproutlab.kmufood.datamod.Timestampdata;
import net.sproutlab.kmufood.datamod.chungFooddata;
import net.sproutlab.kmufood.datamod.dormFooddata;
import net.sproutlab.kmufood.datamod.lawFooddata;
import net.sproutlab.kmufood.datamod.staffFooddata;
import net.sproutlab.kmufood.datamod.stuFooddata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by kde713 on 2016. 5. 13..
 */
public class IntegratedParser extends AsyncTask<String, Void, Boolean> {

    private Context mContext;
    private Handler mHandler;

    String TARGETURL;

    String[][] lawMenu = new String[7][5];
    String[][] stuMenu = new String[11][7];
    String[][] staffMenu = new String[4][5];
    String[][] chungMenu = new String[8][6];
    String[][] dormMenu = new String[4][7];

    lawFooddata mlawdataAdapter;
    stuFooddata mstudataAdapter;
    staffFooddata mstaffdataAdapter;
    chungFooddata mchungdataAdapter;
    dormFooddata mdormdataAdapter;
    Timestampdata mTSAdapter;

    public IntegratedParser(Context c, Handler h){
        mContext = c;
        mHandler = h;
        mlawdataAdapter = new lawFooddata(c);
        mstudataAdapter = new stuFooddata(c);
        mstaffdataAdapter = new staffFooddata(c);
        mchungdataAdapter = new chungFooddata(c);
        mdormdataAdapter = new dormFooddata(c);
        mTSAdapter = new Timestampdata(c);
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        TARGETURL = "http://kmucoop.kookmin.ac.kr/restaurant/restaurant.php?w=";
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            //한울식당 파싱
            Document doc = Jsoup.connect(TARGETURL+"1").get();
            Element table0 = doc.select("table.ft1").get(1);
            Elements table = table0.getAllElements().select("table.ft1 > tbody > tr");
            for(int i = 1; i < 8; i++){
                Elements entry = table.get(i).getElementsByTag("td").select("td.ft_mn");
                for(int j = 0; j < 5; j++){
                    lawMenu[i-1][j] = entry.select("tbody > tr > td.ft1").get(j).text();
                }
            }

            //학생식당 파싱
            doc = Jsoup.connect(TARGETURL+"2").get();
            table0 = doc.select("table.ft1").get(1);
            table = table0.getAllElements().select("table.ft1 > tbody > tr");
            for(int i = 1; i < 12; i++){
                Elements entry = table.get(i).getElementsByTag("td").select("td.ft_mn");
                for(int j = 0; j < 7; j++){
                    stuMenu[i-1][j] = entry.select("tbody > tr > td.ft1").get(j).text();
                }
            }

            //교직원식당 파싱
            doc = Jsoup.connect(TARGETURL+"3").get();
            table0 = doc.select("table.ft1").get(1);
            table = table0.getAllElements().select("table.ft1 > tbody > tr");
            for(int i = 1; i < 5; i++){
                Elements entry = table.get(i).getElementsByTag("td").select("td.ft_mn");
                for(int j = 0; j < 5; j++){
                    staffMenu[i-1][j] = entry.select("tbody > tr > td.ft1").get(j).text();
                }
            }

            //청향 파싱
            doc = Jsoup.connect(TARGETURL+"4").get();
            table0 = doc.select("table.ft1").get(1);
            table = table0.getAllElements().select("table.ft1 > tbody > tr");
            for(int i = 1; i < 9; i++){
                Elements entry = table.get(i).getElementsByTag("td").select("td.ft_mn");
                for(int j = 0; j < 6; j++){
                    chungMenu[i-1][j] = entry.select("tbody").get(j).text();
                }
            }

            //기숙사 식당 파싱
            doc = Jsoup.connect(TARGETURL+"6").get();
            table0 = doc.select("table.ft1").get(1);
            table = table0.getAllElements().select("table.ft1 > tbody > tr");
            for(int i = 1; i < 4; i++){
                Elements entry = table.get(i).getElementsByTag("td").select("td.ft_mn");
                for(int j = 0; j < 7; j++){
                    dormMenu[i-1][j] = entry.select("tbody > tr > td.ft1").get(j).text();
                }
            }
            doc = Jsoup.connect(TARGETURL+"5").get();
            table0 = doc.select("table.ft1").get(1);
            table = table0.getAllElements().select("table.ft1 > tbody > tr");
            for(int i = 0; i < 5; i++){
                dormMenu[3][i] = table.get(1).getElementsByTag("td").select("td.ft_mn").select("tbody > tr > td.ft1").get(i).text();
            }

            return true;
        } catch (IOException e) {
            //파싱 실패
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result){
        super.onPostExecute(result);
        mHandler.sendEmptyMessage(1);
        Message msg = mHandler.obtainMessage();
        if(result){
            msg.what = 2;
            mlawdataAdapter.saveMenu(lawMenu);
            mstudataAdapter.saveMenu(stuMenu);
            mstaffdataAdapter.saveMenu(staffMenu);
            mchungdataAdapter.saveMenu(chungMenu);
            mdormdataAdapter.saveMenu(dormMenu);
            mTSAdapter.updateTS();
        } else{
            msg.what = -1;
        }
        mHandler.sendMessage(msg);
    }
}
