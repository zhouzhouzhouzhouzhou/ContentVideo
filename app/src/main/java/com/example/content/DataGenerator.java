package com.example.content;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import com.example.content.Fragment.HomeFragment;
import com.example.content.Fragment.MyFragment;


/**
 * Created by 佳南 on 2017/9/13.
 */
/*确定两个fragment*/
public class DataGenerator {
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static Fragment[] getFragments(String from) {
        Fragment fragments[] = new Fragment[2];
        fragments[0] = HomeFragment.newInstance(from);
        fragments[1] = MyFragment.newInstance(from);
        return fragments;
    }
    /**
     * 获取Tab 显示的内容
     * @param context
     * @param position
     * @return
     */
}