package Entities;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector3f;

import com.bulletphysics.dynamics.RigidBody;

import Components.TransformComponent;
import DataInput.Input;
import GameStuff.DeltaTime;
import GameStuff.Physics;
import Render.Camera;
import Render.CamerasList;

public class Player extends Entity {
	private Camera camera;
	private static double time;
	private static double second;
	private static RigidBody body;
	
	static {
		time = 0;
		second = 0;
	}
	
	public Player(int cameraID) {
		camera = CamerasList.GetCamera(cameraID);
		camera.IncreseRotation(30, 180, 0);
		camera.SetPosition(0, 3, 0);
	}
	
	public void setBody(RigidBody body) {
		this.body = body;
	}
	
	@Override
	public void Update() {
		super.Update();
		
		time += DeltaTime.getDelta();
		second += DeltaTime.getDelta();
		
		if(time > 5) {
			Physics.update();
			
			float movement = (float) (DeltaTime.getDelta() * 1f);
			float movementAdditional = (float) (DeltaTime.getDelta() * 0.5f);
			
			javax.vecmath.Vector3f bodyPosition = new javax.vecmath.Vector3f();
			body.getCenterOfMassPosition(bodyPosition);
			//body.getWorldTransform(out)
			
			Vector3f rotation = getComponent(TransformComponent.class).GetRotation();
			Vector3f position = getComponent(TransformComponent.class).GetPosition();
			
			position.x = bodyPosition.x;
			position.y = bodyPosition.y;
			position.z = bodyPosition.z;
			
			body.clearForces();
			
			if(Input.Keys[GLFW_KEY_LEFT_SHIFT]) {
				//position.y -= movement;
			}  
			if(Input.Keys[GLFW_KEY_SPACE]) {
				body.applyImpulse(new javax.vecmath.Vector3f(0f, 1, 0.0f), new javax.vecmath.Vector3f(0, 0, 0));
				//position.y += movement;
			}
			
			
			if(Input.Keys[GLFW_KEY_A]) {
				//position.x += movement;
				rotation.z -= 360 * DeltaTime.getDelta();
			}  
			if(Input.Keys[GLFW_KEY_D]) {
				//position.x -= movement;
				rotation.z += 360 * DeltaTime.getDelta();
			}
			
			if(Input.Keys[GLFW_KEY_W]) {
				//position.z += movementAdditional;
				rotation.x += 360 * DeltaTime.getDelta();
			}
			if(Input.Keys[GLFW_KEY_S]) {
				//position.z -= movementAdditional;
				rotation.x -= 360 * DeltaTime.getDelta();
			}
			camera.SetPosition(position.x, position.y + 2, position.z - 2);
		} else {
			if(second > 1) {
				int countDown = (int)(5 - time);
				if(countDown > 0) {
					System.out.println("Begining in: " + countDown);
				} else {
					System.out.println("Go !");
					body.applyImpulse(new javax.vecmath.Vector3f(0f, 0, 10f), new javax.vecmath.Vector3f(0, 0, 0));
				}
				second = 0;
			}
		}
	}
}