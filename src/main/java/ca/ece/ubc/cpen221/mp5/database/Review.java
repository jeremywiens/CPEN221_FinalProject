package ca.ece.ubc.cpen221.mp5.database;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class Review {

	Date today = Calendar.getInstance().getTime();
	private String text = null;
	private String review_id = null;
	private Integer stars = null;
	private HashMap<String, Integer> votes = new HashMap<String, Integer>();
	private String business_id = null;
	private String user_id = null;
	private String date = "2017-12-02";
	private String type = null;
	private List<String> reviewIDs = new ArrayList<String>();

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

		if (this.text == null) {
			text = "";		
		}
		if (votes.isEmpty()) {
			votes.put("funny", 0);
			votes.put("useful", 0);
			votes.put("cool", 0);
		}
		if (notNull(this.review_id, this.business_id, this.type, this.user_id)){
			throw new IllegalArgumentException(); //change this exception
		}
	}
	
	private boolean notNull(Object... args) {
	    for (Object arg : args) {
	        if (arg != null) {
	            return false;
	        }
	        else {System.out.println("hello");}
	    }
	    return true;
	}

	public String getType() {
		return this.type;
	}

	public String getBusinessID() {
		return this.business_id;
	}

	public HashMap<String, Integer> getVotes() {
		return this.votes;
	}

	public int getCoolVotes() {
		return this.votes.get("cool");
	}

	public int getUsefulVotes() {
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

}
