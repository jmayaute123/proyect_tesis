package ro.ui.pttdroid;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import ro.ui.pttdroid.codecs.Audio;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.os.Environment;
import android.util.Log;

public class ServerAudio {

	private InetAddress address;
	private InetAddress address1;
	private InetAddress address2;
	private InetAddress address3;
	private InetAddress address4;
	private int port = 50100;
	private int port1 = 50102;
	private int port2 = 50103;
	private int port3 = 50104;
	private int port4 = 50105;
	private boolean mic = false;
	private boolean grave = false;
	private static final int BUF_SIZE = Audio.FRAME_SIZE_IN_BYTES;
	int contador = 0;
	int orden1 = 400;
	int orden2 = 400;
	int orden3 = 400;
	int calidadtotal = 0;
	private String IP1 = "192.168.1.102";
	private String IP2 = "192.168.1.103";
	private String IP3 = "192.168.1.104";
	private String IP4 = "192.168.1.105";
	private  String TipoFilter = "normal";	
	private float [][] coeficientes = new float[30][400];
	ServerParametros c1=new ServerParametros();
	ServerParametros1 c2=new ServerParametros1();
	ServerParametros2 c3=new ServerParametros2();

	public ServerAudio(InetAddress address) {
		
		this.address = address;
		try {
			address1 = InetAddress.getByName(IP1);
			address2 = InetAddress.getByName(IP2);
			address3 = InetAddress.getByName(IP3);
			address4 = InetAddress.getByName(IP4);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public void startCall() {
		
		
		
		startMic();
		coeficientes[0] = ServerParametros.coeficientes1; 
		coeficientes[1] = ServerParametros.coeficientes2; 
		coeficientes[2] = ServerParametros.coeficientes3; 
		coeficientes[3] = ServerParametros.coeficientes4; 
		coeficientes[4] = ServerParametros.coeficientes5; 
		coeficientes[5] = ServerParametros.coeficientes6; 
		coeficientes[6] = ServerParametros.coeficientes7; 
		coeficientes[7] = ServerParametros.coeficientes8; 
		coeficientes[8] = ServerParametros.coeficientes9; 
		coeficientes[9] = ServerParametros.coeficientes10;
		coeficientes[10] = ServerParametros1.coeficientes11; 
		coeficientes[11] = ServerParametros1.coeficientes12; 
		coeficientes[12] = ServerParametros1.coeficientes13; 
		coeficientes[13] = ServerParametros1.coeficientes14; 
		coeficientes[14] = ServerParametros1.coeficientes15; 
		coeficientes[15] = ServerParametros1.coeficientes16; 
		coeficientes[16] = ServerParametros1.coeficientes17; 
		coeficientes[17] = ServerParametros1.coeficientes18; 
		coeficientes[18] = ServerParametros1.coeficientes19; 
		coeficientes[19] = ServerParametros1.coeficientes20;
		coeficientes[20] = ServerParametros2.coeficientes21; 
		coeficientes[21] = ServerParametros2.coeficientes22; 
		coeficientes[22] = ServerParametros2.coeficientes23; 
		coeficientes[23] = ServerParametros2.coeficientes24; 
		coeficientes[24] = ServerParametros2.coeficientes25; 
		coeficientes[25] = ServerParametros2.coeficientes26; 
		coeficientes[26] = ServerParametros2.coeficientes27; 
		coeficientes[27] = ServerParametros2.coeficientes28; 
		coeficientes[28] = ServerParametros2.coeficientes29; 
		coeficientes[29] = ServerParametros2.coeficientes30;   
	}
	
	public void endCall() {

		muteMic();

	}
	
	public void muteMic() {
		
		mic = false;
	}
	
	public void tipofiltro(int var ,int calidad){
	
		calidadtotal = calidad;
		if(var==0)
			{TipoFilter = "normal";}
		else if (var==1)
			TipoFilter = "cardiacos";
		else if (var==2)
			TipoFilter = "abdominales";
		else if (var==3)
			TipoFilter = "pulmonares";	
		
	}

	public static short[] bytetoshort(byte[] bytes) {
	    short[] out = new short[bytes.length / 2]; // 
	    ByteBuffer bb = ByteBuffer.wrap(bytes);
	    for (int i = 0; i < out.length; i++) {
	        out[i] = bb.getShort();
	    }
	    return out;
	}
	
	public static float[] shorttofloat(short[] pcms) {
	    float[] floaters = new float[pcms.length];
	    for (int i = 0; i < pcms.length; i++) {
	        floaters[i] = pcms[i];
	    }
	    return floaters;
	}
	
	public static short[] floattoshort(float[] pcms) {
	    short[] floaters = new short[pcms.length];
	    for (int i = 0; i < pcms.length; i++) {
	        floaters[i] = (short) pcms[i];
	    }
	    return floaters;
	}
	 
	public static byte[] shorttobyte(short[] pcms) {
	    
		byte[] floaters = new byte[pcms.length * 2];
		for (int i = 0; i < pcms.length; i++) {
		floaters[i*2+1] = (byte) (pcms[i] & 0xff);
		floaters[i*2] = (byte) ((pcms[i] >>> 8) & 0xff);
		}
	    return floaters;
	}
	
	public static float[] escalamiento(float[] pcms) {
	    float[] floaters = new float[pcms.length];
	    for (int i = 0; i < pcms.length; i++) {
	        floaters[i] = (float) (pcms[i]/(32768.0)); 			
	    }
	    
	    return floaters;
	}
	
	public static float[] reescalamiento(float[] pcms) {
	    float[] floaters = new float[pcms.length];
	    for (int i = 0; i < pcms.length; i++) {
	        floaters[i] = (float) Math.round(pcms[i]*32768.0); 			
	    }
	    
	    return floaters;
	}
	
	public  float[] filtro(float[] data, float[] coef, int orden, int contador, float[] backup) {
	    
		if(contador == 0){
			data = filtro1(coef,data,orden);
			
		}
		else{			
			data = filtro2(coef,backup,data,orden);			
		}
			
	    return data;
	}
	
	public float[] filtro1 (float[] h,float [] buf, int orden){
		
		int k=0,z=orden,x=buf.length - 1,n=orden;
		float aux=0,a=0;
		float [] filtrada = new float[BUF_SIZE/2];
			
         
		 for (int m=0;m<=z-1;m++){
        	 filtrada[m] = buf[m];
		 }
		
		for(;z<=x;z=z+1){	 
        	 
	        	 for(k=0;k<=n;k=k+1){        	 
	        	 aux= buf[k+z-orden]*h[n-k];
	        	 a=aux+a;                   
	         	 }
        	 
	        filtrada[z]=a;
	        a=0;
         }  
              
         return filtrada;
         
    }
	
	public float[] filtro2 (float[] h,float[] bufaux,float [] buf, int orden){
		int k=0,l=0,m=0,z=0,x=buf.length - 1,n=orden,otro=0;
		float aux=0,a=0;
		float [] filtrada = new float[BUF_SIZE/2];
		for(;z<=x;z=z+1){
			
			if(otro<=orden-1)
			{for(k=orden;k>=otro+1;k--){        	 
	        	 aux= bufaux[x-k+z+1]*h[k];
	        	 a=aux+a;                   
	         	 }
                        
			for(l=0,m=0;l<=otro;l++,m++){        	 
	        	 aux= buf[l]*h[otro-m];
	        	 a=aux+a;                   
	         	 }
			otro++;}
			
			else
			{
				 for(k=0;k<=n;k=k+1){        	 
		        	 aux= buf[k+z-orden]*h[n-k];
		        	 a=aux+a;                   
		         	 }
				
				
			}
		 	
		
        filtrada[z]=a;
        a=0;
        }	
		return filtrada;
	}
		
	public void startMic() {

		audioRecorder = new AudioRecord(
    			AudioSource.MIC, 
    			Audio.SAMPLE_RATE, 
    			AudioFormat.CHANNEL_CONFIGURATION_MONO, 
    			Audio.ENCODING_PCM_NUM_BITS, 
    			Audio.RECORD_BUFFER_SIZE);
		mic = true;
		
		Thread thread = new Thread(new Runnable() {
			
			
			public void run() {

				getSamples();
				
			}
		});
		thread.start();
	}
		
	public byte[] manipulateSamples(byte[] data, int contador, float[] backup) {

		// Convert byte[] to short[] (16 bit) to float[] (32 bit) (End result: Big Endian)
		ShortBuffer sbuf = ByteBuffer.wrap(data).asShortBuffer();
		short[] audioShorts = new short[sbuf.capacity()];
		sbuf.get(audioShorts);

		float[] audioFloats = new float[audioShorts.length];

		for (int i = 0; i < audioShorts.length; i++) {
			audioFloats[i] = ((float)Short.reverseBytes(audioShorts[i])/0x8000);
		}
		
		///////PROCESAMIENTO"""""""""""
		
		if(TipoFilter=="normal")
			{audioFloats=audioFloats;}
		else if(TipoFilter=="cardiacos")
			{audioFloats = filtro(audioFloats, coeficientes[calidadtotal],orden1, contador, backup);}
		else if(TipoFilter=="abdominales")
			{audioFloats = filtro(audioFloats, coeficientes[calidadtotal+10],orden2, contador, backup);} 
		else if(TipoFilter=="pulmonares")
			{audioFloats = filtro(audioFloats, coeficientes[calidadtotal+20],orden3, contador, backup);}
		
		float MaxValue = getMaxValue(audioFloats);
		
		//for (int i = 0; i < audioFloats.length; i++) {
			//audioFloats[i] = ((float) 0.8)*audioFloats[i]/MaxValue;
		//}
		
		
		///////////////////////////////
		// Convert float[] to short[] to byte[] (End result: Little Endian)
		audioShorts = new short[audioFloats.length];
		for (int i = 0; i < audioFloats.length; i++) {
		audioShorts[i] = Short.reverseBytes((short) ((audioFloats[i])*0x8000));
		}
		
		byte byteArray[] = new byte[audioShorts.length * 2];
		ByteBuffer buffer = ByteBuffer.wrap(byteArray);
		sbuf = buffer.asShortBuffer();
		sbuf.put(audioShorts);
		data = buffer.array();
		
		return data;

		}

	public static float getMaxValue(float[] numbers){  
		  float maxValue = numbers[0];  
		  for(int i=1;i < numbers.length;i++){  
		    if(Math.abs(numbers[i]) > Math.abs(maxValue)){  
		      maxValue = Math.abs(numbers[i]);  
		    }  
		  }  
		  return maxValue;  
		}  
	
	public float[] bloqueafter(byte[] data)

	{
		ShortBuffer sbuf = ByteBuffer.wrap(data).asShortBuffer();
		short[] audioShorts = new short[sbuf.capacity()];
		sbuf.get(audioShorts);

		float[] audioFloats = new float[audioShorts.length];

		for (int i = 0; i < audioShorts.length; i++) {
			audioFloats[i] = ((float)Short.reverseBytes(audioShorts[i])/0x8000);
		}
		
		return audioFloats;
	}
	
	private AudioRecord audioRecorder;

	private static final String LOG_TAG1 = "Probando Buffer";
	private void getSamples(){
		mic = true;
		
		// Create an instance of the AudioRecord class
		//audioRecorder = new AudioRecord (MediaRecorder.AudioSource.MIC, SAMPLE_RATE,
			//	AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, 
				//AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT));
		int bytes_read = 0;
		byte[] buf = new byte[Audio.FRAME_SIZE_IN_BYTES]; //BUFFER ORIGINAL
		byte[] buf1 = new byte[Audio.FRAME_SIZE_IN_BYTES]; //BUFFER FILTRADO
		
		float[] backup = new float[(Audio.FRAME_SIZE_IN_BYTES)/2]; //Backup escalado

		try {
			// Create a socket and sta rt recording
			
			DatagramSocket socket = new DatagramSocket();
			DatagramSocket socket1 = new DatagramSocket();
			DatagramSocket socket2 = new DatagramSocket();
			DatagramSocket socket3 = new DatagramSocket();
			DatagramSocket socket4 = new DatagramSocket();
			//MulticastSocket enviador = new MulticastSocket();
			audioRecorder.startRecording();
			while(mic) {
				
				
				// Capture audio from the mic and transmit it
				bytes_read = audioRecorder.read(buf, 0, buf.length);
				buf1 = manipulateSamples(buf,contador,backup);
				backup = bloqueafter(buf);
				contador = 1;
						
				DatagramPacket packet =  new DatagramPacket(buf1, buf1.length, address, port);
				DatagramPacket 	packet1 = new DatagramPacket(buf1, buf1.length, address1, port1);
				DatagramPacket 	packet2 = new DatagramPacket(buf1, buf1.length, address2, port2);
				DatagramPacket 	packet3 = new DatagramPacket(buf1, buf1.length, address3, port3);
				DatagramPacket 	packet4 = new DatagramPacket(buf1, buf1.length, address4, port4);
				socket.send(packet);
				socket1.send(packet1);
				socket2.send(packet2);
				socket3.send(packet3);
				socket4.send(packet4);
			}
			// Stop recording and release resources
			audioRecorder.stop();
			audioRecorder.release();
			socket.disconnect();
			socket.close();
			socket1.disconnect();
			socket1.close();
			socket2.disconnect();
			socket2.close();
			socket3.disconnect();
			socket3.close();
			socket4.disconnect();
			socket4.close();
			mic = false;
			return;
		}
		catch(SocketException e) {
			
			mic = false;
		}
		catch(UnknownHostException e) {
			
		
			mic = false;
		}
		catch(IOException e) {
			
			
			mic = false;
		}
	}
	
}

