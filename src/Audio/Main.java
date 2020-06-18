package Audio;

import java.io.File;
import java.io.IOException;

import org.joml.Vector3f;
import org.lwjgl.openal.AL10;


/*example.
 * Listener is person.
 * Source is player (like CD player)
 * Audio is like CD witch is placed in CD player
 * */


/*Sound testing class*/
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Init library.
		Audio.Init();
		
		//Create listener
		Listiner listener = new Listiner();
		listener.SetPosition(new Vector3f(0, 0, 0));
		listener.SetVelocity(new Vector3f(0, 0, 0));
		
		//Load sound from folder and give name name is used to easier maintain sounds.
		Audio.LoadSound("Audio/TestSound.wav", "Testas");
		
		//Create sound source.
		Source source = new Source();
		//Set position in 3D world.
		source.SetPosition(new Vector3f(0, 0, 2));
		//Set to play sound from buffer ID. Buffer ID found by name of sound.
		source.Play(Audio.GetSound("Testas"));
		
		//While loop for not closing program to keep playing sound in background.
		while(true) {}
	}

}
