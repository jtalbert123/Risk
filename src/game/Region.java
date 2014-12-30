package game;

import java.util.HashSet;

public class Region {

	int bonus;
	HashSet<Country> countries;
	String name;
	public Region(String name) {
		this.name = name;
		countries = new HashSet<>();
		bonus = 0;
	}
	
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	
	public void addCountry(Country c) {
		countries.add(c);
	}
	
	public String getName() {
		return name;
	}
	
	public int getBonus() {
		return bonus;
	}
	
	public HashSet<Country> getCountries() {
		return countries;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		return name.equals(((Region)obj).name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
