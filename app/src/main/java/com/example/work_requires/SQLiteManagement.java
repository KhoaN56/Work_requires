package com.example.work_requires;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SQLiteManagement extends SQLiteOpenHelper {
    SQLiteManagement(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super (context, name, factory, version);
    }
    void queryData (String sqlQuery){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sqlQuery);
    }
    public Cursor getDatasql (String sqlQuery){
        return getWritableDatabase().rawQuery(sqlQuery,null);
    }
    public void insertUser (User user){
        SQLiteDatabase database= this.getWritableDatabase();
        String sql="INSERT INTO User VALUES (?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, user.password);
        statement.bindString(2, String.valueOf(user.type));
        statement.bindString(3, user.name);
        statement.bindString(4, user.email);
        statement.bindString(5, user.phone);
        statement.bindString(6, user.fax);
        statement.bindString(7, user.address);
        statement.bindString(8, user.area);
        statement.bindString(9, String.valueOf(user.dateOfBirth));
        statement.bindString(1, user.username);
        statement.bindString(2, user.password);
        statement.bindDouble(3, user.type);
        statement.bindString(4, user.name);
        statement.bindString(5, user.email);
        statement.bindString(6, user.phone);
        statement.bindString(7, user.fax);
        statement.bindString(8, user.address);
        statement.bindString(9, user.area);
        statement.bindString(10, user.major);
        statement.executeInsert();
    }

    public void insertRequirement(WorkRequirement requirement){

    }
    @Override
    public void onCreate(SQLiteDatabase db){

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
