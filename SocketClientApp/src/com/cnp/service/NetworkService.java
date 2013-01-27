package com.cnp.service;

import com.cnp.net.SocketListener;
import com.cnp.utils.Utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NetworkService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("fuck", "onStartCommand()½ÇÇàµÊ");
		if(new Utils().isConnected(getApplicationContext())){
			new SocketListener().execute();
		}
		return super.onStartCommand(intent, flags, startId);
	}
}
