package Shader;

/* Completed */

public class SpotLight extends PointLight {
	private float innerCutOff;
	private float outerCutOff;
	
	public SpotLight	() {
		super();
		
		innerCutOff = 0;
		outerCutOff	= 0;
	}
	@Override
	public void activate() {
		ID = LightsLists.addSpotLight(this);
	}
	@Override
	public void disable	() {
		LightsLists.spotLights().remove(ID);
	}
	
	public float innerCutOff (float innerCutOff	) {
		this.innerCutOff = innerCutOff;
		return innerCutOff;
	}
	public float innerCutOff (					) {
		return innerCutOff;
	}
	public float outerCutOff (float outerCutOff	) {
		this.outerCutOff = outerCutOff;
		return outerCutOff;
	}
	public float outerCutOff (					) {
		return outerCutOff;
	}
}