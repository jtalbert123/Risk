package game;

import java.util.HashSet;

public class Country {

	Player owner;
	Region parent;
	int armies;
	HashSet<Country> travelRoutes;
	
	public Country() {
		// TODO Auto-generated constructor stub
	}

}
