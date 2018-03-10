package com.example.content;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.content.Fragment.SortFragment;

import static com.example.content.Global.Constant.mainListLable;

public class SortActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Button btn_back;
    private int tabPosition;
    private Fragment[] mFragments;
    private final static String TAG = "SortActivity";
    private final String lable1 = "0-3";
    private final String lable2 = "4-6";
    private final String lable3 = "7-10";
    private final String lable4 = "7-10";

//    private SortFragment fragment1 = new SortFragment();
    private SortFragment fragment1 = SortFragment.newInstance(mainListLable+lable1);
    private SortFragment fragment2 = SortFragment.newInstance(mainListLable+lable2);
    private SortFragment fragment3 = SortFragment.newInstance(mainListLable+lable3);
    private SortFragment fragment4 = SortFragment.newInstance(mainListLable+lable4);
//    private SortFragment fragment1 = SortFragment.newInstance("http://ejoytest.21d.me:8080/RSP/app/main/mainList");
//    Bundle bundle = new Bundle();
//
//            bundle.putString("order","带兵攻打岛国");
//            fragment1.setArguments(bundle);

//    private SortFragment fragment2 = new SortFragment();
//    private SortFragment fragment3 = new SortFragment();
//    private SortFragment fragment4 = new SortFragment();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        tabPosition = getIntent().getIntExtra("position", 0);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //设置点击进入的tab
        tabLayout.getTabAt(tabPosition).select();

        btn_back = (Button) findViewById(R.id.btn_back);
        //注册监听
        viewPager.addOnPageChangeListener(this);


        tabLayout.addOnTabSelectedListener(this);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //添加适配器，在viewPager里引入Fragment
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return fragment1;
                    case 1:
                        return fragment2;
                    case 2:
                        return fragment3;
                    case 3:
                        return fragment4;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }
        });

    }

    //这样就简单解决了联动问题 之前只能改变tab被选中，而viewpager还是没有变化
    @Override
    protected void onResume() {
        super.onResume();
        //设置当前加载的viewpager；
        viewPager.setCurrentItem(tabPosition);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //TabLayout里的TabItem被选中的时候触发
        Log.i(TAG, "onTabSelected: " + tab.getPosition());
        viewPager.setCurrentItem(tab.getPosition());
//        viewPager.setCurrentItem(tabPosition);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //viewPager滑动之后显示触发
        tabLayout.getTabAt(position).select();
        Log.i(TAG, "onPageSelected: " + position);
//        tabLayout.getTabAt(tabPosition).select();
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
