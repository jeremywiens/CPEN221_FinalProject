package ca.ubc.ece.cpen221.mp5.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import ca.ece.ubc.cpen221.mp5.database.Restaurant;
import ca.ece.ubc.cpen221.mp5.database.Review;
import ca.ece.ubc.cpen221.mp5.database.User;

import org.junit.Test;

public class ItemTests {

	@Test
	public void test1() {
		try {
			User user = new User("{\"url\": \"http://www.yelp.com/user_details?userid=7RsdY4_1Bb_bCf5ZbK6tyQ\", \"votes\": {\"funny\": 3, \"useful\": 25, \"cool\": 4}, \"review_count\": 21, \"type\": \"user\", \"user_id\": \"7RsdY4_1Bb_bCf5ZbK6tyQ\", \"name\": \"cat u.\", \"average_stars\": 3.0, \"potato\": \"mashed\" }");
			user.getVotes();
			assertEquals ("http://www.yelp.com/user_details?userid=7RsdY4_1Bb_bCf5ZbK6tyQ", user.getUrl());
			assertEquals(3, user.getFunnyVotes());
			assertEquals(25, user.getUsefulVotes());
			assertEquals(4, user.getCoolVotes());
			assertEquals(21, user.getNumReviews());
			assertEquals("user", user.getType());
			assertEquals("7RsdY4_1Bb_bCf5ZbK6tyQ", user.getUserID());
			assertEquals("cat u.", user.getName());
			assertEquals(3.0, user.getAvgStars(), 0.000005);
			user.toString();
			User onlyName = new User ("{\"name\": \"Bob\"}");
			onlyName.changeID("Billy");
			assertEquals("Billy", onlyName.getUserID());
			User error = new User("{\"potato\": true}");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void test2() {
		try {
			Restaurant restaurant = new Restaurant("{\"open\": true, \"url\": \"http://www.yelp.com/biz/cafe-3-berkeley\", \"longitude\": -122.260408, \"neighborhoods\": [\"Telegraph Ave\", \"UC Campus Area\"], \"business_id\": \"gclB3ED6uk6viWlolSb_uA\", \"name\": \"Cafe 3\", \"categories\": [\"Cafes\", \"Restaurants\"], \"state\": \"CA\", \"type\": \"business\", \"stars\": 2.0, \"city\": \"Berkeley\", \"full_address\": \"2400 Durant Ave\\nTelegraph Ave\\nBerkeley, CA 94701\", \"review_count\": 9, \"photo_url\": \"http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg\", \"schools\": [\"University of California at Berkeley\"], \"latitude\": 37.867417, \"price\": 1}");
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
			assertEquals("Cafes", restaurant.getCategories().get(0));
			assertEquals("CA", restaurant.getState());
			assertEquals("business", restaurant.getType());
			assertEquals(2.0, restaurant.getStars(), 0.005);
			assertEquals("Berkeley", restaurant.getCity());
			assertEquals("2400 Durant Ave\nTelegraph Ave\nBerkeley, CA 94701", restaurant.getAddress());
			assertEquals(9, restaurant.getNumReviews());
			assertEquals("http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg", restaurant.getPhotoURL());
			assertEquals("University of California at Berkeley", restaurant.getSchools().get(0));
			assertEquals(37.867417, restaurant.getLatitude(), 0.000005);
			assertEquals(1, restaurant.getPrice());
			restaurant.toString();
			restaurant.addReview(5);
			assertEquals(10, restaurant.getNumReviews());
			assertEquals(2.3, restaurant.getStars(), 0.05);
			Restaurant noURL = new Restaurant("{\"open\": true, \"longitude\": -122.260408, \"neighborhoods\": [\"Telegraph Ave\", \"UC Campus Area\"], \"business_id\": \"gclB3ED6uk6viWlolSb_uA\", \"name\": \"Cafe 3\", \"categories\": [\"Cafes\", \"Restaurants\"], \"state\": \"CA\", \"type\": \"business\", \"stars\": 2.0, \"city\": \"Berkeley\", \"full_address\": \"2400 Durant Ave\\nTelegraph Ave\\nBerkeley, CA 94701\", \"review_count\": 9, \"photo_url\": \"http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg\", \"schools\": [\"University of California at Berkeley\"], \"latitude\": 37.867417, \"price\": 1}");
			assertEquals("http://www.yelp.com/biz/cafe-3-berkeley", noURL.getUrl());
			noURL.changeID("banana");
			assertEquals("banana", noURL.getBusinessID());
			Restaurant Error = new Restaurant("{\"open\": false}");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void test3() {
		try {
			Restaurant restaurant = new Restaurant("{\"open\": true, \"url\": \"http://www.yelp.com/biz/cafe-3-berkeley\", \"longitude\": -122.260408, \"neighborhoods\": [\"Telegraph Ave\", \"UC Campus Area\"], \"business_id\": \"gclB3ED6uk6viWlolSb_uA\", \"name\": \"Cafe 3\", \"categories\": [\"Cafes\", \"Restaurants\"], \"state\": \"CA\", \"type\": \"turtle\", \"stars\": 2.0, \"city\": \"Berkeley\", \"full_address\": \"2400 Durant Ave\\nTelegraph Ave\\nBerkeley, CA 94701\", \"review_count\": 9, \"photo_url\": \"http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg\", \"schools\": [\"University of California at Berkeley\"], \"latitude\": 37.867417, \"price\": 1}");
		} catch (Exception e ) {
			System.out.println(e.getMessage());
		}
		
	}

	@Test
	public void test4() {
		try {
			Review review = new Review("{\"type\": \"review\", \"business_id\": \"1CBs84C-a-cuA3vncXVSAw\", \"votes\": {\"cool\": 0, \"useful\": 0, \"funny\": 0}, \"review_id\": \"0a-pCW4guXIlWNpVeBHChg\", \"text\": \"Very Good\", \"stars\": 2, \"user_id\": \"90wm_01FAIqhcgV_mPON9Q\", \"date\": \"2006-07-26\"}");
			assertEquals("review", review.getType());
			assertEquals("1CBs84C-a-cuA3vncXVSAw", review.getBusinessID());
			assertEquals(3, review.getVotes().size());
			assertEquals(0, review.getUsefulVotes());
			assertEquals(0, review.getFunnyVotes());
			assertEquals(0, review.getCoolVotes());
			assertEquals("0a-pCW4guXIlWNpVeBHChg", review.getReviewID());
			assertEquals("Very Good", review.getText());
			assertEquals(2, review.getNumStars());
			assertEquals("90wm_01FAIqhcgV_mPON9Q", review.getUserID());
			assertEquals("2006-07-26", review.getDate());
			review.toString();
			review.changeID("joey");
			assertEquals("joey", review.getReviewID());
			Review minInfo = new Review("{\"business_id\": \"1CBs84C-a-cuA3vncXVSAw\", \"review_id\": \"0a-pCW4guXIlWNpVeBHChg\", \"stars\": 2, \"user_id\": \"90wm_01FAIqhcgV_mPON9Q\", \"day\": true}");
			Review error = new Review("{\"business_id\": \"1CBs84C-a-cuA3vncXVSAw\"}");
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void test5() {
		try {
			Review review = new Review("{\"type\": \"user\", \"business_id\": \"1CBs84C-a-cuA3vncXVSAw\", \"votes\": {\"cool\": 0, \"useful\": 0, \"funny\": 0}, \"review_id\": \"0a-pCW4guXIlWNpVeBHChg\", \"text\": \"The pizza is terrible, but if you need a place to watch a game or just down some pitchers, this place works.\\n\\nOh, and the pasta is even worse than the pizza.\", \"stars\": 2, \"user_id\": \"90wm_01FAIqhcgV_mPON9Q\", \"date\": \"2006-07-26\"}");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void test6() {
		try {

			User onlyName = new User ("{\"name\": \"Bob\", \"type\": \"review\"}");
			
		} catch (Exception e) {
			
		}
	}
}
