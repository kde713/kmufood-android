package net.sproutlab.kmufood.api.models;

import com.google.gson.annotations.SerializedName;

public class DormFood2 {
    @SerializedName("조식")
    Sikdan morning;

    @SerializedName("중식")
    Sikdan lunch;

    @SerializedName("석식")
    Sikdan dinner;
}
