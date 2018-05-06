package com.dc.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * Created by 43497 on 2018/5/3.
 */

public class DaoSupportFactory {

    private static DaoSupportFactory mFactory;
    private SQLiteDatabase mSQLiteDatabase;

    private DaoSupportFactory(){

        //把数据库放到内存卡  判断是否有内存卡  6.0要申请动态权限
        File dbRoot = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "nhdz" + File.separator + "database");
        if (!dbRoot.exists()) {
             dbRoot.mkdirs();
        }
        File dbFile = new File(dbRoot, "nhdz.db");
        mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
    }

    public static DaoSupportFactory getFactory() {
        if (mFactory == null) {
            synchronized (DaoSupportFactory.class) {
                if (mFactory == null){
                    mFactory = new DaoSupportFactory();
                }
            }
        }
        return mFactory;
    }

    public <T> IDaoSupport getDao(Class<T> clazz){
        IDaoSupport<T> daoSupport = new DaoSupport<>();
        daoSupport.init(mSQLiteDatabase,clazz);
        return daoSupport;
    }
}
