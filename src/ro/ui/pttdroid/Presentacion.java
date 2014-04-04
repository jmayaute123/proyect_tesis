package ro.ui.pttdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Presentacion extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		
		
		final Button btnServer = (Button) findViewById(R.id.Servidor);
		btnServer.setOnClickListener(new OnClickListener() {
			
			//@Override
			public void onClick(View v) {
				Intent intent = new Intent(Presentacion.this, ServerMain.class);
				btnServer.setBackgroundResource(R.drawable.button_shape_press);
				startActivity(intent);
			}
		});	
		
		final Button btnCliente = (Button) findViewById(R.id.Cliente);
		btnCliente.setOnClickListener(new OnClickListener() {
			
			//@Override
			public void onClick(View v) {
				Intent intent = new Intent(Presentacion.this, ClienteMain.class);
				btnCliente.setBackgroundResource(R.drawable.button_shape_press);
				startActivity(intent);
			}
		});	
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		final Button btnServer = (Button) findViewById(R.id.Servidor);
		btnServer.setBackgroundResource(R.drawable.button_shape);
		final Button btnCliente = (Button) findViewById(R.id.Cliente);
		btnCliente.setBackgroundResource(R.drawable.button_shape);
	}

	
}
