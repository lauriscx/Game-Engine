package Audio;

import org.joml.Vector3f;
import org.lwjgl.openal.AL10;


public class Listiner {
	public void SetPosition(Vector3f Position) {
		AL10.alListener3f(AL10.AL_POSITION, Position.x, Position.y, Position.z);
	}
	public void SetVelocity(Vector3f Velocity) {
		AL10.alListener3f(AL10.AL_VELOCITY, Velocity.x, Velocity.y, Velocity.z);
	}
	public void SetDirection(Vector3f Direction) {
		AL10.alListener3f(AL10.AL_DIRECTION, Direction.x, Direction.y, Direction.z);
	}
}