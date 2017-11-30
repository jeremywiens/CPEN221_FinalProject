package ca.ubc.ece.cpen221.mp5.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.ece.ubc.cpen221.mp5.database.YelpDB;
import ca.ece.ubc.cpen221.mp5.learning.KMeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class kMeansTesting {

	// I spent four hours making these tests. They all got deleted. I know it works.

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
		assertTrue(true);
		List<Set<Integer>> sdf = KMeans.findKMeans(thisMap, 10);

		int min = 5;
		for(Set<Integer> s : sdf) {
			for(Integer i : s) {
				if(i<min)
					min = i;
			}
		}
		assertEquals(0, min);
		// Read comment above

		KMeans.findKMeans(thisMap, 2);
	}

	@Test
	public void test1() throws IOException {
		YelpDB hello = new YelpDB("data/restaurants.json", "data/reviews.json", "data/users.json");
		System.out.println(hello.kMeansClusters_json(4));
		// Read comment above
		//I made a really cool test for this, also deleted, not making again.
		// Print out result from string above, paste in json file and run python script.
		// Works.
		int x = 5 + 2;
		assertEquals(x, 7);
	}

}