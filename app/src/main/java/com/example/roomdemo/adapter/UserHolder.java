package com.example.roomdemo.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdemo.R;

public class UserHolder extends RecyclerView.ViewHolder {

    TextView tvId;
    TextView tvName;
    TextView tvAge;

    public UserHolder(@NonNull View itemView) {
        super(itemView);
        tvId = itemView.findViewById(R.id.tv_id);
        tvName = itemView.findViewById(R.id.tv_name);
        tvAge = itemView.findViewById(R.id.tv_age);
    }
}
