package ca.ubc.ece.cpen221.mp5.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.function.ToDoubleBiFunction;

import org.junit.Test;

import ca.ece.ubc.cpen221.mp5.database.MP5Db;
import ca.ece.ubc.cpen221.mp5.database.Restaurant;
import ca.ece.ubc.cpen221.mp5.database.YelpDB;

public class PredictorTests {

	@Test
	public void test() throws IOException{
		try {
			YelpDB yelped = new YelpDB("data/restaurants.json", "data/reviews.json", "data/users.json");
			ToDoubleBiFunction<MP5Db<Restaurant>, String> predict = yelped.getPredictorFunction("Rto4xWr5gXA2IbrfyAn-Xg");
			predict.applyAsDouble(yelped, "gclB3ED6uk6viWlolSb_uA");
			predict = yelped.getPredictorFunction("billy");
			
		} catch (Exception e) {}
		
	}
	
	@Test
	public void test1() throws IOException {
		try {
			YelpDB yelped = new YelpDB("data/restaurants.json", "data/reviews.json", "data/users.json");
			ToDoubleBiFunction<MP5Db<Restaurant>, String> predict = yelped.getPredictorFunction("9fMogxnnd0m9_FKSi-4AoQ");
		} catch (Exception e) {}
	}

}
