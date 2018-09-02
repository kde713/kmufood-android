package net.sproutlab.kmufood.api.models;

import com.google.gson.annotations.SerializedName;

public class StuFood {
    @SerializedName("착한아침")
    public Sikdan morning;

    @SerializedName("가마<br>중식")
    public Sikdan gamaLunch;

    @SerializedName("누들송(면)<br>중식")
    public Sikdan noodleLunch;

    @SerializedName("누들송<br>(카페테리아)<br>중식")
    public Sikdan cafeLunch;

    @SerializedName("인터쉐프<br>중식")
    public Sikdan chefLunch;

    @SerializedName("데일리밥<br>중식")
    public Sikdan dailyLunch;

    @SerializedName("가마<br>석식")
    public Sikdan gamaDinner;

    @SerializedName("인터쉐프<br>석식")
    public Sikdan chefDinner;

    @SerializedName("데일리밥<br>석식")
    public Sikdan dailyDinner;

    @SerializedName("차이웨이<br>상시")
    public Sikdan china;

    @SerializedName("차이웨이<br>특화")
    public Sikdan chinaSpecial;
}
