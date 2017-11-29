package ca.ece.ubc.cpen221.mp5.learning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * KMeans - Performs the KMeans clustering algorithm. Random points which are
 * clusters, will be within the domain and range of the given points with x and
 * y coordinates. Each point will be assigned to the closest cluster point, then
 * the clusters will re-evaluate their position to be be in the middle of their
 * cluster. After re-evaluation, given points will re-determine which cluster
 * point. This will iterate until the clusters have no longer changed, the
 * result will be that each given point will be closest to its associated
 * cluster point. In effect, clusters will be formed of the given points based
 * on their position.
 *
 */
public class KMeans {

	/**
	 * FindKMeans requires a map of Integers to x and y coordinates. The integers
	 * can represent any object which will be clustered in 2 dimensions. This method
	 * returns a list of sets which are the various clusters from the given map.
	 *
	 * @param thisMap
	 *            is the map which will be clustered. The key values are Integers
	 *            which resemble each individual point, the list of doubles, which
	 *            are the values, has it's x coordinate at the first index and y
	 *            coordinate at the second index. All other numbers are ignored.
	 * @param k
	 *            is the number of clusters to be formed.
	 * @return Returns a list of sets of Integers, where each Integer is the same
	 *         Integer from the given map. Each set is a cluster.
	 */
	public static List<Set<Integer>> findKMeans(Map<Integer, List<Double>> thisMap, int k) {
		// Map each integer to integers which are it's cluster number.
		Map<Integer, Integer> clusteredMap = DoKMeans(thisMap, k);

		// Create the sets based on its map. Each cluster number from clusteredMap will
		// create a new set in this list.
		List<Set<Integer>> clusteredList = new ArrayList<Set<Integer>>();
		for (int i = 0; i < k; i++) {
			Set<Integer> kCluster = new HashSet<>();
			for (int Restaurant : clusteredMap.keySet()) {
				if (clusteredMap.get(Restaurant) == i) {
					kCluster.add(Restaurant);
				}
			}
			clusteredList.add(kCluster);
		}
		return clusteredList;
	}

	/**
	 * DoKMeans is the heart of this algorithm. It's purpose is to re-evaluate the
	 * clusters to which each point is in. It will call the appropriate methods to
	 * do so.
	 * 
	 * @param thisMap
	 *            is the same map from findKMeans. thisMap is the map which will be
	 *            clustered. The key values are Integers which resemble each
	 *            individual point, the list of doubles, which are the values, has
	 *            it's x coordinate at the first index and y coordinate at the
	 *            second index. All other numbers are ignored.
	 * @param k
	 *            is the number of clusters to be formed.
	 * @return A map which maps the given Integers to Integers which will be the
	 *         cluster values. If K clusters are to be found. Their will be K
	 *         distinct integer values in this map, each resembling a cluster.
	 */
	private static Map<Integer, Integer> DoKMeans(Map<Integer, List<Double>> thisMap, int k) {
		// Get random inital points for each cluster k.
		HashMap<Integer, List<Double>> kPoints = getInitialPoints(thisMap, k);
		boolean change = true;

		HashMap<Integer, Integer> objectToCluster = new HashMap<>();
		// Initialize thisMap to put all points in the first cluster
		for (Integer o : thisMap.keySet())
			objectToCluster.put(o, 0);

		// Keep performing the algorithm of finding new cluster points, until no point
		// changes its cluster
		while (change) {
			change = false;
			for (Integer o : objectToCluster.keySet()) {
				int kCounter = 0;
				int kValue = 0;
				double previousDistance = 999999999;
				double thisDistance;

				// Get X-coordinate
				double thisX = thisMap.get(o).get(0);
				// Get Y-coordinate
				double thisY = thisMap.get(o).get(1);

				// compare distances and move any that are closer to another
				for (Integer K : kPoints.keySet()) {
					thisDistance = getDistance(thisX, thisY, kPoints.get(K).get(0), kPoints.get(K).get(1));
					if (thisDistance < previousDistance) {
						kValue = kCounter;
						previousDistance = thisDistance;
					}
					// Keep track of which cluster value we are considering.
					kCounter++;
				}
				// Re-assigns each object to its cluster
				if (kValue != objectToCluster.get(o)) {
					change = true;
					objectToCluster.put(o, kValue);
				}
			}
			kPoints = reEvalKPoints(thisMap, objectToCluster, k, kPoints);
		}
		return objectToCluster;
	}

