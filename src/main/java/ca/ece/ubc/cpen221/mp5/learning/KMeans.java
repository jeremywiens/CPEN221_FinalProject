package ca.ece.ubc.cpen221.mp5.learning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class KMeans {
	
	public static List<Set<Integer>> findKMeans(Map<Integer, List<Double>> thisMap, int k){
		Map<Integer, Integer> clusteredMap = DoKMeans(thisMap, k);
		List<Set<Integer>> clusteredList = new ArrayList<Set<Integer>>();
		for(int i = 0; i < k; i++) {
			Set<Integer> kCluster = new HashSet<>();
			for(int Restaurant : clusteredMap.keySet()) {
				if(clusteredMap.get(Restaurant) == i) {
					kCluster.add(Restaurant);
				}
			}
			clusteredList.add(kCluster);
		}
		return clusteredList;
	}
	
	// The maps parameter is integer which symbolizes the element and lists is x and
	// y coordinates
	// returns a map with integer which symolizes the element and second integer
	// symbolizes its cluster
	// Map<Integer, Object>
	private static Map<Integer, Integer> DoKMeans(Map<Integer, List<Double>> thisMap, int k) {
		HashMap<Integer, List<Double>> kPoints = getInitialPoints(thisMap, k);
		System.out.println(kPoints + "initial k");
		boolean change = true;
		HashMap<Integer, Integer> objectToCluster = new HashMap<>();
		// Initialize thisMap to put all points in the first cluster
		for (Integer o : thisMap.keySet())
			objectToCluster.put(o, 0);

		while (change) {
			// re-evaluate K
			// compare distances and move any that are closer to another
			change = false;
			for (Integer o : objectToCluster.keySet()) {
				int kCounter = 0;
				int kValue = 0;
				double previousDistance = 999999999;
				double thisDistance;
				double thisX = thisMap.get(o).get(0);
				double thisY = thisMap.get(o).get(1);

				for (Integer K : kPoints.keySet()) {
					thisDistance = getDistance(thisX, thisY, kPoints.get(K).get(0), kPoints.get(K).get(1));
					if (thisDistance < previousDistance) {
						kValue = kCounter;
						previousDistance = thisDistance;
					}
					kCounter++;
				}
				if (kValue != objectToCluster.get(o)) {
					change = true;
					objectToCluster.put(o, kValue);
				}
			}
			kPoints = reEvalKPoints(thisMap, objectToCluster, k, kPoints);
			System.out.println(kPoints);
		}
		return objectToCluster;
	}

	private static HashMap<Integer, List<Double>> reEvalKPoints(Map<Integer, List<Double>> thisMap,
			HashMap<Integer, Integer> objectToCluster, int k, HashMap<Integer, List<Double>> kPoints) {
		HashMap<Integer, List<Double>> newKPoints = kPoints;
		double weightedX;
		double weightedY;
		int count;

		for (int thisK = 0; thisK < k; thisK++) {
			count = 0;
			weightedX = 0;
			weightedY = 0;
			for (Integer object : objectToCluster.keySet()) {
				if (thisK == objectToCluster.get(object)) {
					weightedX = weightedX + thisMap.get(object).get(0);
					weightedY = weightedY + thisMap.get(object).get(1);
					count++;
				}
			}
			if (count > 0) {
				List<Double> kCoordinates = new ArrayList<Double>();
				kCoordinates.add(weightedX / count);
				kCoordinates.add(weightedY / count);
				newKPoints.put(thisK, kCoordinates);
			}
		}

		return newKPoints;
	}

	private static double getDistance(double thisX, double thisY, double kX, double kY) {
		double distance = 0;
		double difX = kX - thisX;
		double difY = kY - thisY;
		distance = Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2));

		return distance;
	}

	private static HashMap<Integer, List<Double>> getInitialPoints(Map<Integer, List<Double>> thisMap, int k) {
		HashMap<Integer, List<Double>> firstKPoints = new HashMap<Integer, List<Double>>();
		List<Double> firstPoint = thisMap.get(0);
		double minX = firstPoint.get(0);
		double minY = firstPoint.get(1);
		double maxX = minX;
		double maxY = minY;
		double currentX;
		double currentY;

		for (Integer i : thisMap.keySet()) {
			currentX = thisMap.get(i).get(0);
			currentY = thisMap.get(i).get(1);
			if (currentX <= minX)
				minX = currentX;
			if (currentY <= minY)
				minY = currentY;
			if (currentX >= maxX)
				maxX = currentX;
			if (currentY >= maxY)
				maxY = currentY;
		}

		Random rand = new Random();

		int n = rand.nextInt(50) + 1;
		// 50 is the maximum and the 1 is our minimum
		for (int i = 0; i < k; i++) {
			List<Double> kList = new ArrayList<>();
			kList.add(minX + (maxX - minX) * rand.nextDouble());
			kList.add(minY + (maxY - minY) * rand.nextDouble());
			firstKPoints.put(i, kList);
		}
		return firstKPoints;

	}

	/*
	 * private static HashMap<Integer, List<Double>> getInitialPoints(Map<Integer,
	 * List<Double>> thisMap, int k) { // First integer in this map identifies the
	 * cluster, the list is x and Y // coordinate HashMap<Integer, List<Double>>
	 * firstKPoints = new HashMap<Integer, List<Double>>(); List<Double> firstPoint
	 * = thisMap.get(0); double minX = firstPoint.get(0); double minY =
	 * firstPoint.get(1); double maxX = minX; double maxY = minY; double currentX;
	 * double currentY;
	 * 
	 * for (Integer i : thisMap.keySet()) { currentX = thisMap.get(i).get(0);
	 * currentY = thisMap.get(i).get(1); if (currentX <= minX) minX = currentX; if
	 * (currentY <= minY) minY = currentY; if (currentX >= maxX) maxX = currentX; if
	 * (currentY >= maxY) maxY = currentY; }
	 * 
	 * double stepSizeX = (maxX - minX) / k; double stepSizeY = (maxY - minY) / k;
	 * double initialX = minX + stepSizeX / 2; double initialY = minY + stepSizeY /
	 * 2;
	 * 
	 * for (int i = 0; i < k; i++) { List<Double> kList = new ArrayList<>();
	 * kList.add(initialX + i * stepSizeX); kList.add(initialY + i * stepSizeY);
	 * firstKPoints.put(i, kList); } return firstKPoints; }
	 */
}
