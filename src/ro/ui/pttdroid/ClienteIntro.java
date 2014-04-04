package ro.ui.pttdroid;



import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import ro.ui.pttdroid.settings.CommSettings;

public class ClienteIntro extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clienteintro);
		
		init();
	}

	String puerto;
	
	private void init() 
    {    	    	    	
    		CommSettings.getSettings(this);
    	puerto = 	String.valueOf(CommSettings.getPort());
    	
    	//Creating TextView Variable
        //TextView text = (TextView) findViewById(R.id.any_id);
       
        //Sets the new text to TextView (runtime click event)
        //text.setText(puerto);
    	//getIp();
    				
    
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu, menu);
    	return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent i; 
    	
    	switch(item.getItemId()) {
    	case R.id.quit:
    		shutdown();
    		return true;
    	case R.id.settings_comm:
    		i = new Intent(this, CommSettings.class);
    		startActivityForResult(i, 0);    		
    		return true;
    	 		
    	default:
    		return super.onOptionsItemSelected(item);
    	}
	}
	
	
	
	   @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		   CommSettings.getSettings(this);   
	}

	private void shutdown() 
	    {    	  
	    	                   
	        finish();
	    }  

   
	 
	
	/////////////////////////////////////////////////////////////////////////////

}
