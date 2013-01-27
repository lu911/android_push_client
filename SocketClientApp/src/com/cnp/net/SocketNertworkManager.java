/* 
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package com.cnp.net;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.cnp.utils.URLs;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SocketNertworkManager 
{

	private final static String HOST = new URLs().getTCP_SERVER_URL();
	private final static int PORT = new URLs().getTCP_SERVER_PORT();
	private static Socket socket;
	private static Handler handler;
	
	public static Socket getSocket() throws IOException
	{
		if(socket == null)
			socket = new Socket();
		
		if(!socket.isConnected())
			socket.connect(new InetSocketAddress(HOST, PORT));
		return socket;
	}
	
	public static void closeSocket() throws IOException
	{
		if (socket != null)
			socket.close();
	}
	
	public static void setHandler(Handler newHandler)
	{
		handler = newHandler;
	}
		
	public static void sendMsg(String msg, Handler newHandler) throws IOException
	{
		handler = newHandler;
		byte[] buffer = msg.getBytes("UTF-8");
		getSocket().getOutputStream().write(buffer);
		getSocket().getOutputStream().flush();
		Log.d("fuck", "send msg length: " + (msg.length()));
		Log.d("fuck", "send msg : " + (msg));
	}
	
	public static boolean receiveMessage(int size)
	{
		byte[] buf = null;
		String data = null;
		
		if(size != 0)
		{
			buf = new byte[size];
		}
		else
		{
			buf = new byte[32];
		}
		try 
		{	
			int readbytes = new BufferedInputStream(getSocket().getInputStream()).read(buf);
			if(readbytes == -1)
			{
				socket = null;
				return false;
			}
			else
			{
				data = new String(buf, 0, readbytes, "UTF-8");
			}
			Log.d("fuck", "recv msg : " + (data.toString()));
		} 
		catch (Exception e) 
		{
			Log.d("fuck","receiveMessage error : " + e.getMessage());
			socket = null;
			return false;
		}
		if(handler != null)
		{
			Message msg = handler.obtainMessage();
			msg.obj = data;
			handler.sendMessage(msg);	
		}
		return true;
	}

}
