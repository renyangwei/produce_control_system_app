package com.papermanagement.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * 数据处理类
 */
public class DataUtils {

    private static final int TIME_LIMIT = 5;//默认倒计时5秒

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

    /**
     * 保存产线
     * @param group     产线
     * @param context   上下文
     */
    public static void saveGroup(String group, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Group", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Group", group);
        editor.apply();
    }

    /**
     * 读取产线
     * @param context   上下文
     * @return  产线
     */
    public static String readGroup(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Group", context.MODE_PRIVATE);
        return sharedPreferences.getString("Group", "empty");
    }

    /**
     * 解析时间
     * @param time 时间
     * @return     解析后得时间
     */
    public static String parasTime(String time) {
        if (TextUtils.isEmpty(time))
            return "";
        if (time.contains("T")) {
            String finishTime = time.replace("T", " ");
            return finishTime.substring(0, 19);
        }
        return time;

    }

    /**
     * 判断该字符串是否为空
     * @param s 字符串
     * @return  如果为空则返回""
     */
    public static String isEmpty(String s) {
        return (TextUtils.isEmpty(s)?"":s);
    }

    /**
     * 保存倒计时时间
     * @param timeLimit 倒计时时间
     */
    public static void saveTimeLimit(Context context, int timeLimit) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TimeLimit", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("TimeLimit", timeLimit);
        editor.apply();
    }

    /**
     * 读取倒计时时间
     * @param context
     * @return 倒计时时间
     */
    public static int readTimeLimit(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TimeLimit", context.MODE_PRIVATE);
        return sharedPreferences.getInt("TimeLimit", TIME_LIMIT);
    }


}
