package net.sproutlab.kmufood.utils;

public class StringUtil {
    public static String processMenuString(String menuString) {
        return menuString.replace("\\", "₩");
    }
}
