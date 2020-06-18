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
	//Entity all components map. It's store components for updating and access data for manipulation.
	@SuppressWarnings("rawtypes")
	protected HashMap<Class, ? extends RawComponent> components = new HashMap<>();
	
	//The entity is created is added to entity hash map for updating and tracking.
	public Entity() {
		Entities.AddEntity(this, UUID.randomUUID());
	}
	
	//Update entity and all components.
	@SuppressWarnings("rawtypes")
	public 	void UpdateSystem	() {
		//Update entity.
		Update();
		//Update components.
		for (HashMap.Entry<Class, ? extends RawComponent> componentEntities : components.entrySet()) {
			componentEntities.getValue().Update();
		}
	}
	public 	void Update			() {}
	
	//Disable components.
	@SuppressWarnings("rawtypes")
	public 	void Disable		() {
		for (HashMap.Entry<Class, ? extends RawComponent> componentEntities : components.entrySet()) {
			componentEntities.getValue().Disable();
		}
	}
	
	//Enable components.
	@SuppressWarnings("rawtypes")
	public 	void Enable			() {
		for (HashMap.Entry<Class, ? extends RawComponent> componentEntities : components.entrySet()) {
			componentEntities.getValue().Enable();
		}
	}
	
	//Add component to hash map.
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public 	<T extends RawComponent> void addComponent(T component		) {
		//synchronize is used if it is used in few treads. If it's not synchronized data can by corrupted.
		synchronized(components) {
			//Check if exits this class in this hash map already.
			if(!components.containsKey(component.getClass())){
				component.SetParent(this);//Set component parent this entity.
				//Add to hash map.
				((HashMap<Class, T>) components).put(component.getClass(), component);
			}
		}
	}
	
	//Get component by class.
	@SuppressWarnings("unchecked")
	public 	<T> T getComponent		( Class<T> component 				) {
		return (T) components.get(component);
	}
	
	//Get components list with same class.(not working now because we can't add same class)
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
	
	//Check is hash map contains object.
	public 	<T> boolean hasComponent( Class<T> component				) {
		if(components.get(component) == null)
			return false;
		return true;
	}
}