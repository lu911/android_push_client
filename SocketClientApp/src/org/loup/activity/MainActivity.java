/* 
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package org.loup.activity;

import android.content.Intent;
import android.util.Log;
import com.cnp.activity.R;
import org.bson.*;
import org.bson.io.BSONByteBuffer;
import org.loup.net.NetworkCallback;
import org.loup.net.manager.SocketNetworkManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.loup.service.NetworkService;

import java.io.UnsupportedEncodingException;

public class MainActivity extends Activity implements OnClickListener, NetworkCallback
{
	
	private Button btn_send = null;
	private TextView tv_viewer = null;
	private EditText et_data = null;
	private SocketNetworkManager socketNetworkManager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
		socketNetworkManager = SocketNetworkManager.getInstance();
		socketNetworkManager.setNetworkCallback(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_send = (Button)findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        tv_viewer = (TextView)findViewById(R.id.tv_viewer);
        et_data = (EditText)findViewById(R.id.et_data);
        Intent in = new Intent(this, NetworkService.class);
        startService(in);

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.btn_send:
            BasicBSONObject basicBSONObject = new BasicBSONObject();
            basicBSONObject.put("url", "/chat/push/");
            basicBSONObject.put("message", et_data.getText().toString());
            SocketNetworkManager.getInstance().send(new BasicBSONEncoder().encode(basicBSONObject));
            break;
		}
	}
	
	private final Handler handler = new Handler()
	{
		public void handleMessage(Message msg) {
			tv_viewer.setText("You Send : " + msg.obj.toString() + "");
    	}
    };

    @Override
    public void connect()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void disconnect()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void success(byte[] data)
    {
        BSONByteBuffer buffer = BSONByteBuffer.wrap(data);
        BSONObject bsonObject = new BasicBSONDecoder().readObject(buffer.array());
        String message = bsonObject.get("message").toString();
        Log.d("loup", "bson : " + bsonObject);
        Log.d("loup", "message : " + message);
        Message msg = handler.obtainMessage();
        msg.obj = message;
        handler.sendMessage(msg);
    }

    @Override
    public void error(String data) {
        Log.d("loup", data);
    }
}
