package ro.ui.pttdroid;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ClienteArchivos extends ListActivity {
	
	private List<String> item = null;
	private List<String> path = null;
	private String root;
	private TextView myPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clientearchivos);
        myPath = (TextView)findViewById(R.id.path);
        
        File external = Environment.getExternalStorageDirectory();
		File audio = new File(external.getAbsolutePath() + "/EstetoscopioDigital"); 
		audio.mkdirs();

		root =	audio.getAbsolutePath();        
        
        getDir(root);
	}
	private void getDir(String dirPath)
    {
    	myPath.setText("Location: " + dirPath);
    	item = new ArrayList<String>();
    	path = new ArrayList<String>();
    	File f = new File(dirPath);
    	File[] files = f.listFiles();
    	
    	if(!dirPath.equals(root))
    	{
    		item.add(root);
    		path.add(root);
    		item.add("../");
    		path.add(f.getParent());	
    	}
    	
    	for(int i=0; i < files.length; i++)
    	{
    		File file = files[i];
    		
    		if(!file.isHidden() && file.canRead()){
    			path.add(file.getPath());
        		if(file.isDirectory()){
        			item.add(file.getName() + "/");
        		}else{
        			item.add(file.getName());
        		}
    		}	
    	}

    	ArrayAdapter<String> fileList =
    			new ArrayAdapter<String>(this, R.layout.clienterow, item);
    	setListAdapter(fileList);	
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		File file = new File(path.get(position));
		
		myPath.setText("Location: " + file.getPath());
		
		MediaPlayer mp = new MediaPlayer();
		try {
			mp.setDataSource(file.getPath());
			mp.prepare();
			mp.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*if (file.isDirectory())
		{
			if(file.canRead()){
				getDir(path.get(position));
			}else{
				new AlertDialog.Builder(this)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("[" + file.getName() + "] folder can't be read!")
					.setPositiveButton("OK", null).show();	
			}	
		}else {
			new AlertDialog.Builder(this)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("[" + file.getPath() + "]")
					.setPositiveButton("OK", null).show();

		  }*/
	}

}
