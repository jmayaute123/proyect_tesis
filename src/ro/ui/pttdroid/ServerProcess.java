package ro.ui.pttdroid;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;

public class ServerProcess extends Activity {
	public static final int SAMPLE_RATE = 16000;
	private String IP;
	private ServerAudio call;
	private RadioGroup radioGroup1;
	private SeekBar SeekBar1;
	private SeekBar SeekBar2;
	private SeekBar SeekBar3;
	TextView value;
	private AudioRecord mRecorder;
	private File mRecording;
	private short[] mBuffer;
	private final String startRecordingLabel = "Start recording";
	private final String stopRecordingLabel = "Stop recording";
	private boolean mIsRecording = false;
	private ProgressBar mProgressBar;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.serverprocess);
		
		//initRecorder();
		//mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		//mIsRecording = true;
		//mRecorder.startRecording();
		//mRecording = getFile("raw");
		//startBufferedWrite(mRecording);
		
		/*	
    <ProgressBar
        android:id="@+id/progressBar"        
        android:layout_above="@+id/radioGroup1"
        style="?android:attr/progressBarStyleHorizontal"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        android:progressDrawable="@drawable/progressbar" />*/
		
		IniciodeTransmision();
		
		SeekBar1 = (SeekBar) findViewById(R.id.seekBar1);
    	SeekBar1.setVisibility(View.INVISIBLE);
    	SeekBar2 = (SeekBar) findViewById(R.id.seekBar2);
    	SeekBar2.setVisibility(View.INVISIBLE);
    	SeekBar3 = (SeekBar) findViewById(R.id.seekBar3);
    	SeekBar3.setVisibility(View.INVISIBLE);
    	
		radioGroup1 = (RadioGroup)findViewById(R.id.radioGroup1);
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			 
		    //@Override
		    public void onCheckedChanged(RadioGroup group, int checkedId) {
		        // TODO Auto-generated method stub
		        if (checkedId == R.id.radio0){
		        	call.tipofiltro(0,0);
		        	SeekBar1 = (SeekBar) findViewById(R.id.seekBar1);
		        	SeekBar1.setVisibility(View.INVISIBLE);
		        	SeekBar2 = (SeekBar) findViewById(R.id.seekBar2);
		        	SeekBar2.setVisibility(View.INVISIBLE);
		        	SeekBar3 = (SeekBar) findViewById(R.id.seekBar3);
		        	SeekBar3.setVisibility(View.INVISIBLE);    
		        	
		        }else if (checkedId == R.id.radio1){
		        	SeekBar1 = (SeekBar) findViewById(R.id.seekBar1);
		        	SeekBar1.setVisibility(View.VISIBLE);
		        	SeekBar2 = (SeekBar) findViewById(R.id.seekBar2);
		        	SeekBar2.setVisibility(View.INVISIBLE);
		        	SeekBar3 = (SeekBar) findViewById(R.id.seekBar3);
		        	SeekBar3.setVisibility(View.INVISIBLE);
		        	
		        	SeekBar1.setMax(9);
		        	SeekBar1.setOnSeekBarChangeListener( new OnSeekBarChangeListener()
		        	   {
		        	   public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
		        	   {
		        	           // TODO Auto-generated method stub
		        	           value.setText("SeekBar value is "+progress);
		   		        	   call.tipofiltro(1,progress);
		        	   }

		        	   public void onStartTrackingTouch(SeekBar seekBar)
		        	   {
		        	   // TODO Auto-generated method stub
		        	   }

		        	   public void onStopTrackingTouch(SeekBar seekBar)
		        	   {
		        	   // TODO Auto-generated method stub
		        	   }
		        	   });
		        }else if (checkedId == R.id.radio2){
		        	//call.tipofiltro(2);
		        	SeekBar1 = (SeekBar) findViewById(R.id.seekBar1);
		        	SeekBar1.setVisibility(View.INVISIBLE);
		        	SeekBar2 = (SeekBar) findViewById(R.id.seekBar2);
		        	SeekBar2.setVisibility(View.VISIBLE);
		        	SeekBar3 = (SeekBar) findViewById(R.id.seekBar3);
		        	SeekBar3.setVisibility(View.INVISIBLE);
		        	
		        	SeekBar2.setMax(9);
		        	SeekBar2.setOnSeekBarChangeListener( new OnSeekBarChangeListener()
		        	   {
		        	   public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
		        	   {
		        	           // TODO Auto-generated method stub
		        	           value.setText("SeekBar value is "+progress);
		   		        	   call.tipofiltro(2,progress);
		        	   }

		        	   public void onStartTrackingTouch(SeekBar seekBar)
		        	   {
		        	   // TODO Auto-generated method stub
		        	   }

		        	   public void onStopTrackingTouch(SeekBar seekBar)
		        	   {
		        	   // TODO Auto-generated method stub
		        	   }
		        	   });
		        }else if (checkedId == R.id.radio3){
		        	//call.tipofiltro(3);
		        	SeekBar1 = (SeekBar) findViewById(R.id.seekBar1);
		        	SeekBar1.setVisibility(View.INVISIBLE);
		        	SeekBar2 = (SeekBar) findViewById(R.id.seekBar2);
		        	SeekBar2.setVisibility(View.INVISIBLE);
		        	SeekBar3 = (SeekBar) findViewById(R.id.seekBar3);
		        	SeekBar3.setVisibility(View.VISIBLE);
		        	
		        	SeekBar3.setMax(9);
		        	SeekBar3.setOnSeekBarChangeListener( new OnSeekBarChangeListener()
		        	   {
		        	   public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
		        	   {
		        	           // TODO Auto-generated method stub
		        	           value.setText("SeekBar value is "+progress);
		   		        	   call.tipofiltro(3,progress);
		        	   }

		        	   public void onStartTrackingTouch(SeekBar seekBar)
		        	   {
		        	   // TODO Auto-generated method stub
		        	   }

		        	   public void onStopTrackingTouch(SeekBar seekBar)
		        	   {
		        	   // TODO Auto-generated method stub
		        	   }
		        	   });
		        }
		         
		    }
		     
		});
	}



	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		FindeTransmision(); 	
	}
	
	private void IniciodeTransmision(){
		IP = "192.168.1.100";							
		InetAddress address;
		try {
			address = InetAddress.getByName(IP);
			call = new ServerAudio(address);
			call.startCall();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}		
	}
	
	private void FindeTransmision() {		
		call.endCall();			
	}
	
	private void initRecorder() {
		int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
				AudioFormat.ENCODING_PCM_16BIT);
		mBuffer = new short[bufferSize];
		mRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
				AudioFormat.ENCODING_PCM_16BIT, bufferSize);
	}

	private void startBufferedWrite(final File file) {
		new Thread(new Runnable() {
			//@Override
			public void run() {
				DataOutputStream output = null;
				try {
					output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
					while (mIsRecording) {
						double sum = 0;
						int readSize = mRecorder.read(mBuffer, 0, mBuffer.length);
						for (int i = 0; i < readSize; i++) {
							output.writeShort(mBuffer[i]);
							sum += mBuffer[i] * mBuffer[i];
						}
						if (readSize > 0) {
							final double amplitude = sum / readSize;
							mProgressBar.setProgress((int) Math.sqrt(amplitude));
						}
					}
				} catch (IOException e) {
					Toast.makeText(ServerProcess.this, e.getMessage(), Toast.LENGTH_SHORT).show();
				} finally {
					mProgressBar.setProgress(0);
					if (output != null) {
						try {
							output.flush();
						} catch (IOException e) {
							Toast.makeText(ServerProcess.this, e.getMessage(), Toast.LENGTH_SHORT)
									.show();
						} finally {
							try {
								output.close();
							} catch (IOException e) {
								Toast.makeText(ServerProcess.this, e.getMessage(), Toast.LENGTH_SHORT)
										.show();
							}
						}
					}
				}
			}
		}).start();
	}

	

	private File getFile(final String suffix) {
		Time time = new Time();
		time.setToNow();
		return new File(Environment.getExternalStorageDirectory(), time.format("%Y%m%d%H%M%S") + "." + suffix);
	}
	
	

}
