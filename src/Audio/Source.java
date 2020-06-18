package Audio;

import org.joml.Vector3f;
import org.lwjgl.openal.AL10;


public class Source {
	//Sound source ID.
	private int ID;
	
	public Source() {
		//Generate sound source object and returning ID in list or collection.
		this.ID = AL10.alGenSources();
	}
	
	//Play sound by sound ID in this sound source.
	public void Play		(int SoundID) {
		//Stop playing last active sound if is active.
		Stop();
		//Set new sound for this sound source.
		AL10.alSourcei(this.ID, AL10.AL_BUFFER, SoundID);
		//Start play new sound.
		Play();
	}
	
	//Set sound source position in 3D world.
	public void SetPosition	(Vector3f Position) {
		AL10.alSource3f(this.ID, AL10.AL_POSITION, Position.x, Position.y, Position.z);
	}
	//Set sound velocity to simulate sound level over time.
	public void SetVelocity	(Vector3f Velocity) {
		AL10.alSource3f(this.ID, AL10.AL_VELOCITY, Velocity.x, Velocity.y, Velocity.z);
	}
	//Set gain "sound level" how loud it will be played. 
	public void SetGain		(float Gain) {
		AL10.alSourcef(this.ID, AL10.AL_GAIN, Gain);
	}
	//Set pitch how fast sound will be played.
	public void SetPitch	(float Pitch) {
		AL10.alSourcef(this.ID, AL10.AL_PITCH, Pitch);
	}
	
	//Play sound.
	public void Play	() {
		AL10.alSourcePlay(this.ID);
	}
	//Pause sound. If play again it will be played from beginning.
	public void Pause	() {
		AL10.alSourcePause(this.ID);
	}
	//Stop play sound. If play again it will be played from where it was stopped.
	public void Stop	() {
		AL10.alSourceStop(this.ID);
	}

	//Set to loop sound over and over.
	public void SetLooping	(boolean Parametre) {
		AL10.alSourcei(this.ID, AL10.AL_LOOPING, Parametre ? AL10.AL_TRUE : AL10.AL_FALSE);
	}
	//Check is this audio source is playing now any sound.
	public Boolean IsPlaying() {
		return AL10.alGetSourcei(this.ID, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}
	
	//This factors make sound damping. How fast it damping, from where start damping, and from where you don't hear it anymore. 
	public void SetFactors	(float RollOfFactor, float ReferenceDistance, float MaxDistance) {
		AL10.alSourcef(this.ID, AL10.AL_ROLLOFF_FACTOR, RollOfFactor);
		AL10.alSourcef(this.ID, AL10.AL_REFERENCE_DISTANCE, ReferenceDistance);
		AL10.alSourcef(this.ID, AL10.AL_MAX_DISTANCE, MaxDistance);
	}
	
	//Cleanup object and pointers from memory.
	public void CleanUp		() {
		//Stop play sound.
		Stop();
		//Delete audio source.
		AL10.alDeleteSources(this.ID);
	}
}
