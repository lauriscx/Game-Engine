package Entities;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class Entities {
	//Entities map.
	private static HashMap<UUID, Entity> Entities = new HashMap<UUID, Entity>();
	
	//Update all entities once per frame.
	static public void Update() {
		for(Entry<UUID, Entity> entity : Entities.entrySet()) {
			entity.getValue().UpdateSystem();
		}
	}
	//Add entity to map for tracking.
	static public void AddEntity(Entity entity, UUID id) {
		if(Entities.get(id) == null) {
			Entities.put(id, entity);
		}
	}
	//Get entity by it UUID
	static public Entity GetEntity(UUID id) {
		return Entities.get(id);
	}
}
