package net.sproutlab.kmufood.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ApiResponse {
    @SerializedName("한울식당")
    public Map<String, Lawfood> lawfood;
}
