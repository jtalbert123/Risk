package game;

import java.util.ArrayList;
import java.util.Random;

public class Player {

	ArrayList<RiskCard> cards;
	
	public Player() {
		// TODO Auto-generated constructor stub
	}
	 
	void takeRiskCard(Random r) {
		cards.add(RiskCard.values()[r.nextInt(3)]);
	}
	
	private enum RiskCard {
		HORSEMAN, FOOT_SOLDIER, CANNON
	}
}
