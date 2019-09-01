package net.sproutlab.kmufood.api.models;

import com.google.gson.annotations.SerializedName;

public class Lawfood {
    @SerializedName("1코너<br>SNACK1")
    public Sikdan snack1;

    @SerializedName("1코너<br>SNACK2")
    public Sikdan snack2;

    @SerializedName("2코너<BR>NOODLE")
    public Sikdan noodle;

    @SerializedName("3코너<br>CUTLET")
    public Sikdan cutlet;

    @SerializedName("4코너<br>RICE.Oven")
    public Sikdan riceOven;

    @SerializedName("5코너<br>GUKBAP.Chef")
    public Sikdan gukbapChef;

    @SerializedName("5코너<br>(조식)")
    public Sikdan morning;
}
