package com.example.content.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.content.AboutActivity;
import com.example.content.CollectionActivity;
import com.example.content.DisclaimerActivity;
import com.example.content.R;


/**
 * Created by 佳南 on 2017/9/13.
 */
//我的fragment
public class MyFragment extends Fragment implements View.OnClickListener {
    private LinearLayout ln_collection;
    private LinearLayout ll_about_we;
    private LinearLayout ll_debt_explanation;

//    public static Fragment newInstance(String from) {
//        Fragment fragment = new Fragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
    public static MyFragment newInstance(String index) {
        MyFragment f = new MyFragment();
        Bundle args = new Bundle();
        args.putString("index", index);
        f.setArguments(args);
        return f;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_my,container,false);
        initView(view);
      return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initView(View view) {
        ln_collection = (LinearLayout) view.findViewById(R.id.ln_collection);
        ll_debt_explanation = (LinearLayout) view.findViewById(R.id.ll_debt_explanation);
        ll_about_we = (LinearLayout) view.findViewById(R.id.ll_about_we);
        ll_about_we.setOnClickListener(this);
        ll_debt_explanation.setOnClickListener(this);
        ln_collection.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ln_collection:
                jumpToActivity();
                break;
            case R.id.ll_about_we:
                Intent intent = new Intent(getContext(), AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_debt_explanation:
                Intent intent2 = new Intent(getContext(), DisclaimerActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    private void jumpToActivity() {
        Intent intent = new Intent(getContext(), CollectionActivity.class);
        startActivity(intent);
    }
}
