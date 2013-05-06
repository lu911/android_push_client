/* 
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package com.cnp.activity;

import com.cnp.net.manager.SocketNetworkManager;

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

public class MainActivity extends Activity implements OnClickListener
{
	
	private Button btn_send = null;
	private TextView tv_viewer = null;
	private EditText et_data = null;
	private SocketNetworkManager socketNetworkManager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
		socketNetworkManager = SocketNetworkManager.getInstance();
		socketNetworkManager.setOnHandler(handler);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_send = (Button)findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        tv_viewer = (TextView)findViewById(R.id.tv_viewer);
        et_data = (EditText)findViewById(R.id.et_data);

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
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
				SocketNetworkManager.getInstance().send(et_data.getText().toString());
			break;
		}
	}
	
	Handler handler = new Handler() 
	{
		public void handleMessage(Message msg) {
			tv_viewer.setText(msg.obj.toString() + "");
    	}
    };
}
