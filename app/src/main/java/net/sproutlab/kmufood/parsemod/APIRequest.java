package net.sproutlab.kmufood.parsemod;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.sproutlab.kmufood.datamod.Timestampdata;
import net.sproutlab.kmufood.datamod.chungFooddata;
import net.sproutlab.kmufood.datamod.dormFooddata;
import net.sproutlab.kmufood.datamod.lawFooddata;
import net.sproutlab.kmufood.datamod.staffFooddata;
import net.sproutlab.kmufood.datamod.stuFooddata;

/**
 * Created by kde713 on 2016. 6. 3..
 */
public class APIRequest {
    public static final String API_URL = "http://server.sproutlab.net:5000/json";
    private Context mContext;
    private Handler mHandler;

    public APIRequest(Context c, Handler h){
        this.mContext = c;
        this.mHandler = h;
    }

    public void callAPI(){
        StringRequest mRequest = new StringRequest(API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONParse mParse = new JSONParse(response);
                        lawFooddata mLawfood = new lawFooddata(mContext);
                        mLawfood.saveData(mParse.runParse("lawfood"));
                        stuFooddata mStufood = new stuFooddata(mContext);
                        mStufood.saveData(mParse.runParse("stufood"));
                        staffFooddata mStafffood = new staffFooddata(mContext);
                        mStafffood.saveData(mParse.runParse("stafffood"));
                        chungFooddata mChungfood = new chungFooddata(mContext);
                        mChungfood.saveData(mParse.runParse("chungfood"));
                        dormFooddata mDormfood = new dormFooddata(mContext);
                        mDormfood.saveData(mParse.runParse("dormfood"));
                        Timestampdata mTSData = new Timestampdata(mContext);
                        mTSData.updateTS();
                        mHandler.sendEmptyMessage(1);
                        Message msg = mHandler.obtainMessage();
                        msg.what = 2;
                        mHandler.sendMessage(msg);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mHandler.sendEmptyMessage(1);
                        Message msg = mHandler.obtainMessage();
                        msg.what = -1;
                        mHandler.sendMessage(msg);
                    }
                });
        RequestQueue mQueue = Volley.newRequestQueue(mContext);
        mQueue.add(mRequest);
    }
}
