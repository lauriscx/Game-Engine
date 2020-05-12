package Render;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

import Components.GraphicComponent;
import Components.TransformComponent;
import Mesh.update15v.Texture;
import Shader.Shader;
import Shader.ShadersList;


public class Render {
	private static List<RenderParams> 	rendersParams;

	//Processing data
	private static GraphicComponent currentComponent;
	private static Shader			currentShader;
	private static int 				currentParams;
	
	static {
		rendersParams 	= new ArrayList<RenderParams>();
		currentParams  	= -1;
	}
	public static int 				AddRenderParams		(RenderParams renderParams) {
		rendersParams.add(renderParams);
		return rendersParams.size() - 1;
	}
	public static RenderParams 		GetRenderParams		(int ID) {
		return rendersParams.get(ID);
	}
	public static List<RenderParams> GetRendersParams	() {
		return rendersParams;
	}
	public static void Update				() {
		boolean NotDefaultFrame = false;
		PrepareRender();

		//Render for each camera
		for(Camera camera : CamerasList.GetCameras()) {//CameraNumber * ItemsNumber = NumberOfCallsToDraw
			if(NotDefaultFrame) {
				camera.FrameBuffer().Unbind();
			}
			if(!camera.RenderToScreen()) {
				camera.FrameBuffer().Bind();
				NotDefaultFrame = true;
			}
			//If camera is not active then don't render to it
			if(!camera.Active()) {
				continue;
			}
			for(Shader shader : ShadersList.GetShaders()) {
				shader.update();
				shader.sendUniform("Projection"	, camera.GetPerspective()							);
				shader.sendUniform("Camera"		, camera.GetView()									);
			}
			
			//Items which render to camera
			for(int i = 0; i < RenderQueue.Components().size(); i++) {
				//Ready data for fast access
				currentComponent 	= RenderQueue.Components().get(i);
				currentShader 		= ShadersList.GetShader(currentComponent.GetShader());
				//If items is disable then skip them
				if(!currentComponent.GetState()) {
					continue;
				}
				
				PrepareDraw();
				Draw();
				End();

			}
		}
	}
	
	
	private static void PrepareRender() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0f, 0f, 0f, 1.0f);
	}
	private static void PrepareDraw() {
		//Set params
		if(currentParams != currentComponent.GetRender()) {
			currentParams = currentComponent.GetRender();
			GetRenderParams(currentParams).setParams();
		}
	
		//activate shader and send component data to gpu
		currentShader.start();
		currentShader.sendUniform("material.specular",  currentComponent.Material().Specular());
		currentShader.sendUniform("material.color",  currentComponent.Material().Color());
		
		if(currentComponent.GetParent().hasComponent(TransformComponent.class)) {
			currentShader.sendUniform(
						"ModelTransformation", currentComponent.GetParent().getComponent(TransformComponent.class).GetTransformation()
					);
		}

		//activate mesh buffer in gpu
		currentComponent.Mesh().vao().Bind();
		
		//optimize textures activation calculate all shaders how many texture handle and y max number activate texture slots.
		for(int t = 0; t < currentComponent.Material().GetTextures().size(); t++) {
			Texture.activateSlot(currentShader.getTextureSlot(currentComponent.Material().GetNames().get(t)));//Activate texture slot by unifrom name id
			currentComponent.Material().GetTextures().get(t).bind();
		}
	}
	private static void Draw() {
		if(rendersParams.get(currentComponent.GetRender()).drawType() == 0) {
			glDrawElements(rendersParams.get(currentComponent.GetRender()).renderMode(), currentComponent.Mesh().getVertexCount(), GL_UNSIGNED_INT, 0);
		} else if(rendersParams.get(currentComponent.GetRender()).drawType() == 1) {
			glDrawArrays(rendersParams.get(currentComponent.GetRender()).renderMode(), 0, currentComponent.Mesh().getVertexCount());
		}
	}
	private static void End() {
		currentComponent.Mesh().vao().Unbind();
		currentShader.stop();
	}
	
	public static void CleanUp				() {
		rendersParams.clear();
	}
}