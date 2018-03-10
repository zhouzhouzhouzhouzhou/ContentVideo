package com.example.content.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.content.Entity.Video;
import com.example.content.PlayActivity;
import com.example.content.R;

import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by 佳南 on 2017/9/12.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Context mcontext;
    private List<Video> mList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView videoImage;
        TextView videoName;
        TextView videoTitle;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            videoImage = (ImageView) view.findViewById(R.id.video_img);
            videoName = (TextView) view.findViewById(R.id.video_name);
            videoTitle = (TextView) view.findViewById(R.id.video_title);
        }
    }


    public VideoAdapter(List<Video> list){
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mcontext == null){
            mcontext = parent.getContext();
        }
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_video,parent,false);
        //设置点击cardview 进行跳转传递参数
        final ViewHolder holder = new ViewHolder(view);

        return new ViewHolder(view);
//        return  holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Video video = mList.get(position);
        holder.videoTitle.setText(video.getTitle());
        holder.videoName.setText(video.getName());
        Glide.with(mcontext).load(Uri.parse(video.getThumbnail())).into(holder.videoImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Video video = mList.get(position);
//                Toast.makeText(mcontext,"tile"+video.getName(),Toast.LENGTH_SHORT).show();
//                Log.i(TAG, "onClick: "+video.getTitle());
                String videoId = video.getVideoId();
                String videoName = video.getName();
                String thumbnail = video.getThumbnail();
                Log.i(TAG, "onClick: "+video.getVideoId());
                Intent intent = new Intent(mcontext, PlayActivity.class);
                intent.putExtra("videoId",videoId);
                intent.putExtra("videoName",videoName);
                intent.putExtra("videoThumbnail",thumbnail);
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
