package Render;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import DataInput.Input;

public class Display {

	// The window handle
	private static long window;
	
	private static double 			lastFrameTime;
	private static double 			delta;
	private static GLFWKeyCallback 	keyCallback = new Input();
	private static int 				Width 		= 0;
	private static int 				Height 		= 0;
	
	private static MemoryStack 	stack	= stackPush();
	private static IntBuffer 	pWidth 	= stack.mallocInt(1); // int*
	private static IntBuffer 	pHeight = stack.mallocInt(1); // int*
	
	private static DoubleBuffer MouseX 	= BufferUtils.createDoubleBuffer(1);
	private static DoubleBuffer MouseY 	= BufferUtils.createDoubleBuffer(1);
	
	private static GLFWVidMode 	vidmode;
	private static double 		currentTime;

	/*Window stuff*/
	public static void CreateDisplay(int width, int height, int vsyn, String title, int multisample) {		
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();
		
		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		//glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
		
		glfwWindowHint(GLFW_STENCIL_BITS, multisample);
		glfwWindowHint(GLFW_SAMPLES, multisample);
		
		// Create the window
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");
		
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});
		
		try {
			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);
			Width = pWidth.get(0);
			Height = pHeight.get(0);
			// Get the resolution of the primary monitor
			vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2,	(vidmode.height() - pHeight.get(0)) / 2);
		} catch (Exception e) {}
		
		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(vsyn);

		// Make the window visible
		glfwShowWindow(window);
		
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
		
		GL30.glEnable(GL30.GL_FRAMEBUFFER_SRGB);
		GL11.glEnable(GL13.GL_MULTISAMPLE);
		GL11.glViewport(0, 0, width, height);
		
		lastFrameTime = GLFW.glfwGetTime();
		
		System.out.println(Version.getVersion());
	}
	
	public static void Update() {
		try {			
			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);
			
			Width = pWidth.get(0);
			Height = pHeight.get(0);
			
			GL11.glViewport(0, 0, pWidth.get(0), pHeight.get(0));
		} catch (Exception e) {}
		
		glfwSwapBuffers(window); // swap the color buffers

		// Poll for window events. The key callback above will only be
		// invoked during this call.
		glfwSetKeyCallback(window, keyCallback);
		glfwPollEvents();
		glfwGetCursorPos(window, MouseX, MouseY);
		
		//Keep track of time for FPS calculations
		currentTime = GLFW.glfwGetTime();
		delta = (currentTime - lastFrameTime);
		lastFrameTime = currentTime;
	}
	
	public static int 	GetFps				() {
		return (int) (1/delta);
	}
	public static double GetFrameTimeSeconds() {
		return delta;
	}
	
	public static int getWidth	() {
		return pWidth.get(0);
	}
	public static int getHeight	() {
		return pHeight.get(0);
	}
	
	public static boolean Close	() {
		return glfwWindowShouldClose(window);
	}
	
	/*Mouse stuff*/
	public static int getMouseX	() {
		return (int)(MouseX.get(0) - (Width / 2));
	}
	public static int getMouseY	() {
		return (int)(MouseY.get(0) - (Height / 2));
	}
	
	public static void SetMousePosition			(int x, int y) {
		glfwSetCursorPos(window, x, y);
	}
	public static void SetMousePositionCenter	() {
		glfwSetCursorPos(window, Width/2, Height/2);
	}
	
	public static boolean 	GetMouseKey	(int key		) {
		return glfwGetMouseButton(window, key) == GLFW_PRESS;
	}
	public static void 		SetMouseHide(boolean mouse	) {
		if(mouse) {
			glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
		} else {
			glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		}
	}
	
	
	public static void CleanUp() {
		keyCallback.close();
		stack.close();
		pWidth.clear();
		pHeight.clear();
		MouseX.clear();
		MouseY.clear();
		vidmode.clear();
		
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		
		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
		
	}
}
