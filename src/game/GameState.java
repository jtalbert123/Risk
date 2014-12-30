package game;

import java.util.HashSet;

/**
 * An object to store all of the game's state features, except for pause etc.
 */
public class GameState {

	Player[] players;
	HashSet<Country> countries;
	HashSet<Region> regions;
	
	int currentPlayer;
	
	int riskCardBonus;

	public GameState() {
		players = null;
		countries = new HashSet<>();
		regions = new HashSet<>();
		riskCardBonus = 4;
	}

}
