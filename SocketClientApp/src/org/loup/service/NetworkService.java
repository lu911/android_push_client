/* 
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package org.loup.service;

import android.util.Log;
import org.loup.net.SocketNetworkAsyncTask;
import org.loup.utils.Utils;

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
        Log.d("loup", "onStartCommand");
        if(new Utils().isConnected(getApplicationContext()) && !socketNetworkAsyncTask.isRunning)
			SocketNetworkAsyncTask.getInstance().execute();
            Log.d("loup", "task run");
		return super.onStartCommand(intent, flags, startId);
	}
}
