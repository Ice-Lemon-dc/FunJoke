package com.dc.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by 43497 on 2018/5/3.
 */

public interface IDaoSupport<T> {

    void init(SQLiteDatabase sqLiteDatabase, Class<T>clazz);

    long  insert(T t);

    void insert(List<T> datas);
}
