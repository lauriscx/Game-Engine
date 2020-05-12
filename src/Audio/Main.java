package Audio;

import java.io.File;
import java.io.IOException;

import org.joml.Vector3f;
import org.lwjgl.openal.AL10;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Audio.Init();
		
		Listiner listener = new Listiner();
		listener.SetPosition(new Vector3f(0, 0, 0));
		listener.SetVelocity(new Vector3f(0, 0, 0));
		
		Audio.LoadSound("Audio/TestSound.wav", "Testas");
		
		Source source = new Source();
		source.SetPosition(new Vector3f(0, 0, 2));
		source.Play(Audio.GetSound("Testas"));
		while(true) {}
	}

}
