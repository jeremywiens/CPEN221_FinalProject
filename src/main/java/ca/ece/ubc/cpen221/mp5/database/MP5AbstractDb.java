package ca.ece.ubc.cpen221.mp5.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.ToDoubleBiFunction;

public class MP5AbstractDb<T> implements MP5Db<T> {

	// Abstraction function: YelpDB exists as three lists. Restaurants which is an
	// object specifically for YelpDB. Users and reviews are objects which exist
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
	
	public MP5AbstractDb() {} 
	
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
