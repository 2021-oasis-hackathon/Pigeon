package com.example.barcode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{

    private ArrayList<trash> arrayList;
    private Context context;
    //어댑터에서 액티비티 액션들을 가져올때 사용


    public CustomAdapter(ArrayList<trash> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 실제 리스트뷰가 어대버에 연결된다음 뷰 홀더를 최초로 만들어냄
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        //리스트아이템이 리사이클러뷰 한 컬럼을 만들때 선언을 해주는 부분
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        //매칭
        Glide.with(holder.itemimage)
                .load(arrayList.get(position).getItemimage()) //사진 받아옴
                .into(holder.itemimage);
        holder.barcodenum.setText(arrayList.get(position).getBarcode());//바코드 번호
        holder.name.setText(arrayList.get(position).getName());
        holder.material.setText(arrayList.get(position).getMaterial());
        holder.recycle.setText(arrayList.get(position).getRecycle());
    }

    @Override
    public int getItemCount() {

        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        ImageView itemimage;
        TextView barcodenum;
        TextView name;
        TextView material;
        TextView recycle;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemimage = itemView.findViewById(R.id.itemimage);
            this.barcodenum=itemView.findViewById(R.id.barcodenum);
            this.name=itemView.findViewById(R.id.name);
            this.material=itemView.findViewById(R.id.material);
            this.recycle=itemView.findViewById(R.id.recycle);
        }
    }
}
