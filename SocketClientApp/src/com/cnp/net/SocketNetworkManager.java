/* 
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package com.cnp.net;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.cnp.net.listener.SocketNetworkDataListener;
import com.cnp.utils.URLs;
import android.util.Log;

public class SocketNetworkManager 
{

	private final static String HOST = new URLs().getTCP_SERVER_URL();
	private final static int PORT = new URLs().getTCP_SERVER_PORT();
	private static Socket socket;
	private static SocketNetworkDataListener socketNetworkDataListener = null;
	private static SocketNetworkManager socketNetworkManager;
	
	public SocketNetworkManager(){}
	
	public static SocketNetworkManager getInstance(){
		if(socketNetworkManager == null)
		{
			synchronized (SocketNetworkManager.class)
			{
				if(socketNetworkManager == null)
				{
					socketNetworkManager = new SocketNetworkManager();
				}
			}
		}
		return socketNetworkManager;
	}
	
	public Socket getSocket() throws IOException
	{
		if(socket == null)
			socket = new Socket();
		
		if(!socket.isConnected())
			socket.connect(new InetSocketAddress(HOST, PORT));
		return socket;
	}
	
	public void closeSocket() throws IOException
	{
		if (socket != null)
			socket.close();
	}
		
	public void send(String msg) throws IOException
	{
		msg = msg.getBytes("UTF-8").length + "\n" + msg;
		byte[] buffer = msg.getBytes("UTF-8");
		getSocket().getOutputStream().write(buffer);
		getSocket().getOutputStream().flush();
		Log.d("fuck", "send msg length: " + (msg.length()));
		Log.d("fuck", "send msg : " + (msg));
	}
	
	public boolean recv(int size)
	{
		byte[] buf = null;
		String data = null;
		
		if(size != 0)
		{
			buf = new byte[size];
		}
		else
		{
			buf = new byte[16];
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
		} 
		catch (Exception e) 
		{
			Log.d("CNP","receiveMessage error : " + e.getMessage());
			socket = null;
			return false;
		}
		if(socketNetworkDataListener != null)
		{
			socketNetworkDataListener.onDataRecive(data);
		}
		return true;
	}

	public void setOnDataReciverListener(SocketNetworkDataListener listener)
	{
		socketNetworkDataListener = listener;
	}
	
}
