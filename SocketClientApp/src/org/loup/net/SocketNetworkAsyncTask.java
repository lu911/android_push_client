/* 
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package org.loup.net;

import org.loup.net.manager.SocketNetworkManager;

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
		this.isRunning = false;
		super.onCancelled();
	}

	@Override
	protected Void doInBackground(Void... params) 
	{
		this.isRunning = true;
		while(true)
        try
        {
            SocketNetworkManager.getInstance().receive();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}