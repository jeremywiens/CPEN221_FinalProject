package ca.ece.ubc.cpen221.mp5.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.ToDoubleBiFunction;

public class MP5AbstractDb<T> implements MP5Db<T> {

	// Abstraction function: A database as three lists. Business' which is any type
	// of business. Users and reviews are objects which exist
	// generically for any product which is reviewed

	// Rep Invariant: Each list must not be null.
	// Business' must have a unique business ID
	private List<Business> business;
	// Reviews must have a unique ID, and be written by users for restaurants which
	// exist in YelpDB
	private List<Review> reviews;
	// Users must have unique user ID
	private List<User> users;
	private int count = 0;
	private List<String> usernames = new ArrayList<String>();
	private List<String> user_IDs = new ArrayList<String>();
	private List<String> business_IDs = new ArrayList<String>();

	/**
	 * MP5AbstractDb constructor as empty database
	 */
	public MP5AbstractDb() {
	}

	/**
	 * YelpDB Constructor - creates the database given three files which are in JSON
	 * format.
	 * 
	 * @param business_file
	 *            JSON file of properly formatted business to be added to
	 *            MP5AbstractDb. Ex of proper format:
	 * 
	 *            {"open": true, "url": "http://www.yelp.com/biz/cafe-3-berkeley",
	 *            "longitude": -122.260408, "neighborhoods": ["Telegraph Ave", "UC
	 *            Campus Area"], "business_id": "gclB3ED6uk6viWlolSb_uA", "name":
	 *            "Cafe 3", "state": "CA", "type": "business", "stars": 2.0, "city":
	 *            "Berkeley", "full_address": "2400 Durant Ave\nTelegraph
	 *            Ave\nBerkeley, CA 94701", "review_count": 9, "latitude":
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
	public MP5AbstractDb(String business_file, String reviews_file, String users_file) throws IOException {
		try {
			business = ParseJSON.ParseBusiness(business_file);
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
		for (Business business : business) {
			business_IDs.add(business.getBusinessID());

		}
	}

	@Override
	public Set getMatches(String queryString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String kMeansClusters_json(int k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ToDoubleBiFunction getPredictorFunction(String user) {
		// TODO Auto-generated method stub
		return null;
	}

}
