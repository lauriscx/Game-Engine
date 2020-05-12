package Render;

import static org.lwjgl.opengl.GL30.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import Components.RawComponentDAO;
import DataInput.Input;
import FrameBuffer.FrameBuffer;
import Mesh.update15v.Texture;

import static org.lwjgl.glfw.GLFW.*;

/* Completed */

public class Camera extends RawComponentDAO {
	
	/*Data stuff*/
	private Texture Color;
	private Texture Depth;
	private FrameBuffer Frame;
	private boolean RenderToScreen;
	
	/*Component stuff*/
	private boolean Active 		= false;
	private int 	CameraID 	= -1;
    
    private static final Vector3f VectorX = new Vector3f(1.0f, 0.0f, 0.0f);
    private static final Vector3f VectorY = new Vector3f(0.0f, 1.0f, 0.0f);
    private static final Vector3f VectorZ = new Vector3f(0.0f, 0.0f, 1.0f);
	
	/* Projection stuff */
	private float FOV		= 80.0f;
	private float Near		= 0.01f;
	private float Far		= 100.0f;
	private float Height	= 600.0f;
	private float Width 	= 800.0f;
	
	/* View stuff */
	private Vector3f Position	= new Vector3f(0.0f, 1.0f, 0.0f);
	private Vector3f Rotation	= new Vector3f(0.0f, 0.0f, 0.0f);
	private Vector3f Zoom		= new Vector3f(1.0f, 1.0f, 1.0f);
	
	private Vector3f PositionS	= new Vector3f(1.0f, 1.0f, 1.0f);
	private Vector3f RotationS	= new Vector3f(1.0f, 1.0f, 1.0f);
	private Vector3f ZoomS		= new Vector3f(1.0f, 1.0f, 1.0f);
	
	private Matrix4f Perspective 	= new Matrix4f();
	private Matrix4f OrthoGraphic 	= new Matrix4f();
	private Matrix4f View 			= new Matrix4f();
	
	private float MovementSpeed = 2f;
	
	public Camera() {
		/* Projection stuff */
		Height 		= Display.getHeight();
		Width 		= Display.getWidth();
		CameraID 	= CamerasList.AddCamera(this);
		
		Color		= new Texture.Builder()
						.setHeight((int)Height)
						.setWidth((int)Width).build();
		
		Depth		= new Texture.Builder()
						.setHeight((int)Height)
						.setWidth((int)Width)
						.setTextureType(GL_TEXTURE_2D)
						.setPixelDataType(GL_FLOAT)
						.setInternalFormat(GL_DEPTH_COMPONENT32).build();
		
		Frame		= new FrameBuffer((int)Width, (int)Height);
		Frame.Attachment(Color, GL_COLOR_ATTACHMENT0);
		Frame.Attachment(Depth, GL_DEPTH_COMPONENT32);
		RenderToScreen = true;
	}

	public void RenderToScreen		(boolean screen) {
		RenderToScreen = screen;
	}
	public boolean RenderToScreen	() {
		return RenderToScreen;
	}
	public Texture ColorTexture		() {
		return Color;
	}
	public Texture DephtTexture		() {
		return Depth;
	}
	public FrameBuffer FrameBuffer	() {
		return Frame;
	}
		
	@Override
	public void Enable		() {
		this.Active = true;
	}
	@Override
	public void Disable		() {
		this.Active = false;
	}
	public boolean Active	() {
		return this.Active;
	}
	
	public int 	GetID		() {
		return this.CameraID;
	}
	
	public void SetFOV			(float FOV		) {
		this.FOV = FOV;
	}
	public void SetNear			(float Near		) {
		this.Near = Near;
	}
	public void SetFar			(float Far		) {
		this.Far = Far;
	}
	public void SetHeight		(float Height	) {
		this.Height = Height;
	}
	public void SetWidth		(float Width	) {
		this.Width = Width;
	}
	
