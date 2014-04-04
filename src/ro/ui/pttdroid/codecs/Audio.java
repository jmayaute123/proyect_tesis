package ro.ui.pttdroid.codecs;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;


public class Audio {
	
	public static final int SAMPLE_RATE = 44100;
	public static final int FRAME_SIZE_IN_BYTES = 1764;
	public static final int ENCODING_PCM_NUM_BITS = AudioFormat.ENCODING_PCM_16BIT;	
	
				
	
	/*public static final int RECORD_BUFFER_SIZE = Math.max(
			SAMPLE_RATE, 
			ceil(AudioRecord.getMinBufferSize(
					SAMPLE_RATE, 
					AudioFormat.CHANNEL_CONFIGURATION_MONO, 
					ENCODING_PCM_NUM_BITS)));
	
	public static final int TRACK_BUFFER_SIZE = Math.max(
			FRAME_SIZE, 
			ceil(AudioTrack.getMinBufferSize(
					SAMPLE_RATE, 
					AudioFormat.CHANNEL_CONFIGURATION_MONO, 
					ENCODING_PCM_NUM_BITS)));		*/
	
	public static final int RECORD_BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_CONFIGURATION_MONO, ENCODING_PCM_NUM_BITS);
	public static final int TRACK_BUFFER_SIZE = AudioTrack.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_CONFIGURATION_MONO, ENCODING_PCM_NUM_BITS);
	
	/**
	 * 
	 * @param size
	 * @return
	 */
	/*private static int ceil(int size) 
	{
		return (int) Math.ceil(((double) size / FRAME_SIZE )) * FRAME_SIZE;
	}*/

}
