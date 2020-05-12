package GameStuff;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.dynamics.*;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

import Components.TransformComponent;
import Entities.Player;
import Entities.Terrain;

public class Physics {
	private static DiscreteDynamicsWorld dynamicsWorld;
	
    static {
			createPhysicWorld();
	        //addGround();
	}
	
    public static void update() {
        dynamicsWorld.stepSimulation(1 / 60.0f);
    }

    
	private static void createPhysicWorld() {
	 	BroadphaseInterface broadphase = new DbvtBroadphase();
        CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
        /**
         * The object that will accurately find out whether, when, how, and where bodies are colliding.
         */
        CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);
        /**
         * The object that will determine what to do after collision.
         */
        ConstraintSolver solver = new SequentialImpulseConstraintSolver();
        // Initialise the JBullet world.
        dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
        // Set the gravity to 10 metres per second squared (m/s^2). Gravity affects all bodies with a mass larger than
        // zero.
        dynamicsWorld.setGravity(new Vector3f(0, -10, 0));
	}
	
	private static void addGround() {
        // Initialise 'groundShape' to a static plane shape on the origin facing upwards ([0, 1, 0] is the normal).
        // 0.25 metres is an added buffer between the ground and potential colliding bodies, used to prevent the bodies
        // from partially going through the floor. It is also possible to think of this as the plane being lifted 0.25m.
        CollisionShape groundShape = new StaticPlaneShape(new Vector3f(1000, 1000, 1000), 0.25f);
        
        // Initialise 'groundMotionState' to a motion state that simply assigns the origin [0, 0, 0] as the origin of
        // the ground.
        MotionState groundMotionState = new DefaultMotionState(new Transform(new Matrix4f(new Quat4f(0, 0, 0, 1), new Vector3f(0, 0, 0), 1.0f)));
        
        // Initialise 'groundBodyConstructionInfo' to a value that contains the mass, the motion state, the shape, and the inertia (= resistance to change).
        RigidBodyConstructionInfo groundBodyConstructionInfo = new RigidBodyConstructionInfo(0, groundMotionState, groundShape, new Vector3f(0, 0, 0));
        
        // Set the restitution, also known as the bounciness or spring, to 0.25. The restitution may range from 0.0
        // not bouncy) to 1.0 (extremely bouncy).
        groundBodyConstructionInfo.restitution = 0.25f;
        
        // Initialise 'groundRigidBody', the final variable representing the ground, to a rigid body with the previously
        // assigned construction information.
        RigidBody groundRigidBody = new RigidBody(groundBodyConstructionInfo);
        
        // Add the ground to the JBullet world.
        dynamicsWorld.addRigidBody(groundRigidBody);
	}
	
	public static void addCube(Vector3f size, Player player) {

		org.joml.Vector3f position = player.getComponent(TransformComponent.class).GetPosition();
        // Initialise 'ballMotion' to a motion state that assigns a specified location to the ball.
        MotionState Motion = new DefaultMotionState(new Transform(new Matrix4f(new Quat4f(0, 0, 0, 1), new Vector3f(position.x, position.y, position.z), 1.0f)));
        
        // Initialise 'ballShape' to a sphere with a radius of 3 metres.
        CollisionShape Shape = new BoxShape(new Vector3f(size.x, size.y, size.z));
        
        // Calculate the ball's inertia (resistance to movement) using its mass (2.5 kilograms).
        Vector3f Inertia = new Vector3f(1, 1, 1);
        Shape.calculateLocalInertia(1f, Inertia);
        
        // Composes the ball's construction info of its mass, its motion state, its shape, and its inertia.
        RigidBodyConstructionInfo ConstructionInfo = new RigidBodyConstructionInfo(1f, Motion, Shape, Inertia);
        
        // Set the restitution, also known as the bounciness or spring, to 0.5. The restitution may range from 0.0
        // not bouncy) to 1.0 (extremely bouncy).
        ConstructionInfo.restitution = 0f;
        ConstructionInfo.angularDamping = 0f;
        
        // Initialise 'controlBall', the final variable representing the controlled ball, to a rigid body with the
        // previously assigned construction information.
        RigidBody body = new RigidBody(ConstructionInfo);
        
        player.setBody(body);
        
        // Disable 'sleeping' for the controllable ball.
        body.setActivationState(CollisionObject.DISABLE_DEACTIVATION);
        
        // Add the control ball to the JBullet world.
        dynamicsWorld.addRigidBody(body);
	}
	
	public static void addCube(Vector3f size, Terrain terrain) {
		org.joml.Vector3f position = terrain.getComponent(TransformComponent.class).GetPosition();
        // Initialise 'ballMotion' to a motion state that assigns a specified location to the ball.
        MotionState Motion = new DefaultMotionState(new Transform(new Transform(new Matrix4f(new Quat4f(0, 0, 0, 1), new Vector3f(position.x, position.y, position.z), 1.0f))));
        
        // Initialise 'ballShape' to a sphere with a radius of 3 metres.
        CollisionShape Shape = new BoxShape(new Vector3f(size.x, size.y, size.z));
        
        // Calculate the ball's inertia (resistance to movement) using its mass (2.5 kilograms).
        Vector3f Inertia = new Vector3f(0, 0, 0);
        Shape.calculateLocalInertia(0, Inertia);
        
        // Composes the ball's construction info of its mass, its motion state, its shape, and its inertia.
        RigidBodyConstructionInfo ConstructionInfo = new RigidBodyConstructionInfo(0, Motion, Shape, Inertia);
        
        // Set the restitution, also known as the bounciness or spring, to 0.5. The restitution may range from 0.0
        // not bouncy) to 1.0 (extremely bouncy).
        ConstructionInfo.restitution = 0f;
        ConstructionInfo.angularDamping = 0f;
        
        // Initialise 'controlBall', the final variable representing the controlled ball, to a rigid body with the
        // previously assigned construction information.
        RigidBody body = new RigidBody(ConstructionInfo);
        
        
        terrain.setBody(body);
        
        // Add the control ball to the JBullet world.
        dynamicsWorld.addRigidBody(body);
	}
	public static void addSphere() {
		
	}
	public static void addPlane() {
		
	}
}