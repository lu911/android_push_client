/* 
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package com.cnp.service;

import com.cnp.net.SocketNetworkAsyncTask;
import com.cnp.utils.Utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NetworkService extends Service 
{
	private SocketNetworkAsyncTask socketNetworkAsyncTask = SocketNetworkAsyncTask.getInstance();
	
	@Override
	public IBinder onBind(Intent intent) 
	{
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
		if(new Utils().isConnected(getApplicationContext()) && !socketNetworkAsyncTask.isRunning)
			SocketNetworkAsyncTask.getInstance().execute();
		return super.onStartCommand(intent, flags, startId);
	}
}
