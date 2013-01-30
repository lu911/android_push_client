/* 
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package com.cnp.net.listener;

import java.util.EventListener;

public interface SocketNetworkDataListener extends EventListener {
	 void onDataRecive(Object data);
}
