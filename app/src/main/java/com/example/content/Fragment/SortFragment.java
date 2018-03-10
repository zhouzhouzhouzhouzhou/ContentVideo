package com.example.content.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.content.Adapter.VideoAdapter;
import com.example.content.Entity.Video;
import com.example.content.Entity.VideoItemJson;
import com.example.content.Global.Constant;
import com.example.content.Network.NetState;
import com.example.content.R;
import com.example.content.Util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.content.Global.Constant.mainList;

/**
 * Created by 佳南 on 2017/9/13.
 */
//分类fragment
public class SortFragment extends Fragment {

    private List<Video> kindList = new ArrayList<>();
    private VideoAdapter videoAdapter;
    private final static int OK = 1;
    private final static String TAG = "SortFragment";
    private String videoListUrl;
    private View rootView;
    private Message msg;

    public static SortFragment newInstance(String Url) {
        SortFragment mSortFragment = new SortFragment();
        Bundle args = new Bundle();
        args.putString("Url", Url);
        mSortFragment.setArguments(args);
        return mSortFragment;
    }

    public SortFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }
        Log.i(TAG, "看看是不是还在执行: ");
        rootView = inflater.inflate(R.layout.fragment_sort, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            videoListUrl = bundle.getString("Url");
        } else {
            videoListUrl = mainList;
        }
        Log.i(TAG, "onCreateView: " + videoListUrl);
        initView(rootView);
        initData();
        new Thread(runnable).start();
        // Inflate the layout for this fragment
        return rootView;
    }

    private void initData() {

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    videoAdapter.notifyDataSetChanged();
                    break;
                case 2:
                   /* AlertDialog.Builder dialog = new AlertDialog.Builder
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
                   Toast.makeText(getActivity(), "请求网络失败,请连接网络", Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
        }
    };

    private void initView(View view) {
        if (NetState.NETWORK_STATE == Constant.NETWORK_ON) {
            new Thread(runnable).start();
        }
        RecyclerView rvKind = (RecyclerView) view.findViewById(R.id.rv_kind);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        GridLayoutManager layoutKindManager = new GridLayoutManager(view.getContext(), 2);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvKind.setLayoutManager(layoutKindManager);
        videoAdapter = new VideoAdapter(kindList);
        rvKind.setAdapter(videoAdapter);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (TextUtils.isEmpty(videoListUrl)) {
                videoListUrl = "http://ejoytest.21d.me:8080/RSP/app/main/mainList";
            }
//            String videoListUrl = "http://ejoytest.21d.me:8080/RSP/app/main/mainList";
//            String videoListUrl = Constant.mainList;
            HttpUtil.sendOkHttpRequest(videoListUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
//                    Log.i(TAG, "onFailure: 请问网络失败啦");
                    msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
//                    .makeText(getActivity(), "请求网络失败啦。。。！", Toast.LENGTH_SHORT).show();
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
                        String thumbnails = videoItemJson.list.get(i).thumbnail;
                        String name = videoItemJson.list.get(i).name;
                        String title = videoItemJson.list.get(i).title;
                        String videoId = videoItemJson.list.get(i).listId;
                        Video item = new Video(name, thumbnails, title, videoId);
                        kindList.add(i, item);
                    }
                    msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            });
        }
    };
}
