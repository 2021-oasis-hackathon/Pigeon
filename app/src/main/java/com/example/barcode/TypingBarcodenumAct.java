package com.example.barcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TypingBarcodenumAct extends AppCompatActivity {
    private EditText typingbarcode;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typing_barcode);
        confirm = findViewById(R.id.confirm);
        typingbarcode = findViewById(R.id.typebarcode);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id"); //MainActivity로부터 전달받음
        int reward = intent.getIntExtra("reward", 0);
        Toast.makeText(TypingBarcodenumAct.this, String.format("타이핑 액티비티 : %d",reward),Toast.LENGTH_SHORT).show();
        confirm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String Typingbarcode = typingbarcode.getText().toString();
                typebarcode.typebarcodenum=Typingbarcode;
                Intent intent = new Intent(getApplicationContext(),ShowList.class);
                intent.putExtra("id",id);
                intent.putExtra("reward",reward);
                startActivity(intent);
                finish();
            }
        });
    }
}