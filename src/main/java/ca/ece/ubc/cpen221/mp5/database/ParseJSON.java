package ca.ece.ubc.cpen221.mp5.database;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParseJSON {

	private static List<Object> thisList = new ArrayList<>();

	public static List<Object> ParseFile(String filename, int type) throws IOException, ParseException {
		
		JSONParser parser = new JSONParser();
		JSONArray a = (JSONArray) parser.parse(new FileReader(filename));
		
		 for (Object o : a) {
		
//			if(type == 1 )
//				Restaurant thisRestaurant = new Restaurant(o);
//			else if(type == 2) 
//				Review thisReview = new Review(o);
//			else if(type == 3)
//				User thisUser = new User(o);
//			thisList.add(o);	
		}

		return thisList;
	}

}
