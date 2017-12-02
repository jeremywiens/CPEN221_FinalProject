package ca.ece.ubc.cpen221.mp5.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.ToDoubleBiFunction;

import ca.ece.ubc.cpen221.mp5.learning.*;

import java.io.*;

/**
 * YelpDB - This is a database of information found on YelpDB. This implements
 * MP5Db but is used with Restaurants as the business type which are reviewed.
 * This database exists as a list of restaurants, reviews and users. Various
 * algorithms can be performed on this database.
 *
 */
public class YelpDB extends MP5AbstractDb<Restaurant> {

	// Abstraction function: YelpDB exists as three lists. Restaurants which is an
	// object specifically for YelpDB. Users and reviews are objects which exist
	// generically for any product which is reviewed

	// Rep Invariant: Each list must not be null.
	// Restaurants must have a unique business ID
	private List<Restaurant> restaurants;
	// Reviews must have a unique ID, and be written by users for restaurants which
	// exist in YelpDB
	// private List<Review> reviews;
	// Users must have unique user ID
	// private List<User> users;
	private int count = 0;
	// private List<String> usernames = new ArrayList<String>();
	// private List<String> user_IDs = new ArrayList<String>();
	// private List<String> business_IDs = new ArrayList<String>();
	// private List<String> review_IDs = new ArrayList<String>();

	/**
	 * YelpDB Constructor - creates the database given three files which are in JSON
	 * format.
	 * 
	 * @param restaurants_file
	 *            JSON file of properly formatted restaurants to be added to YelpDB.
	 *            Ex of proper format:
	 * 
	 *            {"open": true, "url": "http://www.yelp.com/biz/cafe-3-berkeley",
	 *            "longitude": -122.260408, "neighborhoods": ["Telegraph Ave", "UC
	 *            Campus Area"], "business_id": "gclB3ED6uk6viWlolSb_uA", "name":
	 *            "Cafe 3", "categories": ["Cafes", "Restaurants"], "state": "CA",
	 *            "type": "business", "stars": 2.0, "city": "Berkeley",
	 *            "full_address": "2400 Durant Ave\nTelegraph Ave\nBerkeley, CA
	 *            94701", "review_count": 9, "photo_url":
	 *            "http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg",
	 *            "schools": ["University of California at Berkeley"], "latitude":
	 *            37.867417, "price": 1}
	 * 
	 * @param reviews_file
	 *            JSON file of properly formatted reviews to be added to YelpDB. Ex.
	 *            of proper format:
	 * 
	 *            {"type": "review", "business_id": "1CBs84C-a-cuA3vncXVSAw",
	 *            "votes": {"cool": 0, "useful": 0, "funny": 0}, "review_id":
	 *            "0a-pCW4guXIlWNpVeBHChg", "text": "The pizza is terrible, but if
	 *            you need a place to watch a game or just down some pitchers, this
	 *            place works.\n\nOh, and the pasta is even worse than the pizza.",
	 *            "stars": 2, "user_id": "90wm_01FAIqhcgV_mPON9Q", "date":
	 *            "2006-07-26"}
	 * 
	 * 
	 * @param users_file
	 *            JSON file of properly formatted users to be added to YelpDB. Ex.
	 *            of proper format:
	 * 
	 *            {"url":
	 *            "http://www.yelp.com/user_details?userid=_NH7Cpq3qZkByP5xR4gXog",
	 *            "votes": {"funny": 35, "useful": 21, "cool": 14}, "review_count":
	 *            29, "type": "user", "user_id": "_NH7Cpq3qZkByP5xR4gXog", "name":
	 *            "Chris M.", "average_stars": 3.89655172413793}
	 * 
	 * @throws IOException
	 *             if either of the files cannot be read.
	 */
	public YelpDB(String restaurants_file, String reviews_file, String users_file) throws IOException {

		try {
			restaurants = ParseJSON.ParseRestaurant(restaurants_file);
			business = restaurants;
		} catch (Exception e) {
			throw new IOException("Cannot read restaurants_file");
		}
		try {
			reviews = ParseJSON.ParseReview(reviews_file);
		} catch (Exception e) {
			throw new IOException("Cannot read reviews_file");
		}
		try {
			users = ParseJSON.ParseUser(users_file);
		} catch (Exception e) {
			throw new IOException("Cannot read users_file");

		}
		for (User user : users) {
			usernames.add(user.getName());
			user_IDs.add(user.getUserID());
		}
		for (Restaurant business : restaurants) {
			business_IDs.add(business.getBusinessID());
		}
		for (Review review : reviews) {
			review_IDs.add(review.getReviewID());
		}
	}
	
	//Not yet implented 
	@Override
	public Set<Restaurant> getMatches(String queryString) {
		// TODO Auto-generated method stub
		return null;
	}

	

	/**
	 * getRestaurant - Takes in a businessID and searches this YelpDb for the
	 * restaurant with that business businessID and returns the Restuarant.
	 * 
	 * @param business_id
	 *            - The string of the business_ID of the restaurant to search for in
	 *            the Yelp database, must not be null
	 * @return The Restaurant with the provided businessID, or throws an
	 *         IllegalArgumentException is the restaurant doesn't exist.
	 */
	public Restaurant getRestaurant(String business_id) {
		Restaurant rest = null;
		// no data races!
		synchronized (sync) {
			for (Restaurant restaurant : restaurants) {
				rest = restaurant;
				if (rest.getBusinessID().equals(business_id)) {
					return rest;
				}
			}
		}
		throw new IllegalArgumentException("ERR: NO SUCH RESTAURANT");
	}

	/**
	 * AddRestaurant - Takes in a string in JSON format representing a restaurant to
	 * be added to the database. The user is added to the database provided that all
	 * the restaurant information is provided in JSON format excluding the stars,
	 * url, photo_url, and review_count of the Restaurant. If the businessID already
	 * exists, it is replaced with a unique businessID. If the string is not in JSON
	 * format or does not contain valid restaurant information an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param string
	 *            - The String in JSON format representing the restaurant to be
	 *            added, cannot be null.
	 * @return A string representing the restaurant in JSON format
	 */
	public String AddRestaurant(String string) {
		try {
			Restaurant newRestaurant;
			synchronized (sync) {
				// synchronized here to ensure business_ID is unique and review information is
				// not incorrect when a simultaneous review is added
				newRestaurant = new Restaurant(string);
				// if the database contains the businessID already or if it is null, generate a
				// new one based on an int value
				while (business_IDs.contains(newRestaurant.getBusinessID()) || newRestaurant.getBusinessID() == null) {
					newRestaurant.changeID(Integer.toString(count));
					count++;
				}
				restaurants.add(newRestaurant);
				business_IDs.add(newRestaurant.getBusinessID());
			}
			return newRestaurant.toString();
		} catch (Exception e) {
			throw new IllegalArgumentException("ERR: INVALID_RESTAURANT_STRING");
		}
	}

}