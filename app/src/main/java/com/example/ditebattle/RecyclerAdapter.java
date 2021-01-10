package com.example.ditebattle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private ArrayList<ItemData> mData;
    public RecyclerAdapter(ArrayList<ItemData> dates) {
        this.mData = dates;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.matchinglistview,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNumber.setText(mData.get(position).number);
        holder.tvTitle.setText(mData.get(position).title);
        holder.tvMemo.setText(mData.get(position).memo);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    static class ViewHolder extends RecyclerView.ViewHolder { // 자료를 담고 있는 클래스
        TextView tvNumber, tvTitle, tvMemo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)

            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvMemo = itemView.findViewById(R.id.tvMemo);
        }
    }
}


