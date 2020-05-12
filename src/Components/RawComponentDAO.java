package Components;

import Entities.Entity;

public class RawComponentDAO implements RawComponent {
	private Entity Entyti = null;
	
	@Override
	public void SetParent(Entity Entyti) {
		this.Entyti = Entyti;
	}
	@Override
	public Entity GetParent() {
		return Entyti;
	}
	
	@Override
	public void Disable() {}
	@Override
	public void Enable() {}
	@Override
	public void Update() {}
	@Override
	public void Delete() {}
}
