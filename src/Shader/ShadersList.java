package Shader;

import java.util.ArrayList;
import java.util.List;

public class ShadersList {
	static private List<Shader> Shaders;
	
	static {
		Shaders = new ArrayList<Shader>();
	}
	
	static public int 			AddShader	(Shader Shader	) {
		Shaders.add(Shader);
		return Shaders.size() - 1;
	}
	static public Shader 		GetShader	(int ID			) {
		return Shaders.get(ID);
	}
	static public List<Shader> 	GetShaders	(				) {
		return Shaders;
	}

	static public void CleanUp() {
		for(Shader shader:Shaders) {
			shader.cleanUp();
		}
		Shaders.clear();
	}
}
