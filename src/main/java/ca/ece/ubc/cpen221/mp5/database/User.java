package ca.ece.ubc.cpen221.mp5.database;

import java.io.StringReader;
import java.util.HashMap;


import javax.json.*;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

/**
 * User - This is a representation of a user in a database which can make reviews for different products.
 * It takes in a JSON formatted string and parses it to store all the information for a user.
 * A user has various methods which can be called to obtain various information for
 * each instance of the user.
 */
public class User {
	// Abstraction Function: A User exists as a combination of strings representing the name of the user
	// the user_id, the type, and the url for the user. It also consists of a hashmap storing the votes
	// values, an int representing the review_count and a double value representing the average stars
	// for the user.
	
	// Rep Invariant: Each review has a review count which is an int greater than 0
	// and the each user review must be a valid review in the database
	private int review_count = 0;
	// must have a non-null string representing the name of the user
	private String name;
	// must have integer values for "funny", "cool" and "useful" votes, all of which must be greater than 0
	private HashMap<String, Integer> votes = new HashMap<String, Integer>();
	// must have a unique user_id represented by a non-null string
	private String user_id = null;
	// must have a type of user
	private String type = "user";
	// must have a url which is a valid webpage representing this user
	private String url = null;
	// must have an average number of stars between 0.0 and 5.0 inclusive
	private double average_stars = 0.0;

	
	/**
	 * User Constructor - creates a user from a String in proper JSON Format which must 
	 * contain only the name of the user at a minimum. If type is not included, it is defaulted to "user".
	 * If average_stars is not included it is defaulted to 0.0. If review_count is not included it is
	 * defaulted to 0. If the votes are not included, they are all defaulted to 0. If the type is not "user"
	 * an IllegalArgumentException is thrown and if the name is not included an IllegalArgumentException is
	 * thrown. If extra information in JSON format is included, it is ignored.
	 * 
	 * @param User
	 * 			JSON String containing User information.
	 * 			The string must be in proper JSON format. For example: 
	 * 			{"url": "http://www.yelp.com/user_details?userid=_NH7Cpq3qZkByP5xR4gXog", "votes": 
	 * 			{"funny": 35, "useful": 21, "cool": 14}, "review_count": 29, "type": "user", "user_id": 
	 *			"_NH7Cpq3qZkByP5xR4gXog", "name": "Chris M.", "average_stars": 3.89655172413793}
	 */
	public User(String User) {
		JsonParser parser = Json.createParser(new StringReader(User));
		String key = null;
		while (parser.hasNext()) {
			final Event event = parser.next();
			switch (event) {
			case KEY_NAME:
				key = parser.getString();
				break;
			case VALUE_STRING:
				if ("url".equals(key)) {
					this.url = parser.getString();
				}
				if ("user_id".equals(key)) {
					this.user_id = parser.getString();
				}
				if ("name".equals(key)) {
					this.name = parser.getString();
				}
				if ("type".equals(key)) {
					this.type = parser.getString();
				}
				break;
			case VALUE_NUMBER:
				if ("funny".equals(key)) {
					this.votes.put(key, parser.getInt());
				}
				if ("useful".equals(key)) {
					this.votes.put(key, parser.getInt());
				}
				if ("cool".equals(key)) {
					this.votes.put(key, parser.getInt());
				}
				if ("review_count".equals(key)) {
					this.review_count = parser.getInt();
				}
				if ("average_stars".equals(key)) {
					this.average_stars = parser.getBigDecimal().doubleValue();
				}
				break;
			case VALUE_TRUE:
				break;
			case VALUE_FALSE:
				break;
			}
		}
		parser.close();
		if (name == null) {
			throw new IllegalArgumentException("ERR: INVALID_USER_STRING");
		}
		if (votes.isEmpty()) {
			votes.put("funny", 0);
			votes.put("useful", 0);
			votes.put("cool", 0);
		}
		if (!type.equals("user")) {
			throw new IllegalArgumentException("ERR: INVALID_USER_STRING");
		}
		if (url == null) {
			url = "http://www.yelp.com/user_details?userid=" + user_id;
		}
	}

