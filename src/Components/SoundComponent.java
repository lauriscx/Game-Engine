package Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Vector3f;

import Audio.Audio;
import Audio.Source;
import Components.RawComponentDAO;
import TestComponents.PositionComponent;

//Sound component.

public class SoundComponent extends RawComponentDAO {
	//This component can emit different sounds.
	private List<Integer> SoundEffects = new ArrayList<>();
	//Sound source.
	private Source source;
	private Random rand;
	
	static {
		//Init audio once then program starts.
		Audio.Init();
	}
	
	//Constructor to init objects.
	public SoundComponent() {
		source = new Source();
		rand = new Random();
		//Add default sound track. Named a0
		Audio.LoadSound("Audio/TestSound.wav", "a0");
	}
	
	//Add sounds 
	public void AddSound(String file) {
		//Audio.LoadSound(file, Integer.toString(SoundEffects.size()));
		//Add sound to sounds list with generated name.
		SoundEffects.add(Audio.LoadSound(file, Integer.toString(SoundEffects.size())));
	}
	
	/*Define new functionality on function witch are called once per frame*/
	@Override
	public void Update() {
		//Get from entity components list and check is position component exist.
		if(GetParent().getComponent(PositionComponent.class) != null) {
			//Set audio source position as object position.
			source.SetPosition(new Vector3f(GetParent().getComponent(PositionComponent.class).x, GetParent().getComponent(PositionComponent.class).y, 0));
		}
		//Check is sound playing.
		if(!source.IsPlaying()) {
			//If sound not playing then randomly select new sound and starts play it.
			int n = rand.nextInt(SoundEffects.size());
			source.Play(SoundEffects.get(n));
			//System.out.println("Playing");
		} else {
			//System.out.println("Not playing");
		}
	}
}
