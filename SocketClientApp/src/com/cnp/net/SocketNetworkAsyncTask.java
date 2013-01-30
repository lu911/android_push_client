/* 
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package com.cnp.net;

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
		super.onCancelled();
	}

	@Override
	protected Void doInBackground(Void... arg0) 
	{
		while(true){
			this.isRunning = true;
			SocketNetworkManager.getInstance().recv(0);
		}	
	}	
}