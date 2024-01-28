package com.polytech.aps.utils.calback;

public class CallbackUtils {
    public static double getBeginTime(String src) {
        return Double.parseDouble(getDataByTag(src, "generationTime =", "currentTime = "));
    }

    public static double getEndTime(String src) {
        return Double.parseDouble(getDataByTag(src, "currentTime =", "|"));
    }

    public static int getSrcId(String src) {
        return Integer.parseInt(getDataByTag(src, "srcId =", "generationTime = ")) - 1;
    }

    public static String getDataByTag(String src, String srcTag, String destTag) {
        return src.substring(src.indexOf(srcTag) + srcTag.length(), src.indexOf(destTag)).trim();
    }
}
