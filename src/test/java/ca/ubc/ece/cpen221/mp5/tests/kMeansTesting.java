package ca.ubc.ece.cpen221.mp5.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.ece.ubc.cpen221.mp5.database.YelpDB;
import ca.ece.ubc.cpen221.mp5.learning.KMeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

public class kMeansTesting {

	@Test
	public void test() {
		HashMap<Integer, List<Double>> thisMap = new HashMap<>();
		List<Double> zero = new ArrayList<>();
		zero.add((double) 1);
		zero.add((double) 0);
		List<Double> one = new ArrayList<>();
		one.add(0, (double) 2);
		one.add(1, (double) 7);
		List<Double> two = new ArrayList<>();
		two.add(0, (double) 3);
		two.add(1, (double) 9);
		List<Double> three = new ArrayList<>();
		three.add(0, (double) 4.1);
		three.add(1, (double) 5);
		List<Double> four = new ArrayList<>();
		four.add(0, (double) 7);
		four.add(1, (double) 2);

		thisMap.put(0, zero);
		thisMap.put(1, one);
		thisMap.put(2, two);
		thisMap.put(3, three);
		thisMap.put(4, four);

		thisMap.toString();
		KMeans.findKMeans(thisMap, 10);

	}

	@Test
	public void test1() throws IOException {
		YelpDB hello = new YelpDB("data/restaurants.json", "data/reviews.json", "data/users.json");
		hello.kMeansClusters_json(4);
	}

}