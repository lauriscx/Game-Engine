package Mesh.update15v;

import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTexSubImage2D;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.GL_DEPTH_TEXTURE_MODE;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_COMPARE_FUNC;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_COMPARE_MODE;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_LOD_BIAS;
import static org.lwjgl.opengl.GL30.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL;

//glBindSampler(4, depthComparison); // Special sampler for depth comparisons.

/* Completed */

public class Texture {
	private int width;
	private int height;
	private List<ByteBuffer> data;
	
	private int 	id;
	private int 	textureType;
	private int 	filter;
	private int 	wrap;
	private float 	mipMapLevel;
	private int 	internalFormat;
	private int 	pixelDataFromat;
	private float 	antisotropyFilterLevel;
	private int 	compareFunc;
	private int 	compareMode;
	private int 	dephtMode;
	private int 	pixelDataType;
	
	public static class Builder {
		private int 				width;
		private int 				height;
		private List<ByteBuffer> 	data;
		
		private int 	textureType;//Texture type 2D, 3D, CubeMap ect.
		private int 	pixelDataType;//Pixel data type int, unsigned int, float...
		private int 	pixelDataFromat;//Pixel format data R, RG. RGB...
		
		private int 	filter;
		private int 	wrap;
		private float 	mipMapLevel;
		private int 	internalFormat;
		private float 	antisotropyFilterLevel;
		private int 	compareFunc;
		private int 	compareMode;
		private int 	dephtMode;
		
		public Builder() {
			this.data 					= new ArrayList<ByteBuffer>();
			this.textureType 			= GL_TEXTURE_2D;
			this.filter 				= GL_LINEAR_MIPMAP_LINEAR;
			this.wrap 					= GL_CLAMP_TO_EDGE;
			this.mipMapLevel 			= 0.0f;
			this.internalFormat 		= GL_RGBA;
			this.pixelDataFromat 		= GL_RGBA;
			this.antisotropyFilterLevel = 0.0f;
			this.compareFunc 			= GL_NONE;
			this.compareMode 			= GL_NONE;
			this.dephtMode 				= GL_NONE;
			this.pixelDataType 			= GL_UNSIGNED_BYTE;
		}
		
		public Builder setWidth					(int width						) {
			this.width = width;
			return this;
		}
		public Builder setHeight				(int height						) {
			this.height = height;
			return this;
		}
		public Builder setData					(ByteBuffer data				) {
			this.data.add(data);
			return this;
		}
		public Builder addData					(List<ByteBuffer> data			) {
			this.data = data;
			return this;
		}
		public Builder setTextureType			(int textureType				) {
			this.textureType = textureType;
			return this;
		}
		public Builder setPixelDataType			(int pixelDataType				) {
			/* 
			 * GL_UNSIGNED_BYTE, GL_BYTE, GL_UNSIGNED_SHORT, GL_SHORT, GL_UNSIGNED_INT, GL_INT, GL_HALF_FLOAT,
			 * GL_FLOAT, GL_UNSIGNED_BYTE_3_3_2, GL_UNSIGNED_BYTE_2_3_3_REV, GL_UNSIGNED_SHORT_5_6_5,
			 * GL_UNSIGNED_SHORT_5_6_5_REV, GL_UNSIGNED_SHORT_4_4_4_4, GL_UNSIGNED_SHORT_4_4_4_4_REV,
			 * GL_UNSIGNED_SHORT_5_5_5_1, GL_UNSIGNED_SHORT_1_5_5_5_REV, GL_UNSIGNED_INT_8_8_8_8,
			 * GL_UNSIGNED_INT_8_8_8_8_REV, GL_UNSIGNED_INT_10_10_10_2, and GL_UNSIGNED_INT_2_10_10_10_REV.
			 */
			this.pixelDataType = pixelDataType;
			return this;
		}
		public Builder setPixelDataFromat		(int pixelDataFromat			) {
			this.pixelDataFromat = pixelDataFromat;
			return this;
		}
		public Builder setFilter				(int filter						) {
			this.filter = filter;
			return this;
		}
		public Builder setWrap					(int wrap						) {
			this.wrap = wrap;
			return this;
		}
		public Builder setMipMapLevel			(float mipMapLevel				) {
			this.mipMapLevel = mipMapLevel;
			return this;
		}
		public Builder setInternalFormat		(int internalFormat				) {
			/*
			 * GL_DEPTH_COMPONENT, GL_DEPTH_STENCIL, GL_RED, GL_RG, GL_RGB, GL_RGBA...
			 */
			this.internalFormat = internalFormat;
			return this;
		}
		public Builder setAntisotropyFilterLevel(float antisotropyFilterLevel	) {
			this.antisotropyFilterLevel = antisotropyFilterLevel;
			return this;
		}
		public Builder setCompareFunc			(int compareFunc				) {
			this.compareFunc = compareFunc;
			return this;
		}
		public Builder setCompareMode			(int compareMode				) {
			this.compareMode = compareMode;
			return this;
		}
		public Builder setDephtMode				(int dephtMode					) {
			this.dephtMode = dephtMode;
			return this;
		}
		
