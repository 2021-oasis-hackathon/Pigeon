package com.example.barcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TypingBarcodenumAct extends AppCompatActivity {
    private EditText typingbarcode;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typing_barcode);
        confirm = findViewById(R.id.confirm);
        typingbarcode = findViewById(R.id.typebarcode);

        confirm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String Typingbarcode = typingbarcode.getText().toString();
                typebarcode.typebarcodenum=Typingbarcode;
                Intent intent = new Intent(getApplicationContext(),ShowList.class);
                startActivity(intent);
            }
        });
    }
}