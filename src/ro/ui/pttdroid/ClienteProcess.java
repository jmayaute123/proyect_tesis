package ro.ui.pttdroid;

import java.net.InetAddress;

import ro.ui.pttdroid.settings.CommSettings;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ClienteProcess extends Activity {
	private ClienteAudio call;
	String puerto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clienteprocess);
		
		puerto = 	String.valueOf(CommSettings.getPort());
    	
    		
		
		
		iniciarecepcion();		

		
		Button inicio = (Button) findViewById(R.id.inicio);
		inicio.setOnClickListener(new OnClickListener() {
			
			//@Override
			public void onClick(View v) {
			
			grava();
			Button fin = (Button) findViewById(R.id.fin);
			fin.setVisibility(View.VISIBLE);
			
			Button inicio = (Button) findViewById(R.id.inicio);
			inicio.setVisibility(View.INVISIBLE);
			
			}
		});
		Button fin = (Button) findViewById(R.id.fin);
		fin.setOnClickListener(new OnClickListener() {
			
			//@Override
			public void onClick(View v) {
				// Button to end the call has been pressed
				nograva();
				
				Button inicio = (Button) findViewById(R.id.inicio);
				inicio.setVisibility(View.VISIBLE);
				
				Button fin = (Button) findViewById(R.id.fin);
				fin.setVisibility(View.INVISIBLE);
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		endCall();
	}
	private void iniciarecepcion(){
		try {
			// Accepting call. Send a notification and start the call
			InetAddress address = InetAddress.getByName("192.168.1.112");
			call = new ClienteAudio(address);
			call.startCall();
			
			
		}
		catch(Exception e) {
			
		}
	}
	
	private void grava() {
		// End the call and send a notification
		call.prepare1();
		call.prepare2();
		call.gravando();
	}
	
	private void nograva() {
		// End the call and send a notification
		call.nogravando();
	}
	
	private void endCall() {
		// End the call and send a notification
			
		call.endCall();
	}
	

}
