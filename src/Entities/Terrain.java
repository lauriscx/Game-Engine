package Entities;

import org.joml.Vector3f;

import com.bulletphysics.dynamics.RigidBody;

import Components.TransformComponent;

public class Terrain extends Entity {

	private RigidBody body;
	
	public void setBody(RigidBody body) {
		this.body = body;
	}
	
	@Override
	public void Update() {
		// TODO Auto-generated method stub
		super.Update();
		
		javax.vecmath.Vector3f bodyPosition = new javax.vecmath.Vector3f();
		body.getCenterOfMassPosition(bodyPosition);
		
		Vector3f position = getComponent(TransformComponent.class).GetPosition();
		
		position.x = bodyPosition.x;
		position.y = bodyPosition.y;
		position.z = bodyPosition.z;
	}
}