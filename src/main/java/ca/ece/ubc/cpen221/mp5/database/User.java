package ca.ece.ubc.cpen221.mp5.database;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.json.*;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class User {
	
	private int review_count;
	private String name;
	private HashMap votes = new HashMap<String, Integer>();
	private String user_id;
	private String type;
	private String url;
	private BigDecimal average_stars;
	
	public User(String User) {
		JsonParser parser = Json.createParser(new StringReader(User));
	    String key = null;
	    String value = null;
	    while (parser.hasNext()) {
	         final Event event = parser.next();
	         switch (event) {
	            case KEY_NAME:
	                 key = parser.getString();
	                 break;
	            case VALUE_STRING:
	            	if (key.equals("url")) {
	            		this.url = parser.getString();
	            	}
	            	if (key.equals("votes")) {
	            		this.votes.put(parser.getString(), null);
	            	}
	            	if (key.equals("user_id")) {
	            		this.user_id = parser.getString();
	            	}
	            	if (key.equals("name")) {
	            		this.name = parser.getString();
	            	}
	            	if (key.equals("type")) {
	            		this.type = parser.getString();
	            	}
	                 break;
	            case VALUE_NUMBER:
	            	if (key.equals("funny")) {
	            		this.votes.put(key, parser.getInt());
	            	}
	            	if (key.equals("useful")) {
	            		this.votes.put(key, parser.getInt());
	            	}
	            	if (key.equals("cool")) {
	            		this.votes.put(key, parser.getInt());
	            	}
	            	if (key.equals("review_count")) {
	            		this.review_count = parser.getInt();
	            	}
	            	if (key.equals("average_stars")) {
	            		this.average_stars = parser.getBigDecimal();
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

	public String getUrl() {
		return this.url;
	}

	public HashMap getVotes() {
		return this.votes;
	}
	
	public int getFunnyVotes() {
		return (int)this.votes.get("funny");
	}
	
	public int getUsefulVotes() {
		return (int)this.votes.get("useful");
	}
	
	public int getCoolVotes() {
		return (int)this.votes.get("cool");
	}
	public int getNumReviews() {
		return this.review_count;
	}

	public String getType() {
		return this.type;
	}

	public String getUserID() {
		return this.user_id;
	}

	public String getName() {
		return this.name;
	}

	public BigDecimal getAvgStars() {
		return this.average_stars;
	}

}

