package Components;

import Entities.Entity;

/*
 * Defines RawComponent functions headers.
 * */
public class RawComponentDAO implements RawComponent {
	//Every component will be used in entities. Every component must know in witch entity they are.
	//Then we can access from this component other components from entity component list.
	private Entity Entyti = null;
	
	
	//Defined functions.
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
