package ro.ui.pttdroid;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ServerMain extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.server);
		
	    TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        
    
        TabSpec firstTabSpec = tabHost.newTabSpec("tid1");
        TabSpec secondTabSpec = tabHost.newTabSpec("tid1");
        

        firstTabSpec.setIndicator("¿Cómo funciona?").setContent(new Intent(this,ServerIntro.class));
        secondTabSpec.setIndicator("Transmisión").setContent(new Intent(this,ServerProcess.class));
        
        tabHost.addTab(firstTabSpec);
        tabHost.addTab(secondTabSpec);
	}

}
