package ca.ece.ubc.cpen221.mp5.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * ParseJSON - is a class to parse the JSON Files for restaurants, users and
 * reviews. Lists of restaurants, users and reviews will be returned with
 * information based on the given JSON files.
 *
 */
public class ParseJSON {

	// Rep Invariant: Each of the following lists must not contain null values. All
	// objects should be properly formatted according to their object types.

	private static List<Restaurant> RestaurantList = new ArrayList<Restaurant>();
	private static List<User> UserList = new ArrayList<User>();
	private static List<Review> ReviewList = new ArrayList<Review>();
	private static List<Business> BusinessList = new ArrayList<Business>();

	/**
	 * Parse a JSON file containing information about restaurants. A list of these
	 * restaurants will be returned.
	 * 
	 * @param filename
	 *            is the file name and appropriate directory which contains
	 *            information about restaurants in a JSON format.
	 * @return A complete list of restaurants based on the given file.
	 * @throws IOException
	 *             if the file cannot be read.
	 */
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
	/**
	 * Parse a JSON file containing information about restaurants. A list of these
	 * restaurants will be returned.
	 * 
	 * @param filename
	 *            is the file name and appropriate directory which contains
	 *            information about restaurants in a JSON format.
	 * @return A complete list of restaurants based on the given file.
	 * @throws IOException
	 *             if the file cannot be read.
	 */
	public static List<Business> ParseBusiness(String filename) throws IOException {

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				Business thisBusiness = new Business(line);
				BusinessList.add(thisBusiness);
			}
			return BusinessList;
		}
	}

	/**
	 * Parse a JSON file containing information about reviews. A list of these
	 * reviews will be returned.
	 * 
	 * @param filename
	 *            is the file name and appropriate directory which contains
	 *            information about reviews in a JSON format.
	 * @return A complete list of reviews based on the given file.
	 * @throws IOException
	 *             if the file cannot be read.
	 */
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

	/**
	 * Parse a JSON file containing information about users. A list of these
	 * users will be returned.
	 * 
	 * @param filename
	 *            is the file name and appropriate directory which contains
	 *            users about reviews in a JSON format.
	 * @return A complete list of users based on the given file.
	 * @throws IOException
	 *             if the file cannot be read.
	 */
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
