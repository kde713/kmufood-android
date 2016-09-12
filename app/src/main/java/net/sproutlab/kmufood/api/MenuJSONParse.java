package net.sproutlab.kmufood.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kde713 on 2016. 6. 3..
 */
public class MenuJSONParse {

    private String jsonData;
    private JSONObject jsonObject;

    public MenuJSONParse(String str_json) {
        this.jsonData = str_json;
        try {
            jsonObject = new JSONObject(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public returnUnit runParse(String rescode) {
        try {
            JSONArray foodMenu = jsonObject.getJSONArray(rescode);
            String[][] returnMenu = new String[foodMenu.length()][foodMenu.getJSONArray(0).length()];
            String[][] returnPrice = new String[foodMenu.length()][foodMenu.getJSONArray(0).length()];
            for (int i = 0; i < foodMenu.length(); i++) {
                JSONArray posMenu = foodMenu.getJSONArray(i);
                for (int j = 0; j < posMenu.length(); j++) {
                    returnMenu[i][j] = posMenu.getJSONObject(j).getString("food");
                    returnPrice[i][j] = posMenu.getJSONObject(j).getString("price");
                }
            }
            return new returnUnit(returnMenu, returnPrice);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public class returnUnit {
        public String[][] mFood;
        public String[][] mPrice;

        public returnUnit(String[][] food, String[][] price) {
            this.mFood = food;
            this.mPrice = price;
        }
    }
}
