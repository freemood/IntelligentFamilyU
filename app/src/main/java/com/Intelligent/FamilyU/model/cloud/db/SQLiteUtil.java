package com.Intelligent.FamilyU.model.cloud.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.Intelligent.FamilyU.model.cloud.db.MySqliteOpenAndCreateHelper;
import com.Intelligent.FamilyU.model.cloud.entity.CloudFileBean;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SQLiteUtil {

    private static final String select = "select * from " + MySqliteOpenAndCreateHelper.TABLE_NAME;
    private final static String SELECT_PERSONNE_ONE = "select * from " + MySqliteOpenAndCreateHelper.TABLE_NAME + " where sdcardFilePath = ?";

    public static SQLiteDatabase getSqlDB(Context context) {
        MySqliteOpenAndCreateHelper mySqliteOpenHelper = new MySqliteOpenAndCreateHelper(context);
        //调用getReadableDatabase方法,来初始化数据库的创建
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        return db;
    }

    public static void add(Context context, CloudFileBean cloudFileBean) {
        SQLiteDatabase db = getSqlDB(context);

        long nowTime = System.currentTimeMillis();
        ContentValues values = new ContentValues();
        values.put("fileName", cloudFileBean.getFileName());
        values.put("type", cloudFileBean.getType());
        values.put("create_time", getDate(nowTime));
        values.put("status", cloudFileBean.getStatus());
        values.put("fileLength", cloudFileBean.getFileLength());
        values.put("fileDowdloadLength", cloudFileBean.getFileDowdloadLength());
        values.put("fileUpPath", cloudFileBean.getFileUpPath());
        values.put("fileDownPath", cloudFileBean.getFileDownPath());
        values.put("sdcardFilePath", cloudFileBean.getSdcardFilePath());
        values.put("userName", cloudFileBean.getUserName());
        values.put("serialNo", cloudFileBean.getSerialNo());
        if(isQueryPersonneOne(context,cloudFileBean.getSdcardFilePath())){
            db.update(MySqliteOpenAndCreateHelper.TABLE_NAME,values,"sdcardFilePath=?",new String[]{cloudFileBean.getSdcardFilePath()});
        }else{
            db.insert(MySqliteOpenAndCreateHelper.TABLE_NAME, null, values);
        }
        close(db);
    }

    public static List<CloudFileBean> query(Context context) {
        List<CloudFileBean> data = new ArrayList<CloudFileBean>();
        SQLiteDatabase db = getSqlDB(context);
        Cursor cursor = db.rawQuery(select, null);
        while (cursor.moveToNext()) {
            CloudFileBean mCloudFileBean = new CloudFileBean();
            mCloudFileBean.setFileName(cursor.getString(cursor.getColumnIndex("fileName")));
            mCloudFileBean.setCreateTime(cursor.getString(cursor.getColumnIndex("create_time")));
            mCloudFileBean.setType(cursor.getString(cursor.getColumnIndex("type")));
            mCloudFileBean.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            mCloudFileBean.setFileLength(cursor.getString(cursor.getColumnIndex("fileLength")));
            mCloudFileBean.setFileDowdloadLength(cursor.getString(cursor.getColumnIndex("fileDowdloadLength")));
            mCloudFileBean.setFileUpPath(cursor.getString(cursor.getColumnIndex("fileUpPath")));
            mCloudFileBean.setFileDownPath(cursor.getString(cursor.getColumnIndex("fileDownPath")));
            mCloudFileBean.setSdcardFilePath(cursor.getString(cursor.getColumnIndex("sdcardFilePath")));
            mCloudFileBean.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
            mCloudFileBean.setSerialNo(cursor.getString(cursor.getColumnIndex("serialNo")));
            data.add(mCloudFileBean);
        }
        cursor.close();
        close(db);
        return data;
    }

    public static void update(Context context, CloudFileBean cloudFileBean) {
        SQLiteDatabase db = getSqlDB(context);
        ContentValues values = new ContentValues();
        values.put("status", cloudFileBean.getStatus());
        values.put("fileDowdloadLength", cloudFileBean.getFileDowdloadLength());
        if(isQueryPersonneOne(context,cloudFileBean.getSdcardFilePath())){
            db.update(MySqliteOpenAndCreateHelper.TABLE_NAME,values,"sdcardFilePath=?",new String[]{cloudFileBean.getSdcardFilePath()});
        }
        close(db);
    }

    public static void delete(Context context, CloudFileBean cloudFileBean) {
        SQLiteDatabase db = getSqlDB(context);
        ContentValues values = new ContentValues();
       String status = cloudFileBean.getStatus();
        if(!isQueryPersonneOne(context,cloudFileBean.getSdcardFilePath())){
            return;
        }
       if("delete".equals(status)){
           values.put("status", "complete");
               db.update(MySqliteOpenAndCreateHelper.TABLE_NAME,values,"sdcardFilePath=?",new String[]{cloudFileBean.getSdcardFilePath()});
       }else if("complete".equals(status)){
           values.put("status", "delete");
               db.update(MySqliteOpenAndCreateHelper.TABLE_NAME,values,"sdcardFilePath=?",new String[]{cloudFileBean.getSdcardFilePath()});
        }else{
                db.delete(MySqliteOpenAndCreateHelper.TABLE_NAME,"sdcardFilePath=?",new String[]{cloudFileBean.getSdcardFilePath()});
        }
        close(db);
    }
    /**
     * 判断该人员是否在人员信息表
     *
     * @param context
     * @return
     */
    public static Boolean isQueryPersonneOne(Context context, String sdcardFilePath) {
        String personnelOne = SELECT_PERSONNE_ONE;
        SQLiteDatabase db = getSqlDB(context);
        Cursor cursor = db.rawQuery(personnelOne, new String[]{sdcardFilePath});
        while (cursor.moveToNext()) {
            return true;
        }
        cursor.close();
        close(db);
        return false;
    }
    /**
     * 时间转换为时间戳
     *
     * @return
     */
    public static String getDate(long nowTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(nowTime);//获取当前时间
        String str = simpleDateFormat.format(curDate);
        return str;
    }

    private void getNetTime() {
        URL url = null;//取得资源对象
        try {
            url = new URL("http://www.baidu.com");
            //url = new URL("http://www.ntsc.ac.cn");//中国科学院国家授时中心
            //url = new URL("http://www.bjtime.cn");
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            long ld = uc.getDate(); //取得网站日期时间
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(ld);
            final String format = formatter.format(calendar.getTime());
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(SQLiteDatabase db) {
        if (db != null) {
            db.close();
        }
    }

}
