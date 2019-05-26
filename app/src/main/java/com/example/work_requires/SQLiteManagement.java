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
    public void insert(User user){
        SQLiteDatabase database= this.getWritableDatabase();
        String sql="INSERT INTO User (username, type, Password, name, email, phone, fax, address, area ) VALUES (?,?,?,?,?,?,?,?,?)";
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
        statement.executeInsert();
        Log.d("type", user.getType()+"");
    }

    public void insert(WorkRequirement requirement, User user){
        SQLiteDatabase database = getWritableDatabase();
        String sql ="INSERT INTO Recruitments VALUES(NULL,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, user.getUsername());
        statement.bindString(2, requirement.getJobName());
        statement.bindString(3, requirement.getMajor());
        statement.bindString(4, requirement.getArea());
        statement.bindLong(5, requirement.getSalary());
        statement.bindString(6, requirement.getDegree());
        statement.bindString(7, requirement.getWorkPos());
        statement.bindLong(8, requirement.getExperience());
        statement.bindString(9, requirement.getDescription());
        statement.bindString(10, requirement.getRequirement());
        statement.bindString(11, requirement.getBenefit());
        statement.bindString(12, requirement.getEndDate());
        statement.executeInsert();
    }

    public  void update (String jobPos, String degree, int experience, String date_of_birth,
                         String country, String sex, String school, String major, String classify, String detail_experience, String username)
    {
        SQLiteDatabase database= getWritableDatabase();
        String sql="UPDATE USER SET JobPos = ?, degree=?, experience=?, DateOfBirth=?, country=?, sex=?, school=?, major=?, " +
                "classify=?, detail_experience=? WHERE USERNAME= '"+username+"'";
        SQLiteStatement statement= database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, jobPos);
        statement.bindString(2, degree);
        statement.bindDouble(3, experience);
        statement.bindString(4, date_of_birth);
        statement.bindString(5, country);
        statement.bindString(6, sex);
        statement.bindString(7, school);
        statement.bindString(8, major);
        statement.bindString(9, classify);
        statement.bindString(10, detail_experience);
        statement.executeUpdateDelete();

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
