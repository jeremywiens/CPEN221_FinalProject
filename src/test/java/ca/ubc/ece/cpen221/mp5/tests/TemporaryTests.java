package ca.ubc.ece.cpen221.mp5.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import ca.ece.ubc.cpen221.mp5.database.YelpDB;

public class TemporaryTests {

	@Test
	public void test() throws IOException {
		YelpDB yelped = new YelpDB("data/restaurants.json", "data/reviews.json", "data/users.json");
		System.out.println(yelped.AddRestaurant("{\"open\": true, \"url\": \"http://www.yelp.com/biz/cafe-3-berkeley\", \"longitude\": -122.260408, \"neighborhoods\": [\"Telegraph Ave\", \"UC Campus Area\"], \"business_id\": \"bobby\", \"name\": \"Jim Bob\", \"categories\": [\"Cafes\", \"Restaurants\"], \"state\": \"CA\", \"type\": \"business\", \"stars\": 2.0, \"city\": \"Berkeley\", \"full_address\": \"2400 Durant Ave\\nTelegraph Ave\\nBerkeley, CA 94701\", \"review_count\": 9, \"photo_url\": \"http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg\", \"schools\": [\"University of California at Berkeley\"], \"latitude\": 37.867417, \"price\": 1}"));
		System.out.println(yelped.AddUser("{\"votes\": {\"funny\": 35, \"useful\": 21, \"cool\": 14}, \"review_count\": 29, \"type\": \"user\", \"user_id\": \"bobby\", \"name\": \"Chris M.\", \"average_stars\": 3.89655172413793}"));
		System.out.println(yelped.AddUser("{\"name\": \"Chris M.\"}"));
		System.out.println(yelped.getUser("90wm_01FAIqhcgV_mPON9Q").getVotes());
		System.out.println(yelped.getUser("90wm_01FAIqhcgV_mPON9Q").getNumReviews());
		System.out.println(yelped.AddReview("{\"type\": \"review\", \"business_id\": \"1CBs84C-a-cuA3vncXVSAw\", \"votes\": {\"cool\": 10, \"useful\": 10, \"funny\": 10}, \"review_id\": \"0a-pCW4guXIlWNpVeBHChg\", \"stars\": 2, \"user_id\": \"90wm_01FAIqhcgV_mPON9Q\", \"date\": \"2006-07-26\"}"));
		System.out.println(yelped.getUser("90wm_01FAIqhcgV_mPON9Q").getVotes());
		System.out.println(yelped.getUser("90wm_01FAIqhcgV_mPON9Q").getNumReviews());
		
		
	}

}