	public float GetFOV			() {
		return FOV;
	}
	public float GetNear		() {
		return Near;
	}
	public float GetFar			() {
		return Far;
	}
	public float GetHeight		() {
		return Height;
	}
	public float GetWidth		() {
		return Width;
	}
	public float GetAspectRatio	() {
		return Width / Height;
	}
	
	public void SetPosition			(float x, float y, float z) {
		this.Position.x = x;
		this.Position.y = y;
		this.Position.z = z;
	}
	public void IncresePosition		(float x, float y, float z) {
		this.Position.x += x;
		this.Position.y += y;
		this.Position.z += z;
	}
	public void SetRotation			(float x, float y, float z) {
		this.Rotation.x = x;
		this.Rotation.y = y;
		this.Rotation.z = z;
	}
	public void IncreseRotation		(float x, float y, float z) {
		this.Rotation.x += x;
		this.Rotation.y += y;
		this.Rotation.z += z;
	}
	public void SetZoom				(float x, float y, float z) {
		this.Zoom.x = x;
		this.Zoom.y = y;
		this.Zoom.z = z;
	}
	public void IncreseZoom			(float x, float y, float z) {
		this.Zoom.x += x;
		this.Zoom.y += y;
		this.Zoom.z += z;
	}
	
	public void SetRotationSpeed	(float x, float y, float z) {
		this.RotationS.x += x;
		this.RotationS.y += y;
		this.RotationS.z += z;
	}
	public void SetZoomSpeed		(float x, float y, float z) {
		this.ZoomS.x = x;
		this.ZoomS.y = y;
		this.ZoomS.z = z;
	}
	public void SetMovementSpeed(float speed) {
		MovementSpeed = speed;
	}
	
	public float GetMovementSpeed() {
		return MovementSpeed;
	}
	public Vector3f GetPosition		() {
		return Position;
	}
	public Vector3f GetRotation		() {
		return Rotation;
	}
	public Vector3f GetZoom			() {
		return Zoom;
	}
	
	public Matrix4f GetPerspective 	() {
		Width 	= Display.getWidth();
		Height 	= Display.getHeight();
		float AspectRatio 	= Width / Height;
		float FrustumLenght = (Far - Near);
		float YScale 		= (float) ((1.0f / Math.tan(Math.toRadians(FOV / 2.0f))));
		float XScale 		= YScale / AspectRatio;
		
		Perspective.m00(XScale);
		Perspective.m11(YScale);
		Perspective.m22(-((Far + Near) / FrustumLenght));
		Perspective.m23(-1.0f);
		Perspective.m32(-((2.0f * Near * Far) / FrustumLenght));
		Perspective.m33(0);
		
		return Perspective;
	}
	public Matrix4f GetOrthoGraphic () {
		Width 	= Display.getWidth();
		Height 	= Display.getHeight();
		float FrustumLenght = (Far - Near);
		
		OrthoGraphic.m00(2.0f / Width);
		OrthoGraphic.m11(2.0f / Height);
		OrthoGraphic.m22(-2.0f / FrustumLenght);
		OrthoGraphic.m33(1.0f);
		
		return OrthoGraphic;
	}
	public Matrix4f GetView			() {
		Controll();
		View.identity();
		View.rotate((float) Math.toRadians(Rotation.x), VectorX);
		View.rotate((float) Math.toRadians(Rotation.y), VectorY);
		View.rotate((float) Math.toRadians(Rotation.z), VectorZ);
		View.scale(Zoom);
		View.translate(new Vector3f(-Position.x, -Position.y, -Position.z));
		
		return View;
	}
	public Matrix4f Get3PersonView	() {
		View.identity();
		float Horizontal = (float) (Zoom.z * Math.cos(Math.toDegrees(Rotation.x)));
		float Vertical	 = (float) (Zoom.z * Math.sin(Math.toDegrees(Rotation.x)));
		
		float Offsetx = (float) (Position.x * Horizontal + Math.sin(Math.toDegrees(Rotation.y)));
		float Offsetz = (float) (Position.y * Vertical+ Math.cos(Math.toDegrees(Rotation.y)));
		Position.x -= Offsetx;
		Position.y += Vertical;
		Position.z -= Offsetz;
		View.translate(new Vector3f(-Position.x, -Position.y, -Position.z));
		
		return View;
	}
	
