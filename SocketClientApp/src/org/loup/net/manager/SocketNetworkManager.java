/* 
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */
package org.loup.net.manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

import org.loup.net.NetworkCallback;
import org.loup.net.model.ReceiveDataModel;
import org.loup.utils.URLs;
import org.loup.utils.Utils;

public class SocketNetworkManager 
{
    private static Socket socket;
    private static SocketNetworkManager socketNetworkManager;
    private NetworkCallback networkCallback;

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
	
	private Socket getSocket()
	{
		if(socket == null) socket = new Socket();
		if(!socket.isConnected())
        try
        {
            socket.connect(new InetSocketAddress(URLs.TCP_SERVER_HOST, URLs.TCP_SERVER_PORT));
        }
        catch (IOException e)
        {
            networkCallback.error(e.getMessage());
        }
        networkCallback.connect();
        return socket;
	}
	
	public void closeSocket()
	{
		if (socket != null)
        {
            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                networkCallback.error(e.getMessage());
            }
            networkCallback.disconnect();
        }
	}

    public void send(byte[] data)
    {
        try
        {
            getSocket().getOutputStream().write(data);
            getSocket().getOutputStream().flush();
        }
        catch (IOException e)
        {
            networkCallback.error(e.getMessage());
        }
    }

	public void receive()
    {
        int length = 0, readbyte = 0;

        // length
        ReceiveDataModel receiveDataModel = getReceiveDataModel(12);

        try {
            length = getLength(new String(receiveDataModel.getData(), 0, 12, "UTF-8").trim());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        // data
        ByteBuffer data = ByteBuffer.allocate(length);
        while(length > 0 && readbyte != length)
        {
            receiveDataModel = getReceiveDataModel(length);
            readbyte = receiveDataModel.getLength();
            data.put(receiveDataModel.getData());
        }
        if(data.array().length > 0) networkCallback.success(data.array());
	}

    private ReceiveDataModel getReceiveDataModel(int length)
    {
		int readbyte = 0;
        byte[] buf = new byte[length];
        InputStream inputStream = null;
        try
        {
            inputStream = getSocket().getInputStream();
        }
        catch (IOException e)
        {
            networkCallback.error(e.getMessage());
        }

        if(inputStream != null)
        {
            try
            {
                readbyte = inputStream.read(buf);
            }
            catch (IOException e)
            {
                networkCallback.error(e.getMessage());
            }
        }
        return new ReceiveDataModel(readbyte, buf);
    }
	
	private int getLength(String data)
	{
		if(new Utils().isNumber(data)) return Integer.parseInt(data); else return 0;
	}

    public void setNetworkCallback(NetworkCallback networkCallback)
    {
        this.networkCallback = networkCallback;
    }
}
