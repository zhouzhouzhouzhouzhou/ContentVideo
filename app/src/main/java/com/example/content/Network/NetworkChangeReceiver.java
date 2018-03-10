package com.example.content.Network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.example.content.Network.NetState.CheckNetwork;

/**
 * Created by 佳南 on 2017/10/20.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    private Context mContext;


    @Override
    public void onReceive(Context context, Intent intent) {
        CheckNetwork(context);
    }

    /*public void showDialog(String title, String message, String ok) {

        AlertDialog.Builder dialog = new AlertDialog.Builder
                (MyApplication.getContext());
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setPositiveButton(ok, new DialogInterface.
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
    }*/
}


