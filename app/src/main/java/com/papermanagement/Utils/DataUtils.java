package com.papermanagement.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 数据处理类
 */
public class DataUtils {

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
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 保存厂家
     * @param factory 厂家名称
     * @param context 上下文
     */
    public static void saveFactory(String factory, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Factory", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("factory", factory);
        editor.apply();
    }

    /**
     * 读取厂家
     * @param context 上下文
     * @return 厂家名称
     */
    public static String readFactory(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Factory", context.MODE_PRIVATE);
        return sharedPreferences.getString("factory", "empty");
    }

}
