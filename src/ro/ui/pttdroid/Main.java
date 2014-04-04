/* Copyright 2011 Ionut Ursuleanu
 
This file is part of pttdroid.
 
pttdroid is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
 
pttdroid is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with pttdroid.  If not, see <http://www.gnu.org/licenses/>. */

package ro.ui.pttdroid;



import ro.ui.pttdroid.codecs.Speex;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class Main extends Activity
{
	
	

	private static final String LOG_TAG1 = "Probando Speex";
	
	
		
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
    	//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    //requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.presentacion);
		init();
		 Thread timer = new Thread(){
	            public void run(){
	                try{
	                    sleep(1000);
	                }catch(InterruptedException e){
	                    e.printStackTrace();
	                }finally{
	                    Intent actividaPrincipal = new Intent(Main.this, Presentacion.class);
	                    finish();
	                    startActivity(actividaPrincipal);
	                }                
	            }
	        };
	        timer.start();  
    }
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	getMenuInflater().inflate(R.menu.menu, menu);
    	return true;
    }

    
    private void init() 
    {    	    	    	
    	
    		   		
    		Speex.open(10);
    		Log.v(LOG_TAG1, "Libreria abierta correctamente");   	 	   		 
    	

    }
    

        
}