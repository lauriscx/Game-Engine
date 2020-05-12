package FrameBuffer;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glDrawBuffer;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_UNSUPPORTED;
import static org.lwjgl.opengl.GL30.GL_READ_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBlitFramebuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import Mesh.update15v.Texture;
import Render.Display;

//TODO: Make new class where create two frame buffers for multisample and simple call update method pass data to simple fbo from MSfbo and add at the smae time attachments to bout fbo but in MSfbo render buffer allways

public class FrameBuffer {
	private int 		ID;
	private int 		Height;
	private int 		Width;
	private IntBuffer 	ColorAttachments;
	
	public FrameBuffer	(int Width, int Height	) {
		this.Height 			= Height;
		this.Width 				= Width;
		this.ColorAttachments 	= BufferUtils.createIntBuffer(16); 
		ID = glGenFramebuffers();
		
		Bind();
		CheckErrors();
		Unbind();
	}
	
	public void Bind	(int Width, int Height	) {
		glBindFramebuffer(GL_FRAMEBUFFER, ID);
		glViewport(0, 0, Width, Height);
	}
	public void Bind	(						) {
		glBindFramebuffer(GL_FRAMEBUFFER, ID);
		glViewport(0, 0, this.Width, this.Height);
	}
	public void Unbind	(int Width, int Height	) {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glViewport(0, 0, Width, Height);
	}
	public void Unbind	(						) {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}
	
	public void SetUpFrameBuffer() {
		Bind();
		glDrawBuffers(ColorAttachments);
		Unbind();
	}
	
	public void Attachment		(Texture Texture, 				int Attachment		) {
		if(Attachment != GL_DEPTH_ATTACHMENT) {
			ColorAttachments.put(Attachment);
		} else {
			ColorAttachments.put(GL_NONE);
		}
		Bind();
		glFramebufferTexture(GL_FRAMEBUFFER, Attachment, Texture.getID(), 0);
		CheckErrors();
		Unbind();
	}
	public void SingleAttachment(Texture Texture, int Attachment, int TextureType	) {
		Bind();
		Texture.bind();
		glFramebufferTexture2D(GL_FRAMEBUFFER, Attachment, Texture.getID(), TextureType, 0);
		CheckErrors();
		Unbind();
	}
	public void Attachment		(RenderBuffer RenderBuffer, 	int Attachment		) {
		if(Attachment != GL_DEPTH_ATTACHMENT) {
			ColorAttachments.put(Attachment);
		} else {
			ColorAttachments.put(GL_NONE);
		}
		Bind();
		RenderBuffer.Bind();
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, Attachment, GL_RENDERBUFFER, RenderBuffer.GetID());
		CheckErrors();
		Unbind();
	}
	
	public void TransferData		(FrameBuffer FrameBufferObject	) {
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, FrameBufferObject.ID);
		glBindFramebuffer(GL_READ_FRAMEBUFFER, this.ID);
		glBlitFramebuffer(0, 0, this.Width, this.Height, 0, 0, FrameBufferObject.Width, FrameBufferObject.Height, GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT, GL_NEAREST);
		Unbind();
	}
	public void TransferDataToSreen	(int Width, int Height			) {
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
		glBindFramebuffer(GL_READ_FRAMEBUFFER, this.ID);
		glDrawBuffer(GL_BACK);
		glBlitFramebuffer(0, 0, this.Width, this.Height, 0, 0, Width, Height, GL_COLOR_BUFFER_BIT, GL_NEAREST);
		Unbind();
	}
		
	public void CleanUp	() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glDeleteFramebuffers(ID);
	}
	
	private static void CheckErrors() {
		@SuppressWarnings("unused")
		int err;
		if((err = glCheckFramebufferStatus(GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT)) == GL_TRUE) {
			System.err.println("Incomplete attchment!");
		}
		if((err = glCheckFramebufferStatus(GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT)) == GL_TRUE) {
			System.err.println("Incomplete missing attachment!");
		}
		if((err = glCheckFramebufferStatus(GL_FRAMEBUFFER_UNSUPPORTED)) == GL_TRUE) {
			System.err.println("Frame buufer unsupported!");
		}
	}
	
}