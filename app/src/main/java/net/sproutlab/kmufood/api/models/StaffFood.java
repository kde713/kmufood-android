package net.sproutlab.kmufood.api.models;

import com.google.gson.annotations.SerializedName;

public class StaffFood {
    @SerializedName("키친1")
    public Sikdan kitchen1;

    @SerializedName("키친2")
    public Sikdan kitchen2;

    @SerializedName("오늘의<br>샐러드")
    public Sikdan joomoon;
    
    @SerializedName("석식")
    public Sikdan dinner;
}
