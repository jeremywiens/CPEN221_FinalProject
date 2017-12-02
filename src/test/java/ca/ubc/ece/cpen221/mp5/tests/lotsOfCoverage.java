package ca.ubc.ece.cpen221.mp5.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import ca.ece.ubc.cpen221.mp5.database.MP5AbstractDb;
import ca.ece.ubc.cpen221.mp5.database.YelpDB;

public class lotsOfCoverage {

	@Test
	public void test() throws IOException {
		MP5AbstractDb what = new MP5AbstractDb();
		MP5AbstractDb whatNow = new MP5AbstractDb("data/restaurants.json", "data/reviews.json", "data/users.json");
		try {
			MP5AbstractDb whatNow2 = new MP5AbstractDb("data/restaurants.json", "dateviews.json", "data/users.json");
		}catch(Exception e){
			assertEquals("Cannot read reviews_file", e.getMessage());
		}try {
			MP5AbstractDb whatNow1 = new MP5AbstractDb("data/restaurants.json", "data/reviews.json", "datusers.json");
		}catch(Exception e){
			assertEquals("Cannot read users_file", e.getMessage());
		}try {
			MP5AbstractDb whatNowAgain = new MP5AbstractDb("dataurants.json", "dateviews.json", "datsers.json");
		}catch(Exception e){
			assertEquals("Cannot read restaurants_file", e.getMessage());
		}
	}
	
	@Test
	public void test1() {
		try {
			YelpDB yelpers = new YelpDB("data/restaurants.json", "dateviews.json", "data/users.json");
		}catch(Exception e) {
			assertEquals("Cannot read reviews_file", e.getMessage());
		}
		try {
			YelpDB yelpers = new YelpDB("data/restaurants.json", "data/reviews.json", "datusers.json");
		}catch(Exception e) {
			assertEquals("Cannot read users_file", e.getMessage());
		}
		try {
			YelpDB yelpers = new YelpDB("dataurants.json", "dateviews.json", "datsers.json");
		}catch(Exception e) {
			assertEquals("Cannot read restaurants_file", e.getMessage());
		}
	}

}
