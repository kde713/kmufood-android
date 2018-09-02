package net.sproutlab.kmufood.api.models;

import com.google.gson.annotations.SerializedName;

public class DormFood2 {
    @SerializedName("조식")
    public Sikdan morning;

    @SerializedName("중식")
    public Sikdan lunch;

    @SerializedName("석식")
    public Sikdan dinner;
}
