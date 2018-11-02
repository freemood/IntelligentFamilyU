package com.Intelligent.FamilyU.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.Intelligent.FamilyU.base.BaseApplication;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static android.content.Context.MODE_PRIVATE;

public class Utils {
    /**
     * 匹配手机号的规则：[3578]是手机号第二位可能出现的数字
     */
    public static final String REGEX_MOBILE = "^[1][3578][0-9]{9}$";
    public static String ROOT_NAME = "app";

    public static void write(String key, Object value) {
        SharedPreferences spfs = BaseApplication.getContext().getApplicationContext().getSharedPreferences(ROOT_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spfs.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Double) {
            editor.putString(key, String.valueOf(((Double) value).doubleValue()));
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            throw new IllegalArgumentException("not support type");
        }
        editor.commit();
    }

    public static void writeSharedPreferences(String shareName, Map<String, String> properties) {
        SharedPreferences.Editor editor = BaseApplication.getContext().getSharedPreferences(shareName,
                MODE_PRIVATE).edit();

        if (properties != null) {
            for (Iterator<Map.Entry<String, String>> it = properties.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, String> entry = it.next();
                editor.putString(entry.getKey(), entry.getValue());
            }
        }
        editor.commit();
    }

    public static String readSharedPreferences(String shareName, String key) {
        SharedPreferences editor = BaseApplication.getContext().getSharedPreferences(shareName,
                MODE_PRIVATE);
        return editor.getString(key, "");
    }

    public static String readSharedPreferences(String key) {
        SharedPreferences editor = BaseApplication.getContext().getSharedPreferences(ROOT_NAME,
                MODE_PRIVATE);
        return editor.getString(key, "");
    }

    public static boolean readBoolean(String key, boolean defValue) {
        SharedPreferences spfs = BaseApplication.getContext().getApplicationContext().getSharedPreferences(ROOT_NAME, Context.MODE_PRIVATE);
        return spfs.getBoolean(key, defValue);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    public static boolean isChinaPhoneLegal(String str)
            throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String showNowTime() {
        Date curDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String showNowTime(long time) {
        Date curDate = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return
     */
    public static String getTimeShort(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format(date);
        return dateString;
    }

}
