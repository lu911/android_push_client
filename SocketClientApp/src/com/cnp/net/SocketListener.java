/* 
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package com.cnp.net;

import android.os.AsyncTask;
import android.util.Log;

public class SocketListener extends AsyncTask<Void, Void, Void> {

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
		Log.d("fuck","async task cancel");
	}

	@Override
	protected Void doInBackground(Void... arg0) 
	{
		while(true)
			SocketNertworkManager.receiveMessage(0);
	}	
}