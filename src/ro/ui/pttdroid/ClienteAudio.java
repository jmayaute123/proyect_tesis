package ro.ui.pttdroid;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import ro.ui.pttdroid.codecs.Audio;
import ro.ui.pttdroid.settings.CommSettings;

public class ClienteAudio {
	
	
	private InetAddress address; // Address to call
	private int port; // Port the packets are addressed to
	private boolean speakers = false; // Enable speakers?
	private boolean grave = false; // Enable mic?
	//private static final int BUF_SIZE = AudioRecord.getMinBufferSize(Audio.SAMPLE_RATE, AudioFormat.CHANNEL_CONFIGURATION_MONO, Audio.ENCODING_PCM_NUM_BITS);
	
	public ClienteAudio(InetAddress address) {
		
		this.address = address;
		getIp();
	}
	
	 	public String intf = "eth0";
	    public String ip = NOIP;
	    public static final String NOIP = "0.0.0.0";
		/////////////////////////////////////////////////////////////////////////////
	    public void getIp() {
	       
	        try {
	        
	                // Automatic interface selection
	                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
	                        .hasMoreElements();) {
	                    NetworkInterface ni = en.nextElement();
	                  
	                    ip = getInterfaceFirstIp(ni);
	                    if (ip != NOIP) {
	                        break;
	                    }
	                }
	            
	        } catch (SocketException e) {
	           
	        }
	        String[] arrayColores = ip.split("\\.");
	      //Creating TextView Variable
	       port = Integer.parseInt(arrayColores[3]) + 50000;
	       
	        //Sets the new text to TextView (runtime click event)
	       // text.setText(arrayColores[3]);
	        
	    }
		 private String getInterfaceFirstIp(NetworkInterface ni) {
		        if (ni != null) {
		            for (Enumeration<InetAddress> nis = ni.getInetAddresses(); nis.hasMoreElements();) {
		                InetAddress ia = nis.nextElement();
		                if (!ia.isLoopbackAddress()) {
		                    if (ia instanceof Inet6Address) {
		                        continue;
		                    }
		                    return ia.getHostAddress();
		                  
		                }
		            }
		        }
		        return NOIP;
		    }
	
	public void startCall() {
		
		//startMic();
		//port = CommSettings.getPort();
		
		track = new AudioTrack(
				AudioManager.STREAM_MUSIC, 
				Audio.SAMPLE_RATE, 
				AudioFormat.CHANNEL_CONFIGURATION_MONO, 
				Audio.ENCODING_PCM_NUM_BITS, 
				Audio.TRACK_BUFFER_SIZE, 
				AudioTrack.MODE_STREAM);
		
		startSpeakers();
		
	}
	
	public void endCall() {
		

		//muteMic();
		muteSpeakers();
	}
	
	
	public void muteSpeakers() {
		
		speakers = false;
	}
	
	public void gravando() {

		payloadSize = 0;
		grave = true;
	}
	
	public void nogravando() {
		
		grave = false;
		stop();
	}
	
	private AudioTrack 	track;
	private static final String LOG_TAG1 = "Buffer de recepcion";
	
	public void startSpeakers() {
		
		
		
		// Creates the thread for receiving and playing back audio
		if(!speakers) {
			
			speakers = true;
			Thread receiveThread = new Thread(new Runnable() {
				
				
				public void run() {
					// Create an instance of AudioTrack, used for playing back audio
					
					track.play();
					try {
						// Define a socket to receive the audio
						
						
						DatagramSocket socket = new DatagramSocket(port);
						
						byte[] buf = new byte[Audio.FRAME_SIZE_IN_BYTES];
						
						
						
						while(speakers) {
							// Play back the audio received from packets
							DatagramPacket packet = new DatagramPacket(buf, buf.length);
							socket.receive(packet);
							//Log.v(LOG_TAG1, String.valueOf(Audio.FRAME_SIZE_IN_BYTES) );
							track.write(packet.getData(), 0, Audio.FRAME_SIZE_IN_BYTES);
							if(grave)
							{
								fWriter.write(packet.getData());
								payloadSize += packet.getData().length;
							}
						}
						// Stop playing back and release resources
						socket.disconnect();
						socket.close();
						track.stop();
						track.flush();
						track.release();
						speakers = false;
						return;
					}
					catch(SocketException e) {
					
						speakers = false;
					}
					catch(IOException e) {
						
						speakers = false;
					}
				}
			});
			receiveThread.start();
		}
	}
	
	String mOutputFile;
	private RandomAccessFile fWriter;
	private short 			 nChannels = 1;
	private short			 bSamples = 16;
	private int				 payloadSize = 0;
	///GUARDADO
	public void prepare1()
	{

		
		
    		// create the directory
		File external = Environment.getExternalStorageDirectory();
		File audio = new File(external.getAbsolutePath() + "/EstetoscopioDigital"); 
		audio.mkdirs();

			// construct file name
			mOutputFile =
				audio.getAbsolutePath() + "/" + System.currentTimeMillis() + ".wav";
			
	
	}
	public void prepare2()
	{
		try
		{
			
					if ((mOutputFile != null))
					{
						// write file header	
						fWriter = new RandomAccessFile(mOutputFile, "rw");
						
						fWriter.setLength(0); // Set file length to 0, to prevent unexpected behavior in case the file already existed
						fWriter.writeBytes("RIFF");
						fWriter.writeInt(0); // Final file size not known yet, write 0 
						fWriter.writeBytes("WAVE");
						fWriter.writeBytes("fmt ");
						fWriter.writeInt(Integer.reverseBytes(16)); // Sub-chunk size, 16 for PCM
						fWriter.writeShort(Short.reverseBytes((short) 1)); // AudioFormat, 1 for PCM
						fWriter.writeShort(Short.reverseBytes(nChannels));// Number of channels, 1 for mono, 2 for stereo
						fWriter.writeInt(Integer.reverseBytes(Audio.SAMPLE_RATE)); // Sample rate
						fWriter.writeInt(Integer.reverseBytes(Audio.SAMPLE_RATE*bSamples*nChannels/8)); // Byte rate, SampleRate*NumberOfChannels*BitsPerSample/8
						fWriter.writeShort(Short.reverseBytes((short)(nChannels*bSamples/8))); // Block align, NumberOfChannels*BitsPerSample/8
						fWriter.writeShort(Short.reverseBytes(bSamples)); // Bits per sample
						fWriter.writeBytes("data");
						fWriter.writeInt(0); // Data chunk size not known yet, write 0
						}
					else
						{
							
						}
					}
					catch(Exception e)
					{
						
					}
		
		
		
	}
	
	public void stop()
	{
		try
		{
					fWriter.seek(4); // Write size to RIFF header
					fWriter.writeInt(Integer.reverseBytes(36+payloadSize));
				
					fWriter.seek(40); // Write size to Subchunk2Size field
					fWriter.writeInt(Integer.reverseBytes(payloadSize));
				
					fWriter.close();}
		
		catch(IOException e)
					{
			
					}
				
	}
}