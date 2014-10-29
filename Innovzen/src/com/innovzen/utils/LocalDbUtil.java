package com.innovzen.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.innovzen.db.DaoMaster;
import com.innovzen.db.DaoMaster.DevOpenHelper;
import com.innovzen.db.DaoSession;
import com.innovzen.db.HistoryDao;

public class LocalDbUtil {

    private static String DATABASE_NAME = "innovzen-db";

    public static HistoryDao getHistoryDao(Context context) {
        return getDaoSession(context).getHistoryDao();
    }

    private static DaoSession getDaoSession(Context ctx) {
        DevOpenHelper helper = new DaoMaster.DevOpenHelper(ctx, DATABASE_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    public static boolean eraseAllData(Context ctx) {

        DevOpenHelper helper = new DaoMaster.DevOpenHelper(ctx, DATABASE_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);

        DaoSession session = daoMaster.newSession();
        session.getHistoryDao().deleteAll();
        
        session.getDatabase().close();
        
        return true;
    }

}
