package Audio;

import org.joml.Vector3f;
import org.lwjgl.openal.AL10;


public class Source {
	private int ID;
	
	public Source() {
		this.ID = AL10.alGenSources();
	}
	
	public void Play		(int SoundID) {
		Stop();
		AL10.alSourcei(this.ID, AL10.AL_BUFFER, SoundID);
		Play();
	}
	
	public void SetPosition	(Vector3f Position) {
		AL10.alSource3f(this.ID, AL10.AL_POSITION, Position.x, Position.y, Position.z);
	}
	public void SetVelocity	(Vector3f Velocity) {
		AL10.alSource3f(this.ID, AL10.AL_VELOCITY, Velocity.x, Velocity.y, Velocity.z);
	}
	public void SetGain		(float Gain) {
		AL10.alSourcef(this.ID, AL10.AL_GAIN, Gain);
	}
	public void SetPitch	(float Pitch) {
		AL10.alSourcef(this.ID, AL10.AL_PITCH, Pitch);
	}
	
	public void Play	() {
		AL10.alSourcePlay(this.ID);
	}
	public void Pause	() {
		AL10.alSourcePause(this.ID);
	}
	public void Stop	() {
		AL10.alSourceStop(this.ID);
	}

	public void SetLooping	(boolean Parametre) {
		AL10.alSourcei(this.ID, AL10.AL_LOOPING, Parametre ? AL10.AL_TRUE : AL10.AL_FALSE);
	}
	public Boolean IsPlaying() {
		return AL10.alGetSourcei(this.ID, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}
	
	public void SetFactors	(float RollOfFactor, float ReferenceDistance, float MaxDistance) {
		AL10.alSourcef(this.ID, AL10.AL_ROLLOFF_FACTOR, RollOfFactor);
		AL10.alSourcef(this.ID, AL10.AL_REFERENCE_DISTANCE, ReferenceDistance);
		AL10.alSourcef(this.ID, AL10.AL_MAX_DISTANCE, MaxDistance);
	}
	
	public void CleanUp		() {
		Stop();
		AL10.alDeleteSources(this.ID);
	}
}
