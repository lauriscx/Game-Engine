package TestComponents;

import Components.RawComponentDAO;

public class PositionComponent extends RawComponentDAO {
	public float x, y;
	@Override
	public void Update() {
		if(GetParent().getComponent(NameComponent.class).name.equals("Tom")) {
			x = 1000;
			y = 1000;

		}
	}
}