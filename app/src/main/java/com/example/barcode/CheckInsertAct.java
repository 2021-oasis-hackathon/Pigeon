package com.example.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CheckInsertAct extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkinsert);

    }

    public void insert(View view){
        Intent intent = new Intent(getApplicationContext(), InsertRecycleAct.class);
        startActivity(intent); // 정보 입력 창으로 이동
    }


    public void backtomenu(View view){
        finish(); // 메인 메뉴로 돌아가기
    }

}
