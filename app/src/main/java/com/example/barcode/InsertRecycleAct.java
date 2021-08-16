package com.example.barcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class InsertRecycleAct extends AppCompatActivity {
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText name, material, recycle; // 분리수거 이름, 재질, 방식 입력 필드
    private Button mBtnregihowbunri;    // 분리수거 등록 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("trash");

        name = findViewById(R.id.et_pdId); //제품명
        material = findViewById(R.id.et_pdmat); //재질
        recycle = findViewById(R.id.et_howbunri); //방식
        mBtnregihowbunri = findViewById(R.id.btn_regibunri);

        mBtnregihowbunri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //등록하기 버튼 클릭 후 액션
                //text박스에 적힌 값을 문자열로 변환 후 변수에 할당
                String Name = name.getText().toString();
                String Material = material.getText().toString();
                String Recycle = recycle.getText().toString();

                addrecycle insertedthing = new addrecycle();
                addrecycle.setBaCord(TakeBarcode.scanedBarcode);
                addrecycle.setNaMe(Name);
                addrecycle.setMaTerial(Material);
                addrecycle.setReCycle(Recycle);
                // setValue : database에 insert (삽입)
                mDatabaseRef.child("trash").setValue(insertedthing);
                Toast.makeText(InsertRecycleAct.this, "분리수거 정보가 등록되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}