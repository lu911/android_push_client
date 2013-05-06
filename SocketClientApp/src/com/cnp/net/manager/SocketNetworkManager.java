/* 
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package com.cnp.net.manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.cnp.net.SocketNetworkAsyncTask;
import com.cnp.utils.URLs;
import com.cnp.utils.Utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SocketNetworkManager 
{

	private static Socket socket;
	private Handler handler = null;
	private boolean isSocketErrorNotified = false;
	private static SocketNetworkManager socketNetworkManager;
	private SocketNetworkAsyncTask socketNetworkAsyncTask = SocketNetworkAsyncTask.getInstance();
	
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
			socket.connect(new InetSocketAddress(new URLs().getTCP_SERVER_HOST(), new URLs().getTCP_SERVER_PORT()));
		return socket;
	}
	
	public void closeSocket() throws IOException
	{
		if (socket != null)
			socket.close();
	}
		
	public void send(String msg) 
	{	
		isSocketErrorNotified = false;
		try
		{
			getSocket().getOutputStream().write(UTF8EncodeStringToByte(msg));
			getSocket().getOutputStream().flush();
		} catch (IOException e)
		{
			socketErrorHandler();
		}
		Log.d("CNP", "send msg : " + (msg));
	}
	
	private byte[] UTF8EncodeStringToByte(String msg)
	{
		byte[] buffer = null;
		try
		{
			buffer = msg.getBytes("UTF-8");
			buffer = (buffer.length + "\n" + msg).getBytes("UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return buffer;
	}
	
	public void recv()
	{
		String data = null;
		int length = 0, readbytes = 0;
		Map<String, Object> readData = null;
		
		// read length
		readData = getReadData(16);
		if(isSuccessRecv(readData))
		{
			String[] header = readData.get("data").toString().split("\n");
			length = getLengthFromHeader(header[0]);
			if(header.length > 1)
			{
				data = header[1];
				length = length - data.length();
			}
			else
			{
				data = "";
			}
			
			// read data
			readData = getReadData(length);
			readbytes = Integer.parseInt(readData.get("readbytes").toString());
			data += readData.get("data").toString();
			while(readbytes != length)
			{
				readData = getReadData(length-readbytes);
				readbytes += Integer.parseInt(readData.get("readbytes").toString());
				data += readData.get("data").toString();
			}
			sendDataToActivity(data);
		}
	}

	private InputStream getInputStreamFromSocket()
	{
		InputStream is = null;
		try
		{
			is = getSocket().getInputStream();
		} catch (IOException e)
		{
			socketErrorHandler();
		}
		return is;
	}
	
	private Map<String, Object> getReadData(int length)
	{
		int readbytes = 0;
		String data = null;
		byte[] buf = new byte[length];
		Map<String, Object> map = new HashMap<String, Object>();
		InputStream is = getInputStreamFromSocket();
		if(is != null)
		{
			try
			{
				readbytes = is.read(buf);
				data = new String(buf, 0, readbytes, "UTF-8");
			} catch (IOException e)
			{
				socketErrorHandler();
				data = "ERROR";
				readbytes = -1;
			}
		}
		else
		{
			socketErrorHandler();
			data = "ERROR";
			readbytes = -1;
		}
		map.put("readbytes", readbytes);
		map.put("data", data);
		return map;
	}
	
	private int getLengthFromHeader(String header)
	{
		if(new Utils().isNumber(header))
		{
			return  Integer.parseInt(header);
		}
		else
		{
			return 0;
		}
	}

	private boolean isSuccessRecv(Map<String, Object> map)
	{
		boolean result = true;
		if(map.get("data").toString().equals("ERROR") || Integer.parseInt(map.get("readbytes").toString()) == -1)
			result = false;
		else
			isSocketErrorNotified = false;
		return result;
	}
	
	private void socketErrorHandler()
	{
		socket = null;
		if(!isSocketErrorNotified)
		{
			sendDataToActivity("SocketError");	
			isSocketErrorNotified = true;
		}
	}
	
	private void sendDataToActivity(String data)
	{
		if(handler != null)
		{
			Message msg = handler.obtainMessage();
			msg.obj = data;
			handler.sendMessage(msg);
		}
	}
	
	public void setOnHandler(Handler handler)
	{
		this.handler = handler;
		if(!socketNetworkAsyncTask.isRunning)
			socketNetworkAsyncTask.execute();
	}
}
