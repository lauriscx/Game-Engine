package Mesh;

import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;

/*Using library assimp*/

public class ObjectFile {
	//Variables to store laoded data.
	private int[] 	Index;
	private int[]	MaterialIndex;
	private float[]	Positions;
	private float[] Normals;
	private float[] Tangents;
	private float[] BiTangents;
	private float[] UVs;
	private float[]	Colors;
	private float[] Weights;
	private float[] BonesIndex;
	
	private int ArraysSize 	= 0;
	private int ID 			= 0;
	private int IDMID 		= 0;
	private int IDPos 		= 0;
	private int IDNor 		= 0;
	private int IDTan 		= 0;
	private int IDBiTan 	= 0;
	private int IDUVs 		= 0;
	//private int IDCol 		= 0;
	
	
	//Load data.
	public ObjectFile(String File) {
		//Load data and select what data how should be processed.
		AIScene aiScene = Assimp.aiImportFile(File, Assimp.aiProcess_Triangulate | Assimp.aiProcess_GenNormals |
				Assimp.aiProcess_FlipUVs | Assimp.aiProcess_CalcTangentSpace | Assimp.aiProcess_LimitBoneWeights);
		//If aiScene null it's mean failed to load data.
		if (aiScene == null) {
			System.out.println("Failed load file: " + File);
			return;
		}
		//Fill variables with data.
		Load(aiScene);
	}
	
	/* Loading data from file this function depends on usable file reader */
	private void Load(AIScene aiScene) {
		//Get first mesh in mesh list.
		AIMesh mesh = AIMesh.create(aiScene.mMeshes().get(0));
		
		//Create arrays with size of mesh.
		ArraysSize 		= mesh.mNumVertices();
		Index 			= new int[ArraysSize];
		MaterialIndex	= new int[ArraysSize];
		Positions 		= new float[ArraysSize * 3];
		Normals 		= new float[ArraysSize * 3];
		Tangents 		= new float[ArraysSize * 3];
		BiTangents 		= new float[ArraysSize * 3];
		UVs 			= new float[ArraysSize * 3];
		Colors 			= new float[ArraysSize * 4];
		Weights			= new float[ArraysSize * 12];
		BonesIndex	 	= new float[ArraysSize * 12];
		
		//Fill data to arrays.
		for(int v = 0; v < mesh.mNumVertices(); v++) {
			MaterialIndex[IDMID++] = mesh.mMaterialIndex();
			
			Positions[IDPos++] = mesh.mVertices().get(v).x();
			Positions[IDPos++] = mesh.mVertices().get(v).y();
			Positions[IDPos++] = mesh.mVertices().get(v).z();
			
			Normals[IDNor++] = mesh.mNormals().get(v).x();
			Normals[IDNor++] = mesh.mNormals().get(v).y();
			Normals[IDNor++] = mesh.mNormals().get(v).z();
			
			Tangents[IDTan++] = mesh.mTangents().get(v).x();
			Tangents[IDTan++] = mesh.mTangents().get(v).y();
			Tangents[IDTan++] = mesh.mTangents().get(v).z();
			
			BiTangents[IDBiTan++] = mesh.mBitangents().get(v).x();
			BiTangents[IDBiTan++] = mesh.mBitangents().get(v).y();
			BiTangents[IDBiTan++] = mesh.mBitangents().get(v).z();
			
			UVs[IDUVs++] = mesh.mTextureCoords(0).get(v).x();
			UVs[IDUVs++] = mesh.mTextureCoords(0).get(v).y();
			//UVs[IDUVs++] = mesh.mTextureCoords(0).get(v).z();
			
			/*Colors[IDCol++] = mesh.mColors(v).r();
			Colors[IDCol++] = mesh.mColors(v).g();
			Colors[IDCol++] = mesh.mColors(v).b();
			Colors[IDCol++] = mesh.mColors(v).a();*/
		}
		
		//Get indices
		for (int f = 0; f < mesh.mNumFaces(); f++) {
		    AIFace Face = mesh.mFaces().get(f);
		    for(int i = 0; i < Face.mNumIndices(); i++) {
		    	Index[ID++] = Face.mIndices().get(i);
		    }
		}
	}

	
	/*Setters getters*/
	public int[] getIndex() {
		return Index;
	}

	public int[] getMaterialIndex() {
		return MaterialIndex;
	}

	public float[] getPositions() {
		return Positions;
	}

	public float[] getNormals() {
		return Normals;
	}

	public float[] getTangents() {
		return Tangents;
	}
	public float[] getBiTangents() {
		return BiTangents;
	}

	public float[] getUVs() {
		return UVs;
	}
	public float[] getColors() {
		return Colors;
	}

	public float[] getWeights() {
		return Weights;
	}
	public float[] getBonesIndex() {
		return BonesIndex;
	}
}