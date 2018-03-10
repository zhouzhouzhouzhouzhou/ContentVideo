package com.example.content.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by 佳南 on 2017/9/29.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Collection.db";
    private static final int  DATABASE_VERSION =  1;
    private static final String CREATE_TABLE = "create table Collection ("
            +"videoId text,"
            +"name text,"
            +"episode text,"
            +"thumbnail text,"
            +"url text)";
    private Context mContext;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.delete(CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
        Toast.makeText(mContext,"Create successed",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    switch (newVersion) {
        case 2:
            db.execSQL(CREATE_TABLE);
            break;
        default:break;
    }
    }
}
