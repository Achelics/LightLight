package com.lzq.anniversary.util;

import com.lzq.soeasy.R;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyToast {
	
private static View view;
	
	public static void toast(Context context,String content){
		
		view=LayoutInflater.from(context).inflate(R.layout.my_toast, null);
		TextView textView=(TextView) view.findViewById(R.id.text);
		textView.setText(content);
		Toast toast=new Toast(context);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM, 0, 40);
		toast.setView(view);
		toast.show();
	}
	
	//判断是否有网络连接 
		public static boolean isNetworkConnected(Context context) {  
		    if (context != null) {  
		        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
		                .getSystemService(Context.CONNECTIVITY_SERVICE);  
		        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
		        if (mNetworkInfo != null) {  
		            return mNetworkInfo.isAvailable();  
		        }  
		    }
			return false;  
		    
		}
}
