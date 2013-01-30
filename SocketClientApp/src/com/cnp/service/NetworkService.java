/* 
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package com.cnp.service;

import com.cnp.net.SocketNetworkAsyncTask;
import com.cnp.utils.Utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NetworkService extends Service 
{

	private SocketNetworkAsyncTask socketNetworkAsyncTask = SocketNetworkAsyncTask.getInstance();
	
	@Override
	public IBinder onBind(Intent intent) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		Log.d("CNP", "onStartCommand()");
		if(new Utils().isConnected(getApplicationContext()))
		{
			if(!socketNetworkAsyncTask.isRunning)
				SocketNetworkAsyncTask.getInstance().execute();
		}else{
			Log.d("CNP", "network disconnect");
		}
		return super.onStartCommand(intent, flags, startId);
	}
}
