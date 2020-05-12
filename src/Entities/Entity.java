package Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;

import Components.RawComponent;

// ? for interface
// T for implemented class from interface


public abstract class Entity {
	@SuppressWarnings("rawtypes")
	protected HashMap<Class, ? extends RawComponent> components = new HashMap<>();
	
	public Entity() {
		Entities.AddEntity(this, UUID.randomUUID());
	}
	
	@SuppressWarnings("rawtypes")
	public 	void UpdateSystem	() {
		Update();
		for (HashMap.Entry<Class, ? extends RawComponent> componentEntities : components.entrySet()) {
			componentEntities.getValue().Update();
		}
	}
	public 	void Update			() {}
	@SuppressWarnings("rawtypes")
	public 	void Disable		() {
		for (HashMap.Entry<Class, ? extends RawComponent> componentEntities : components.entrySet()) {
			componentEntities.getValue().Disable();
		}
	}
	@SuppressWarnings("rawtypes")
	public 	void Enable			() {
		for (HashMap.Entry<Class, ? extends RawComponent> componentEntities : components.entrySet()) {
			componentEntities.getValue().Enable();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public 	<T extends RawComponent> void addComponent(T component		) {
		synchronized(components) {
			if(!components.containsKey(component.getClass())){
				component.SetParent(this);
				((HashMap<Class, T>) components).put(component.getClass(), component);
			}
		}
	}
	@SuppressWarnings("unchecked")
	public 	<T> T getComponent		( Class<T> component 				) {
		return (T) components.get(component);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public 	<T> List<T> getComponents( Class<T> component 				) {
		List<T> componentList = new ArrayList<T>();
		for(Entry<Class, ? extends RawComponent> _component : components.entrySet()) {
			if(_component.getKey().equals(component) ) {
				componentList.add((T) _component.getValue());
			}
		}
		return componentList;
	}
	public 	<T> boolean hasComponent( Class<T> component				) {
		if(components.get(component) == null)
			return false;
		return true;
	}
}