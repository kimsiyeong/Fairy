package com.cookandroid.fairy;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Display;
import android.view.View;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "fairyDB.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        db = openOrCreateDatabase("fairyDB", null, null);

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

        db.execSQL("CREATE TABLE IF NOT EXISTS Calender (UID INTEGER PRIMARY KEY," +
                "theme varchar(10) not null," +
                "foreign key(UID) references User(UID) on delete cascade)");
        // theme 은 테마

        db.execSQL("CREATE TABLE IF NOT EXISTS Photo (UID INTEGER," +
                "calendarDate Text, photoPath varchar(50) not null," +
                "updateDate Text not null," +
                "PRIMARY KEY (UID, calendarDate)," +
                "FOREIGN KEY(UID) references User(UID) on delete cascade)");
        // calendarDate 는 달력에서 선택한 날짜
        // photoPath 는 사진의 절대경로
        // updateDate 는 최종 수정 날짜

        db.execSQL("CREATE TABLE IF NOT EXISTS Post (UID INTEGER," +
                "calendarDate Text, content varchar(100) not null," +
                "updateDate Text not null," +
                "PRIMARY KEY (UID, calendarDate)," +
                "FOREIGN KEY(UID) references User(UID) on delete cascade)");
        // content는 게시글의 내용

    }

    public boolean checkUser(String id, String pw) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE id = ? AND pw = ?", new String[]{id, pw});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Calendar");
        db.execSQL("DROP TABLE IF EXISTS Photo");
        db.execSQL("DROP TABLE IF EXISTS Post");
        onCreate(db);
    }
}
