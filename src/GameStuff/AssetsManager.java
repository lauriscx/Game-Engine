package GameStuff;

import java.util.HashMap;
import java.util.Map;

import Mesh.Material;
import Mesh.Mesh;
import Mesh.ObjectFile;
import Mesh.update15v.Texture;

public class AssetsManager {
	private static Map<String, Texture> textures;
	private static Map<String, ObjectFile> objs;
	private static Map<String, Material> materials;
	private static Map<String, Mesh> meshes;
	
	static {
		textures 	= new HashMap<String, Texture>();
		objs 		= new HashMap<String, ObjectFile>();
		materials 	= new HashMap<String, Material>();
		meshes 		= new HashMap<String, Mesh>();
	}
	
	
	public static void addTexture(String file, Texture texture) {
		textures.put(file, texture);
	}
	public static Texture getTexture(String file) {
		if(textures.get(file) == null) {
			System.err.println("Asset " + file + " not found!");
			return null;
		}
		return textures.get(file);
	}
	
	public static void addObj(String file, ObjectFile obj) {
		objs.put(file, obj);
	}
	public static ObjectFile getObj(String file) {
		if(objs.get(file) == null) {
			System.err.println("Asset " + file + " not found!");
			return null;
		}
		return objs.get(file);
	}
	
	
	public static void addMaterial(String file, Material material) {
		materials.put(file, material);
	}
	public static Material getMaterial(String file) {
		if(materials.get(file) == null) {
			System.err.println("Asset " + file + " not found!");
			return null;
		}
		return materials.get(file);
	}
	
	
	public static void addMesh(String file, Mesh mesh) {
		meshes.put(file, mesh);
	}
	public static Mesh getMesh(String file) {
		if(meshes.get(file) == null) {
			System.err.println("Asset " + file + " not found!");
			return null;
		}
		return meshes.get(file);
	}
}