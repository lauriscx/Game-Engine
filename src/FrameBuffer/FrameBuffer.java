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
	//Frame buffer is storage to hold different rendered data as color, depths. It's like folder to store multiple images but images must "match" folder size.
	
	//ID of frame buffer witch is get from OpelGL.
	private int 		ID;
	//Frame buffer height.
	private int 		Height;
	//Frame buffer width.
	private int 		Width;
	//What attachments contains frame buffer example color, depth, stencil ect.
	private IntBuffer 	ColorAttachments;
	
	//Set up frame bugger generate it in openGl and set width and height.
	public FrameBuffer	(int Width, int Height	) {
		this.Height 			= Height;
		this.Width 				= Width;
		//Generate int buffer. Max 16 different attachments exist in openGL.
		this.ColorAttachments 	= BufferUtils.createIntBuffer(16); 
		//Generate frame buffer object and return ID from list or collection.
		ID = glGenFramebuffers();
		
		//Activate frame buffer and check for errors.
		Bind();
		CheckErrors();
		//Deactivate buffer. Activate default buffer witch is use to display data to screen.
		Unbind();
	}
	
	//Bind buffer
	public void Bind	(int Width, int Height	) {
		//Buffer type, ID.
		glBindFramebuffer(GL_FRAMEBUFFER, ID);
		//Size and dimensions.
		glViewport(0, 0, Width, Height);
	}
	//Bind buffer
	public void Bind	(						) {
		//Bind this buffer with preset size.
		glBindFramebuffer(GL_FRAMEBUFFER, ID);
		glViewport(0, 0, this.Width, this.Height);
	}
	//Bind default buffer and set size as program window.
	public void Unbind	(int Width, int Height	) {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glViewport(0, 0, Width, Height);
	}
	//Bind default buffer and set size as program window.
	public void Unbind	(						) {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}
	
	//Set in witch color attachment should draw frame buffer.
	public void SetUpFrameBuffer() {
		Bind();
		glDrawBuffers(ColorAttachments);
		Unbind();
	}
	
	//Add texture attachment to frame buffer. Used to multiple attachments add.
	public void Attachment		(Texture Texture, 				int Attachment		) {
		//Added to list latter is used for SetUpFrameBuffer function.
		if(Attachment != GL_DEPTH_ATTACHMENT) {
			ColorAttachments.put(Attachment);
		} else {
			ColorAttachments.put(GL_NONE);
		}
		//Activate this frame buffer.
		Bind();
		//Add attachment to frame buffer.
		glFramebufferTexture(GL_FRAMEBUFFER, Attachment, Texture.getID(), 0);
		//Check for errors.
		CheckErrors();
		//Bind default frame buffer.
		Unbind();
	}
	//Add Texture attachment.
	public void SingleAttachment(Texture Texture, int Attachment, int TextureType	) {
		//activate this frame buffer.
		Bind();
		//Activate texture.
		Texture.bind();
		//Add texture to frame buffer.
		glFramebufferTexture2D(GL_FRAMEBUFFER, Attachment, Texture.getID(), TextureType, 0);
		//Check for errors.
		CheckErrors();
		//Activate default frame buffer.
		Unbind();
	}
	
	//Add render buffer attachment just for hold data then processed in graphic card. Used for multiple attachments work together with Texture attachment function.
	public void Attachment		(RenderBuffer RenderBuffer, 	int Attachment		) {
		//Added to list latter is used for SetUpFrameBuffer function.
		if(Attachment != GL_DEPTH_ATTACHMENT) {
			ColorAttachments.put(Attachment);
		} else {
			ColorAttachments.put(GL_NONE);
		}
		//Activate this frame buffer.
		Bind();
		//Activate render buffer.
		RenderBuffer.Bind();
		//Add render buffer to frame buffer.
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, Attachment, GL_RENDERBUFFER, RenderBuffer.GetID());
		//Check for errors.
		CheckErrors();
		//Activate default frame buffer.
		Unbind();
	}
	
	//Transfer data from passed frame buffer to this frame buffer.
	public void TransferData		(FrameBuffer FrameBufferObject	) {
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, FrameBufferObject.ID);
		glBindFramebuffer(GL_READ_FRAMEBUFFER, this.ID);
		glBlitFramebuffer(0, 0, this.Width, this.Height, 0, 0, FrameBufferObject.Width, FrameBufferObject.Height, GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT, GL_NEAREST);
		Unbind();
	}
	//Transfer this frame buffer data to default frame buffer data witch is shown on screen.
	public void TransferDataToSreen	(int Width, int Height			) {
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
		glBindFramebuffer(GL_READ_FRAMEBUFFER, this.ID);
		glDrawBuffer(GL_BACK);
		glBlitFramebuffer(0, 0, this.Width, this.Height, 0, 0, Width, Height, GL_COLOR_BUFFER_BIT, GL_NEAREST);
		Unbind();
	}
		
	//Before destroying this object disable this frame buffer and then delete it.
	public void CleanUp	() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glDeleteFramebuffers(ID);
	}
	
	//Check for errors is error occurs then is shown in console.
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