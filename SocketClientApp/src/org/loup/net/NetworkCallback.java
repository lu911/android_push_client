/*
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package org.loup.net;

public interface NetworkCallback
{
    public void connect();
    public void disconnect();
    public void success(byte[] data);
    public void error(String data);
}
