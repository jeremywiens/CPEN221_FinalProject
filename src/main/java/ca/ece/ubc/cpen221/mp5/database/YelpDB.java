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

public class YelpDB implements MP5Db {

	private List<Restaurant> restaurants;
	private List<Review> reviews;
	private List<User> users;
	private int count = 0;
	private List<String> usernames = new ArrayList<String>();
	private List<String> user_IDs = new ArrayList<String>();
	private List<String> business_IDs = new ArrayList<String>();

	// Maybe we should change the order so users are created first? Then every new
	// review object can be added to the user who wrote it?
	public YelpDB(String restaurants_file, String reviews_file, String users_file) throws IOException {
		restaurants = ParseJSON.ParseRestaurant(restaurants_file);
		reviews = ParseJSON.ParseReview(reviews_file);
		users = ParseJSON.ParseUser(users_file);
		for (User user:users) {
			usernames.add(user.getName());
			user_IDs.add(user.getUserID());
		}
		for (Restaurant business: restaurants) {
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
		//Map Integers to Restaurants
		int count = 0;
		HashMap<Integer, List<Double>> sendToKeans = new HashMap<>();
		HashMap<Integer, Restaurant> intToRestaurant = new HashMap<>();
		
		for(Restaurant R : restaurants) {
			List<Double> xAndY = new ArrayList<>();
			xAndY.add(R.getLatitude().doubleValue());
			xAndY.add(R.getLongitude().doubleValue());
			sendToKeans.put(count, xAndY);
			intToRestaurant.put(count, R);
			count++;
		}
		
		List<Set<Integer>> clusteredList = KMeans.findKMeans(sendToKeans, k);
		String returnString = "[";
		//[{"x": 37.8702006, "y": -122.2659014, "name": "Cinnaholic", "cluster": 3, "weight": 5.0}, {"x": 37.8703...
		int cluster = 0;
		for(Set<Integer> clusterSet : clusteredList) {
			for(int restaurant : clusterSet) {
				returnString = returnString + "{\"x\": " + intToRestaurant.get(restaurant).getLatitude() + ", \"y\": " + intToRestaurant.get(restaurant).getLongitude();
				returnString = returnString + ", \"name\": \"" + intToRestaurant.get(restaurant).getName() + "\", \"cluster\": " + cluster;
				returnString = returnString + ", \"weight\": 4.0}, ";
			}
			cluster++;
		}
		
		returnString = returnString.substring(0, returnString.length()-2) + "]";
		return returnString;
	}

	@Override
	public ToDoubleBiFunction getPredictorFunction(String user) { //What do I return??? This is weird.
		User thisUser = null; // is this the user id or username?
		List<Restaurant> Restaurants = new ArrayList<Restaurant>();
		List<Review> Reviews = new ArrayList<Review>();
		List<Double> sumMeanx = new ArrayList<Double>();
		List<Double> sumMeany = new ArrayList<Double>();
		ToDoubleBiFunction <Double, Double> Regression;
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
		
		b = Sxy/Sxx;
		a = avgY - b*avgX;
		Rsquared = Sxy*Sxy/(Sxx*Syy); //Now just have to compute the function and create it but unclear about how to do that.
		//Possibly y = ax+b
		
		Regression = (x,y) -> a*x +b;

		return Regression;
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

	private List<Double> sumMeanX(List<Restaurant> Restaurants , double avg) {
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
		
		return avg/count;
	}
	
	private double calcAvgY(List<Review> Reviews) {
		double avg = 0.0;
		int count = 0;
		for (int i = 0; i < Reviews.size(); i++) {
			avg+= Reviews.get(i).getNumStars();
			count++;
		}
		return avg/count;
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
	
	public Restaurant getRestaurant (String business_id) throws IllegalArgumentException {
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
		String username = null;
		JsonParser parser = Json.createParser(new StringReader(string));
		String key = null;
		String value = null;
		while (parser.hasNext()) {
			final Event event = parser.next();
			switch (event) {
			case KEY_NAME:
				key = parser.getString();
				break;
			case VALUE_STRING:
				if (key.equals("name")) {
					username = parser.getString();
				}
				break;
			case VALUE_NUMBER:
				break;
			case VALUE_TRUE:
				break;
			case VALUE_FALSE:
				break;
			}
		}
		parser.close();
		if (username == null) {
			throw new IllegalArgumentException();
		}
		
		while (users.contains(username)) {
			count++;
		}
		int userID = count;
		
		JsonObject Votes = Json.createObjectBuilder()
				.add("funny", BigDecimal.valueOf(0))
				.add("useful", BigDecimal.valueOf(0))
				.add("cool", BigDecimal.valueOf(0)).build();
		
		JsonObject user = Json.createObjectBuilder()
				.add("url", "http://www.yelp.com/user_details?userid=" + userID)
				.add("votes", Votes)
				.add("review_count", 0)
				.add("type", "user")
				.add("user_id", Integer.toString(userID))
				.add("name", username)
				.add("average_stars", BigDecimal.valueOf(0)).build();
		
		User newUser = new User(user.toString());
		users.add(newUser);
		user_IDs.add(newUser.getUserID());
		count++;

		return user.toString();
	}
	
	public String AddRestaurant(String string) throws IllegalArgumentException {
		try {
			Restaurant newRestaurant = new Restaurant (string);
			restaurants.add(newRestaurant);
			business_IDs.add(newRestaurant.getBusinessID());
			
			JsonArrayBuilder neighborhood = Json.createArrayBuilder();
			JsonArrayBuilder categories = Json.createArrayBuilder();
			JsonArrayBuilder schools = Json.createArrayBuilder();
			
			for (String str : newRestaurant.getNeighborhoods()) {
				neighborhood.add(str);
			}
			
			for (String str : newRestaurant.getCategories()) {
				categories.add(str);
			}
			
			for (String str : newRestaurant.getSchools()) {
				schools.add(str);
			}
			
			JsonObject rest = Json.createObjectBuilder()
					.add("open", newRestaurant.getOpen())
					.add("url", newRestaurant.getUrl())
					.add("longitude", newRestaurant.getLongitude())
					.add("neighborhoods", neighborhood)
					.add("business_id", newRestaurant.getBusinessID())
					.add("name", newRestaurant.getName())
					.add("categories", categories)
					.add("state", newRestaurant.getState())
					.add("stars", newRestaurant.getStars())
					.add("city", newRestaurant.getCity())
					.add("full_address", newRestaurant.getAddress())
					.add("review_count", 0)
					.add("photo_url", newRestaurant.getPhotoURL())
					.add("schools", schools)
					.add("latitude", newRestaurant.getLatitude())
					.add("price", newRestaurant.getPrice())
					.add("type", newRestaurant.getType()).build();

			return rest.toString();
		}
		catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
	
	public String AddReview(String string) {//assume don't have to pass votes, text or date
		Review newReview = new Review(string);
		if (!business_IDs.contains(newReview.getBusinessID())) {
			throw new IllegalArgumentException();//should change this to a specific exception
		} else if (!user_IDs.contains(newReview.getUserID())) {
			throw new IllegalArgumentException(); //should change this too
		}
		JsonObject votes = Json.createObjectBuilder()
				.add("cool", newReview.getCoolVotes())
				.add("useful", newReview.getUsefulVotes())
				.add("funny", newReview.getFunnyVotes()).build();
		
		JsonObject rev = Json.createObjectBuilder()
				.add("type", newReview.getType())
				.add("business_id", newReview.getBusinessID())
				.add("votes", votes)
				.add("review_id", newReview.getReviewID())
				.add("text", newReview.getText())
				.add("stars", newReview.getNumStars())
				.add("user_id", newReview.getBusinessID())
				.add("date", newReview.getDate()).build();
		
		return rev.toString();
	}
	

}
