package com.cookandroid.fairy_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private LinearLayout datePickerContainer;
    private DatePicker datePicker;
    public String readDay = null;
    public String str = null;
    public CalendarView calendarView;
    public Button myPageBtn, notiBtn, cha_Btn, del_Btn, save_Btn;
    public TextView todayDate;
    public EditText contextEditText;

    public void onMyPageButtonClick(View view) {
        // 이미지 버튼이 클릭되었을 때 수행할 동작을 여기에 추가
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = findViewById(R.id.calendarView);
        todayDate = findViewById(R.id.todayDate);
        myPageBtn = findViewById(R.id.myPageBtn);
        notiBtn = findViewById(R.id.notiBtn);
        save_Btn = findViewById(R.id.save_Btn);
        del_Btn = findViewById(R.id.del_Btn);
        cha_Btn = findViewById(R.id.cha_Btn);
        contextEditText = findViewById(R.id.contextEditText);
        datePicker = findViewById(R.id.datePicker);
        // DatePicker를 처음에는 숨김
        datePicker.setVisibility(View.GONE);
        // 날짜가 선택되었을 때의 이벤트 처리

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                todayDate.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                todayDate.setText(String.format("%d / %d / %d", year, month + 1, dayOfMonth));
                contextEditText.setText("");
                checkDay(year, month, dayOfMonth);
            }
        });
        save_Btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view){
                saveDiary(readDay);
                str = contextEditText.getText().toString();
                save_Btn.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
            }
        });

        // 버튼에 클릭 이벤트 처리를 추가
        notiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼이 클릭되었을 때 수행할 동작을 여기에 추가
            }
        });
    }

    // XML에서 지정한 android:onClick 속성에 해당하는 메서드
    public void onButtonClick(View view) {
        // 버튼이 클릭되었을 때 수행할 동작을 여기에 추가
    }

    public void checkDay(int cYear, int cMonth, int cDay)
    {
        readDay = "" + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt";
        FileInputStream fis;

        try {
            fis = openFileInput(readDay);

            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            str = new String(fileData);

            contextEditText.setVisibility(View.INVISIBLE);

            save_Btn.setVisibility(View.INVISIBLE);
            cha_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);

            cha_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contextEditText.setVisibility(View.VISIBLE);
                    contextEditText.setText(str);

                    save_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);
                }
            });
            del_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contextEditText.setText("");
                    contextEditText.setVisibility(View.VISIBLE);
                    save_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);
                    removeDiary(readDay);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay)
    {
        FileOutputStream fos;
        try
        {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = "";
            fos.write((content).getBytes());
            fos.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay)
    {
        FileOutputStream fos;
        try
        {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = contextEditText.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}