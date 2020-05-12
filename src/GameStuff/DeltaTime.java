package GameStuff;

public class DeltaTime {
	private static long time;
	private static double delta;
	
	static {
		time = System.currentTimeMillis();
	}
	
	static public void Update() {
		delta = ((System.currentTimeMillis() - time) / 1000.0d);
		time = System.currentTimeMillis();
	}
	
	static public double getDelta() {
		return delta;
	}	
}