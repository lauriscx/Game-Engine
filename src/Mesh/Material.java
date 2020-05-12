package Mesh;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector4f;

import Mesh.update15v.Texture;

public class Material {
	private List<Texture> 	Textures;
	private List<String> 	Names;
	private Vector4f 		Color;
	private Vector2f 		specular;
	
	public static class Build{
		
	}
	
	public Material() {
		this.Textures 	= new ArrayList<Texture>();
		this.Names 		= new ArrayList<String>();
		this.Color 		= new Vector4f(1.0f, 1.0f, 1.0f, 1.0f	);
		this.specular 	= new Vector2f(1.0f, 32.0f				);
	}
	
	public Texture Texture			(Texture Texture, String Name	) {
		Textures.add(Texture);
		Names.add(Name);
		return Texture;
	}
	public List<Texture> GetTextures(								) {
		return Textures;
	}
	public List<String> GetNames	(								) {
		return Names;
	}
	public Texture Texture			(String Name					) {
		for(int i = 0; i < Names.size(); i++) {
			if(Name == Names.get(i)) {
				return Textures.get(i);
			}
		}
		return Textures.get(0);
	}

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