	/**
	 * getUrl returns the url of this user
	 * 
	 * @return the url of the this user as a string
	 */
	public String getUrl() {
		return this.url;
	}

	public HashMap<String, Integer> getVotes() {
		return this.votes;
	}

	/**
	 * getFunnyVotes returns the number of funny votes for this user
	 * 
	 * @return the number of funny votes for this user as an integer
	 */
	public int getFunnyVotes() {
		return (int) this.votes.get("funny");
	}

	/**
	 * getUsefulVotes returns the number of useful votes for this user
	 * 
	 * @return the number of useful votes for this user as an integer
	 */
	public int getUsefulVotes() {
		return (int) this.votes.get("useful");
	}

	/**
	 * getUsefulVotes returns the number of cool votes for this user
	 * 
	 * @return the number of cool votes for this user as an integer
	 */
	public int getCoolVotes() {
		return (int) this.votes.get("cool");
	}

	/**
	 * getNumReviews returns the number of reviews for this user
	 * 
	 * @return the number of reviews for this user as an integer
	 */
	public int getNumReviews() {
		return this.review_count;
	}

	/**
	 * getType returns the type of this user
	 * 
	 * @return the type of the this user as a string
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * getUserID returns the userID of this user
	 * 
	 * @return the userID of the this user as a string
	 */
	public String getUserID() {
		return this.user_id;
	}

	/**
	 * getUrl returns the name of this user
	 * 
	 * @return the name of the this user as a string
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * getAvgStars returns the average stars for the reviews given by this user
	 * 
	 * @return the average stars of the this user as a double value
	 */
	public double getAvgStars() {
		return this.average_stars;
	}
	
	/**
	 * toString() returns the information contained for this user back into JSON string format.
	 * For example: {"url": "http://www.yelp.com/user_details?userid=_NH7Cpq3qZkByP5xR4gXog", 
	 * "votes": {"funny": 35, "useful": 21, "cool": 14}, "review_count": 29, "type": "user", 
	 * "user_id": "_NH7Cpq3qZkByP5xR4gXog", "name": "Chris M.", "average_stars": 3.89655172413793}
	 * 
	 * @return  a string representation for this user in JSON string format
	 */
	@Override
	public String toString() {
		JsonObject Votes = Json.createObjectBuilder()
				.add("funny", this.getFunnyVotes())
				.add("useful", this.getUsefulVotes())
				.add("cool", this.getCoolVotes()).build();

		JsonObject user = Json.createObjectBuilder()
				.add("url", this.url)
				.add("votes", Votes)
				.add("review_count", this.review_count)
				.add("type", "user")
				.add("user_id", this.user_id)
				.add("name", this.name)
				.add("average_stars", this.average_stars).build();
		return user.toString();
	}
	
	/**
	 * changeID - changes the userID for this user to the newID provided
	 * 
	 * @param newID must be a non-null string
	 */
	public void changeID(String newID) {
		user_id = newID;
		url = "http://www.yelp.com/user_details?userid=" + user_id;
	}
	
	/**
	 * addReview - calculates the new average star rating for this user with the number of stars passed.
	 * It increases the review_count of this user by one and adds the funny, useful, and cool votes
	 * for that review to the users total. 
	 * 
	 * 
	 * @param addStars must be an int between 1 and 5 inclusive
	 * @param funny must be an int >= 0
	 * @param useful must be an int >= 0
	 * @param cool must be an in >= 0
	 */
	public void addReview(int addStars, int funny, int useful, int cool)  {
		this.average_stars = (this.average_stars*this.review_count + addStars)/++this.review_count;
		this.votes.put("funny", this.getFunnyVotes() + funny);
		this.votes.put("cool", this.getCoolVotes() + cool);
		this.votes.put("useful", this.getUsefulVotes() + useful);
	}
}