	public Vector3f GetPositionSpeed() {
		return PositionS;
	}
	public Vector3f GetRotationSpeed() {
		return RotationS;
	}
	public Vector3f GetZoomSpeed	() {
		return ZoomS;
	}
	
	public void Move(Vector3f Move) {
		float x = (float) (Move.z * Math.sin(Math.toRadians(this.Rotation.y)));
		float z = (float) (Move.z * Math.cos(Math.toRadians(this.Rotation.y)));
		//float sidez = (float) (Move.x * Math.sin(Math.toRadians(this.Rotation.y + 90)));
		//float sidex = (float) (Move.x * Math.cos(Math.toRadians(this.Rotation.y + 90)));
		this.Position.x += -x + Move.x;
		this.Position.z +=  z;
		this.Position.y +=  Move.y;
	}
	
	private boolean Controll = false;
	@SuppressWarnings("unused")
	private float movement = 0.2f;
	private float x = 0, y = 0;
	private void Controll() {
		if(Display.GetMouseKey(GLFW_MOUSE_BUTTON_2)) {
			if(Controll) {
				Controll = false;
				//Mouse.setGrabbed(false);
				Display.SetMouseHide(false);
			}
		}
		if(Display.GetMouseKey(GLFW_MOUSE_BUTTON_1)) {
			if(!Controll) {
				Controll = true;
				Display.SetMouseHide(true);
			}
		}
		if(Input.Keys[GLFW_KEY_W]) {
			//camera.GetPosition().translate(0.00f, 0.0f, -0.05f * movement);
			Move(new Vector3f(0, 0, -0.05f * MovementSpeed));
		}
		if(Input.Keys[GLFW_KEY_S]) {
			Move(new Vector3f(0, 0, 0.05f * MovementSpeed));
			//camera.GetPosition().translate(0.00f, 0.0f, 0.05f * movement);
		}  
		if(Input.Keys[GLFW_KEY_A]) {
			//Vector3f Move = new Vector3f(-0.05f * movement, 0, 0);
			//Vector3f Left = Rotation.cross(VectorY).normalize();

			//camera.GetPosition().translate(-0.05f * movement, 0.0f, 0.0f);
			//Move(Left.mul(movement));
		}  
		if(Input.Keys[GLFW_KEY_D]) {
			//Vector3f Move = new Vector3f(-0.05f * movement, 0, 0);
			//Vector3f Right = Rotation.cross(VectorY).normalize();
			//camera.GetPosition().translate(0.05f * movement, 0.0f, 0.0f);
			//Move(Right.mul(movement));
		}  
		if(Input.Keys[GLFW_KEY_LEFT_SHIFT]) {
			//camera.GetPosition().translate(0.00f, -0.05f * movement, 0.0f);
			Move(new Vector3f(0, -0.05f * MovementSpeed, 0));
		}  
		if(Input.Keys[GLFW_KEY_SPACE]) {
			//camera.GetPosition().translate(0.00f, 0.05f * movement, 0.0f);
			Move(new Vector3f(0, 0.05f * MovementSpeed, 0));
		}
		if(Controll) {
			float Sensetive = 0.1f;
			float hor = Display.getMouseX();
			float ver = Display.getMouseY();

			x = ver * Sensetive * (-1);
			y = hor * Sensetive;
			IncreseRotation(-x, y, 0);
			
			Display.SetMousePositionCenter();
		} else {
			
		}
	}
	
	public void CleanUp() {
		Color.cleanUp();
		Depth.cleanUp();
		Frame.CleanUp();
	}
}