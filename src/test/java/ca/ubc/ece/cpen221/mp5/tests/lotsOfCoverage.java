package ca.ubc.ece.cpen221.mp5.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import ca.ece.ubc.cpen221.mp5.database.Business;
import ca.ece.ubc.cpen221.mp5.database.MP5AbstractDb;
import ca.ece.ubc.cpen221.mp5.database.Restaurant;
import ca.ece.ubc.cpen221.mp5.database.Review;
import ca.ece.ubc.cpen221.mp5.database.User;
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

	
	@Test
	public void test2() {
		try {
			Business restaurant = new Business("{\"open\": true, \"url\": \"http://www.yelp.com/biz/cafe-3-berkeley\", \"longitude\": -122.260408, \"neighborhoods\": [\"Telegraph Ave\", \"UC Campus Area\"], \"business_id\": \"gclB3ED6uk6viWlolSb_uA\", \"name\": \"Cafe 3\", \"categories\": [\"Cafes\", \"Restaurants\"], \"state\": \"CA\", \"type\": \"business\", \"stars\": 2.0, \"city\": \"Berkeley\", \"full_address\": \"2400 Durant Ave\\nTelegraph Ave\\nBerkeley, CA 94701\", \"review_count\": 9, \"photo_url\": \"http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg\", \"schools\": [\"University of California at Berkeley\"], \"latitude\": 37.867417, \"price\": 1}");
			assertTrue(restaurant.getOpen());
			assertEquals("http://www.yelp.com/biz/cafe-3-berkeley", restaurant.getUrl());
			assertEquals(-122.260408, restaurant.getLongitude(), 0.000005);
			ArrayList<String> test = new ArrayList<String>();
			test.add("Telegraph Ave");
			test.add("UC Campus Area");
			assertEquals(test.get(0), restaurant.getNeighborhoods().get(0));
			assertEquals(test.get(1), restaurant.getNeighborhoods().get(1));
			assertEquals("gclB3ED6uk6viWlolSb_uA", restaurant.getBusinessID());
			assertEquals("Cafe 3", restaurant.getName());
			assertEquals("CA", restaurant.getState());
			assertEquals("business", restaurant.getType());
			assertEquals(2.0, restaurant.getStars(), 0.005);
			assertEquals("Berkeley", restaurant.getCity());
			assertEquals("2400 Durant Ave\nTelegraph Ave\nBerkeley, CA 94701", restaurant.getAddress());
			assertEquals(9, restaurant.getNumReviews());
			assertEquals(37.867417, restaurant.getLatitude(), 0.000005);
			assertEquals(1, restaurant.getPrice());
			restaurant.toString();
			restaurant.addReview(5);
			assertEquals(10, restaurant.getNumReviews());
			assertEquals(2.3, restaurant.getStars(), 0.05);
			Business noURL = new Business("{\"open\": true, \"longitude\": -122.260408, \"neighborhoods\": [\"Telegraph Ave\", \"UC Campus Area\"], \"business_id\": \"gclB3ED6uk6viWlolSb_uA\", \"name\": \"Cafe 3\", \"categories\": [\"Cafes\", \"Restaurants\"], \"state\": \"CA\", \"type\": \"business\", \"stars\": 2.0, \"city\": \"Berkeley\", \"full_address\": \"2400 Durant Ave\\nTelegraph Ave\\nBerkeley, CA 94701\", \"review_count\": 9, \"photo_url\": \"http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg\", \"schools\": [\"University of California at Berkeley\"], \"latitude\": 37.867417, \"price\": 1}");
			assertEquals("http://www.yelp.com/biz/cafe-3-berkeley", noURL.getUrl());
			noURL.changeID("banana");
			assertEquals("banana", noURL.getBusinessID());
			Business Error = new Business("{\"open\": false}");
			
		} catch (Exception e) {}
	}
	
	@Test
	public void test3() {
		try {
			Business restaurant = new Business("{\"open\": true, \"url\": \"http://www.yelp.com/biz/cafe-3-berkeley\", \"longitude\": -122.260408, \"neighborhoods\": [\"Telegraph Ave\", \"UC Campus Area\"], \"business_id\": \"gclB3ED6uk6viWlolSb_uA\", \"name\": \"Cafe 3\", \"categories\": [\"Cafes\", \"Restaurants\"], \"state\": \"CA\", \"type\": \"turtle\", \"stars\": 2.0, \"city\": \"Berkeley\", \"full_address\": \"2400 Durant Ave\\nTelegraph Ave\\nBerkeley, CA 94701\", \"review_count\": 9, \"photo_url\": \"http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg\", \"schools\": [\"University of California at Berkeley\"], \"latitude\": 37.867417, \"price\": 1}");
		} catch (Exception e ) {}
		
	}

}
