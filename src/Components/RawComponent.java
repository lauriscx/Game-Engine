package Components;

import Entities.Entity;

public interface RawComponent {
	public void SetParent(Entity entity);
	public Entity GetParent();
	public void Disable();
	public void Enable();
	public void Update();
	public void Delete();
}