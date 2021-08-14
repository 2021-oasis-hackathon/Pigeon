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

public class firebaseAdapter extends RecyclerView.Adapter<firebaseAdapter.ViewHolder>{

    private ArrayList<productInf>arrayList;
    private Context context; //컨택한 activity에 대한 context를 가져올 때 사용


    public firebaseAdapter(ArrayList<productInf> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //뷰 연결
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    //각 아이템들에 대한 실제적인 매칭 + 이미지 로딩은 어떻게할것이냐.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getImage())
                .into(holder.iv_image);
                //파이어베이스 데이터를 받아오고 productInf 객체가 있는 arraylist에 담아서 adapter에 쏨.
                // 그걸 이 메소드가 받아서 glide로 로드
        holder.tv_name.setText(arrayList.get(position).getName());
        holder.tv_quality.setText(arrayList.get(position).getQuality());
        holder.tv_way.setText(arrayList.get(position).getWay());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_name;
        TextView tv_quality;
        TextView tv_way;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_image = itemView.findViewById(R.id.iv_image);
            this.tv_name = itemView.findViewById(R.id.tv_name);
            this.tv_quality = itemView.findViewById(R.id.tv_quality);
            this.tv_way = itemView.findViewById(R.id.tv_way);

        }
    }
}
