package com.example.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
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
                    if(TakeBarcode.scanedBarcode.equals(trash1.barcodenum)) {
                         // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                        arrayList.add(trash1);
                        count=1;
                        break;
                    }

                }

                if (count==0) {
                    Intent intent = new Intent(getApplicationContext(),CheckInsertAct.class);
                    startActivity(intent);
                }
                // 새창띄우고 정보입력 코드
                /**
                et_test = findViewById(R.id.et_test)
                레이아웃에서 설정해놓은 텍스트 박스 id 불러오고
                str = et_test.getText().toString();
                getText한 값을 문자열로 저장
                **/
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

}
