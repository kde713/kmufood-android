package net.sproutlab.kmufood.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import net.sproutlab.kmufood.api.models.ChungFood;
import net.sproutlab.kmufood.api.models.DormFood1;
import net.sproutlab.kmufood.api.models.DormFood2;
import net.sproutlab.kmufood.api.models.Lawfood;
import net.sproutlab.kmufood.api.models.Sikdan;
import net.sproutlab.kmufood.api.models.StaffFood;
import net.sproutlab.kmufood.api.models.StuFood;

import java.util.Date;
import java.util.Map;

public class MenuDataHelper {

    private final String PREF_NAME = "net.sproutlab.kmufood";

    private final String[] dayKeys = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};
    private final int chungDayLength = 6;
    private final String[] chungKeys = {"1", "2", "3", "4", "5", "6", "7"};
    private final int dormDayLength = 7;
    private final String[] dormKeys = {"mor", "lun", "lun2", "din"};
    private final int lawDayLength = 5;
    private final String[] lawKeys = {"snack1", "snack2", "noodle", "cutlet", "riceOven", "gukbapChef", "morning"};
    private final int staffDayLength = 5;
    private final String[] staffKeys = {"k1", "k2", "sp", "din"};
    private final int stuDayLength = 7;
    private final String[] stuKeys = {"mor", "lun_gama", "lun_nood", "lun_cafe", "lun_inter", "lun_bap ", "din_gama", "din_inter", "din_bap", "chi", "chisp"};

    private Date startDate;
    private SharedPreferences preferences;
    private boolean isReadonly;

    public MenuDataHelper(Context c, Date startDate) {
        this.preferences = c.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        this.startDate = startDate;
        this.isReadonly = false;
    }

    public MenuDataHelper(Context c) {
        this.preferences = c.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        this.isReadonly = true;
    }

    private void saveMenuData(
            SharedPreferences.Editor prefEditor,
            String foodCode, String dayKey, String labelKey,
            Sikdan sikdan
    ) {
        prefEditor.putString(String.format("%s_%s_%s_menu", foodCode, dayKey, labelKey), StringUtil.processMenuString(sikdan.menu));
        prefEditor.putString(String.format("%s_%s_%s_price", foodCode, dayKey, labelKey), StringUtil.processMenuString(sikdan.price));
    }

    private Sikdan loadMenuData(String foodCode, String dayKey, String labelKey) {
        Sikdan sikdan = new Sikdan();
        sikdan.menu = preferences.getString(String.format("%s_%s_%s_menu", foodCode, dayKey, labelKey), "");
        sikdan.price = preferences.getString(String.format("%s_%s_%s_price", foodCode, dayKey, labelKey), "");
        return sikdan;
    }

    public void saveChungFood(Map<String, ChungFood> chungFoodMap) {
        if (!this.isReadonly) {
            SharedPreferences.Editor prefEditor = preferences.edit();
            Date loopDate = (Date) startDate.clone();
            for (int d = 0; d < chungDayLength; d++) {
                String dayKey = dayKeys[d];
                ChungFood currentFood = chungFoodMap.get(DateUtil.getStringFromDate(loopDate));

                if (currentFood == null) {
                    Log.d("MenuDataHelper", String.format("saveChungFood.%s failed. isNull.", dayKey));
                    Sikdan blank = new Sikdan();
                    blank.menu = "";
                    blank.price = "";
                    for (String chungKey : chungKeys) {
                        saveMenuData(prefEditor, "chung", dayKey, chungKey, blank);
                    }
                } else {
                    saveMenuData(prefEditor, "chung", dayKey, chungKeys[0], currentFood.menu1);
                    saveMenuData(prefEditor, "chung", dayKey, chungKeys[1], currentFood.menu2);
                    saveMenuData(prefEditor, "chung", dayKey, chungKeys[2], currentFood.menu3);
                    saveMenuData(prefEditor, "chung", dayKey, chungKeys[3], currentFood.menu4);
                    saveMenuData(prefEditor, "chung", dayKey, chungKeys[4], currentFood.menu5);
                    saveMenuData(prefEditor, "chung", dayKey, chungKeys[5], currentFood.menu6);
                    saveMenuData(prefEditor, "chung", dayKey, chungKeys[6], currentFood.menu7);
                }

                loopDate = DateUtil.addDaysToDate(loopDate, 1);
            }
            prefEditor.commit();
        }
    }

    public Sikdan[][] loadChungFood() {
        Sikdan[][] chungFood = new Sikdan[chungDayLength][chungKeys.length];
        for (int i = 0; i < chungDayLength; i++) {
            for (int j = 0; j < chungKeys.length; j++) {
                chungFood[i][j] = loadMenuData("chung", dayKeys[i], chungKeys[j]);
            }
        }
        return chungFood;
    }

    public void saveDormFood(Map<String, DormFood1> dormFoodMap1, Map<String, DormFood2> dormFoodMap2) {
        if (!this.isReadonly) {
            SharedPreferences.Editor prefEditor = preferences.edit();
            Date loopDate = (Date) startDate.clone();
            for (int d = 0; d < dormDayLength; d++) {
                String dayKey = dayKeys[d];
                DormFood1 currentFood1 = dormFoodMap1.get(DateUtil.getStringFromDate(loopDate));
                DormFood2 currentFood2 = dormFoodMap2.get(DateUtil.getStringFromDate(loopDate));

                if (currentFood1 == null) {
                    Log.d("MenuDataHelper", String.format("saveDormFood.1.%s failed. isNull.", dayKey));
                    Sikdan blank = new Sikdan();
                    blank.menu = "";
                    blank.price = "";
                    saveMenuData(prefEditor, "dorm", dayKey, dormKeys[2], blank);
                } else {
                    saveMenuData(prefEditor, "dorm", dayKey, dormKeys[2], currentFood1.lunch);
                }

                if (currentFood2 == null) {
                    Log.d("MenuDataHelper", String.format("saveDormFood.2.%s failed. isNull.", dayKey));
                    Sikdan blank = new Sikdan();
                    blank.menu = "";
                    blank.price = "";
                    saveMenuData(prefEditor, "dorm", dayKey, dormKeys[0], blank);
                    saveMenuData(prefEditor, "dorm", dayKey, dormKeys[1], blank);
                    saveMenuData(prefEditor, "dorm", dayKey, dormKeys[3], blank);
                } else {
                    saveMenuData(prefEditor, "dorm", dayKey, dormKeys[0], currentFood2.morning);
                    saveMenuData(prefEditor, "dorm", dayKey, dormKeys[1], currentFood2.lunch);
                    saveMenuData(prefEditor, "dorm", dayKey, dormKeys[3], currentFood2.dinner);
                }

                loopDate = DateUtil.addDaysToDate(loopDate, 1);
            }
            prefEditor.commit();
        }
    }

    public Sikdan[][] loadDormFood() {
        Sikdan[][] dormFood = new Sikdan[dormDayLength][dormKeys.length];
        for (int i = 0; i < dormDayLength; i++) {
            for (int j = 0; j < dormKeys.length; j++) {
                dormFood[i][j] = loadMenuData("dorm", dayKeys[i], dormKeys[j]);
            }
        }
        return dormFood;
    }

    public void saveLawFood(Map<String, Lawfood> lawfoodMap) {
        if (!this.isReadonly) {
            SharedPreferences.Editor prefEditor = preferences.edit();
            Date loopDate = (Date) startDate.clone();
            for (int d = 0; d < lawDayLength; d++) {
                String dayKey = dayKeys[d];
                Lawfood currentFood = lawfoodMap.get(DateUtil.getStringFromDate(loopDate));

                if (currentFood == null) {
                    Log.d("MenuDataHelper", String.format("saveLawFood.%s failed. isNull.", dayKey));
                    Sikdan blank = new Sikdan();
                    blank.menu = "";
                    blank.price = "";
                    for (String lawKey : lawKeys) {
                        saveMenuData(prefEditor, "law", dayKey, lawKey, blank);
                    }
                } else {
                    saveMenuData(prefEditor, "law", dayKey, lawKeys[0], currentFood.snack1);
                    saveMenuData(prefEditor, "law", dayKey, lawKeys[1], currentFood.snack2);
                    saveMenuData(prefEditor, "law", dayKey, lawKeys[2], currentFood.noodle);
                    saveMenuData(prefEditor, "law", dayKey, lawKeys[3], currentFood.cutlet);
                    saveMenuData(prefEditor, "law", dayKey, lawKeys[4], currentFood.riceOven);
                    saveMenuData(prefEditor, "law", dayKey, lawKeys[5], currentFood.gukbapChef);
                    saveMenuData(prefEditor, "law", dayKey, lawKeys[6], currentFood.morning);
                }

                loopDate = DateUtil.addDaysToDate(loopDate, 1);
            }
            prefEditor.commit();
        }
    }

    public Sikdan[][] loadLawFood() {
        Sikdan[][] lawFood = new Sikdan[lawDayLength][lawKeys.length];
        for (int i = 0; i < lawDayLength; i++) {
            for (int j = 0; j < lawKeys.length; j++) {
                lawFood[i][j] = loadMenuData("law", dayKeys[i], lawKeys[j]);
            }
        }
        return lawFood;
    }

    public void saveStaffFood(Map<String, StaffFood> staffFoodMap) {
        if (!this.isReadonly) {
            SharedPreferences.Editor prefEditor = preferences.edit();
            Date loopDate = (Date) startDate.clone();
            for (int d = 0; d < staffDayLength; d++) {
                String dayKey = dayKeys[d];
                StaffFood currentFood = staffFoodMap.get(DateUtil.getStringFromDate(loopDate));

                if (currentFood == null) {
                    Log.d("MenuDataHelper", String.format("saveStaffFood.%s failed. isNull.", dayKey));
                    Sikdan blank = new Sikdan();
                    blank.menu = "";
                    blank.price = "";
                    for (String staffKey : staffKeys) {
                        saveMenuData(prefEditor, "staff", dayKey, staffKey, blank);
                    }
                } else {
                    saveMenuData(prefEditor, "staff", dayKey, staffKeys[0], currentFood.kitchen1);
                    saveMenuData(prefEditor, "staff", dayKey, staffKeys[1], currentFood.kitchen2);
                    saveMenuData(prefEditor, "staff", dayKey, staffKeys[2], currentFood.joomoon);
                    saveMenuData(prefEditor, "staff", dayKey, staffKeys[3], currentFood.dinner);
                }

                loopDate = DateUtil.addDaysToDate(loopDate, 1);
            }
            prefEditor.commit();
        }
    }

    public Sikdan[][] loadStaffFood() {
        Sikdan[][] staffFood = new Sikdan[staffDayLength][staffKeys.length];
        for (int i = 0; i < staffDayLength; i++) {
            for (int j = 0; j < staffKeys.length; j++) {
                staffFood[i][j] = loadMenuData("staff", dayKeys[i], staffKeys[j]);
            }
        }
        return staffFood;
    }

    public void saveStuFood(Map<String, StuFood> stuFoodMap) {
        if (!this.isReadonly) {
            SharedPreferences.Editor prefEditor = preferences.edit();
            Date loopDate = (Date) startDate.clone();
            for (int d = 0; d < stuDayLength; d++) {
                String dayKey = dayKeys[d];
                StuFood currentFood = stuFoodMap.get(DateUtil.getStringFromDate(loopDate));

                if (currentFood == null) {
                    Log.d("MenuDataHelper", String.format("saveStuFood.%s failed. isNull.", dayKey));
                    Sikdan blank = new Sikdan();
                    blank.menu = "";
                    blank.price = "";
                    for (String stuKey : stuKeys) {
                        saveMenuData(prefEditor, "stu", dayKey, stuKey, blank);
                    }
                } else {
                    saveMenuData(prefEditor, "stu", dayKey, stuKeys[0], currentFood.morning);
                    saveMenuData(prefEditor, "stu", dayKey, stuKeys[1], currentFood.gamaLunch);
                    saveMenuData(prefEditor, "stu", dayKey, stuKeys[2], currentFood.noodleLunch);
                    saveMenuData(prefEditor, "stu", dayKey, stuKeys[3], currentFood.cafeLunch);
                    saveMenuData(prefEditor, "stu", dayKey, stuKeys[4], currentFood.chefLunch);
                    saveMenuData(prefEditor, "stu", dayKey, stuKeys[5], currentFood.dailyLunch);
                    saveMenuData(prefEditor, "stu", dayKey, stuKeys[6], currentFood.gamaDinner);
                    saveMenuData(prefEditor, "stu", dayKey, stuKeys[7], currentFood.chefDinner);
                    saveMenuData(prefEditor, "stu", dayKey, stuKeys[8], currentFood.dailyDinner);
                    saveMenuData(prefEditor, "stu", dayKey, stuKeys[9], currentFood.china);
                    saveMenuData(prefEditor, "stu", dayKey, stuKeys[10], currentFood.chinaSpecial);
                }

                loopDate = DateUtil.addDaysToDate(loopDate, 1);
            }
            prefEditor.commit();
        }
    }

    public Sikdan[][] loadStuFood() {
        Sikdan[][] stuFood = new Sikdan[stuDayLength][stuKeys.length];
        for (int i = 0; i < stuDayLength; i++) {
            for (int j = 0; j < stuKeys.length; j++) {
                stuFood[i][j] = loadMenuData("stu", dayKeys[i], stuKeys[j]);
            }
        }
        return stuFood;
    }
}
