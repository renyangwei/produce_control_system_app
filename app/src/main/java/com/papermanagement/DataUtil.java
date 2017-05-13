package com.papermanagement;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * 数据处理类
 */
public class DataUtil {

    /**
     * 解析数据
     * @param other 其他数据
     * @return ArrayList
     */
    public static ArrayList<String> parseInfo(String other) throws Exception{
        ArrayList<String> arrayList = new ArrayList<>();
        String[] strings = other.split("&");
        for (String s: strings) {
            s = s.replace("=", ":");
            arrayList.add(s);
        }
        return arrayList;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int dp = (int) (pxValue / scale + 0.5f);
        Log.d("pax2dip", "dp:" + dp);
        return dp;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
