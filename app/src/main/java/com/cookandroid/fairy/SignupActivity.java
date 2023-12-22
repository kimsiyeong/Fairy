package com.cookandroid.fairy;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    private EditText nameSEditText, idSEditText, passwordSEditText, confirmSPasswordEditText;
    private Button signupSButton;
    private Spinner yearSpinner, monthSpinner, daySpinner;
    private int selectedYear, selectedMonth, selectedDay;

    public SQLiteDatabase getWritableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameSEditText = findViewById(R.id.nameSEditText);
        idSEditText = findViewById(R.id.idSEditText);
        passwordSEditText = findViewById(R.id.passwordSEditText);
        confirmSPasswordEditText = findViewById(R.id.confirmSPasswordEditText);
        signupSButton = findViewById(R.id.signupSButton);
        yearSpinner = findViewById(R.id.yearSpinner);
        monthSpinner = findViewById(R.id.monthSpinner);
        daySpinner = findViewById(R.id.daySpinner);

        // 선택받은 날짜 받는 것


        // 년도 스피너 설정 (2023부터 2000까지)
        List<String> years = new ArrayList<>();
        for (int i = 2023; i >= 2000; i--) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        // 월 스피너 설정 (1부터 12까지)
        List<String> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(String.valueOf(i));
        }
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, months);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        // 일 스피너 설정
        updateDaySpinner(31);  // 초기값은 31로 설정

        // 월 스피너의 선택이 변경되었을 때, 일 스피너 업데이트
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedMonth = Integer.parseInt(monthSpinner.getSelectedItem().toString());
                int maxDays = getMaxDays(selectedMonth);
                updateDaySpinner(maxDays);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // 아무 것도 선택되지 않았을 때의 처리
            }
        });

        signupSButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //db 연결
                try {
                    Toast.makeText(getApplicationContext(), "객체 연결전", Toast.LENGTH_SHORT).show();
                    DBHelper dbHelper = new DBHelper(getApplicationContext()); // 이 액티비티에서 객체 초기화

                    SQLiteDatabase sqlDB;
                    sqlDB = dbHelper.getWritableDatabase( );
                    Toast.makeText(getApplicationContext(), "db 연결", Toast.LENGTH_SHORT).show();

                    String name = nameSEditText.getText().toString(); // EditText에서 이름 가져오기
                    String id = idSEditText.getText().toString();
                    String password = passwordSEditText.getText().toString(); // EditText에서 비밀번호 가져오기

                    // selected 된 생년월일 가지고 오는 코드
                    selectedYear = Integer.parseInt(yearSpinner.getSelectedItem().toString());
                    selectedDay = Integer.parseInt(daySpinner.getSelectedItem().toString());

                    String birth = selectedYear + "-" + selectedMonth + "-" + selectedDay; // 생년월일 문자열로 변환


                    sqlDB.execSQL("INSERT INTO User (name, id, pw, birth, intro) VALUES (?, ?, ?, ?, ?)", new String[]{ name, id, password, birth, "안녕"});


                    Toast.makeText(getApplicationContext(), "sql문 실행", Toast.LENGTH_SHORT).show();

                    sqlDB.close( ); // 열려있는 DB개체 닫기

                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();

                } catch (SQLException e) {
                    Toast.makeText(getApplicationContext(), "sql문 오류", Toast.LENGTH_SHORT).show();
                    Log.e("SQL_ERROR", "SQL Exception: " + e.getMessage());
                }
                catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "db 연결오류", Toast.LENGTH_SHORT).show();
                    Log.e("ERROR", "Exception: " + e.getMessage());
                }
            }
        });
    }

    // 해당 월의 최대 일 수를 반환하는 메서드
    private int getMaxDays(int month) {
        if (month == 2) {
            // 2월은 28일로 처리
            return 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            // 4, 6, 9, 11월은 30일
            return 30;
        } else {
            // 나머지 월은 31일
            return 31;
        }
    }

    // 일 스피너 업데이트 메서드
    private void updateDaySpinner(int maxDays) {
        List<String> days = new ArrayList<>();
        for (int i = 1; i <= maxDays; i++) {
            days.add(String.valueOf(i));
        }
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, days);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
    }
}