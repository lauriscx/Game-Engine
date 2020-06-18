package Mesh.update15v;

import static org.lwjgl.opengl.GL30.*;

/* Completed */

public class VAO {
	//VAO id returned by openGL.
	private int VAOID;
	
	public VAO() {
		//Generate vertex array object.
		VAOID = glGenVertexArrays();
	}
	
	//Activate object.
	public void Bind	() {
		glBindVertexArray(VAOID);
	}
	//Disable object.
	public void Unbind	() {
		glBindVertexArray(0);
	}
	
	//Clean up data.
	public void CleanUp	() {
		Unbind();
		glDeleteVertexArrays(VAOID);
	}
}