package probabilities;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Dice {

	public static void main(String[] args) {
		Dice d = new Dice();
		System.out.println(d.getProbability(3, 1));
		System.out.println(d.getProbability(1, 2));
	}

	private Object[][][][] outcomes = new Object[3][2][][];

	public Dice() {
		for (int aDice = 1; aDice <= 3; aDice++) {
			for (int dDice = 1; dDice <= 2; dDice++) {
				outcomes[aDice - 1][dDice - 1] = makeSet(aDice, dDice);

				Object[][] set = outcomes[aDice - 1][dDice - 1];

				if (aDice == 1) {
					if (dDice == 1) {
						for (int a1 = 0; a1 < 6; a1++) {
							for (int d1 = 0; d1 < 6; d1++) {
								set[a1][d1] = new Battle(new Integer[] { a1 },
										new Integer[] { d1 });
							}
						}
					} else if (dDice == 2) {
						for (int a1 = 0; a1 < 6; a1++) {
							for (int d1 = 0; d1 < 6; d1++) {
								for (int d2 = 0; d2 < 6; d2++) {
									((Battle[][][]) set)[a1][d1][d2] = new Battle(
											new Integer[] { a1 },
											new Integer[] { d1, d2 });
								}
							}
						}
					}
				} else if (aDice == 2) {
					if (dDice == 1) {
						for (int a1 = 0; a1 < 6; a1++) {
							for (int a2 = 0; a2 < 6; a2++) {
								for (int d1 = 0; d1 < 6; d1++) {
									((Battle[][][]) set)[a1][a2][d1] = new Battle(
											new Integer[] { a1, a2 },
											new Integer[] { d1 });
								}
							}
						}
					} else if (dDice == 2) {
						for (int a1 = 0; a1 < 6; a1++) {
							for (int a2 = 0; a2 < 6; a2++) {
								for (int d1 = 0; d1 < 6; d1++) {
									for (int d2 = 0; d2 < 6; d2++) {
										((Battle[][][][]) set)[a1][a2][d1][d2] = new Battle(
												new Integer[] { a1, a2 },
												new Integer[] { d1, d2 });
									}
								}
							}
						}
					}
				} else if (aDice == 3) {
					if (dDice == 1) {
						for (int a1 = 0; a1 < 6; a1++) {
							for (int a2 = 0; a2 < 6; a2++) {
								for (int a3 = 0; a3 < 6; a3++) {
									for (int d1 = 0; d1 < 6; d1++) {
										((Battle[][][][]) set)[a1][a2][a3][d1] = new Battle(
												new Integer[] { a1, a2, a3 },
												new Integer[] { d1 });
									}
								}
							}
						}
					} else if (dDice == 2) {
						for (int a1 = 0; a1 < 6; a1++) {
							for (int a2 = 0; a2 < 6; a2++) {
								for (int a3 = 0; a3 < 6; a3++) {
									for (int d1 = 0; d1 < 6; d1++) {
										for (int d2 = 0; d2 < 6; d2++) {
											((Battle[][][][][]) set)[a1][a2][a3][d1][d2] = new Battle(
													new Integer[] { a1, a2, a3 },
													new Integer[] { d1, d2 });
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public double getProbability(int aDice, int dDice) {
		Object[][] set = outcomes[aDice - 1][dDice - 1];
		int wins = 0;
		int totalOutcomes = (int) Math.pow(6, aDice + dDice);
		for (Object[] o : set) {
			for (Object p : o) {
				if (Object[].class.isInstance(p)) {
					for (Object q : (Object[]) p) {
						if (Object[].class.isInstance(q)) {
							for (Object r : (Object[]) q) {
								if (Battle[].class.isInstance(r)) {
									for (Battle s : (Battle[]) r) {
										int aWins = s.getDefenderLosses();
										int dWins = s.getAttackerLosses();
										wins += aWins - dWins;
									}
								} else {
									int aWins = ((Battle) r)
											.getDefenderLosses();
									int dWins = ((Battle) r)
											.getAttackerLosses();
									wins += aWins - dWins;
								}
							}
						} else {
							int aWins = ((Battle) q).getDefenderLosses();
							int dWins = ((Battle) q).getAttackerLosses();
							wins += aWins - dWins;
						}
					}
				} else {
					int aWins = ((Battle) p).getDefenderLosses();
					int dWins = ((Battle) p).getAttackerLosses();
					wins += aWins - dWins;
				}
			}
		}
		return (((double)wins)/((double)totalOutcomes))*100.0;
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
