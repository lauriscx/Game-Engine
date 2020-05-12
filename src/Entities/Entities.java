package Entities;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class Entities {
	private static HashMap<UUID, Entity> Entities = new HashMap<UUID, Entity>();
	
	static public void Update() {
		for(Entry<UUID, Entity> entity : Entities.entrySet()) {
			entity.getValue().UpdateSystem();
		}
	}
	static public void AddEntity(Entity entity, UUID id) {
		if(Entities.get(id) == null) {
			Entities.put(id, entity);
		}
	}
	static public Entity GetEntity(UUID id) {
		return Entities.get(id);
	}
}