	/**
	 * Re-evaluate the K-Point coordinates. Each K-point becomes the centroid of all
	 * the objects which are in it's cluster. Coordinates will be unchanged if the
	 * centroid remains the same.
	 * 
	 * @param thisMap
	 *            is the same map from findKMeans. thisMap is the map which will be
	 *            clustered. The key values are Integers which resemble each
	 *            individual point, the list of doubles, which are the values, has
	 *            it's x coordinate at the first index and y coordinate at the
	 *            second index. All other numbers are ignored.
	 * @param objectToCluster
	 *            A map which maps the given Integers (which represent objects) to
	 *            Integers which will be the cluster values. If K clusters are to be
	 *            found. Their will be K distinct integer values in this map, each
	 *            resembling a cluster.
	 * @param k
	 *            is the number of clusters to be formed.
	 * @param kPoints
	 *            kPoints is the map of the current K-points and their coordinates.
	 * @return Returns the re-evaluated K-points. Each k-point is the centroid of
	 *         all the objects which are in it's cluster. If a K points coordinate
	 *         does not change, it will be returned the same value as it was passed.
	 */
	private static HashMap<Integer, List<Double>> reEvalKPoints(Map<Integer, List<Double>> thisMap,
			HashMap<Integer, Integer> objectToCluster, int k, HashMap<Integer, List<Double>> kPoints) {
		HashMap<Integer, List<Double>> newKPoints = kPoints;
		double weightedX;
		double weightedY;
		int count;

		for (int thisK = 0; thisK < k; thisK++) {
			// Keeps track of whether or not the K-point is re-evaluated.
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

	/**
	 * Evaluates the distance between this K-point and the given point.
	 * 
	 * @param thisX
	 *            is the X-coordinate of given point.
	 * @param thisY
	 *            is the Y-coordinate of given point.
	 * @param kX
	 *            is the X-coordinate of the k-point
	 * @param kY
	 *            is the Y-coordinate of the k-point
	 * @return The distance between the two points
	 */
	private static double getDistance(double thisX, double thisY, double kX, double kY) {
		double distance = 0;
		double difX = kX - thisX;
		double difY = kY - thisY;
		distance = Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2));

		return distance;
	}

	/**
	 * Determines random initial x and y coordinates for the clusters for this
	 * KMeans algorithm. These initial points are random, however the will be within
	 * the domain and range of the given points.
	 * 
	 * @param thisMap
	 *            is the same map from findKMeans. thisMap is the map which will be
	 *            clustered. The key values are Integers which resemble each
	 *            individual point, the list of doubles, which are the values, has
	 *            it's x coordinate at the first index and y coordinate at the
	 *            second index. All other numbers are ignored.
	 * @param k
	 *            is the number of clusters to be formed.
	 * @return A map of Integers, which represent each k point. These integers map
	 *         to a list of Doubles which are the initial X and Y coordinates for
	 *         this instance of KMeans algorithm.
	 */
	private static HashMap<Integer, List<Double>> getInitialPoints(Map<Integer, List<Double>> thisMap, int k) {
		HashMap<Integer, List<Double>> firstKPoints = new HashMap<Integer, List<Double>>();
		List<Double> firstPoint = thisMap.get(0);
		double minX = firstPoint.get(0);
		double minY = firstPoint.get(1);
		double maxX = minX;
		double maxY = minY;
		double currentX;
		double currentY;

		// Determine the max and minimum x coordinates and max and min y coordinates.
		// Determines the domain and range
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

		// Find random points within this domain and range
		for (int i = 0; i < k; i++) {
			List<Double> kList = new ArrayList<>();
			kList.add(minX + (maxX - minX) * rand.nextDouble());
			kList.add(minY + (maxY - minY) * rand.nextDouble());
			firstKPoints.put(i, kList);
		}
		return firstKPoints;
	}

}
