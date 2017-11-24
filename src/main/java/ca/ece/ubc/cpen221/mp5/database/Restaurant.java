package ca.ece.ubc.cpen221.mp5.database;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import javax.json.*;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.json.stream.JsonParserFactory;

public class Restaurant {
	
	 private String review_count;
	 private String photo_url;
	 private String state;
	 private String full_address;
	 private String type;
	 private String url;
	 private String city;
	 private String open;
	 private String[] neighborhoods;
	 private String price;
	 private String[] schools;
	 private String stars;
	 private String name;
	 private String business_id;
	 private long longitude;
	 private String[] categories;
	 private long latitude;
	 
	public Restaurant (JsonObject Restaurant) {
		JsonParser parser = Json.createParserFactory(Restaurant).createParser(Restaurant);
		
		while (parser.hasNext()) {
			final Event event = parser.next();
//	         switch (event) {
//	            case KEY_NAME:
//	                 String key = parser.getString();
//	                 System.out.println(key);
//	                 break;
//	            case VALUE_STRING:
//	                 String string = parser.getString();
//	                 System.out.println(string);
//	                 break;
//	            case VALUE_NUMBER:
//	                BigDecimal number = parser.getBigDecimal();
//	                System.out.println(number);
//	                break;
//	            case VALUE_TRUE:
//	                System.out.println(true);
//	                break;
//	            case VALUE_FALSE:
//	                System.out.println(false);
//	                break;
//			
		}
		
	}
}