		public Texture build() {
			return new Texture(this);
		}
	}
	
	public Texture(Builder build) {
		this.width 					= build.width;
		this.height 				= build.height;
		this.data 					= build.data;
		this.id 					= glGenTextures();
		this.data 					= new ArrayList<ByteBuffer>();
		this.textureType 			= build.textureType;
		this.filter 				= build.filter;//
		this.wrap 					= build.wrap;//
		this.mipMapLevel 			= build.mipMapLevel;//
		this.internalFormat			= build.internalFormat;//
		this.pixelDataFromat 		= build.pixelDataFromat;//
		this.antisotropyFilterLevel = build.antisotropyFilterLevel;//
		this.compareFunc			= build.compareFunc;//
		this.compareMode 			= build.compareMode;//
		this.dephtMode				= build.dephtMode;//
		this.pixelDataType			= build.pixelDataType;//
		
		setUp();
	}

	public void load	() {
		if(data.size() != 0) {
			bind();
			if(this.textureType == GL_TEXTURE_CUBE_MAP) {
				for(int i = 0; i < data.size(); i++) {
					glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, this.internalFormat, this.width, this.height, 0, this.pixelDataFromat, this.pixelDataType, data.get(i));
				}
			} else {
				glTexImage2D(this.textureType, 0, this.internalFormat, this.width, this.height, 0, this.pixelDataFromat, this.pixelDataType, data.get(0));
			}
	
			for(ByteBuffer data:data) {
				data.clear();
			}
			data.clear();
			
			glGenerateMipmap(this.textureType);
			glTexParameteri(this.textureType, GL_TEXTURE_COMPARE_FUNC	, compareFunc	);
			glTexParameteri(this.textureType, GL_TEXTURE_COMPARE_MODE	, compareMode	);
			glTexParameteri(this.textureType, GL_TEXTURE_MIN_FILTER	, filter			);
			glTexParameteri(this.textureType, GL_TEXTURE_MAG_FILTER	, filter			);
			glTexParameteri(this.textureType, GL_TEXTURE_WRAP_S		, wrap	);
			glTexParameteri(this.textureType, GL_TEXTURE_WRAP_T		, wrap	);
			if(this.textureType == GL_TEXTURE_CUBE_MAP) {
				glTexParameteri(this.textureType, GL_TEXTURE_WRAP_R, wrap);
				//glTexParameteri(this.Type, GL_TEXTURE_WRAP_Q, WrapParameter);
			}
			glTexParameterf(this.textureType, GL_TEXTURE_LOD_BIAS		, mipMapLevel	);
			glTexParameteri(this.textureType, GL_DEPTH_TEXTURE_MODE	, dephtMode		);
			if(GL.getCapabilities().GL_EXT_texture_filter_anisotropic) {
				float Amount = Math.min(this.antisotropyFilterLevel, glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
				glTexParameterf(this.textureType, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, Amount);
			} else {
				System.err.println("texture filter anisotropic not supported!");
			}
			unbind();
		} else {
			setUp();
		}
	}
	public void reload	() {
		bind();
		
		if(data.size() != 0) {
			if(this.textureType == GL_TEXTURE_CUBE_MAP) {
				for(int i = 0; i < data.size(); i++) {
					glTexSubImage2D(this.textureType + i, 0, 0, 0, this.width, this.height, this.pixelDataFromat, this.pixelDataType, data.get(i));
				}
			} else {
				glTexSubImage2D(this.textureType, 0, 0, 0, this.width, this.height, this.pixelDataFromat, this.pixelDataType, data.get(0));
			}
			
			for(ByteBuffer data : data) {
				data.clear();
			}
			data.clear();
		}
		
		unbind();
	}
	
