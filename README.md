Android Push Client
==================================================


Example
--------------------------------------
MainActivity
    
	public class MainActivity extends Activity
	{
		private SocketNetworkManager socketNetworkManager;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			socketNetworkManager = SocketNetworkManager.getInstance();
			socketNetworkManager.setOnHandler(handler);
			// data send
			SocketNetworkManager.getInstance().send("");
		}
		Handler handler = new Handler() 
		{
			public void handleMessage(Message msg) {
				// rece data processor
			}
		};
	}


	
  
If error is found, please contact 'loup1788@gmail.com'
