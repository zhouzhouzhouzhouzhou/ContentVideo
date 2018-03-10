package com.example.content.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.content.Entity.Sort;
import com.example.content.R;

import java.util.List;

/**
 * Created by 佳南 on 2017/9/12.
 */

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> implements View.OnClickListener {

    private List<Sort> mList;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView videoImage;

        public ViewHolder(View view) {
            super(view);
            videoImage = (ImageView) view.findViewById(R.id.sort_img);
        }
    }

    private OnItemClickListener mOnItemClickListener = null;

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public SortAdapter(List<Sort> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort, parent, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Sort video = mList.get(position);
//        holder.getAdapterPosition();
        holder.videoImage.setImageResource(video.getThumbnail());
//        holder.mTextView.setText(datas[position]);
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
