/* 
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package com.cnp.net;

import com.cnp.net.manager.SocketNetworkManager;

import android.os.AsyncTask;

public class SocketNetworkAsyncTask extends AsyncTask<Void, Void, Void> {
	
	private static SocketNetworkAsyncTask socketNetworkAsyncTask;
	public boolean isRunning = false;
	public SocketNetworkAsyncTask() {}
	
	public static SocketNetworkAsyncTask getInstance(){
		if(socketNetworkAsyncTask == null)
		{
			synchronized (SocketNetworkAsyncTask.class)
			{
				if(socketNetworkAsyncTask == null)
				{
					socketNetworkAsyncTask = new SocketNetworkAsyncTask();
				}
			}
		}
		return socketNetworkAsyncTask;
	}
	
	@Override
	protected void onPreExecute() 
	{
		super.onPreExecute();
	}

	@Override
	protected void onCancelled() 
	{
		// TODO Auto-generated method stub
		this.isRunning = false;
		super.onCancelled();
	}

	@Override
	protected Void doInBackground(Void... params) 
	{
		this.isRunning = true;
		while(true)
				SocketNetworkManager.getInstance().recv();
	}

}