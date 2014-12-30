package game;

import java.util.HashSet;

public class Country {

	Player owner;
	Region parent;
	int armies;
	String name;
	HashSet<Country> travelRoutes;
	
	public Country(String name) {
		this.name = name;
		owner = null;
		parent = null;
		armies = 0;
		travelRoutes = new HashSet<>();
	}
	
	public void setParent(Region parent) {
		this.parent = parent;
	}
	
	public Region getParent() {
		return parent;
	}
	
	public String getName() {
		return name;
	}
	
	public void addNeighbor(Country c) {
		travelRoutes.add(c);
	}
	
	public HashSet<Country> getNeighbors() {
		return travelRoutes;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		
		return this.name.equals(((Country)obj).name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public String toString() {
		return name;
	}
}
