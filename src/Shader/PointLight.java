package Shader;

import org.joml.Vector3f;

/* Completed */

public class PointLight extends BaseLight {
	private Vector3f 	position;

	private float 		constant;
	private float 		linear;
	private float 		quadratic;
	
	private float 		range;
	
	public PointLight	() {
		super();
		
		constant 	= 1;
		linear 		= 0;
		quadratic 	= 0;
		
		position 	= new Vector3f(0.0f, 1.0f, 0.0f	);
		range		= 0.0f;

	}

	@Override
	public void activate() {
		ID = LightsLists.addPointLight(this);
	}
	@Override
	public void disable	() {
		LightsLists.pointLights().remove(ID);
	}
	
	public Vector3f Position	(float X, float Y, float Z	) {
		position.x = X;
		position.y = Y;
		position.z = Z;
		return position;
	}
	public Vector3f Position	(Vector3f position			) {
		this.position = position;
		return position;
	}
	public Vector3f Position	(							) {
		return position;
	}
	
	public float attenuationConstant	(float constant	) {
			this.constant = constant;
			return constant;
	}
	public float attenuationConstant	(				) {
		return constant;
}
	public float attenuationLinear		(float linear	) {
		this.linear = linear;
		return linear;
	}
	public float attenuationLinear		(				) {
		return linear;
	}
	public float attenuationQuadratic	(float quadratic) {
		this.quadratic = quadratic;
		return quadratic;
	}
	public float attenuationQuadratic	(				) {
	return quadratic;
	}

	public float Range(float range	) {
		this.range = range;
		return range;
	}
	public float Range(	 			) {
		return range;
	}
}