	public void load	(String File) {
		try	{
			BufferedImage 	image 		= ImageIO.read(new File(File));
			ByteBuffer 		buffer 		= ByteBuffer.allocateDirect(image.getHeight() * image.getWidth() * 4);
			boolean 		hasAlpha 	= image.getColorModel().hasAlpha();
			
			height 	= image.getHeight();
			width 	= image.getWidth();
			
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

			/*Parse data from int array*/
			for(int y = 0; y < image.getHeight(); y++) {
				for(int x = 0; x < image.getWidth(); x++) {
					int pixel = pixels[y * image.getWidth() + x];

					buffer.put((byte)((pixel >> 16) & 0xFF));
					buffer.put((byte)((pixel >> 8) & 0xFF));
					buffer.put((byte)((pixel) & 0xFF));
					if(hasAlpha)
						buffer.put((byte)((pixel >> 24) & 0xFF));
					else
						buffer.put((byte)(0xFF));
				}
			}

			buffer.flip();
			this.data.add(buffer);
			load();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Failed to load file: " + File);
		}
	}
	public void reload	(String File) {
		try	{
			BufferedImage 	image 		= ImageIO.read(new File(File));
			ByteBuffer 		buffer 		= ByteBuffer.allocateDirect(image.getHeight() * image.getWidth() * 4);
			boolean 		hasAlpha 	= image.getColorModel().hasAlpha();
			
			height 	= image.getHeight();
			width 	= image.getWidth();
			
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

			/*Parse data from int array*/
			for(int y = 0; y < image.getHeight(); y++) {
				for(int x = 0; x < image.getWidth(); x++) {
					int pixel = pixels[y * image.getWidth() + x];

					buffer.put((byte)((pixel >> 16) & 0xFF));
					buffer.put((byte)((pixel >> 8) & 0xFF));
					buffer.put((byte)((pixel) & 0xFF));
					if(hasAlpha)
						buffer.put((byte)((pixel >> 24) & 0xFF));
					else
						buffer.put((byte)(0xFF));
				}
			}

			buffer.flip();
			this.data.add(buffer);
			reload();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Failed to load file: " + File);
		}
	}
	
	public void setUp	() {
		bind();
		
		glTexImage2D(this.textureType, 0, this.internalFormat, this.width, this.height, 0, this.pixelDataFromat, this.pixelDataType, (ByteBuffer)null);
		
		glGenerateMipmap(this.textureType);
		glTexParameteri(this.textureType, GL_TEXTURE_COMPARE_FUNC	, compareFunc	);
		glTexParameteri(this.textureType, GL_TEXTURE_COMPARE_MODE	, compareMode	);
		glTexParameteri(this.textureType, GL_TEXTURE_MIN_FILTER	, filter			);
		glTexParameteri(this.textureType, GL_TEXTURE_MAG_FILTER	, filter			);
		glTexParameteri(this.textureType, GL_TEXTURE_WRAP_S		, wrap	);
		glTexParameteri(this.textureType, GL_TEXTURE_WRAP_T		, wrap	);
		if(this.textureType == GL_TEXTURE_CUBE_MAP) {
			glTexParameteri(this.textureType, GL_TEXTURE_WRAP_R	, wrap	);
		}
		glTexParameterf(this.textureType, GL_TEXTURE_LOD_BIAS		, mipMapLevel	);
		glTexParameteri(this.textureType, GL_DEPTH_TEXTURE_MODE	, dephtMode		);
		
		if(GL.getCapabilities().GL_EXT_texture_filter_anisotropic) {
			float Amount = Math.min(this.antisotropyFilterLevel, glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
			glTexParameterf(this.textureType, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, Amount);
		} else {
			System.out.println("texture filter anisotropic not supported!");
		}
		
		unbind();
	}
	
	public void bind	() {
		glBindTexture(textureType, id);
	}
	public void unbind	() {
		glBindTexture(textureType, 0);
	}
	
	public void setMipMapLevel		(float MipMapLevel	) {
		this.mipMapLevel = MipMapLevel;
	}
	public void setMipMapFilter		(int Param			) {
		this.filter = Param;
	}
	public void setWrapParameter	(int WrapParameter	) {
		this.wrap 	= WrapParameter;
	}
	public void setAtisotropuFilter	(float Amount		) {
		this.antisotropyFilterLevel = Amount;
	}
	public void setCompareFunction	(int CompareFunc	) {
		this.compareFunc = CompareFunc;
	}
	public void setCompareMode		(int CompareMode	) {
		this.compareMode = CompareMode;
	}
	public void setDephtMode		(int DephtMode		) {
		this.dephtMode = DephtMode;
	}
	
	public void setID	(int ID) {
		this.id = ID;
	}
	public void setType	(int Type) {
		this.textureType = Type;
	}
	
	public int getID	() {
		return id;
	}
	public int getType	() {
		return this.textureType;
	}
	
	public void setWidth	(int Width	) {
		this.width = Width;
	}
	public void setHeight	(int Height	) {
		this.height = Height;
	}
	public void setData		(ByteBuffer Data		) {
		this.data.add(Data);
	}
	public void setData		(List<ByteBuffer> Data	) {
		this.data = Data;
	}
	
	public int getWidth	() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public List<ByteBuffer> getData() {
		return this.data;
	}
	
	public static void activateSlot(int Slot) {
		glActiveTexture(GL_TEXTURE0 + Slot);
	}
	
	public void cleanUp() {
		unbind();
		glDeleteTextures(id);
		for(ByteBuffer data:data) {
			data.clear();
		}
		data.clear();
		data = null;
	}
}
