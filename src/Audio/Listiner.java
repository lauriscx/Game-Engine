package Audio;

import org.joml.Vector3f;
import org.lwjgl.openal.AL10;

public class Listiner {
	
	//Set position of virtual listener to imitate 3D sound.
	public void SetPosition(Vector3f Position) {
		AL10.alListener3f(AL10.AL_POSITION, Position.x, Position.y, Position.z);
	}
	//Set velocity of player o simulate sound level changing.
	public void SetVelocity(Vector3f Velocity) {
		AL10.alListener3f(AL10.AL_VELOCITY, Velocity.x, Velocity.y, Velocity.z);
	}
	//Set listener position to simulate sound direction from left. right. Library adjust left and right sound levels(makes it stereo).  
	public void SetDirection(Vector3f Direction) {
		AL10.alListener3f(AL10.AL_DIRECTION, Direction.x, Direction.y, Direction.z);
	}
}