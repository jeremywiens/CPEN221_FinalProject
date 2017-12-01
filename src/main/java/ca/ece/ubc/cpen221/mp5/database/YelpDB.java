package ca.ece.ubc.cpen221.mp5.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.ToDoubleBiFunction;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import ca.ece.ubc.cpen221.mp5.learning.KMeans;

import java.io.*;
import java.math.BigDecimal;

/**
 * YelpDB - This is a database of information found on YelpDB. This implements
 * MP5Db but is used with Restaurants as the product which are reviewed. This
 * database exists as a list of restaurants, reviews and users. Various
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
	private List<Review> reviews;
	// Users must have unique user ID
	private List<User> users;
	private int count = 0;
	private List<String> usernames = new ArrayList<String>();
	private List<String> user_IDs = new ArrayList<String>();
	private List<String> business_IDs = new ArrayList<String>();
	private List<String> review_IDs = new ArrayList<String>();

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
		for (Review  review : reviews) {
			review_IDs.add(review.getReviewID());
		}
	}

/*	@Override
	public Set getMatches(String queryString) {
		// TODO Auto-generated method stub
		return null;
	}*/

	/**
	 * kMeansClusters_json performs the KMeans clustering algorithm on the current
	 * YelpDB. The algorithm assigns points into k clusters such that no point is
	 * closer to the center point (centroid) of a cluster other than the one to
	 * which it is assigned.
	 * 
	 * Specifically, these points are coordinates based on the latitude and
	 * Longitude of the restaurants in YelpDB.
	 * 
	 * @param k
	 *            is the number of clusters to be formed. Requires k != null && k >
	 *            0
	 * @return A string which is in JSON format of all the restaurants and their
	 *         respective clusters.
	 * 
	 *         An example of the String returned is this:
	 * 
	 *         {"x": 37.8727784971583, "y": -122.255591154098, "name": "The Coffee
	 *         Lab", "cluster": 0, "weight": 4.0}
	 * 
	 *         There will be an array of these in the returned String
	 */
	@Override
	public String kMeansClusters_json(int k) {
		// Map Integers to Restaurants
		int count = 0;
		HashMap<Integer, List<Double>> sendToKeans = new HashMap<>();
		HashMap<Integer, Restaurant> intToRestaurant = new HashMap<>();

		// Get the x and y coordinates of restaurants based on latitude and longitude.
		for (Restaurant R : restaurants) {
			List<Double> xAndY = new ArrayList<>();
			xAndY.add(R.getLatitude());
			xAndY.add(R.getLongitude());
			sendToKeans.put(count, xAndY);
			intToRestaurant.put(count, R);
			count++;
		}

		// Each set of Integers is a cluster. Each Integer points to a Restaurant object
		List<Set<Integer>> clusteredList = KMeans.findKMeans(sendToKeans, k);
		String returnString = "[";

		// Create the proper string with JSON format
		int cluster = 0;
		for (Set<Integer> clusterSet : clusteredList) {
			for (int restaurant : clusterSet) {
				returnString = returnString + "{\"x\": " + intToRestaurant.get(restaurant).getLatitude() + ", \"y\": "
						+ intToRestaurant.get(restaurant).getLongitude() + ", \"name\": \""
						+ intToRestaurant.get(restaurant).getName() + "\", \"cluster\": " + cluster
						+ ", \"weight\": 4.0}, ";
			}
			cluster++;
		}

		returnString = returnString.substring(0, returnString.length() - 2) + "]";
		return returnString;
	}

	@Override
	public ToDoubleBiFunction getPredictorFunction(String user) { // What do I return??? This is weird.
		User thisUser = null;
		List<Restaurant> Restaurants = new ArrayList<Restaurant>();
		List<Review> Reviews = new ArrayList<Review>();
		List<Double> sumMeanx = new ArrayList<Double>();
		List<Double> sumMeany = new ArrayList<Double>();
		double avgY;
		double avgX;
		double Sxx = 0.0;
		double Syy = 0.0;
		double Sxy = 0.0;
		double b;
		double a;
		double Rsquared;
		for (int i = 0; i < this.users.size(); i++) {
			if (user.equals(this.users.get(i).getUserID())) {
				thisUser = this.users.get(i);
			}
		}

		Restaurants = this.getRestaurantReviews(user);// this assumes passing a user ID to getPredictorFunction
		Reviews = this.getUserReviews(user);
		avgY = calcAvgY(Reviews);// this assumes passing a user ID to getPredictorFunction
		avgX = calcAvgX(Restaurants);
		sumMeanx = sumMeanX(Restaurants, avgX);
		sumMeany = sumMeanY(Reviews, avgY); // based on avgY assumption

		for (int i = 0; i < sumMeany.size(); i++) {
			double x = sumMeanx.get(i);
			double y = sumMeany.get(i);

			Sxx += x * x;
			Syy += y * y;
			Sxy += x * y;
		}

		b = Sxy / Sxx;
		a = avgY - b * avgX;
		Rsquared = Sxy * Sxy / (Sxx * Syy); // Now just have to compute the function and create it but unclear about how
											// to do that.

		ToDoubleBiFunction<MP5Db<Restaurant>, String> function = (MP5, restaurant_id) -> {
			YelpDB db = (YelpDB) MP5;
			double price = db.getRestaurant(restaurant_id).getPrice();
			return a*price + b;
		};

		return function;
	}
	
	public List<Restaurant> getRestaurantReviews(String user_ID) throws IllegalArgumentException {

		List<Restaurant> restaurants = new ArrayList<Restaurant>();

		for (Review review : reviews) {
			String userID = review.getUserID();

			if (userID.equals(user_ID)) {
				String BusinessID = review.getBusinessID();
				for (Restaurant restaurant : this.restaurants) {
					if (BusinessID.equals(restaurant.getBusinessID())) {
						restaurants.add(restaurant);
					}
				}
			}
		}

		return restaurants;
	}

	private List<Double> sumMeanX(List<Restaurant> Restaurants, double avg) {
		List<Double> sumMean = new ArrayList<Double>();

		for (Restaurant restaurant : Restaurants) {
			int price = restaurant.getPrice();
			sumMean.add(price - avg);
		}
		return sumMean;
	}

	private double calcAvgX(List<Restaurant> Restaurants) {
		double avg = 0.0;
		int count = 0;
		for (int i = 0; i < Restaurants.size(); i++) {
			avg += Restaurants.get(i).getPrice();
			count++;
		}

		return avg / count;
	}

	private double calcAvgY(List<Review> Reviews) {
		double avg = 0.0;
		int count = 0;
		for (int i = 0; i < Reviews.size(); i++) {
			avg += Reviews.get(i).getNumStars();
			count++;
		}
		return avg / count;
	}

	public List<Review> getUserReviews(String user_ID) {
		List<Review> Reviews = new ArrayList<Review>();
		for (Review review : reviews) {
			if (user_ID.equals(review.getUserID())) {
				Reviews.add(review);
			}
		}

		return Reviews;
	}

	private List<Double> sumMeanY(List<Review> Reviews, double avgY) {
		List<Double> sumMean = new ArrayList<Double>();
		for (Review review : Reviews) {
			sumMean.add(review.getNumStars() - avgY);
		}

		return sumMean;
	}

	public Restaurant getRestaurant(String business_id) throws IllegalArgumentException {
		Restaurant rest = null;
		for (Restaurant restaurant : restaurants) {
			rest = restaurant;
			if (rest.getBusinessID().equals(business_id)) {
				return rest;
			}
		}
		throw new IllegalArgumentException();
	}

	public String AddUser(String string) throws IllegalArgumentException {
		User newUser = new User(string);
		while (user_IDs.contains(newUser.getUserID()) || newUser.getUserID() == null) {
			newUser.changeID(Integer.toString(count));
			count++;
		}
		users.add(newUser);
		user_IDs.add(newUser.getUserID());
		return newUser.toString();
	}

	public String AddRestaurant(String string) throws IllegalArgumentException {
		try {
			Restaurant newRestaurant = new Restaurant(string);
			while (business_IDs.contains(newRestaurant.getBusinessID()) || newRestaurant.getBusinessID() == null) {
				newRestaurant.changeID(Integer.toString(count));
				count++;
			}
			restaurants.add(newRestaurant);
			business_IDs.add(newRestaurant.getBusinessID());

			return newRestaurant.toString();
		} catch (Exception e) {
			throw new IllegalArgumentException(); //should change this
		}
	}

	public String AddReview(String string) {// assume don't have to pass votes, text or date
		try {
			Review newReview = new Review(string);
			if (!business_IDs.contains(newReview.getBusinessID())) {
				throw new IllegalArgumentException();// should change this to a specific exception
			} else if (!user_IDs.contains(newReview.getUserID())) {
				throw new IllegalArgumentException(); // should change this too
			}
			while (review_IDs.contains(newReview.getReviewID()) || newReview.getReviewID() == null) {
				newReview.changeID(Integer.toString(count));
				count++;
			}
			Restaurant rest = this.getRestaurant(newReview.getBusinessID());
			rest.addReview(newReview.getNumStars());
			User user = this.getUser(newReview.getUserID());
			user.addReview(newReview.getNumStars(), newReview.getFunnyVotes(), newReview.getUsefulVotes(), newReview.getCoolVotes());
			review_IDs.add(newReview.getReviewID());
			reviews.add(newReview);
			return newReview.toString();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public User getUser(String user_id) {
		for (User user : users) {
			if (user_id.equals(user.getUserID())) {
				return user;
			}
		}
		throw new IllegalArgumentException();
	}

}