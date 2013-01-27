/* 
 * Copyright © 2013 Yuk SeungChan, All rights reserved.
 */

package com.cnp.activity;

import java.io.IOException;

import com.cnp.net.SocketNertworkManager;

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
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
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
			try {
				SocketNertworkManager.sendMsg(et_data.getText().toString(), mHandler);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}
    
	Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			tv_viewer.setText(msg.obj + "");
		}
	};
}
