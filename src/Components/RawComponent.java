package Components;

import Entities.Entity;

/*interface is template for RawComponent Data Access Object witch show witch functionality it will define*/

public interface RawComponent {
	public void SetParent(Entity entity);
	public Entity GetParent();
	public void Disable();
	public void Enable();
	public void Update();
	public void Delete();
}