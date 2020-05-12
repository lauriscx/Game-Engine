package EngineTest;

import java.util.Date;
import java.util.Random;

import javax.vecmath.Vector3f;

import Components.*;
import Entities.*;
import GameStuff.AssetsManager;
import GameStuff.DeltaTime;
import GameStuff.Physics;
import Mesh.Mesh;
import Mesh.update15v.Texture;
import Render.*;
import Shader.*;

public class Main {
	static DirectionLight sun = new DirectionLight();
	static double second = 0;
	
	public static void main(String[] args) {
		//Calls then java application closes
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    @Override
			public void run() {
		        //System.out.println("Engine closes, cleaning up elements");
		        //Shaders.CleanUp();
		        //Render.CleanUp();
		        //CamerasQueue.CleanUp();
				//Display.CleanUp();
				//Audio.CleanUp();
		    }
		}));
		
		
		
		Display.CreateDisplay(600, 800, 1, "Cube dance", 6);
		LoadAssets();
		

		
		Camera camera = new Camera();
		camera.Enable();
		
		Shader shader = new Shader();
		shader.addVertexFile	("src/Render/vertexShader.glsl");
		shader.addFragmentFile	("res/Shaders/Fragment.glsl");
		shader.compile();

		RenderParams render = new RenderParams();
		//render.renderModePoints();
		
		LoadTestGame(shader.getID(), render.GetID(), camera.GetID());
		
		SetUpLights();
		
		int fps = 0;
		while(!Display.Close()) {
			second += DeltaTime.getDelta();
			
			sun.Direction().rotateAxis((float) Math.toRadians(0.1) , 0, 0, 1);
			Entities.Update();
			Render.Update();
			if(second > 1) {
				System.out.println(Display.GetFps());
				fps = 0;
				second = 0;
			}
			fps++;
			Display.Update();
			DeltaTime.Update();
		}
	}
	
	
	static void LoadAssets() {
		/* Textures */
		Texture texture = new Texture.Builder().build();
		texture.load("res/textures/laminated_floor.png");
		AssetsManager.addTexture("res/textures/laminated_floor.png", texture);
		
		texture = new Texture.Builder().build();
		texture.load("res/textures/laminated_floor_normal.png");
		AssetsManager.addTexture("res/textures/laminated_floor_normal.png", texture);
		
		/*Obj files*/
		Mesh mesh = new Mesh();
		mesh.Load("res/GameTest/Cube.obj");
		AssetsManager.addMesh("res/GameTest/Cube.obj", mesh);
		
	}
	
	static void LoadTestGame(int shaderID, int renderID, int cameraID) {
		/*Player*/
		
		TransformComponent playerTransform = new TransformComponent();
		playerTransform.SetPosition(0, 3, 0);
		playerTransform.SetScale(1.0f,  1.0f,  1.0f);
		playerTransform.SetRotation(0, 0, 0);
		
		GraphicComponent playerGraphic = new GraphicComponent();
		playerGraphic.SetShader(shaderID);
		playerGraphic.SetRender(renderID);
		playerGraphic.setMesh(AssetsManager.getMesh("res/GameTest/Cube.obj"));
		playerGraphic.Material().Texture(AssetsManager.getTexture("res/textures/laminated_floor.png"), "DiffuseMap");
		playerGraphic.Material().Texture(AssetsManager.getTexture("res/textures/laminated_floor_normal.png"), "NormalMap");
		playerGraphic.Material().Color(0.5f, 0.5f, 0.5f, 1);
		playerGraphic.Material().Specular(1, 16);
		
		Player player = new Player(cameraID);
		player.addComponent(playerTransform);
		player.addComponent(playerGraphic);
		
		Physics.addCube(new Vector3f(1, 1, 1), player);
		
		Random rand = new Random() ;
		Date date = new Date();
		rand.setSeed(date.getTime());
		/* create environment */
		
		/*Start point*/
		
		TransformComponent startTransform = new TransformComponent();
		startTransform.SetPosition(0, 0, 0);
		startTransform.SetScale(10.0f,  1.0f,  1.0f);
		startTransform.SetRotation(0, 0, 0);
		
		GraphicComponent startGraphic = new GraphicComponent();
		startGraphic.SetShader(shaderID);
		startGraphic.SetRender(renderID);
		startGraphic.setMesh(AssetsManager.getMesh("res/GameTest/Cube.obj"));	
		startGraphic.Material().Texture(AssetsManager.getTexture("res/textures/laminated_floor.png"), "DiffuseMap");
		startGraphic.Material().Texture(AssetsManager.getTexture("res/textures/laminated_floor_normal.png"), "NormalMap");
		startGraphic.Material().Color(0.5f, 0.5f, 0.5f, 1);
		startGraphic.Material().Specular(1, 16);
		
		
		Terrain startGround = new Terrain();
		//player2.addComponent(new SoundComponent());
		//player2.getComponent(SoundComponent.class).AddSound("Components/TestSound.wav");
		startGround.addComponent(startGraphic);
		startGround.addComponent(startTransform);
		
		Physics.addCube(new Vector3f(10, 1, 1), startGround);
		
		/*Path*/
		for(int i = 2; i < 50; i++) {
			
			TransformComponent transform = new TransformComponent();
			transform.SetPosition(rand.nextInt(10) , rand.nextInt(3) - 1, i * 2);
			transform.SetScale(1.0f,  1.0f,  1.0f);
			transform.SetRotation(0, 0, 0);
			
			GraphicComponent graphic = new GraphicComponent();
			graphic.SetShader(shaderID);
			graphic.SetRender(renderID);
			graphic.Mesh().Load("res/GameTest/Cube.obj");		
			graphic.Material().Texture(AssetsManager.getTexture("res/textures/laminated_floor.png"), "DiffuseMap");
			graphic.Material().Texture(AssetsManager.getTexture("res/textures/laminated_floor_normal.png"), "NormalMap");
			graphic.Material().Color(0.5f, 0.5f, 0.5f, 1);
			graphic.Material().Specular(1, 16);
			
			
			Terrain planeGround = new Terrain();
			//player2.addComponent(new SoundComponent());
			//player2.getComponent(SoundComponent.class).AddSound("Components/TestSound.wav");
			planeGround.addComponent(graphic);
			planeGround.addComponent(transform);
			
			Physics.addCube(new Vector3f(1, 1, 1), planeGround);
		}
	}
	static void SetUpLights() {
		sun.Direction(1, 1, 1);
		sun.color(0.4f, 0.3f, 0.1f);
		sun.intensivity(2);
		sun.activate();
		
		int Y = 2;
		
		PointLight OrangeLight = new PointLight();
		OrangeLight.color(0.7f, 0.4f, 0.2f);
		OrangeLight.Position(0.0f, Y, 0.0f);
		OrangeLight.attenuationConstant(0.5f);
		OrangeLight.attenuationLinear(0.1f);
		OrangeLight.attenuationQuadratic(0.1f);
		OrangeLight.intensivity(1);
		OrangeLight.Range(0.01f);
		OrangeLight.activate();
	}
}