  package Components;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import Components.RawComponentDAO;

/*This component contains 3D object position, scale and rotation.
 * Also has transformation matrix for graphic calculations*/

public class TransformComponent extends RawComponentDAO {
	
	//X, Y, Z axis used for calculations.
	private static final Vector3f VectorX = new Vector3f(1.0f, 0.0f, 0.0f);
	private static final Vector3f VectorY = new Vector3f(0.0f, 1.0f, 0.0f);
	private static final Vector3f VectorZ = new Vector3f(0.0f, 0.0f, 1.0f);
	
	//Data.
	private Vector3f Position;
	private Vector3f Rotation;
	private Vector3f Scale;
	private Matrix4f Transformation;
	
	//Constructor for inti objects.
	public TransformComponent			() {
		Position 		= new Vector3f(0.0f, 0.0f, 0.0f);
		Rotation 		= new Vector3f(0.0f, 0.0f, 0.0f);
		Scale 			= new Vector3f(1.0f, 1.0f, 1.0f);
		Transformation 	= new Matrix4f();
		Transformation.identity();
	}
	/*Setters getters*/
	
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
	
	//Calculate transformation matrix.
	public Matrix4f GetTransformation	() {
		//Set identity means set 1 diagonally form left top to right bottom.
		Transformation.identity();
		//Add positions components in matrix right last column from top.
		Transformation.translate(Position);
		//Add sin and cos in matrix middle 4x4.
		Transformation.rotate((float) Math.toRadians(Rotation.x), VectorX);
		//Add sin and cos separated to top and middle matrix.
		Transformation.rotate((float) Math.toRadians(Rotation.y), VectorY);
		//Add sin and cos in matrix left top 4x4.
		Transformation.rotate((float) Math.toRadians(Rotation.z), VectorZ);
		//Add values to identity.
		Transformation.scale(Scale);
		return Transformation;
	}
}