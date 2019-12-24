package com.smallcake.zanghua;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ZanghuaDataBase extends SQLiteOpenHelper {

    public ZanghuaDataBase(Context context){
        super(context, context.getFilesDir() + "/zanghua.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String randomGet(boolean isLong) {
        String msg = "null";
        String tableName = isLong?"max_1":"min_1";
        Cursor cursor = getReadableDatabase().rawQuery("SELECT msg FROM "+ tableName +" ORDER BY (RANDOM() % (SELECT COUNT(*) FROM "+ tableName +")) limit 1", null);
        if (cursor.moveToNext())
        {
            msg = cursor.getString(0);
        }
        cursor.close();
        return msg;
    }
}
