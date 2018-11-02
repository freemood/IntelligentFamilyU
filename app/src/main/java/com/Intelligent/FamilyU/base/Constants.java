package com.Intelligent.FamilyU.base;

import com.Intelligent.FamilyU.model.cloud.entity.CloudStorageFile;

import java.util.ArrayList;

public class Constants {

    public final static String  BASE_API = "http://uapp.gcable.cn";
    public final static String APP_BAIDU_SHA = "84:63:0F:B2:0D:34:B1:AC:70:AE:AD:C1:39:70:59:38:91:5B:05:DB;com.Intelligent.FamilyU";
    public final static String APP_BAIDU_AK = "vj2LOpwOXeTCYx6sHRqM6qo1";
    public final static String APP_WEARTHER_URL = "http://api.map.baidu.com/telematics/v3/weather";
    //验证码
    public final static String APP_SEND_MESSAGE_URL = BASE_API + "/api/user-server/app/send-message/";
    //注册
    public final static String APP_REGISTER_URL = BASE_API + "/api/user-server/app/register";
    public final static String WEARTHER_SHAR_NAME = "wearther_share";
    public final static String CONTENT_TYPE = "application/json;charset=UTF-8";
    public static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    public static String APP_TOKEN = "";
    public static String IOT_USER = "";
    //1032203674570518528
    //1032203673563885568
    //7102201180100032963
    //201808270000
    //7002201164300000745
    //7002201164300000742
    public static String SERIAL_NO = "";
    public static String NETWORK_NO = "";
    public static String SCENCE_NO = "";
//    //1032203673727463424
//    public static String PLUGIN_NO = "1032203673727463424";
    //验证码令牌
    public static String CODE_TOKEN = "";
    public static boolean CLOUD_ADD_TOKEN = false;
    public static ArrayList<CloudStorageFile> CLOUD_ALL_FILES = new ArrayList<CloudStorageFile>();
    public static ArrayList<CloudStorageFile> CLOUD_ALL_FILES_DIR = new ArrayList<CloudStorageFile>();
}
