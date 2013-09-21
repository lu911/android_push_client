Android Push Client
==================================================


Example
--------------------------------------
MainActivity
    
	public class MainActivity extends Activity  implements NetworkCallback
	{
		private SocketNetworkManager socketNetworkManager;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			socketNetworkManager = SocketNetworkManager.getInstance();
			socketNetworkManager.setNetworkCallback(this);
			// data send
			SocketNetworkManager.getInstance().send(data);
		}
		Handler handler = new Handler() 
		{
			public void handleMessage(Message msg) {
				// rece data processor
			}
		};
            @Override
	    public void connect()
	    {
	        Log.d("loup", "MainActivity Socket Connect");
	    }
	
	    @Override
	    public void disconnect()
	    {
	        Log.d("loup", "MainActivity Socket Disconnect");
	    }
	
	    @Override
	    public void success(byte[] data)
	    {
	    }
	
	    @Override
	    public void error(String data)
	    {
	
	    }

	}


	
  
If error is found, please contact 'loup1788@gmail.com'
