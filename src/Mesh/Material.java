package Mesh;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector4f;

import Mesh.update15v.Texture;

public class Material {
	//Texture list for mesh. Color map, normal map, ambient map, ect..
	private List<Texture> 	Textures;
	//Names of textures.
	private List<String> 	Names;
	//object color.
	private Vector4f 		Color;
	//object shines.
	private Vector2f 		specular;
	
	public static class Build{
		
	}
	
	//Set up variables
	public Material() {
		this.Textures 	= new ArrayList<Texture>();
		this.Names 		= new ArrayList<String>();
		this.Color 		= new Vector4f(1.0f, 1.0f, 1.0f, 1.0f	);
		this.specular 	= new Vector2f(1.0f, 32.0f				);
	}
	
	//Add texture.
	public Texture Texture			(Texture Texture, String Name	) {
		Textures.add(Texture);
		Names.add(Name);
		return Texture;
	}
	//Get all textures used for rendering.
	public List<Texture> GetTextures(								) {
		return Textures;
	}
	//get names
	public List<String> GetNames	(								) {
		return Names;
	}
	//Get texture by name.
	public Texture Texture			(String Name					) {
		for(int i = 0; i < Names.size(); i++) {
			if(Name == Names.get(i)) {
				return Textures.get(i);
			}
		}
		return Textures.get(0);
	}

	/* setters getters*/
	public Vector4f Color	(Vector4f Color						) {
		this.Color = Color;
		return Color;
	}
	public void 	Color	(float R, float G, float B, float A	) {
		this.Color.x = R;
		this.Color.y = G;
		this.Color.z = B;
		this.Color.w = A;
	}
	public Vector4f Color	(									) {
		return this.Color;
	}
	public Vector2f Specular(float Intesity, float Exponent		) {
		specular.x = Intesity;
		specular.y = Exponent;
		return specular;
	}
	public Vector2f Specular(Vector2f specular					) {
		this.specular = specular;
		return specular;
	}
	public Vector2f Specular(									) {
		return specular;
	}
	
	//Clean up all objects.
	public void CleanUp() {
		for(Texture texture : Textures) {
			if(texture != null) {
				texture.cleanUp();
			}
		}
		Textures.clear();
		Names.clear();
	}
}