package Audio;

import java.util.ArrayList;
import java.util.List;

//import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class Audio {
	
	private static List<Integer> Sounds;
	private static List<String> Names;
	
	public static void Init() {
		/*try {
			AL.create();
		} catch (LWJGLException e) {
			System.err.println("Failed To load Audio Library!");
			e.printStackTrace();
		}*/
		Sounds 	= new ArrayList<Integer>(); 
		Names 	= new ArrayList<String>(); 
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    @Override
			public void run() {
		        System.out.println("Audio cleaned up");
		        CleanUp();
		    }
		}));
	}
	
	public static int LoadSound(String FileName, String AudioName) {
		int Buffer = AL10.alGenBuffers();
		WaveData Sound = WaveData.create(FileName);
		AL10.alBufferData(Buffer, Sound.format, Sound.data, Sound.samplerate);
		if(Sound.data == null) {
			 System.err.println("Sound not found: " + FileName);
		}
		Sounds.add(Buffer);
		Names.add(AudioName);
		Sound.dispose();
		return Buffer;
	}
	
	public static int GetSound(String Name) {
		for(int i = 0; i < Names.size(); i++) {
			if(Names.get(i) == Name) {
				return Sounds.get(i);							
			}
		}
		return 0;
	}
	
	public static void SetGlobalDistanceModel(int ModelType) {
		AL10.alDistanceModel(ModelType);
	}
	
	public static void CleanUp() {
		for(int i = 0; i < Sounds.size(); i++) {
			AL10.alDeleteBuffers(Sounds.get(i));
		}
		Sounds.clear();
		Names.clear();
		//AL.destroy();
	}
}
