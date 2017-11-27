package ca.ece.ubc.cpen221.mp5.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ParseJSON {

	// errors should be solved once types have been created.
	private static List<Restaurant> RestaurantList = new ArrayList<Restaurant>();
	private static List<User> UserList = new ArrayList<User>();
	private static List<Review> ReviewList = new ArrayList<Review>();

	public static List<Restaurant> ParseRestaurant(String filename) throws IOException {

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				Restaurant thisRestaurant = new Restaurant(line);
				RestaurantList.add(thisRestaurant);
			}
			return RestaurantList;
		}
	}

	public static List<Review> ParseReview(String filename) throws IOException {

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				Review thisReview = new Review(line);
				ReviewList.add(thisReview);
			}
			return ReviewList;
		}
	}
	
	public static List<User> ParseUser(String filename) throws IOException {

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				User thisUser = new User(line);
				UserList.add(thisUser);
			}
			return UserList;
		}
	}
}
