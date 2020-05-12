package FrameBuffer;

import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glDeleteRenderbuffers;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;
import static org.lwjgl.opengl.GL30.glRenderbufferStorageMultisample;

/* Completed */

public class RenderBuffer {
	private int ID;
	private int Width;
	private int Height;
	private int OpenglFormat;
	private int SamlesNumber;
	
	public RenderBuffer(int Width, int Height, int OpenglFormat, int SamlesNumber	) {
		this.Width 			= Width;
		this.Height 		= Height;
		this.OpenglFormat 	= OpenglFormat;
		this.SamlesNumber 	= SamlesNumber;
		ID = glGenRenderbuffers();
		SetUp();
	}
	public RenderBuffer(int Width, int Height, int OpenglFormat						) {
		this.Width 			= Width;
		this.Height 		= Height;
		this.OpenglFormat 	= OpenglFormat;
		this.SamlesNumber 	= 1;
		ID = glGenRenderbuffers();
		SetUp();
	}
	
	public void Bind	() {
		glBindRenderbuffer(GL_RENDERBUFFER, ID);
	}
	public void Unbind	() {
		glBindRenderbuffer(GL_RENDERBUFFER, 0);
	}
	
	public int GetID	() {
		return ID;
	}
	public void SetUp	() {
		Bind();
		if(this.SamlesNumber <= 1) {
			glRenderbufferStorage(GL_RENDERBUFFER, OpenglFormat, Width, Height);
		} else {
			glRenderbufferStorageMultisample(GL_RENDERBUFFER, this.SamlesNumber, OpenglFormat, Width, Height);
		}
		Unbind();
	}
	
	public void CleanUp	() {
		Unbind();
		glDeleteRenderbuffers(ID);
	}
}