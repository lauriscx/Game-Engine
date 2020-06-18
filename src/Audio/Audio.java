package Audio;

import java.util.ArrayList;
import java.util.List;

//import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class Audio {
	
	//Sounds IDs returned from OpenAL library. 
	private static List<Integer> Sounds;
	//Sounds names.
	private static List<String> Names;
	
	public static void Init() {
		/*try {
			AL.create();
		} catch (LWJGLException e) {
			System.err.println("Failed To load Audio Library!");
			e.printStackTrace();
		}*/
		//Init arrays
		Sounds 	= new ArrayList<Integer>(); 
		Names 	= new ArrayList<String>(); 
		
		//Create other thread witch call run function then program is closing.
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    @Override
			public void run() {
		        System.out.println("Audio cleaned up");
		        CleanUp();
		    }
		}));
	}
	
	//Load sound from file and put into OpelAL buffer, witch return ID and give name for sound.
	public static int LoadSound(String FileName, String AudioName) {
		//Generate buffer
		int Buffer = AL10.alGenBuffers();
		//Load sound from file.
		WaveData Sound = WaveData.create(FileName);
		//Fill buffer with data
		AL10.alBufferData(Buffer, Sound.format, Sound.data, Sound.samplerate);
		//Check is file was loaded successfully.
		if(Sound.data == null) {
			 System.err.println("Sound not found: " + FileName);
		}
		//Buffer ID to list
		Sounds.add(Buffer);
		//Add name to list.
		Names.add(AudioName);
		//Delete loaded data with WaveData object.
		Sound.dispose();
		//return buffer ID
		return Buffer;
	}
	
	//Get sound ID by sound name.
	public static int GetSound(String Name) {
		//Loop trough all names list and check for match.
		for(int i = 0; i < Names.size(); i++) {
			//If match use ID in sounds IDs list and return buffer ID.
			if(Names.get(i) == Name) {
				return Sounds.get(i);							
			}
		}
		return 0;
	}
	
	//OpenAL sound level changing model. This model is selected by defines from OpenAL.
	public static void SetGlobalDistanceModel(int ModelType) {
		AL10.alDistanceModel(ModelType);
	}
	
	//Clear up all object and data from memory. Need to be called then program is closing or this object will be destroyed.
	public static void CleanUp() {
		//Delete all sound buffers from memory.
		for(int i = 0; i < Sounds.size(); i++) {
			AL10.alDeleteBuffers(Sounds.get(i));
		}
		//Clear lists.
		Sounds.clear();
		Names.clear();
		//AL.destroy();
	}
}
