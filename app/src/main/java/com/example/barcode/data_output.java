package com.example.barcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class data_output extends AppCompatActivity {

    private RecyclerView dataView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<productInf> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_ouput);

        dataView = findViewById(R.id.dataView); //아이디연결
        dataView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        dataView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();//유저 객체를 담을 리스트

        database = FirebaseDatabase.getInstance();//파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("productInf"); //DB 테이블 연결
        //여기서가리키는 productInf는 firebase 콘솔에서 가르키는 productInf
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는 메소드
                arrayList.clear();//기존 배열 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) { //반복문으로 데이터 list 추출
                    productInf inf = snapshot.getValue(productInf.class);
                    //만들어왔던 productInf 객체에 데이터를 담는다.(productInf클래스 안에 데이터를 넣고 arraylist에 담아서 adapter로 전송)
                    arrayList.add(inf); //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //DB에러 발생 시
                Log.e("data_ouput", String.valueOf(error.toException()));//에러문 출력
            }
        });

        adapter = new firebaseAdapter(arrayList, this);
        dataView.setAdapter(adapter); //어댑터 연결
    }
}