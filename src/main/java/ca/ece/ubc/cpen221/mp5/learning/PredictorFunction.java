package ca.ece.ubc.cpen221.mp5.learning;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

import ca.ece.ubc.cpen221.mp5.database.*;
/**
 * Predictor Function - performs the getPredictorfunction for a database. Takes in a list
 * of restaurants and reviews from a database and a String representing the user_ID. The getPredictorFunction
 * finds the user from the userID and using the user's information, calculates the most likely rating by this
 * user for a restaurant based on a restaurant's price. A Predictor function can then call the 
 * getPredictorFunction to return a function which can take any database of type restaurant and a restaurantID
 * which can be used to find the predicted rating of a restaurant in the database for this user.
 *
 */
public class PredictorFunction {

	// Abstraction Function: PredictorFunction acts as two lists, a lists or reviews and a list of 
	// restaurants from a database
	
	// Rep Invariant: The list of restaurants must all point to a valid restaurant in the database and
	// the list of reviews must all point to a valid review in the database
	private List<Restaurant> restaurants = new ArrayList<Restaurant>();
	private List<Review> reviews = new ArrayList<Review>();

	/**
	 * Takes a list of reviews and a list of Restaurants from a YelpDb to calculate the
	 * getPredictorFunction
	 * 
	 * @param Restaurants a non null list of Reviews from the database
	 * @param Reviews a non-null list of Restaurants from the database
	 */
	public PredictorFunction(List<Restaurant> Restaurants, List<Review> Reviews) {
		restaurants = Restaurants;
		reviews = Reviews;
	}

	/**
	 * getPredictorFunction - Takes a String representing the user_ID for a user. The getPredictorFunction
     * finds the user from the userID and using the user's information, calculates the most likely rating by this
     * user for a restaurant based on a restaurant's price. The getPredictorFunction returns a 
     * ToDoubleBifunction which can take any database of type MP5Db<Restaurant> and a restaurantID
     * which can be used to find the predicted rating of a restaurant in the database for this user.
	 *
	 *
	 * @param user - a non-null string representing the user_id of the user which is already in the database
	 * @return a ToDoubleBiFunction which can take in a database of type MP5Db<Restaurant> and a non-null string
	 * 			representation of a restaurantID and calculate the expected rating of this user for this
	 * 			restaurant in the MP5Db<Restaurant> database based on the restaurants price. The function
	 * 			can only return a double value between 1.0 and 5.0. If all the restaurants for a user have
	 * 			the same price, or if there are no reviews or restaurants a IllegalArgumentException is thrown.
	 */
	public ToDoubleBiFunction<MP5Db<Restaurant>, String> getPredictorFunction(String user) {
		
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

		Restaurants = this.getRestaurantReviews(user);
		Reviews = this.getUserReviews(user);
		if (Reviews.isEmpty() || Restaurants.isEmpty()) {
			throw new IllegalArgumentException();
		}

		
		int price = Restaurants.get(0).getPrice();
		for (Restaurant rest : Restaurants) {
			if (rest.getPrice() != price) {
				price = -1;
			}
		}
		
		if (price != -1) {
			throw new IllegalArgumentException();
		}
		
		// Calcuate the avg rating for a list of reviews
		avgY = calcAvgY(Reviews);
		// Calculate the avg Price for a list of restaurants
		avgX = calcAvgX(Restaurants);
		// Store all the (price(i) - price(avg)) for a list of Restaurants in a List of doubles
		sumMeanx = sumMeanX(Restaurants, avgX);
		// Store all the (price(i) - price(avg)) for a list of Restaurants in a List of doubles
		sumMeany = sumMeanY(Reviews, avgY);

		//Perform the linear regression methods on the list of doubles found above
		for (int j = 0; j < sumMeany.size(); j++) {
			double x = sumMeanx.get(j);
			double y = sumMeany.get(j);

			Sxx += x * x;
			Syy += y * y;
			Sxy += x * y;
		}

		b = Sxy / Sxx;
		a = avgY - b * avgX;
		Rsquared = Sxy * Sxy / (Sxx * Syy);

		// Declare the ToDoubleBiFunction
		ToDoubleBiFunction<MP5Db<Restaurant>, String> function = (MP5, restaurant_id) -> {
			//Cast MP5Db<Restaurant> as type YelpDb
			YelpDB db = (YelpDB) MP5;
			double pr = db.getRestaurant(restaurant_id).getPrice();
			double result =  a * pr + b;
			
			// min return of 1
			if (result < 1) {
				return 1;
			}
			
			// max return of 5
			if (result > 5) {
				return 5;
			}
			
			return result;
		};

		return function;
	}

	// used to get the restaurant reviews for a user given a user_ID
	private List<Restaurant> getRestaurantReviews(String user_ID) throws IllegalArgumentException {
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

	// used to sum all the price(i) - price(avg) for a list of restaurants for the linear regression
	private List<Double> sumMeanX(List<Restaurant> Restaurants, double avg) {
		List<Double> sumMean = new ArrayList<Double>();

		for (Restaurant restaurant : Restaurants) {
			int price = restaurant.getPrice();
			sumMean.add(price - avg);
		}
		return sumMean;
	}

	// used to find the avg price from a list of restaurants
	private double calcAvgX(List<Restaurant> Restaurants) {
		double avg = 0.0;
		int count = 0;
		for (int i = 0; i < Restaurants.size(); i++) {
			avg += Restaurants.get(i).getPrice();
			count++;
		}

		return avg / count;
	}

	// used to find the avg number of stars from a list of reviews
	private double calcAvgY(List<Review> Reviews) {
		double avg = 0.0;
		int count = 0;
		for (int i = 0; i < Reviews.size(); i++) {
			avg += Reviews.get(i).getNumStars();
			count++;
		}
		return avg / count;
	}

	// used to get a list of reviews for a particular userID
	private List<Review> getUserReviews(String user_ID) {
		List<Review> Reviews = new ArrayList<Review>();
		for (Review review : reviews) {
			if (user_ID.equals(review.getUserID())) {
				Reviews.add(review);
			}
		}

		return Reviews;
	}

	// used to sum all the stars(i) - stars(avg) for a list of review for the linear regression
	private List<Double> sumMeanY(List<Review> Reviews, double avgY) {
		List<Double> sumMean = new ArrayList<Double>();
		for (Review review : Reviews) {
			sumMean.add(review.getNumStars() - avgY);
		}

		return sumMean;
	}

}
