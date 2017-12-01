package ca.ece.ubc.cpen221.mp5.database;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
/**
 * Review - This is a representation of a review in a database made by a user for a product.
 * It takes in a JSON formatted string and parses it to store all the information in a review.
 * A review has various methods which can be called to obtain various information for
 * each instance of the review.
 */
public class Review {
	// Abstraction function: Review exists as a combination of strings representing the text,
	// review_id, business_id, user_id, date and type. It also includes an integer representing the number
	// of stars, and a hashmap storing the votes values

	// Rep Invariant: Each review must have a text associated to it which is non-null string
	private String text = null;
	// must have a unique review_id which is  a non-null string
	private String review_id = null;
	// must have an integer number of stars >= 0 and <= 5
	private Integer stars = null;
	// must have a list of votes: "funny", "cool" and "useful" where each type of vote has an integer
	// associated to it which is >= 0
	private HashMap<String, Integer> votes = new HashMap<String, Integer>();
	// must have a business ID which is a non-null string that is not null and is a real business ID
	// in the database
	private String business_id = null;
	// must have a user ID which is a non-null string that is not null and is a real business ID 
	// in the database
	private String user_id = null;
	// must have a date which is in yyyy-mm-dd format represented by a string
	private String date = "2017-12-02";
	// must be of type review represented by a non-null string
	private String type = "review";

	
	/**
	 * Review Constructor - creates a Review from a String in proper JSON Format which must 
	 * contain all Review information excluding date, type, text and votes. If date is not included it
	 * is assumed to be "2017-12-02". If type is not included it is defaulted to "review", if text is not
	 * included it is defaulted to the empty string "". If votes is not included, all values of "cool",
	 * "useful", and "funny" are assigned the value 0. If business_id, user_id, stars, or review_id is null,
	 * the constructor throws an IllegalArgumentException. All other information in JSON format will
	 * be ignored.
	 * 
	 * @param Restaurant
	 * 			JSON String containing Restaurant information excluding type, date, text, and votes.
	 * 			The string must be in proper JSON format. For example: {"type": "review", 
	 * 			"business_id": "1CBs84C-a-cuA3vncXVSAw", "votes": {"cool": 0, "useful": 0, "funny": 0}, 
	 * 			"review_id": "0a-pCW4guXIlWNpVeBHChg", "text": "The pizza is terrible, but if you need a 
	 * 			place to watch a game or just down some pitchers, this place works.\n\nOh, and the pasta is 
	 * 			even worse than the pizza.", "stars": 2, "user_id": "90wm_01FAIqhcgV_mPON9Q", 
	 * 			"date": "2006-07-26"}
	 */
	public Review(String Review) {
		JsonParser parser = Json.createParser(new StringReader(Review));
		String key = null;
		while (parser.hasNext()) {
			final Event event = parser.next();
			switch (event) {
			case KEY_NAME:
				key = parser.getString();
				break;
			case VALUE_STRING:
				if (key.equals("type")) {
					this.type = parser.getString();
				}
				if (key.equals("business_id")) {
					this.business_id = parser.getString();
				}
				if (key.equals("review_id")) {
					this.review_id = parser.getString();
				}
				if (key.equals("text")) {
					this.text = parser.getString();
				}
				if (key.equals("user_id")) {
					this.user_id = parser.getString();
				}
				if (key.equals("date")) {
					this.date = parser.getString();
				}
				break;
			case VALUE_NUMBER:
				if (key.equals("cool")) {
					this.votes.put(key, parser.getInt());
				}

				if (key.equals("useful")) {
					this.votes.put(key, parser.getInt());
				}
				if (key.equals("funny")) {
					this.votes.put(key, parser.getInt());
				}
				if (key.equals("stars")) {
					this.stars = parser.getInt();
				}
				break;
			case VALUE_TRUE:
				break;
			case VALUE_FALSE:
				break;
			}
		}
		parser.close();

		if (this.text == null) {
			text = "";		
		}
		if (votes.isEmpty()) {
			votes.put("funny", 0);
			votes.put("useful", 0);
			votes.put("cool", 0);
		}
		if (Null(this.review_id, this.business_id, this.user_id) || stars < 0 || 
				stars > 5){
			throw new IllegalArgumentException(); //change this exception
		}
		
		if (!type.equals("review")) {
			throw new IllegalArgumentException();
		}
	}
	
