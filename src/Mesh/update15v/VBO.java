package Mesh.update15v;

import static org.lwjgl.opengl.GL33.*;

/* Completed */
public class VBO {
	private int id;
	private int bufferType;
	private int storageType;
	private int dataType;
	private int location;
	private int stride;
	private int offset;
	private int size;
	private boolean normalized;
	
	public static class Builder {
		private int bufferType;
		private int storageType;
		private int dataType;
		private int location;
		private int stride;
		private int offset;
		private int size;
		private boolean normalized;
		
		public Builder() {
			bufferType 	= GL_ARRAY_BUFFER;
			storageType = GL_STATIC_DRAW;
			dataType 	= GL_FLOAT;
			location 	= 0;
			stride 		= 0;
			offset 		= 0;
			normalized 	= false;
		}
		
		public Builder setBufferType	(int bufferType) {
			this.bufferType = bufferType;
			return this;
		}
		public Builder setStorageType	(int storageType) {
			this.storageType = storageType;
			return this;
		}
		public Builder setDataType		(int dataType) {
			this.dataType = dataType;
			return this;
		}
		public Builder setLocation		(int location) {
			this.location = location;
			return this;
		}
		public Builder setStride		(int stride) {
			this.stride = stride;
			return this;
		}
		public Builder setOffset		(int offset) {
			this.offset = offset;
			return this;
		}
		public Builder setSize			(int size) {
			this.size = size;
			return this;
		}
		public Builder setNormalized	(boolean normalized) {
			this.normalized = normalized;
			return this;
		}
		public VBO build			() {
			return new VBO(this);
		}
	}
	
	public VBO(Builder builder) {
		this.id 			= glGenBuffers();
		this.bufferType 	= builder.bufferType;
		this.storageType 	= builder.storageType;
		this.dataType 		= builder.dataType;
		this.location		= builder.location;
		this.stride 		= builder.stride;
		this.offset 		= builder.offset;
		this.size			= builder.size;
		this.normalized 	= builder.normalized;
	}
	
	public void bind	() {
		/*Enables vertex buffer object*/
		glBindBuffer(this.bufferType, this.id);
		
		/*Enables vertex array buffer attribute location if it is not element array*/
		if(this.bufferType != GL_ELEMENT_ARRAY_BUFFER) {
			enableAttributeArray();
		}
	}
	public void unbind	() {
		/*Disables vertex buffer object*/
		glBindBuffer(this.bufferType, 0);
		
		/*Disables vertex array buffer attribute location if it is not element array*/
		if(this.bufferType != GL_ELEMENT_ARRAY_BUFFER) {
			disableAttributeArray();
		}
	}
	
	public void enableAttributeArray	() {
		glEnableVertexAttribArray(this.location);
	}
	public void disableAttributeArray	() {
		glDisableVertexAttribArray(this.location);
	}
	

	public void AttribDivisor	(int Incrament) {
		/*	glVertexAttribDivisor modifies the rate at which generic vertex attributes
		 * 	advance when rendering multiple instances of primitives in a single draw call.
		 * 	If divisor is zero, the attribute at slot index advances once per vertex.
		 * 	If divisor is non-zero, the attribute advances once per divisor instances of
		 * 	the set(s) of vertices being rendered.
		 * 	An attribute is referred to as instanced if its GL_VERTEX_ATTRIB_ARRAY_DIVISOR value is non-zero.
		 * 	index must be less than the value of GL_MAX_VERTEX_ATTRIBS.
		 * */
		glVertexAttribDivisor(this.size, Incrament);
	}
	public void AttributeSetup	() {
		/*
		 * Say to opengl witch buffer(location) in shader program,
		 * how many variables(size) to use for 1 vertex,
		 * dataType(for variable type for example float, int, double),
		 * normalize is values is rounded,
		 * stride how many variables to skip,
		 * offset from witch variable to start.
		 * */
		glVertexAttribPointer(this.location, this.size, this.dataType, this.normalized, this.stride, this.offset);
	}
	public void AttributeISetup	() {
		/*
		 * Say to opengl witch buffer(location) in shader program,
		 * how many variables(size) to use for 1 vertex,
		 * dataType(for variable type for example float, int, double),
		 * stride how many variables to skip,
		 * offset from witch variable to start.
		 * */
		glVertexAttribIPointer(this.location, this.size, this.dataType, this.stride, this.offset);
	}
	
	public void ReserveData	(int Size		) {
		glBufferData(this.bufferType, Size, this.storageType);
	}
	
	public void StoreData	(long 	[] data) {
		glBufferData(this.bufferType, data, this.storageType);
	}
	public void StoreData	(int 	[] data) {
		glBufferData(this.bufferType, data, this.storageType);
	}
	public void StoreData	(float 	[] data) {
		glBufferData(this.bufferType, data, this.storageType);
	}
	public void StoreData	(double [] data) {
		glBufferData(this.bufferType, data, this.storageType);
	}
	
	public void StoreData	(long 	[] Data, int Offset) {
		glBufferSubData(this.bufferType, Offset, Data);
	}
	public void StoreData	(int 	[] Data, int Offset) {
		glBufferSubData(this.bufferType, Offset, Data);
	}
	public void StoreData	(float 	[] Data, int Offset) {
		glBufferSubData(this.bufferType, Offset, Data);
	}
	public void StoreData	(double [] Data, int Offset) {
		glBufferSubData(this.bufferType, Offset, Data);
	}
	
	
	public void CleanUp() {
		unbind();
		glDeleteBuffers(this.id);
	}
}
