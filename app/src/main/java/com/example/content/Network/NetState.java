package com.example.content.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.content.Base.MyApplication;
import com.example.content.Global.Constant;

/**
 * Created by 佳南 on 2017/10/22.
 */

public class NetState {
    public static int NETWORK_STATE = Constant.NETWORK_ON;
    public static int NETWORK_TYPE = Constant.NETWORK_mobile;

    public static void  CheckNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            NETWORK_STATE = Constant.NETWORK_ON;
            if (networkInfo.getType() == connectivityManager.TYPE_WIFI) {
                NETWORK_TYPE = Constant.NETWORK_wifi;
            } else if (networkInfo.getType() == connectivityManager.TYPE_MOBILE) {
//                Toast.makeText(MyApplication.getContext(), "WIFI已断开", Toast.LENGTH_SHORT).show();
//                showDialog("WIFI已断开", "继续播放可能产生流量费用", "继续播放");
                NETWORK_TYPE = Constant.NETWORK_mobile;
            }
        } else {
//            showDialog("当前网络异常", "请重新设置网络", "确认");
            NETWORK_STATE = Constant.NETWORK_OFF;
//            Toast.makeText(MyApplication.getContext(), "当前网络异常", Toast.LENGTH_SHORT).show();

        }
        /*if (networkInfo != null && networkInfo.isAvailable()) {
            Toast.makeText(context,"network is avaliable",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context,"当前网络不可用，请重新设置网络",Toast.LENGTH_SHORT).show();
        }*/
    }
}
