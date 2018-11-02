package com.Intelligent.FamilyU.model.cloud.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 */

public class MySqliteOpenAndCreateHelper extends SQLiteOpenHelper {
    private static int VERSION = 1;
    public static String DBNAME = "family.db";
    public static String TABLE_NAME = "cloud";

    public MySqliteOpenAndCreateHelper(Context context) {

        //context :上下文   ， name：数据库文件的名称    factory：用来创建cursor对象，默认为null
        //version:数据库的版本号，从1开始，如果发生改变，onUpgrade方法将会调用,4.0之后只能升不能将
        super(context, DBNAME, null, VERSION);
    }

    //oncreate方法是数据库第一次创建的时候会被调用;  特别适合做表结构的初始化,需要执行sql语句；SQLiteDatabase db可以用来执行sql语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        //通过SQLiteDatabase执行一个创建表的sql语句
        db.execSQL("create table " + TABLE_NAME + "(_id integer primary key autoincrement,userName varchar(20),serialNo varchar(20),fileName varchar(20),type varchar(10),status varchar(10),fileLength varchar(20),fileDowdloadLength varchar(20),fileUpPath varchar(20),fileDownPath varchar(20),sdcardFilePath varchar(20),create_time varchar(50),parameters1 varchar(50),parameters2 varchar(50))");
    }

    //onUpgrade数据库版本号发生改变时才会执行； 特别适 合做表结构的修改
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (VERSION <= 1) {
            //添加一个phone字段
            //db.execSQL("alter table info add _create_time varchar(11)");
        }

    }
}
