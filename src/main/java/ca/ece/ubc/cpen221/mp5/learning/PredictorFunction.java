package ca.ece.ubc.cpen221.mp5.learning;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

import ca.ece.ubc.cpen221.mp5.database.*;

public class PredictorFunction {

	private List<Restaurant> restaurants = new ArrayList<Restaurant>();
	private List<Review> reviews = new ArrayList<Review>();

	public PredictorFunction(List<Restaurant> Restaurants, List<Review> Reviews) {
		restaurants = Restaurants;
		reviews = Reviews;
	}

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
		avgY = calcAvgY(Reviews);// this assumes passing a user ID to getPredictorFunction
		avgX = calcAvgX(Restaurants);
		sumMeanx = sumMeanX(Restaurants, avgX);
		sumMeany = sumMeanY(Reviews, avgY); // based on avgY assumption

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

		ToDoubleBiFunction<MP5Db<Restaurant>, String> function = (MP5, restaurant_id) -> {
			YelpDB db = (YelpDB) MP5;
			double pr = db.getRestaurant(restaurant_id).getPrice();
			return a * pr + b;
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

	public List<Double> sumMeanX(List<Restaurant> Restaurants, double avg) {
		List<Double> sumMean = new ArrayList<Double>();

		for (Restaurant restaurant : Restaurants) {
			int price = restaurant.getPrice();
			sumMean.add(price - avg);
		}
		return sumMean;
	}

	public double calcAvgX(List<Restaurant> Restaurants) {
		double avg = 0.0;
		int count = 0;
		for (int i = 0; i < Restaurants.size(); i++) {
			avg += Restaurants.get(i).getPrice();
			count++;
		}

		return avg / count;
	}

	public double calcAvgY(List<Review> Reviews) {
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

	public List<Double> sumMeanY(List<Review> Reviews, double avgY) {
		List<Double> sumMean = new ArrayList<Double>();
		for (Review review : Reviews) {
			sumMean.add(review.getNumStars() - avgY);
		}

		return sumMean;
	}

}
