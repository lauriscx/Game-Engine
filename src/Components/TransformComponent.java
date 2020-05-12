  package Components;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import Components.RawComponentDAO;

public class TransformComponent extends RawComponentDAO {
	
	private static final Vector3f VectorX = new Vector3f(1.0f, 0.0f, 0.0f);
	private static final Vector3f VectorY = new Vector3f(0.0f, 1.0f, 0.0f);
	private static final Vector3f VectorZ = new Vector3f(0.0f, 0.0f, 1.0f);
	
	private Vector3f Position;
	private Vector3f Rotation;
	private Vector3f Scale;
	private Matrix4f Transformation;
	
	public TransformComponent			() {
		Position 		= new Vector3f(0.0f, 0.0f, 0.0f);
		Rotation 		= new Vector3f(0.0f, 0.0f, 0.0f);
		Scale 			= new Vector3f(1.0f, 1.0f, 1.0f);
		Transformation 	= new Matrix4f();
		Transformation.identity();
	}
	public void SetPosition				(Vector3f Position			) {
		this.Position = Position;
	}
	public void SetRotation				(Vector3f Rotation			) {
		this.Rotation = Rotation;
	}
	public void SetScale				(Vector3f Scale				) {
		this.Scale = Scale;
	}
	public void SetPosition				(float X, float Y, float Z	) {
		this.Position.x = X;
		this.Position.y = Y;
		this.Position.z = Z;
	}
	public void SetRotation				(float X, float Y, float Z	) {
		this.Rotation.x = X;
		this.Rotation.y = Y;
		this.Rotation.z = Z;
	}
	public void SetScale				(float X, float Y, float Z	) {
		this.Scale.x = X;
		this.Scale.y = Y;
		this.Scale.z = Z;
	}
	public Vector3f GetPosition			() {
		return this.Position;
	}
	public Vector3f GetRotation			() {
		return this.Rotation;
	}
	public Vector3f GetScale			() {
		return this.Scale;
	}
	public Matrix4f GetTransformation	() {
		Transformation.identity();
		Transformation.translate(Position);
		Transformation.rotate((float) Math.toRadians(Rotation.x), VectorX);
		Transformation.rotate((float) Math.toRadians(Rotation.y), VectorY);
		Transformation.rotate((float) Math.toRadians(Rotation.z), VectorZ);
		Transformation.scale(Scale);
		return Transformation;
	}
}