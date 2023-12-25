package com.cookandroid.fairy;


import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CalendarActivity extends AppCompatActivity {
    public ScrollView pageScroll;
    public GridLayout headGrid, editAndRemoveGrid;
    public LinearLayout allPage, calenderLayout;
    private CalendarView calendarView;
    public ImageView photoView;
    private EditText contextEditText;
    public String readDay = null;
    public String str = null;
    public DatePicker datePicker;
    public Button myPageBtn, notiBtn, saveBtn, editBtn, delBtn;
    public TextView todayDate, dailyContent;

    // photoView 클릭 이벤트 처리
    public void onPhotoViewClick(View view) {
        // 이미지 선택 기능 추가
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }
    private ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // 이미지를 선택한 경우 처리
                    Intent data = result.getData();
                    if (data != null) {
                        Uri selectedImageUri = data.getData();
                        // 선택한 이미지에 대한 처리 (예: 이미지뷰에 설정)
                        photoView.setImageURI(selectedImageUri);
                    }
                }
            });
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        pageScroll = findViewById(R.id.pageScroll);
        allPage = findViewById(R.id.allPage);
        headGrid = findViewById(R.id.headGrid);
        myPageBtn = findViewById(R.id.myPageBtn);
        notiBtn = findViewById(R.id.notiBtn);
        calenderLayout = findViewById(R.id.calenderLayout);
        calendarView = findViewById(R.id.calendarView);
        datePicker = findViewById(R.id.datePicker);
        todayDate = findViewById(R.id.todayDate);
        photoView = findViewById(R.id.photoView);
        contextEditText = findViewById(R.id.contextEditText);
        dailyContent = findViewById(R.id.dailyContent);
        saveBtn = findViewById(R.id.saveBtn);
        editAndRemoveGrid = findViewById(R.id.editAndRemoveGrid);
        editBtn = findViewById(R.id.editBtn);
        delBtn = findViewById(R.id.delBtn);

        //좌상단 myPage에 갈 수 있는 버튼
        myPageBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, MyPageActivity.class);
                startActivity(intent);
            }
        });
        //우상단 알림창을 열 수 있는 버튼
        notiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼이 클릭되었을 때 수행할 동작을 여기에 추가
            }
        });

        //calender에서 날짜를 선택했을 때 메모공간 view
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                todayDate.setVisibility(View.VISIBLE);
                todayDate.setText(String.format("%d / %d / %d", year, month + 1, dayOfMonth));
                photoView.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                contextEditText.setText("");
                saveBtn.setVisibility(View.VISIBLE);
                checkDay(year, month, dayOfMonth);
            }
        });
        //saveBtn을 눌렀을 때 저장 및 버튼 종류가 변경
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                saveDiary(readDay);
                str = contextEditText.getText().toString();
                dailyContent.setText(str);
                // SpannableString을 생성
                SpannableString spannableString = new SpannableString(str);

//                // 밑줄 스타일을 적용할 부분의 시작과 끝 인덱스를 지정
//                int start = 0;
//                int end = str.length();

                // R.color.my_color를 통해 색상 리소스에 접근
                int colorRes = R.color.fairy;

                // 리소스에서 실제 색상 값으로 변환
                int fairyColor = ContextCompat.getColor(CalendarActivity.this, colorRes);

                // 밑줄과 색상을 설정하는 Span 생성
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(fairyColor); // 색상을 원하는 색상으로 변경

                // SpannableString에 Span 적용
                //spannableString.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                // TextView에 설정
                dailyContent.setText(spannableString);
                dailyContent.setPaintFlags(dailyContent.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                saveBtn.setVisibility(View.INVISIBLE);
                editBtn.setVisibility(View.VISIBLE);
                delBtn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
                dailyContent.setVisibility(View.VISIBLE);
                // 로그 추가
                Log.d("SaveBtnClick", "dailyContent visibility: " + dailyContent.getVisibility());
            }
        });
    }
    public void checkDay(int cYear,int cMonth,int cDay)
        {
        readDay=""+cYear+"-"+(cMonth+1)+""+"-"+cDay+".txt";
        FileInputStream fis;

        try{
        fis=openFileInput(readDay);

        byte[]fileData=new byte[fis.available()];
        fis.read(fileData);
        fis.close();

        str=new String(fileData);

        contextEditText.setVisibility(View.INVISIBLE);
        dailyContent.setVisibility(View.VISIBLE);
        dailyContent.setText(str);

        saveBtn.setVisibility(View.INVISIBLE);
        editBtn.setVisibility(View.VISIBLE);
        delBtn.setVisibility(View.VISIBLE);

        editBtn.setOnClickListener(new View.OnClickListener(){
@Override
public void onClick(View view){
        contextEditText.setVisibility(View.VISIBLE);
        dailyContent.setVisibility(View.INVISIBLE);
        contextEditText.setText(str);

        saveBtn.setVisibility(View.VISIBLE);
        editBtn.setVisibility(View.INVISIBLE);
        delBtn.setVisibility(View.INVISIBLE);
        dailyContent.setText(contextEditText.getText());
        }
        });
        delBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
            dailyContent.setVisibility(View.INVISIBLE);
            contextEditText.setText("");
            contextEditText.setVisibility(View.VISIBLE);
            saveBtn.setVisibility(View.VISIBLE);
            editBtn.setVisibility(View.INVISIBLE);
            delBtn.setVisibility(View.INVISIBLE);
            removeDiary(readDay);
            // 이미지 초기화
            photoView.setImageResource(R.drawable.placeholder_image);
            }
        });
        if(dailyContent.getText()==null){
            dailyContent.setVisibility(View.INVISIBLE);
            saveBtn.setVisibility(View.VISIBLE);
            editBtn.setVisibility(View.INVISIBLE);
            delBtn.setVisibility(View.INVISIBLE);
            contextEditText.setVisibility(View.VISIBLE);
            }
        }
        catch(Exception e)
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
        fos=openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
        String content="";
        fos.write((content).getBytes());
        fos.close();

        }
        catch(Exception e)
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
        fos=openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
        String content=contextEditText.getText().toString();
        fos.write((content).getBytes());
        fos.close();
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
        }
}