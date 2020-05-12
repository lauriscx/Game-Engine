package Render;

import java.util.ArrayList;
import java.util.List;

public class CamerasList {
	private static List<Camera> Cameras;
	static {
		Cameras = new ArrayList<Camera>();
	}
	
	public static int 			AddCamera	(Camera camera	) {
		Cameras.add(camera);
		return Cameras.size() - 1;
	}
	public static Camera 		GetCamera	(int ID			) {
		return Cameras.get(ID);
	}
	public static List<Camera> 	GetCameras	(				) {
		return Cameras;
	}
	public static void 			CleanUp		(				) {
		for(Camera camera : Cameras) {
			camera.CleanUp();
		}
		Cameras.clear();
	}
}