package com.example.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<trash> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_show);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); //기존 성능 강화
        layoutManager = new LinearLayoutManager(this); //
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); //trash 객체 담을 어레이 리스트(어댑터 쪽ㅇ로 날림)

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터  베이스 연동
        databaseReference = database.getReference("trash");//db테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지 않게 초기화
                int count=0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //반복문으로 데이터리스트 추출
                    trash trash1 = snapshot.getValue(trash.class); //db로부터 가져온 애를 trash에 쏨
                    if(takebarcode.scanedBarcode==null) {
                        if (typebarcode.typebarcodenum.equals(trash1.barcodenum)) {
                            arrayList.add(trash1);
                            count = 1;
                            typebarcode.typebarcodenum=null;
                            break;
                        }
                    }
                    else {
                        if (takebarcode.scanedBarcode.equals(trash1.barcodenum)) {
                            // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                            arrayList.add(trash1);
                            count = 1;
                            takebarcode.scanedBarcode=null;
                            break;
                        }
                    }
                }

                if (count==0) {
                    Intent intent1 = getIntent();
                    String id = intent1.getStringExtra("id"); //MainActivity로부터 전달받음
                    int reward = intent1.getIntExtra("reward", 0);

                    Intent intent = new Intent(getApplicationContext(),CheckInsertAct.class);
                    intent.putExtra("id",id);
                    intent.putExtra("reward",reward);
                    startActivity(intent); // 바코드 정보 없으면 입력창으로 넘어감
                }
                // 새창띄우고 정보입력 코드
                adapter.notifyDataSetChanged();//리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //db를 가져오던중 에러발생 시
                Log.e("MainActivity", String.valueOf(databaseError.toException()));
            }
        });

        adapter = new CustomAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결결
    }

    public void Menu(View view){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
}
