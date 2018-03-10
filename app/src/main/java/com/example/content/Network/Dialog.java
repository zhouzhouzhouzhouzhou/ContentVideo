package com.example.content.Network;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.content.Base.MyApplication;

/**
 * Created by 佳南 on 2017/10/23.
 */

public class Dialog extends Fragment{
    private Context mcontext;
    private String title;
    private String message;
    private String ok;

    public Dialog(String title, String message, String ok) {
        mcontext = MyApplication.getContext();
        this.title = title;
        this.message = message;
        this.ok = ok;
        AlertDialog.Builder dialog = new AlertDialog.Builder
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
        dialog.show();

    }
}
