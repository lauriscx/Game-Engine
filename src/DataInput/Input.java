package DataInput;

import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.*;

public class Input extends GLFWKeyCallback {

	//Keys list witch are pressed.
	public static boolean[] Keys = new boolean[65535];
	
	//GLFW handler event then buttons pressed.
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		//action can by GLFW_PRESSED or GLFW_RELEASE.
		//If it's not released then is set truer on key.
		//Key is ASSCIC ID of key or symbol.
		Keys[key] = action != GLFW_RELEASE;
	}
}