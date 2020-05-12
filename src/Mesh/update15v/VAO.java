package Mesh.update15v;

import static org.lwjgl.opengl.GL30.*;

/* Completed */

public class VAO {
	private int VAOID;
	
	public VAO() {
		VAOID = glGenVertexArrays();
	}
	
	public void Bind	() {
		glBindVertexArray(VAOID);
	}
	public void Unbind	() {
		glBindVertexArray(0);
	}
	
	public void CleanUp	() {
		Unbind();
		glDeleteVertexArrays(VAOID);
	}
}