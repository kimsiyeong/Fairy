package com.cookandroid.fairy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "fairyDB.db";
    private static final int DATABASE_VERSION = 1;

//    public DBHelper(@Nullable View.OnClickListener context) {
//        super((Context) context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS User (UID INTEGER PRIMARY KEY autoincrement," +
                "name varchar(20) not null," +
                "id VARCHAR(20) NOT NULL, pw VARCHAR(40) NOT NULL," +
                "birth DATE NOT NULL, intro VARCHAR(100))");
        // UID 는 시스템 상 부여하는 사용자번호, autoincrement
        // name 은 회원가입할 때 썼던 이름
        // id 는 회원가입할 때 썼던 아이디
        // pw 는회원가입할 때 썼던 비밀번호
        // birth 는 생년월일
        // intro 는 사용자의 한줄 소개

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }
}