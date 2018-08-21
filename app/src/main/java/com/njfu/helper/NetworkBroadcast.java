package com.njfu.helper;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.njfu.scandemo.MainActivity;


/**
 * @ClassName NetworkBroadcast
 * @Description
 * @Author lin
 * @DATE 2018/8/20 15:29
 **/
public class NetworkBroadcast extends BroadcastReceiver{

    private Context context;

    public NetworkBroadcast(Context context){
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null &&networkInfo.isAvailable()){

        }else{
            //网络不可用，弹窗，确定退出
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("");
            dialog.setMessage("网络不可用，请连接网络后重试");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
            dialog.show();
        }
    }
}