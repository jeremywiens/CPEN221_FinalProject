package ca.ece.ubc.cpen221.mp5.database;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class Review {

	private String text;
	private String review_id;
	private Integer stars;
	private HashMap<String, Integer> votes = new HashMap<String, Integer>();
	private String business_id;
	private String user_id;
	private String date;
	private String type;

	public Review(String Review) {
		JsonParser parser = Json.createParser(new StringReader(Review));
		String key = null;
		String value = null;
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
				if (key.equals("votes")) {
					this.votes.put(parser.getString(), null);
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
	}

	public String getType() {
		return this.type;
	}

	public String getBusinessID() {
		return this.business_id;
	}

	public HashMap getVotes() {
		return this.votes;
	}

	public int getCoolVotes() {
		return this.votes.get("cool");
	}
	
	public int getUsefulVotes()	{
		return this.votes.get("useful");
	}
	
	public int getFunnyVotes() {
		return this.votes.get("funny");
	}
	
	public String getReviewID() {
		return this.review_id;
	}
	
	public String getText() {
		return this.text;
	}
	
	public int getNumStars() {
		return this.stars;
	}
	
	public String getUserID() {
		return this.user_id;
	}
	
	public String getDate() {
		return this.date;
	}
	

}
