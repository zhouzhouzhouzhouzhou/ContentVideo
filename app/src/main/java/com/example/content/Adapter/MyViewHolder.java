package com.example.content.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.content.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView delete;
    public LinearLayout layout;
    public ImageView video_img;
    public TextView video_name;
    public TextView video_title;
    public CheckBox btn_check;

    public MyViewHolder(View itemView) {
        super(itemView);
        delete = (TextView) itemView.findViewById(R.id.item_delete);
        layout = (LinearLayout) itemView.findViewById(R.id.ll_content);
        video_img = (ImageView) itemView.findViewById(R.id.video_img);
        video_name = (TextView) itemView.findViewById(R.id.video_name);
        video_title = (TextView) itemView.findViewById(R.id.video_title);
        btn_check = (CheckBox) itemView.findViewById(R.id.btn_check);
    }
}
