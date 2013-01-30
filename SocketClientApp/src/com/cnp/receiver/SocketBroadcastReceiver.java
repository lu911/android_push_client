/* 
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package com.cnp.receiver;

import java.util.Calendar;

import com.cnp.service.NetworkService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SocketBroadcastReceiver extends BroadcastReceiver 
{

	@Override
	public void onReceive(Context context, Intent intent) 
	{
	    // TODO Auto-generated method stub
		if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
		{
			Log.d("CNP", "onReceive");
			int SECS = 1000;
			int MINS = 60 * SECS;
			Calendar cal = Calendar.getInstance();
			Intent in = new Intent(context, NetworkService.class);
			PendingIntent pi = PendingIntent.getService(context, 0, in, PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager alarms = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
			alarms.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), MINS, pi);
		}
	}
}
