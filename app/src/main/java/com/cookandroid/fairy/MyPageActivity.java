package com.cookandroid.fairy;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

public class MyPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        ImageView profileImageView = findViewById(R.id.profileImageView);
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);

        // 프로필 이미지 설정
        profileImageView.setImageResource(R.drawable.profile);

        // 이름과 이메일 설정
        nameTextView.setText("페어리");
        emailTextView.setText("fairy@naver.com");

        profileImageView.setOnClickListener(view -> {
            // 프로필 이미지를 클릭했을 때의 동작
        });
    }
}