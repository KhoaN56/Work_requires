package com.example.work_requires;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.sql.Statement;

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
        String sql ="INSERT INTO Recruitment VALUES(NULL,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
        statement.bindLong(9,requirement.getAmount());
        statement.bindString(10, requirement.getDescription());
        statement.bindString(11, requirement.getRequirement());
        statement.bindString(12, requirement.getBenefit());
        statement.bindString(13, requirement.getEndDate());
        statement.bindLong(14, requirement.getApplied());
        statement.executeInsert();
    }

    public void insert(int idJob, String username){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO DETAIL VALUES(NULL, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, username);
        statement.bindLong(2, idJob);
        statement.executeInsert();
    }

    public  void update (String jobPos, String degree, int experience, String date_of_birth,
                         String country, String sex, String school, String major, String classify,
                         String detail_experience, String username)
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

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS Recruitment(Id_Recruitment INTEGER " +
                "PRIMARY KEY AUTOINCREMENT, Username CHAR(20), JobName CHAR(100), Major NCHAR(50), Area NCHAR(20)," +
                "Salary INTEGER, Degree CHAR(15), Position NCHAR(20), Experience INTEGER, Amount INTEGER," +
                "Description VARCHAR, Requirement NVARCHAR, Benefit NVARCHAR, End_Date CHAR(10), Applied INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS USER (Username VARCHAR(20) PRIMARY KEY NOT NULL," +
                "Type VARCHAR(2), Password VARCHAR(20), Name NVARCHAR(50), Email VARCHAR(50)," +
                "Phone VARCHAR(10), Fax VARCHAR(20), Address NVARCHAR(100), Area NVARCHAR(20), jobPos NVARCHAR(50), " +
                "Degree NVARCHAR(50), Experience  INTEGER, DateOfBirth VARCHAR(10), Country NVARCHAR(30), Sex NVARCHAR(5), " +
                "School NVARCHAR(100), Major NVARCHAR(50), Classify NVARCHAR(20), Detail_experience NVARCHAR(300), CheckLogin VARCHAR(2) DEFAULT '0' )");
        db.execSQL("CREATE TABLE IF NOT EXISTS DETAIL (ID_DETAIL INTEGER PRIMARY KEY" +
                " AUTOINCREMENT, Username CHAR(20), Id_Recruitment INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    /**
     * Dùng để cập nhật số người đăng ký ứng tuyển vào công việc requirement vào CSDL
     * @param applied
     * @param requirement
     */
    public void update(int applied, WorkRequirement requirement) {
        SQLiteDatabase database = getWritableDatabase();
        SQLiteStatement statement = database.compileStatement("UPDATE Recruitment SET Applied = ? " +
                "WHERE Id_Recruitment = '"+requirement.getId()+"'");
        statement.clearBindings();
        statement.bindLong(1, applied);
        statement.executeUpdateDelete();
    }
}
