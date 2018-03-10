package com.example.content.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.content.Adapter.OnRecyclerviewScrollListener;
import com.example.content.Adapter.SortAdapter;
import com.example.content.Adapter.VideoAdapter;
import com.example.content.Entity.Sort;
import com.example.content.Entity.Video;
import com.example.content.Entity.VideoItem;
import com.example.content.Entity.VideoItemJson;
import com.example.content.Global.Constant;
import com.example.content.R;
import com.example.content.SortActivity;
import com.example.content.Util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 佳南 on 2017/9/13.
 */
//首页fragment
public class HomeFragment extends Fragment {
    private List<Sort> sortList = new ArrayList<>();
    private List<Video> kindList = new ArrayList<>();
    private SortAdapter sortAdapter;
    private VideoAdapter videoAdapter;
    private RecyclerView rv_kind;
    private LinearLayout ll_above;
    private final static int OK = 1;
    private Sort[] sorts = {new Sort(R.mipmap.img1), new Sort(R.mipmap.img2), new Sort(R.mipmap.img3), new Sort(R.mipmap.img4)};
    private final String TAG = "HomeFragment";
    private Message msg;

    public static HomeFragment newInstance(String index) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    videoAdapter.notifyDataSetChanged();
                    break;
                case 2:
//                    Toast
                  /*  AlertDialog.Builder dialog = new AlertDialog.Builder
                            (getActivity());
                    dialog.setTitle("请求网络失败");
                    dialog.setMessage("请连接网络");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("确认", new DialogInterface.
                            OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.
                            OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();*/
                  Toast.makeText(getActivity(),"当前网络不可用，请连接网络",Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.i(TAG, "onCreateView: ");
        initView(view);
        initData();

        // Inflate the layout for this fragment
        return view;
    }

    private void initData() {

        if (sortList.size() > 0) {
            sortList.clear();
        }
        for (int i = 0; i < 4; i++) {
            sortList.add(i, sorts[i]);
        }
        MyRunnable runnable = new MyRunnable();
        new Thread(runnable).start();
        msg = new Message();

        /*监听滑动recyclerview滑动*/
        rv_kind.setOnScrollListener(new OnRecyclerviewScrollListener() {
            @Override
            protected void onScrollDown() {
//                ll_above.setVisibility(View.GONE);
                Log.i(TAG, "onScrollDown: ");
            }

            @Override
            protected void onScrollUp() {
//                ll_above.setVisibility(View.GONE);
//                Log.i(TAG, "onScrollUp: ");
            }

            @Override
            protected void onScrollToBottom() {
//                ll_above.setVisibility(View.GONE);
//                Log.i(TAG, "onScrollToBottom: ");
            }

            @Override
            protected void onScrollToTop() {
                //滑动到顶部 没有内容 上面的内容显示出来
                ll_above.setVisibility(View.VISIBLE);
                Log.i(TAG, "onScrollToTop: ");
            }
        });

        sortAdapter.setOnItemClickListener(new SortAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), SortActivity.class);
                Log.i(TAG, "onItemClick: "+position);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }

    private void initView(final View view) {
        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.rv_sort);
        rv_kind = (RecyclerView) view.findViewById(R.id.rv_kind);
        ll_above = (LinearLayout) view.findViewById(R.id.ll_above);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        GridLayoutManager layoutKindManager = new GridLayoutManager(view.getContext(), 2);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_kind.setLayoutManager(layoutKindManager);
        recyclerview.setLayoutManager(layoutManager);

        sortAdapter = new SortAdapter(sortList);
        recyclerview.setAdapter(sortAdapter);
        videoAdapter = new VideoAdapter(kindList);
        rv_kind.setAdapter(videoAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            String videoListUrl = Constant.mainList;
            HttpUtil.sendOkHttpRequest(videoListUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    msg.what = 2;
                    handler.sendMessage(msg);

//                    AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String data = response.body().string();
                    Gson gson = new Gson();
                    VideoItemJson videoItemJson = gson.fromJson(data, VideoItemJson.class);

                    if (kindList.size() > 0) {
                        kindList.clear();
                    }
                    for (int i = 0; i < videoItemJson.list.size(); i++) {
                        VideoItem videoItem = videoItemJson.list.get(i);
                        String thumbnails = videoItem.thumbnail;
                        String name = videoItem.name;
                        String title = videoItem.title;
                        String videoId = videoItem.listId;
                        Video item = new Video(name, thumbnails, title, videoId);
                        kindList.add(i, item);
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }

            });
        }
        }
}
