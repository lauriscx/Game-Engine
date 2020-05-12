package Mesh;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.util.ArrayList;
import java.util.List;

import Mesh.update15v.VAO;
import Mesh.update15v.VBO;

/* Completed */

public class Mesh {
	private VAO 		vao;
	private List<VBO> 	vbos;
	private ObjectFile	objectFile;
	
	public Mesh() {
		this.vao 		= new VAO();
		this.vbos 		= new ArrayList<VBO>();
	}
	
	public void Load(String file) {
		this.objectFile = new ObjectFile(file);
		
		vao = new VAO();
		vao.Bind();
		List<VBO> vbos = new ArrayList<VBO>();
		
		//Indices
		vbos.add(new VBO.Builder().setBufferType(GL_ELEMENT_ARRAY_BUFFER)
				.setStorageType(GL_STATIC_DRAW)
				.setDataType(GL_UNSIGNED_INT)
				.build()
				);
		vbos.get(0).bind();
		vbos.get(0).StoreData(objectFile.getIndex());

		//Positions
		vbos.add(new VBO.Builder().setBufferType(GL_ARRAY_BUFFER)
				.setStorageType(GL_STATIC_DRAW)
				.setDataType(GL_FLOAT)
				.setLocation(0)
				.setSize(3)
				.build()
				);
		vbos.get(1).bind();
		vbos.get(1).AttributeSetup();
		vbos.get(1).StoreData(objectFile.getPositions());
		
		//UVs
		vbos.add(new VBO.Builder().setBufferType(GL_ARRAY_BUFFER)
				.setStorageType(GL_STATIC_DRAW)
				.setDataType(GL_FLOAT)
				.setLocation(1)
				.setSize(2)
				.build()
				);
		vbos.get(2).bind();
		vbos.get(2).AttributeSetup();
		vbos.get(2).StoreData(objectFile.getUVs());
		
		
		//Colors
		vbos.add(new VBO.Builder().setBufferType(GL_ARRAY_BUFFER)
				.setStorageType(GL_STATIC_DRAW)
				.setDataType(GL_FLOAT)
				.setLocation(2)
				.setSize(2)
				.build()
				);
		vbos.get(3).bind();
		vbos.get(3).AttributeSetup();
		vbos.get(3).StoreData(objectFile.getColors());
		
		//Normals
		vbos.add(new VBO.Builder().setBufferType(GL_ARRAY_BUFFER)
				.setStorageType(GL_STATIC_DRAW)
				.setDataType(GL_FLOAT)
				.setLocation(3)
				.setSize(3)
				.build()
				);
		vbos.get(4).bind();
		vbos.get(4).AttributeSetup();
		vbos.get(4).StoreData(objectFile.getNormals());
		
		//Tangents
		vbos.add(new VBO.Builder().setBufferType(GL_ARRAY_BUFFER)
				.setStorageType(GL_STATIC_DRAW)
				.setDataType(GL_FLOAT)
				.setLocation(4)
				.setSize(3)
				.build()
				);
		vbos.get(5).bind();
		vbos.get(5).AttributeSetup();
		vbos.get(5).StoreData(objectFile.getTangents());
		
		//BiTangents
		vbos.add(new VBO.Builder().setBufferType(GL_ARRAY_BUFFER)
				.setStorageType(GL_STATIC_DRAW)
				.setDataType(GL_FLOAT)
				.setLocation(5)
				.setSize(3)
				.build()
				);
		vbos.get(6).bind();
		vbos.get(6).AttributeSetup();
		vbos.get(6).StoreData(objectFile.getBiTangents());
		
		if(objectFile.getWeights().length != 0) {
			//Joint weights
			vbos.add(new VBO.Builder().setBufferType(GL_ARRAY_BUFFER)
					.setStorageType(GL_STATIC_DRAW)
					.setDataType(GL_FLOAT)
					.setLocation(6)
					.setSize(4)
					.build()
					);
			vbos.get(7).bind();
			vbos.get(7).AttributeSetup();
			vbos.get(7).StoreData(objectFile.getWeights());
			
			//joint Index
			vbos.add(new VBO.Builder().setBufferType(GL_ARRAY_BUFFER)
					.setStorageType(GL_STATIC_DRAW)
					.setDataType(GL_INT)
					.setLocation(7)
					.setSize(4)
					.build()
					);
			vbos.get(8).bind();
			vbos.get(8).AttributeISetup();//AttribIPointer.
			vbos.get(8).StoreData(objectFile.getBonesIndex());
		}
		vao.Unbind();
	}
		
	public VAO vao	() {
		return this.vao;
	}
	public List<VBO> vbos() {
		return this.vbos;
	}

	public int getVertexCount() {
		return this.objectFile.getPositions().length;
	}
	
	public void clean() {
		this.vao.Bind();
		for(VBO vbo : this.vbos) {
			vbo.CleanUp();
		}
		this.vao.CleanUp();
	}
}