package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    //带全部参数的构造函数，此构造函数必不可少,name为数据库名称
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //创建数据库sql语句 并 执行,相当于初始化数据库，这里是新建了一张表这个方法继承自SQLiteOpenHelper,会自动调用，也就是会 当新建了一个DatabaseHelper对象时，就会默认新建一张表user，表里存着名为name项
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user (name varchar(20))";
        db.execSQL(sql);
    }

    //这里应当实现数据库升级等操作
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
