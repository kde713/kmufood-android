package net.sproutlab.kmufood.api.models;

import com.google.gson.annotations.SerializedName;

public class Lawfood {
    @SerializedName("바로바로1")
    public Sikdan baro1;

    @SerializedName("바로바로2")
    public Sikdan baro2;

    @SerializedName("면이랑")
    public Sikdan nood;

    @SerializedName("밥이랑 하나")
    public Sikdan bap1;

    @SerializedName("밥이랑 두울")
    public Sikdan bap2;

    @SerializedName("石火랑")
    public Sikdan fire1;

    @SerializedName("石火랑 조식")
    public Sikdan fire2;
}
