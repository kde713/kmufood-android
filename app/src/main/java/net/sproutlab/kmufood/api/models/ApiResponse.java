package net.sproutlab.kmufood.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ApiResponse {
    @SerializedName("한울식당")
    public Map<String, Lawfood> lawFood;

    @SerializedName("학생식당")
    public Map<String, StuFood> stuFood;

    @SerializedName("교직원식당")
    public Map<String, StaffFood> staffFood;

    @SerializedName("청향")
    public Map<String, ChungFood> chungFood;

    @SerializedName("생활관식당(일반식)")
    public Map<String, DormFood1> dormFood1;

    @SerializedName("생활관식당(정기식)")
    public Map<String, DormFood2> dormFood2;
}
