package Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Vector3f;

import Audio.Audio;
import Audio.Source;
import Components.RawComponentDAO;
import TestComponents.PositionComponent;

public class SoundComponent extends RawComponentDAO {
	private List<Integer> SoundEffects = new ArrayList<>();
	private Source source;
	private Random rand;
	
	static {
		Audio.Init();
	}
	
	public SoundComponent() {
		source = new Source();
		rand = new Random();
		Audio.LoadSound("Audio/TestSound.wav", "a0");
	}
	
	public void AddSound(String file) {
		Audio.LoadSound(file, Integer.toString(SoundEffects.size()));
		SoundEffects.add(Audio.LoadSound(file, "0"));
	}
	
	@Override
	public void Update() {
		if(GetParent().getComponent(PositionComponent.class) != null) {
			source.SetPosition(new Vector3f(GetParent().getComponent(PositionComponent.class).x, GetParent().getComponent(PositionComponent.class).y, 0));
		}
		if(!source.IsPlaying()) {
			int n = rand.nextInt(SoundEffects.size());
			source.Play(SoundEffects.get(n));
			//System.out.println("Playing");
		} else {
			//System.out.println("Not playing");
		}
	}
}