	private boolean Null(Object... args) {
		boolean Null = false;
		for (Object arg : args) {
			if (arg == null) {
				return true;
			}
		}
		return Null;
	}

	/**
	 * getType returns the type of this review
	 * 
	 * @return the type of the this review as a string
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * getType returns the business_id for the business this review is for
	 * 
	 * @return  a string of the business_id for this review
	 */
	public String getBusinessID() {
		return this.business_id;
	}

	/**
	 * getType returns the information of the votes for this review
	 * 
	 * @return a HashMap<String, Integer> where the String is "funny", "useful" or "cool" and the 
	 * 			integer is the value for type of vote
	 */
	public HashMap<String, Integer> getVotes() {
		return this.votes;
	}

	/**
	 * getCoolVotes returns the number of cool votes for this review
	 * 
	 * @return an integer of the number of cool votes for this review
	 */
	public int getCoolVotes() {
		return this.votes.get("cool");
	}

	/**
	 * getUsefulVotes returns the number of useful votes for this review
	 * 
	 * @return an integer of the number of useful votes for this review
	 */
	public int getUsefulVotes() {
		return this.votes.get("useful");
	}

	/**
	 * getFunnyVotes returns the number of funny votes for this review
	 * 
	 * @return an integer of the number of funny votes for this review
	 */
	public int getFunnyVotes() {
		return this.votes.get("funny");
	}

	/**
	 * getReviewID returns the review_id for this review
	 * 
	 * @return  a string of the review_id for this review
	 */
	public String getReviewID() {
		return this.review_id;
	}

	/**
	 * getText returns the text portion of this review
	 * 
	 * @return  a string of the text associated with this review
	 */
	public String getText() {
		return this.text;
	}

	public int getNumStars() {
		return this.stars;
	}

	/**
	 * getType returns the user_id for the user who created this review
	 * 
	 * @return  a string of the business_id for this review
	 */
	public String getUserID() {
		return this.user_id;
	}

	/**
	 * getDate returns the date when this review was made in yyyy-mm-dd format
	 * 
	 * @return  a string of the date for this review in yyy-mm-dd format
	 */
	public String getDate() {
		return this.date;
	}
	
	/**
	 * toString() returns the information contained in this review back into JSON string format.
	 * For example: {"type": "review", "business_id": "1CBs84C-a-cuA3vncXVSAw", 
	 * "votes": {"cool": 0, "useful": 0, "funny": 0}, "review_id": "0a-pCW4guXIlWNpVeBHChg", 
	 * "text": "Great pizza!", "stars": 2, "user_id": "90wm_01FAIqhcgV_mPON9Q", "date": "2006-07-26"}
	 * 
	 * @return  a string representation of this review in JSON string format
	 */
	@Override
	public String toString() {
		JsonObject Votes = Json.createObjectBuilder()
				.add("cool", this.getCoolVotes())
				.add("useful", this.getUsefulVotes())
				.add("funny", this.getFunnyVotes()).build();

		JsonObject rev = Json.createObjectBuilder()
				.add("type", this.type)
				.add("business_id", this.business_id)
				.add("votes", Votes)
				.add("review_id", this.review_id)
				.add("text", this.text)
				.add("stars", this.stars)
				.add("user_id", this.user_id)
				.add("date", this.date).build();
		
		return rev.toString();
	}
	
	/**
	 * changeID - changes the reviewID for this review to the newID provided
	 * 
	 * @param newID must be a non-null string
	 */
	public void changeID(String newID) {
		this.review_id = newID;
	}

}
