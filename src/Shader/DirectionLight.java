package Shader;

import org.joml.Vector3f;

/* Completed */

public class DirectionLight extends BaseLight {
	private Vector3f 	direction;
	
	public DirectionLight	() {
		direction = new Vector3f(0.0f, 0.0f, 0.0f	);
	}
	@Override
	public void activate	() {
		ID = LightsLists.addDirectionLight(this);
	}
	@Override
	public void disable		() {
		LightsLists.directionLights().remove(ID);
	}
	
	
	public Vector3f Direction(float X, float Y, float Z	) {
		direction.x = X;
		direction.y = Y;
		direction.z = Z;
		return direction;
	}
	public Vector3f Direction(Vector3f direction		) {
		this.direction = direction;
		return direction;
	}
	public Vector3f Direction(							) {
		return direction;
	}
}