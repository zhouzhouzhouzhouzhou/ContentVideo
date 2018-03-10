package com.example.content;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.content.Adapter.CollectionAdapter;
import com.example.content.CustomView.ItemRemoveRecyclerView;
import com.example.content.CustomView.OnItemClickListener;
import com.example.content.DB.DatabaseHelper;
import com.example.content.Entity.Video;

import java.util.ArrayList;

public class CollectionActivity extends AppCompatActivity {
//Uniform Resource Locator URL  统一资源定位器
    private ItemRemoveRecyclerView recyclerView;
    private ArrayList<Video> mList  = new ArrayList<>();
    private ArrayList<Video> deleteList = new ArrayList<>();
    private TextView tv_edit;
    private LinearLayout ll_bottom;
    private TextView tv_delete;
    private TextView tv_select_all;
    private CollectionAdapter adapter;
    private boolean flag_choose = false ;
    private DatabaseHelper dbHelper;
    private final static String TAG = "CollectionActivity";
    private int newVersion = 2;
    private LinearLayout ly_empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        dbHelper = new DatabaseHelper(this, "Collection.db", null, newVersion);
        initView();
        initData();
    }
    private void deleteDB(String videoName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("collection", "name = ?", new String[] { videoName });
    }
    public void queryDb (){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Collection",null,null,null,null,null,null);
        int i = 0;

        if (cursor.moveToNext()) {
            do {
//                String videoId = cursor.getString(cursor.getColumnIndex("videoId"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String episode = cursor.getString(cursor.getColumnIndex("episode"));
                String thumbnail = cursor.getString(cursor.getColumnIndex("thumbnail"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                String videoId = cursor.getString(cursor.getColumnIndex("videoId"));
                Video video = new Video(name,thumbnail,episode,url);
                mList.add(i,video);
                i++;
                Log.i(TAG, "url"+url+"thumbnail: "+" "+thumbnail+" "+episode+ " "+" "+name+"name"+i+"videoId"+videoId);
            } while (cursor.moveToNext());
        }
        cursor.close();
//        return flag;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

   /* private void deleteDB() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("collection", "videoId = ?", new String[] { "11dIErR6EWIttCeVvEq" });
    }*/
    private void initData() {
        /*Video video = new Video("小猪佩奇", "http://rsp-1253700700.cosgz.myqcloud.com/picture/7BD5FD17-D9A1-431B-882C-0EA89C1C202F.jpg", "第一集","");
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add(i, video);
        }*/
        queryDb();
        if (mList.size() <= 0) {
            ly_empty.setVisibility(View.VISIBLE);
        }
        adapter = new CollectionAdapter(this, mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String videoId = mList.get(position).getVideoId();
                String videoName = mList.get(position).getName();
                Intent intent = new Intent(CollectionActivity.this, PlayActivity.class);
                intent.putExtra("videoId",videoId);
                intent.putExtra("videoName",videoName);
                Log.i(TAG, "onItemClick: "+videoId+" "+videoName);
                startActivity(intent);
//                Toast.makeText(CollectionActivity.this, "** " + mList.get(position) + " **", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onDeleteClick(int position) {
                deleteDB(mList.get(position).getName());
                adapter.removeItem(position);
                Log.i(TAG, "onDeleteClick: "+position+mList.size());
                for(int i = 0; i < mList.size(); i ++){
                    Log.i(TAG, "onDeleteClick: "+mList.get(i).getName());
                }
//                Log.i(TAG, "onDeleteClick: "+mList.get(position).getVideoId());

            }

        });
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textChange();
            }
        });
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    for(int i = 0; i < mList.size(); i++) {
                        Log.i(TAG, "onClick: "+i);
                        if(mList.get(i).isCheck) {
                            deleteDB(mList.get(i).getName());
                            Log.i(TAG, "check: "+i);
//                            mList.remove(i);
                            adapter.removeItem(i);
                            i--;
                        }
                    }adapter.notifyDataSetChanged();
            }
        });
        tv_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAll();
            }
        });
    }

    private void initView() {
        tv_delete = (TextView) findViewById(R.id.tv_delete);
        recyclerView = (ItemRemoveRecyclerView) findViewById(R.id.id_item_remove_recyclerview);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        tv_edit = (TextView) findViewById(R.id.tv_edit);
        tv_select_all = (TextView) findViewById(R.id.tv_select_all);
        ly_empty = (LinearLayout) findViewById(R.id.ly_empty);
    }
    public void textChange() {
        adapter.flag = !adapter.flag;
        if(adapter.flag){
            tv_edit.setText("取消");
            ll_bottom.setVisibility(View.VISIBLE);
        }
        else {
            tv_edit.setText("编辑");
            ll_bottom.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }
    private void insertDB() {
        Log.i(TAG, "insertDB: ");
    }
    private void chooseAll() {
        flag_choose = !flag_choose;
        if (flag_choose) {
            tv_select_all.setText("全不选");
            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).isCheck = true;
            }
        }
        else {
            tv_select_all.setText("全选");
            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).isCheck = false;
            }
        }
        adapter.notifyDataSetChanged();
    }

}
