package com.example.work_requires;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

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
        String sql="INSERT INTO User (username, type, Password, name, email, phone, fax, address, area, major ) VALUES (?,?,?,?,?,?,?,?,?,?)";
        Log.d("type", user.getType()+"");
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, user.username);
        statement.bindString(2, user.type);
        statement.bindString(3, user.password);
        statement.bindString(4, user.name);
        statement.bindString(5, user.email);
        statement.bindString(6, user.phone);
        statement.bindString(7, user.fax);
        statement.bindString(8, user.address);
        statement.bindString(9, user.area);
        statement.bindString(10, user.major);
        statement.executeInsert();
        Log.d("type", user.getType()+"");
        //       ContentValues values= new ContentValues();
//        values.put("username", user.getUsername());
//        values.put("type", user.getType());
//        values.put("password", user.getPassword());
//        values.put("name", user.getName());
//        values.put("email", user.getEmail());
//        values.put("phone", user.getPhone());
//        values.put("fax", user.getFax());
//        values.put("address", user.getAddress());
//        values.put("area", user.getArea());
//        values.put("major", user.getMajor());
//        database.insert("user", null, values);
//        database.close();
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
