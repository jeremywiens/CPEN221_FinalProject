package ca.ece.ubc.cpen221.mp5.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

public class ParseJSON {

	// errors should be solved once types have been created.
	private static List<Object> thisList = new ArrayList<>();

	public static List<Object> ParseFile(String filename, int type) throws IOException {

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (type == 1) {
					Restaurant thisRestaurant = new Restaurant(line);
					thisList.add(thisRestaurant);
				} else if (type == 2) {
					Review thisReview = new Review(line);
					thisList.add(thisReview);
				} else if (type == 3) {
					User thisUser = new User(line);
					thisList.add(thisUser);
				}
			}

			return thisList;
		}
	}
}
