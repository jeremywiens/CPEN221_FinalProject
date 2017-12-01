package ca.ubc.ece.cpen221.mp5.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.json.*;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.junit.Test;

import ca.ece.ubc.cpen221.mp5.database.Restaurant;
import ca.ece.ubc.cpen221.mp5.database.Review;
import ca.ece.ubc.cpen221.mp5.database.User;
import ca.ece.ubc.cpen221.mp5.database.YelpDB;

public class JsonTest {

	@Test
	public void test() throws IOException {
		//YelpDB thisYelp = new YelpDB("data/restaurants.json", "", "");
		Restaurant tester = new Restaurant("{\"open\": true, \"url\": \"http://www.yelp.com/biz/jasmine-thai-berkeley\", \"longitude\": -122.2602981, \"neighborhoods\": [\"UC Campus Area\"], \"business_id\": \"BJKIoQa5N2T_oDlLVf467Q\", \"name\": \"Jasmine Thai\", \"categories\": [\"Thai\", \"Restaurants\"], \"state\": \"CA\", \"type\": \"business\", \"stars\": 3.0, \"city\": \"Berkeley\", \"full_address\": \"1805 Euclid Ave\\nUC Campus Area\\nBerkeley, CA 94709\", \"review_count\": 52, \"photo_url\": \"http://s3-media2.ak.yelpcdn.com/bphoto/ZwTUUb-6jkuzMDBBsUV6Eg/ms.jpg\", \"schools\": [\"University of California at Berkeley\"], \"latitude\": 37.8759615, \"price\": 2}");
		System.out.println(tester.getOpen());
		System.out.println(tester.getUrl());
		System.out.println(tester.getLongitude());
		System.out.println(tester.getNeighborhoods());
		System.out.println(tester.getBusinessID());
		System.out.println(tester.getName());
		System.out.println(tester.getCategories());
		System.out.println(tester.getState());
		System.out.println(tester.getType());
		System.out.println(tester.getStars());
		System.out.println(tester.getAddress());
		System.out.println(tester.getNumReviews());
		System.out.println(tester.getPhotoURL());
		System.out.println(tester.getSchools());
		System.out.println(tester.getPrice());
		System.out.println(tester.getLatitude());
	} 
	
	@Test
	public void test1() throws IOException {
		final String result = "{\"type\": \"review\", \"business_id\": \"1CBs84C-a-cuA3vncXVSAw\", \"votes\": {\"cool\": 0, \"useful\": 0, \"funny\": 0}, \"review_id\": \"0a-pCW4guXIlWNpVeBHChg\", \"text\": \"The pizza is terrible, but if you need a place to watch a game or just down some pitchers, this place works.\\n\\nOh, and the pasta is even worse than the pizza.\", \"stars\": 2, \"user_id\": \"90wm_01FAIqhcgV_mPON9Q\", \"date\": \"2006-07-26\"}";
	    final JsonParser parser = Json.createParser(new StringReader(result));
	    String key = null;
	    String value = null;
	    while (parser.hasNext()) {
	         final Event event = parser.next();
	         switch (event) {
	            case KEY_NAME:
	                 key = parser.getString();
	                 System.out.println("This is a key: " + key);
	                 break;
	            case VALUE_STRING:
	                String string = parser.getString();
	            	System.out.println("This is a string: " + string);
	                 break;
	            case VALUE_NUMBER:
	                BigDecimal number = parser.getBigDecimal();
	                System.out.println("This is a number: " + number);
	                break;
	            case VALUE_TRUE:
	                System.out.println("This is boolean: " + true);
	                break;
	            case VALUE_FALSE:
	                System.out.println(false);
	                break;
	            }
	        }
	        parser.close();
	}
	
	@Test
	public void test2() throws IOException{
		User tester  = new User("{\"url\": \"http://www.yelp.com/user_details?userid=7RsdY4_1Bb_bCf5ZbK6tyQ\", \"votes\": {\"funny\": 3, \"useful\": 25, \"cool\": 4}, \"review_count\": 21, \"type\": \"user\", \"user_id\": \"7RsdY4_1Bb_bCf5ZbK6tyQ\", \"name\": \"cat u.\", \"average_stars\": 3.0}");
		System.out.println(tester.getUrl());
		System.out.println(tester.getVotes());
		System.out.println("funny votes =" + tester.getFunnyVotes());
		System.out.println("useful votes =" + tester.getUsefulVotes());
		System.out.println("cool votes =" + tester.getCoolVotes());
		System.out.println(tester.getNumReviews());
		System.out.println(tester.getType());
		System.out.println(tester.getUserID());
		System.out.println(tester.getName());
		System.out.println(tester.getAvgStars());
	}
	
	@Test
	public void test3() throws IOException {
		Review tester = new Review("{\"type\": \"review\", \"business_id\": \"1CBs84C-a-cuA3vncXVSAw\", \"votes\": {\"cool\": 0, \"useful\": 1, \"funny\": 0}, \"review_id\": \"2ZPRBx_5g1wuvYhy3U6TLQ\", \"text\": \"La Vals is like Gypsie's healthier little brother. They use less salt, oil and butter, but it still tastes delicious. I like their vegetarian pizza because it doesn't leave me feeling guilty, but it does leave me satisfied. If it wasn't so far away, I would attempt to come here more often.\\n\\nP.S., it's cheap!\", \"stars\": 4, \"user_id\": \"XKHLY-KuXfrReXXuSfJkLg\", \"date\": \"2012-08-28\"}");
		System.out.println(tester.getType());
		System.out.println(tester.getBusinessID());
		System.out.println(tester.getVotes());
		System.out.println(tester.getCoolVotes());
		System.out.println(tester.getFunnyVotes());
		System.out.println(tester.getUsefulVotes());
		System.out.println(tester.getReviewID());
		System.out.println(tester.getText());
		System.out.println(tester.getNumStars());
		System.out.println(tester.getUserID());
		System.out.println(tester.getDate());
	}
	

}
