package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.undo.StateEdit;

import com.sun.media.sound.InvalidFormatException;

public class Game {

	GameState state;
	Random rng;

	public Game() {
		// TODO Auto-generated constructor stub
	}

	public void readMap(String fileName) throws FileNotFoundException,
			InvalidFormatException {
		state = new GameState();
		// some metadata at the top of the file.
		Pattern name = Pattern.compile("\"[a-zA-Z -'0-9]+?\"");
		Pattern number = Pattern.compile("\\d+");
		Pattern regionSpec = Pattern.compile(name + "[ ,;] ?" + number);
		Scanner dataFile = new Scanner(new File(fileName));
		// each section starts with a region name on it's own line
		// then each country followed by the bordering countries.
		// example:
		/*
		 * "Australia" 2                                                                                            .
		 * "Indosesia" "West Australia" "New Guinea"
		 * "New Guinea" "Indonesia" "East Australia" "West Australia"
		 * "West Australia" "New Guinea" "Indonesia" "East Australia"
		 * "East Australia" "New Guinea" "West Austraila"
		 */
		// read metadata here.
		HashMap<Country, HashSet<String>> travelRoutes = new HashMap<>();
		Region r = null;
		while (dataFile.hasNextLine()) {
			String line = dataFile.nextLine();
			Matcher names = name.matcher(line);
			Matcher numbers = number.matcher(line);
			if (line.matches(regionSpec.toString())) {
				names.find();
				r = new Region(names.group().substring(1,
						names.group().length() - 1));
				numbers.find();
				r.setBonus(Integer.parseInt(numbers.group()));
				state.regions.add(r);
			} else {
				if (r == null) {
					throw new InvalidFormatException(
							"The file should start with a region.");
				}
				names.find();
				Country c = new Country(names.group().substring(1,
						names.group().length() - 1));
				state.countries.add(c);
				HashSet<String> list = new HashSet<>();
				while (names.find()) {
					list.add(names.group().substring(1,
							names.group().length() - 1));
				}
				c.setParent(r);
				r.addCountry(c);
				travelRoutes.put(c, list);
			}
		}
		// add all countries' neighbors.
		for (Country c : state.countries) {
			HashSet<String> neighbors = travelRoutes.get(c);
			for (String cName : neighbors) {
				for (Country o : state.countries) {
					if (o.getName().equals(cName)) {
						c.addNeighbor(o);
						break;
					}
				}
			}
		}
	}
	
	@Override
	public String toString() {
		String output = "";
		for (Region r : state.regions) {
			output += String.format("\"%s\": bonus of %d armies.\n", r.getName(), r.getBonus());
			for (Country c : r.getCountries()) {
				output += "\t\"" + c.getName() + "\" connects to:";
				for (Country n : c.getNeighbors()) {
					output += "\n\t\t\"" + n.getName() + "\" (in " + n.getParent().getName() + ")";
				}
				output += "\n";
			}
		}
		return output;
	}

	public static void main(String[] args) throws InvalidFormatException, FileNotFoundException {
		Game g = new Game();
		g.readMap("Australia.txt");
		System.out.println(g);
	}

}
