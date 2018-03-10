package com.example.content;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.content.Adapter.ButtonAdapter;
import com.example.content.Base.BaseActivity;
import com.example.content.CustomView.CustomMediaController;
import com.example.content.DB.DatabaseHelper;
import com.example.content.Entity.ButtonSet;
import com.example.content.Entity.EpisodeInfo;
import com.example.content.Entity.EpisodeJson;
import com.example.content.Global.Constant;
import com.example.content.Util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.CenterLayout;
import io.vov.vitamio.widget.VideoView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static io.vov.vitamio.widget.VideoView.VIDEO_LAYOUT_SCALE;


public class PlayActivity extends BaseActivity implements MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {
    //视频地址
    private String videoId;
    private String path = "http://rsp-1253700700.cosgz.myqcloud.com/high/4C7CC8FF-8BFA-4691-8B76-FF10A702DEEC.mp4";
    private String subListUrl;
    private String episodeInfoUrl;
    private Uri uri;
    private ProgressBar pb;
    private TextView downloadRateView, loadRateView;
    private CustomMediaController mCustomMediaController;
    private VideoView mVideoView;
    private ConstraintAnchor mAnchor;
    private Button btn_video_detail;
    private LinearLayout ll_video_info;
    private LinearLayout ll_vidoe_esposide;
    private static String flag;
    private final static String TAG = "PlayActivity";
    private final static String TAG1 = "PlayActivity1";
    private ButtonAdapter episodeAdapter;
    private ButtonAdapter allEpisodeAdapter;
    private RecyclerView rv_button;
    private List<ButtonSet> list = new ArrayList<>();
    private List<ButtonSet> episodelist = new ArrayList<>();
    private RecyclerView rv_dialog_episode;
    private int episodes;
    private int subEpisode;
    private String videoName;
    private TextView video_name;
    private String videoUrl;
    private LoadUrlTask loadUrlTask;
    private TextView tv_episode;
    private List<EpisodeInfo> episodeList = new ArrayList<>();
    private int Tag;
    private CheckBox chk_collection;
    private DatabaseHelper dbHelper;
    private String dbEpisode;
    private String dbName;
    private String dbThumbnail;
//            = "http://rsp-1253700700.cosgz.myqcloud.com/picture/7BD5FD17-D9A1-431B-882C-0EA89C1C202F.jpg";
    private String dbUrl;
    private String dbId;
    private static int newVersion = 2;
    private boolean hasData = false;
    private PopupWindow mPopupWindow;
    private CenterLayout videoLayout;
    private TextView updateToEpisode;
    private TextView kindLabel;
    private String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = PlayActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        //必须写这个，初始化加载库文件
        Vitamio.isInitialized(this);
        //设置视频解码监听
        /*if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }*/

//        GetEpisodeInfoRunnable a = new GetEpisodeInfoRunnable();
        dbHelper = new DatabaseHelper(this, "Collection.db", null, newVersion);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoName = getIntent().getStringExtra("videoName");
        videoId = getIntent().getStringExtra("videoId");
        if (!TextUtils.isEmpty(videoId)) {
            dbUrl = videoId;
        }
        if (videoName != null) {
            video_name.setText(videoName);
            dbName = videoName;
            subListUrl = Constant.subListConcat + videoId;

        }
        Log.i(TAG, "onResume: " + subListUrl);
        /*更改省着button  会错乱*/
//        episodeAdapter.notifyDataSetChanged();
        Log.i(TAG, "onResume: " + videoId);
        if (episodelist.size() > 0 && videoId == null) {
            mVideoView.stopPlayback();
            playVideo();
        } else {
            new LoadUrlTask().execute();
        }

//        episodeInfoUrl = Constant.detailList + episodeList.get(0).getId();
        Log.i(TAG, "onResume:episodeInfoUrl "+episodeInfoUrl);
       /* GetEpisodeInfoRunnable a = new GetEpisodeInfoRunnable();
        new Thread(a).start();*/


