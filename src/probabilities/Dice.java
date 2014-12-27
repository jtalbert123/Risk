package probabilities;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import sun.org.mozilla.javascript.internal.ObjArray;

public class Dice {

	private Object[][][][] outcomes = new Object[3][2][][];
	
	public Dice() {
		for (int aDice = 0; aDice < 3; aDice++) {
			for (int dDice = 0; dDice < 2; dDice++) {
				outcomes[aDice][dDice] = makeSet(aDice, dDice);
			}
		}
	}
	
	public static Object[][] makeSet(int attackerDice, int defenderDice) {
		int length = attackerDice + defenderDice;
		Object[][] battles;
		if (length == 2) {
			battles = new Battle[6][6];
		} else if (length == 3) {
			battles = new Battle[6][6][6];
		} else if (length == 4) {
			battles = new Battle[6][6][6][6];
		} else if (length == 5) {
			battles = new Battle[6][6][6][6][6];
		} else {
			return null;
		}
		return battles;
	}
	
	private class Battle {

		Integer[] attacker;
		Integer[] defender;
		
		public Battle(Integer[] attacker, Integer[] defender) {
			this.attacker = attacker;
			this.defender = defender;
			
			Comparator<Integer> descending = new Comparator<Integer>() {
				
				@Override
				public int compare(Integer o1, Integer o2) {
					return o2 - o1;
				}
			};
			
			Collections.sort(Arrays.asList(this.attacker), descending);
			Collections.sort(Arrays.asList(this.defender), descending);
		}
		
		public int getAttackerLosses() {
			int losses = 0;
			for (int i = 0; i < attacker.length && i < defender.length; i++) {
				if (attacker[i] <= defender[i]) {
					losses++;
				}
			}
			return losses;
		}
		
		public int getDefenderLosses() {
			int losses = 0;
			for (int i = 0; i < attacker.length && i < defender.length; i++) {
				if (attacker[i] > defender[i]) {
					losses++;
				}
			}
			return losses;
		}
	}
}
