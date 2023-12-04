package com.cookandroid.fairy;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    private EditText nameSEditText, passwordSEditText, confirmSPasswordEditText;
    private Button signupSButton;
    private Spinner yearSpinner, monthSpinner, daySpinner;
    private TextView passwordMismatchTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameSEditText = findViewById(R.id.nameSEditText);
        passwordSEditText = findViewById(R.id.passwordSEditText);
        confirmSPasswordEditText = findViewById(R.id.confirmSPasswordEditText);
        signupSButton = findViewById(R.id.signupSButton);
        yearSpinner = findViewById(R.id.yearSpinner);
        monthSpinner = findViewById(R.id.monthSpinner);
        daySpinner = findViewById(R.id.daySpinner);
        passwordMismatchTextView = findViewById(R.id.passwordMismatchTextView);
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
                int selectedMonth = Integer.parseInt(monthSpinner.getSelectedItem().toString());
                int maxDays = getMaxDays(selectedMonth);
                updateDaySpinner(maxDays);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // 아무 것도 선택되지 않았을 때의 처리
            }
        });

        confirmSPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // '비밀번호'와 '비밀번호 확인'이 일치하는지 확인
                if (!passwordSEditText.getText().toString().equals(confirmSPasswordEditText.getText().toString())) {
                    passwordMismatchTextView.setVisibility(View.VISIBLE);
                } else {
                    passwordMismatchTextView.setVisibility(View.GONE);
                }
            }
        });
    }

    // 해당 월의 최대 일 수를 반환하는 메서드
    private int getMaxDays(int month) {
        if (month == 2) {
            // 2월은 28일로 처리 (편의상 윤년은 고려하지 않음)
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