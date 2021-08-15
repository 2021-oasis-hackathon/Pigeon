package com.example.barcode;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button scanBtn;
        Button scanBtn1;
        Button InsertNum;
        scanBtn = findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);
        scanBtn1 = findViewById(R.id.scanBtn1);
        InsertNum = findViewById(R.id.InsertNum);
    }


    @Override
    public void onClick(View view) {
        scanCode();
    }

    private void scanCode(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if (result.getContents()!=null){
                //AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //builder.setMessage(result.getContents());
                //builder.setTitle("스캔 결과");
                //builder.setPositiveButton("다시 스캔", new DialogInterface.OnClickListener() {
                    //@Override
                    //public void onClick(DialogInterface dialog, int which) {
                        //scanCode();
                    //}
                //}).setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    //@Override
                    //public void onClick(DialogInterface dialog, int which) {
                        //finish();
                   // }
               // });
                //AlertDialog dialog = builder.create();
               // dialog.show();
                Intent intent = new Intent(MainActivity.this,Water.class);
                startActivity(intent);

            }
            else {
                Toast.makeText(this, "잘못된 바코드입니다", Toast.LENGTH_SHORT).show();
            }
        }else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

}