        Log.i(TAG1, "onResume: " + episodelist.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG1, "onDestroy: " + episodelist.size());
//        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_play;
    }

    RelativeLayout.LayoutParams lp;

    private void initView() {
        chk_collection = (CheckBox) findViewById(R.id.chk_collection);
        tv_episode = (TextView) findViewById(R.id.tv_episode);
        mVideoView = (VideoView) findViewById(R.id.buffer);
//        lp = (RelativeLayout.LayoutParams) mVideoView.getLayoutParams();
//        lp.height = (int) DensityUtils.px2dp(this, 720);
        mCustomMediaController = new CustomMediaController(this, mVideoView, this);
        videoLayout = (CenterLayout) findViewById(R.id.viewLayout);
        pb = (ProgressBar) findViewById(R.id.probar);
        downloadRateView = (TextView) findViewById(R.id.download_rate);
        loadRateView = (TextView) findViewById(R.id.load_rate);
        btn_video_detail = (Button) findViewById(R.id.btn_video_detail);
        ll_video_info = (LinearLayout) findViewById(R.id.ll_video_info);
        ll_vidoe_esposide = (LinearLayout) findViewById(R.id.ll_vidoe_esposide);
        video_name = (TextView) findViewById(R.id.tv_video_name);
        rv_button = (RecyclerView) findViewById(R.id.rv_episode);
        episodeAdapter = new ButtonAdapter(PlayActivity.this, list);
        GridLayoutManager layoutKindManager = new GridLayoutManager(this, 5);
        rv_button.setLayoutManager(layoutKindManager);
        rv_button.setAdapter(episodeAdapter);
    }

    //初始化数据
    private void initData() {
        dbThumbnail = getIntent().getStringExtra("videoThumbnail");
        videoName = getIntent().getStringExtra("videoName");
        video_name.setText(videoName);
        dbName = videoName;
        ll_video_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = "video_info";
                new Thread(GetEpisodeInfoRunnable).start();
                Log.i(TAG, "onClick:episode "+episodeInfoUrl);
                showPopWindow();
            }
        });
        ll_vidoe_esposide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = "video_esposide";
                showPopWindow();
            }
        });
        chk_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_collection.isChecked()) {
                    insertDB();
                } else {
                    deleteDB();
                }
            }
        });
    }

    private void deleteDB() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("collection", "videoId = ?", new String[]{dbId});
    }

    public void queryDb() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Collection", null, null, null, null, null, null);

        if (cursor.moveToNext()) {
            do {
                String videoId = cursor.getString(cursor.getColumnIndex("videoId"));
                if (videoId.equals(dbId)) {
                    hasData = true;
                    return;
                } else {
                    hasData = false;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void insertDB() {
        queryDb();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (hasData) {
            Log.i(TAG, "insertDB: 不需要插入数据");
        } else {
            values.put("videoId", dbId);
            values.put("name", dbName);
            values.put("episode", dbEpisode);
            values.put("thumbnail", dbThumbnail);
            values.put("url", dbUrl);
            db.insert("Collection", null, values);
            values.clear();
            Log.i(TAG, "插入成功");
        }
    }

    private void playVideo() {
        String url = "";
        if (episodeList.size() > 0) {
            url = episodeList.get(Tag).getUrl();
//            dbUrl = url;
            dbId = episodeList.get(Tag).getId();
        } else {
            url = path;
        }
        episodeInfoUrl = Constant.detailList + episodeList.get(0).getId();
       /* GetEpisodeInfoRunnable a = new GetEpisodeInfoRunnable();
        new Thread(a).start();*/
        Log.i(TAG, "onProgressUpdate: "+episodeInfoUrl);

        mCustomMediaController.setVideoName(episodeList.get(Tag).getTitle());
        dbEpisode = episodeList.get(Tag).getTitle();
        uri = Uri.parse(url);
        mVideoView.setVideoURI(uri);//设置视频播放地址
        mCustomMediaController.show(500);
        mVideoView.setMediaController(mCustomMediaController);
//        mVideoView.setVideoLayout(VIDEO_LAYOUT_SCALE);

//        mVideoView.setMediaController(new MediaController(this));

//        mVideoView.setMediaController(new MediaController(this));
//        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);//高画质
//        mVideoView.requestFocus();
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnBufferingUpdateListener(this);

//        mVideoView.setVideoLayout(VIDEO_LAYOUT_ORIGIN, 0);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        /*if (width > height) {
                            lp.height = (int) DensityUtils.px2dp(PlayActivity.this, 720);
                        }*/
                        int widths = mp.getVideoWidth();
                        int heights = mp.getVideoHeight();
                        Log.i(TAG, "onVideoSizeChanged: " + width + height);
                        Log.i(TAG, "onPrepared:" + "width" + widths + " height " + heights);
                    }
                });
            }
        });
    }

    /*弹出集数的对话框*/
    private void showPopWindow() {
        View inflate = null;
        if (flag.equals("video_info")) {
            inflate = LayoutInflater.from(this).inflate(R.layout.dialog_video_detail, null);
            updateToEpisode = (TextView) inflate.findViewById(R.id.update_episode);
            updateToEpisode.setText("更新至："+String.valueOf(episodes)+"集");
            kindLabel = (TextView) inflate.findViewById(R.id.kindLabel);
            kindLabel.setText("类别："+info);


        } else if (flag.equals("video_esposide")) {
            inflate = LayoutInflater.from(this).inflate(R.layout.dialog_video_episode, null);
            rv_dialog_episode = (RecyclerView) inflate.findViewById(R.id.rv_dialog_episode);
            allEpisodeAdapter = new ButtonAdapter(PlayActivity.this, episodelist);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
            rv_dialog_episode.setLayoutManager(layoutManager);
            rv_dialog_episode.setAdapter(allEpisodeAdapter);
        }
        Button btn_colse_detail = (Button) inflate.findViewById(R.id.btn_close);
        btn_colse_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow = new PopupWindow(inflate, GridLayoutManager.LayoutParams.MATCH_PARENT, GridLayoutManager.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        //设置是返回可以消失popwindow而不是退出当前的活动
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.showAtLocation(findViewById(R.id.video_all_info), Gravity.BOTTOM, 0, 0);
        //显示位于某个控件的下方
        inflate.setFocusable(true);
        mPopupWindow.showAsDropDown(inflate);
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    pb.setVisibility(View.VISIBLE);
                    downloadRateView.setText("");
                    loadRateView.setText("");
                    downloadRateView.setVisibility(View.VISIBLE);
                    loadRateView.setVisibility(View.VISIBLE);
                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                mVideoView.start();
                pb.setVisibility(View.GONE);
                downloadRateView.setVisibility(View.GONE);
                loadRateView.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                downloadRateView.setText("" + extra + "kb/s" + "  ");
                break;
        }
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        loadRateView.setText(percent + "%");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //屏幕切换时，设置全屏
        if (mVideoView != null) {
            //ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            //横屏
            if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                lp = (RelativeLayout.LayoutParams) videoLayout.getLayoutParams();
                lp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
                Log.i(TAG, "onConfigurationChanged: 横屏"+"videoLayout"+videoLayout.getHeight()+"mediaController"+mCustomMediaController.getHeight());
                mVideoView.setVideoLayout(VIDEO_LAYOUT_SCALE, 0);
            }
            //竖屏
            else if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                lp = (RelativeLayout.LayoutParams) videoLayout.getLayoutParams();
                lp.height = 422;
                mCustomMediaController.getHeight();
//                mVideoView.setVideoLayout(VIDEO_LAYOUT_SCALE, 0);
                Log.i(TAG, "onConfigurationChanged: 竖屏"+"videoLayout"+videoLayout.getHeight()+"mediaController"+mCustomMediaController.getHeight());

            }

        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //记住要写setIntent
        setIntent(intent);
        //得到int型的intent数据，如果得不到，就默认为0
        //作为选定某一集的下标
        Tag = getIntent().getIntExtra("episode", 0);
        Log.i(TAG, "onNewIntent: " + Tag);
    }
    public class InfoRunnable implements Runnable {
    String tmp;
        @Override
        public void run() {
            HttpUtil.sendOkHttpRequest(tmp, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PlayActivity.this, "请求网络失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });
        }
    }
//"http://ejoytest.21d.me:8080/RSP/app/sub/findOne?subId=11dIErR6EWIttCeVvEq"
     Runnable GetEpisodeInfoRunnable = new  Runnable() {

         @Override
         public void run() {
             HttpUtil.sendOkHttpRequest(episodeInfoUrl, new Callback() {
                 @Override
                 public void onFailure(Call call, IOException e) {
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             Toast.makeText(PlayActivity.this, "请求网络失败！", Toast.LENGTH_SHORT).show();
                         }
                     });
                 }

                 @Override
                 public void onResponse(Call call, Response response) throws IOException {
                     String data = response.body().string();
                     Log.i(TAG, "onResponse: " + data);
                     Gson gson = new Gson();
                    EpisodeJson episodeJson = gson.fromJson(data,EpisodeJson.class);
                     info = episodeJson.video.info;
//                    Log.i(TAG, "info: "+episodeJson.video.info);
                 }
             });
         }
     };
    private class LoadUrlTask extends AsyncTask<Object, Object, List<EpisodeInfo>> {

        @Override
        protected List<EpisodeInfo> doInBackground(Object... params) {
            HttpUtil.sendOkHttpRequest(subListUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PlayActivity.this, "请求网络失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String data = response.body().string();
                    Gson gson = new Gson();
                    EpisodeJson episodeJson = gson.fromJson(data, EpisodeJson.class);
                    episodes = episodeJson.subList.size();
                    Log.i(TAG, "onResponselalalla: "+data);
                    Log.i(TAG, "onResponse: " + episodes);
                    //第一集的设定
                    if (episodeJson.subList.get(0).url.size() > 0) {
                        videoUrl = episodeJson.subList.get(0).url.get(0).cdnUrl;
                        Log.i(TAG, "thread url: " + path);
                    }
                    if (episodeList.size() > 0) {
                        episodeList.clear();
                    }
                    //读取全集
                    for (int i = 0; i < episodeJson.subList.size(); i++) {
                        Log.i(TAG, "videoSize1: " + episodeJson.subList.size());
                        for (int j = 0; j < episodeJson.subList.get(i).url.size(); j++) {
                            String id = episodeJson.subList.get(i).id;
                            String title = episodeJson.subList.get(i).title;
                            String url = episodeJson.subList.get(i).url.get(j).cdnUrl;
                            String name = episodeJson.subList.get(i).url.get(j).name;
                            Log.d(TAG, "title: " + title);
                            Log.d(TAG, "url: " + url);
                            Log.d(TAG, "id: "+id);
                            EpisodeInfo eposide = new EpisodeInfo(title, url, id, name);
                            episodeList.add(j, eposide);
                        }
                        Log.i(TAG, "onResponse: episodeList" + episodeList.size());

                    }
                    //执行启动更新ui
                    publishProgress();
                    Log.i(TAG, "onResponse: " + episodeList.size());
                }

            });
            return episodeList;
        }

        //onPostExecute用于doInBackground执行完后，更新界面UI。
        @Override
        protected void onPostExecute(List<EpisodeInfo> s) {
            Log.i(TAG, "onPostExecute: ");
        }

        //onProgressUpdate方法用于更新进度信息
        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);

            Log.i(TAG, "onProgressUpdate: episodes" + episodes + "episodeList" + episodeList.size());
            if (episodes > 10) {
                subEpisode = 10;
            } else {
                subEpisode = episodes;
            }
            if (list.size() > 0) {
                list.clear();
            }
            for (int i = 0; i < subEpisode; i++) {
                ButtonSet s = new ButtonSet("" + (i + 1));
                list.add(i, s);
            }
            if (episodelist.size() > 0) {
                episodelist.clear();
            }
            for (int i = 0; i < episodes; i++) {
                ButtonSet all = new ButtonSet("" + (i + 1));
                episodelist.add(i, all);
            }
            tv_episode.setText(String.valueOf(episodes) + "集");
            episodeAdapter.notifyDataSetChanged();
            playVideo();
            Log.i(TAG, "onProgressUpdate: " + list.size() + " " + episodeList.size());
           }
    }
}


