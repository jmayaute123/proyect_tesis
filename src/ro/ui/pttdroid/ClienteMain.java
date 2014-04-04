package ro.ui.pttdroid;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ClienteMain extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cliente);
		
		TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        
	    
        TabSpec firstTabSpec = tabHost.newTabSpec("tid1");
        TabSpec secondTabSpec = tabHost.newTabSpec("tid1");
        TabSpec threeTabSpec = tabHost.newTabSpec("tid1");
        

        firstTabSpec.setIndicator("¿Cómo funciona?").setContent(new Intent(this,ClienteIntro.class));
        secondTabSpec.setIndicator("Recepción").setContent(new Intent(this,ClienteProcess.class));
        threeTabSpec.setIndicator("Audios").setContent(new Intent(this,ClienteArchivos.class));
        
        tabHost.addTab(firstTabSpec);
        tabHost.addTab(secondTabSpec);
        tabHost.addTab(threeTabSpec);
	}

}
