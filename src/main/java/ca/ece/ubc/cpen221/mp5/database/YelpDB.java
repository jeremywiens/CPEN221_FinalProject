package ca.ece.ubc.cpen221.mp5.database;

import java.util.List;
import java.util.Set;
import java.util.function.ToDoubleBiFunction;

import java.io.*;

public class YelpDB implements MP5Db {

	private List<Object> restaurants;
	private List<Object> reviews;
	private List<Object> users;

	// Maybe we should change the order so users are created first? Then every new
	// review object can be added to the user who wrote it?
	public YelpDB(String restaurants_file, String reviews_file, String users_file) throws IOException {
		restaurants = ParseJSON.ParseFile(restaurants_file, 1);
		reviews = ParseJSON.ParseFile(reviews_file, 2);
		users = ParseJSON.ParseFile(users_file, 3);
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
