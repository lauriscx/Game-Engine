package Shader;

import org.joml.Vector3f;

/* Completed */

public class BaseLight {
	protected int 		ID;
	private float		intensivity;
	private Vector3f	color;
	
	public BaseLight	() {
		intensivity = 1;
		color 		= new Vector3f(1.0f, 1.0f, 1.0f	);

	}
	public int ID		() {
		return ID;
	}
	public void activate() {
		
	}
	public void disable	() {
		
	}
	
	public float 	intensivity	(float intensivity					) {
		this.intensivity = intensivity;
		return intensivity;
	}
	public float 	intensivity	(	 								) {
		return intensivity;
	}
	public Vector3f color		(float red, float green, float blue	) {
		color.x = red;
		color.y = green;
		color.z = blue;
		return color;
	}
	public Vector3f color		(Vector3f color						) {
		this.color = color;
		return color;
	}
	public Vector3f color		(									) {
		return color;
	}
}