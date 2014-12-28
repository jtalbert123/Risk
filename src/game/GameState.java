package game;

/**
 * An object to store all of the game's state features, except for pause etc.
 */
public class GameState {

	Player[] players;
	Country[] countries;
	Region[] regions;
	
	int riskCardBonus;

	public GameState() {
		riskCardBonus = 4;
	}

}
