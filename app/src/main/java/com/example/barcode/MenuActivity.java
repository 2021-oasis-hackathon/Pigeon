package com.example.barcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_result;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스

    Button scanBtn,btn_rew;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("User");

        Intent intent = getIntent();
        String id = intent.getStringExtra("id"); //MainActivity로부터 전달받음
        int reward = intent.getIntExtra("reward", 0);
        Toast.makeText(MenuActivity.this, String.format("메뉴 액티비티 : %d",reward),Toast.LENGTH_SHORT).show();


        scanBtn = findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);

        btn_rew = findViewById(R.id.btn_rew);
        btn_rew.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) { //리워드 축적하는 코드
                Intent intent = new Intent(getApplicationContext(),Reward.class);
                intent.putExtra("id",id); //uri라는 객체로 가져옴
                intent.putExtra("reward",reward);
                Toast.makeText(MenuActivity.this, String.format("메뉴에서 보낼때 아이디 : %d\n메뉴에서 보낼때 리워드 : %d",id,reward),Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });

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
    public void onClick(View view) {
        scanCode();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Intent intent = getIntent();
        String id = intent.getStringExtra("id"); //MainActivity로부터 전달받음
        final int reward = intent.getIntExtra("reward", 0);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if (result.getContents()!=null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                takebarcode.scanedBarcode=result.getContents();
                builder.setMessage(result.getContents());
                builder.setTitle("스캔 결과");
                builder.setPositiveButton("다시 스캔", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scanCode();
                    }
                }).setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), ShowList.class);
                        intent.putExtra("id",id);
                        intent.putExtra("reward",reward);
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else {
                Toast.makeText(this, "잘못된 바코드입니다", Toast.LENGTH_SHORT).show();
            }
        }else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    public void typingbarcode(View view){
        Intent intent1 = getIntent();
        String id = intent1.getStringExtra("id"); //MainActivity로부터 전달받음
        final int reward = intent1.getIntExtra("reward", 0);

        Intent intent = new Intent(getApplicationContext(),TypingBarcodenumAct.class);
        intent.putExtra("id",id);
        intent.putExtra("reward",reward);
                startActivity(intent); // 바코드 번호 타이핑 실행창 이동
    }

}
