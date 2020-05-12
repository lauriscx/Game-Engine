package Render;

import java.util.ArrayList;
import java.util.List;

import Components.GraphicComponent;

public class RenderQueue {
	private static List<GraphicComponent> Components;
	private static boolean Sorted;
	
	static {
		Components = new ArrayList<GraphicComponent>();
		Sorted = false;
	}
	
	static public void Add		(GraphicComponent component) {
		Components.add(component);
		Sorted = false;
	}
	static public void Remove	(GraphicComponent component) {
		Components.remove(component);
		Sorted = false;
	}
	
	static public List<GraphicComponent> Components() {
		if(!Sorted) {
			//Radix Sort by shader, Textures, Mesh
			Sorted = true;
		}
		return Components;
	}	
}