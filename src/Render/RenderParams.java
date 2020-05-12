package Render;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL32.GL_PROGRAM_POINT_SIZE;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.*;

public class RenderParams {
	private int ID;
	private int mode = GL_TRIANGLES;
	private int drawType = 0;
	
	/* Prameters */
	boolean depthTest = true;
	int cullFace = 1; // 0 - none, 1 - Back, 2 - Front
	int cullFaceClockWise = 1;// 0 - CW, 1 - CCW
	
	public RenderParams() {
		ID = Render.AddRenderParams(this);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_PROGRAM_POINT_SIZE);
		//glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glFrontFace(GL_CCW); 
	}
	
	public int drawType() {
		return drawType;
	}
	public void drawElements() {
		drawType = 0;
	}
	public void drawArrays() {
		drawType = 1;
	}
	
	public int 	renderMode() {
		return mode;
	}
	public void renderModePoints() {
		mode = GL_POINTS;
	}
	public void renderModeTriangles() {
		mode = GL_TRIANGLES;
	}
	public void renderModeTriangleStrip() {
		mode = GL_TRIANGLE_STRIP;
	}
	public void renderModeLines() {
		mode = GL_LINES;
	}
	public void renderModeQuads() {
		mode = GL_QUADS;
	}
	
	public void cullFaceNone() {
		cullFace = 0;
	}
	public void cullFaceBack() {
		cullFace = 1;
	}
	public void cullFaceFront() {
		cullFace = 2;
	}
	
	public void cullFaceClockWise() {
		cullFaceClockWise = 0;
	}
	public void cullFaceCounterClockWise() {
		cullFaceClockWise = 1;
	}
	
	
	public void setParams() {
		if(cullFace != 0) {
			glEnable(GL_CULL_FACE);
			
			if(cullFace == 1) {
				glCullFace(GL_BACK);
			} else if(cullFace == 2) {
				glCullFace(GL_FRONT);
			}
			
			if(cullFaceClockWise == 0) {
				glFrontFace(GL_CW); 
			} else if(cullFaceClockWise == 1) {
				glFrontFace(GL_CCW); 
			}
		} else {
			glDisable(GL_CULL_FACE);
		}
		
	}
	
	public int GetID() {
		return this.ID;
	}
}