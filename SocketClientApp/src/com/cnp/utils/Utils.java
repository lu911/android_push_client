package com.cnp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class Utils {

	public boolean isConnected(Context context) {
		boolean connected = false;
		ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobile = conMan.getNetworkInfo(0).getState(); // mobile
		State wifi = conMan.getNetworkInfo(1).getState(); // wifi
		if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
			connected = true;
		} else if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
			connected = true;
		}
		return connected;
	}
}
