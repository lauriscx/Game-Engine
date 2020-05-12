package Shader;

import java.util.ArrayList;
import java.util.List;

public class LightsLists {
	static private List<DirectionLight> directionLights;
	static private List<PointLight> 	pointLights;
	static private List<SpotLight> 		spotLights;
	
	static {
		directionLights = new ArrayList<DirectionLight>();
		pointLights 	= new ArrayList<PointLight>();
		spotLights 		= new ArrayList<SpotLight>();
	}
	
	static public int 					addDirectionLight	(DirectionLight light	) {
		directionLights.add(light);
		return directionLights.size() - 1;
	}
	static public DirectionLight 		directionLight		(int ID					) {
		return directionLights.get(ID);
	}
	static public List<DirectionLight> 	directionLights		(						) {
		return directionLights;
	}
	
	static public int 					addPointLight		(PointLight light		) {
		pointLights.add(light);
		return pointLights.size() - 1;
	}
	static public PointLight 			pointLight			(int ID					) {
		return pointLights.get(ID);
	}
	static public List<PointLight> 		pointLights			(						) {
		return pointLights;
	}
	
	static public int 					addSpotLight		(SpotLight light		) {
		spotLights.add(light);
		return spotLights.size() - 1;
	}
	static public SpotLight 			spotLight			(int ID					) {
		return spotLights.get(ID);
	}
	static public List<SpotLight> 		spotLights			(						) {
		return spotLights;
	}
	

	static public void cleanUp	() {
		directionLights.clear();
		pointLights.clear();
		spotLights.clear();
	}
}