package com.example.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class CheckInsertAct extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkinsert);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    public void insert(View view){
        Intent intent1 = getIntent();
        String id = intent1.getStringExtra("id");
        int reward = intent1.getIntExtra("reward", 0);

        Intent intent = new Intent(getApplicationContext(), InsertRecycleAct.class);
        intent.putExtra("id",id);
        intent.putExtra("reward",reward);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }


    public void backtomenu(View view){

        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }

}
