/*
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package org.loup.net.model;

public class ReceiveDataModel
{
    private int length;
    private byte[] data;

    public ReceiveDataModel(int length, byte[] data)
    {
        this.length = length;
        this.data = data;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public byte[] getData()
    {
        return data;
    }

    public void setData(byte[] data)
    {
        this.data = data;
    }
}
