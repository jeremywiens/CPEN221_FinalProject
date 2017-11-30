package ca.ece.ubc.cpen221.mp5.database;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.json.*;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class User {

	private int review_count;
	private String name;
	private HashMap<String, Integer> votes = new HashMap<String, Integer>();
	private String user_id = null;
	private List<String> userIDs = new ArrayList<String>();
	private String type = null;
	private String url = null;
	private double average_stars = 0.0;
	private int count = 0;

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
			throw new IllegalArgumentException();
		}
		if (votes.isEmpty()) {
			votes.put("funny", 0);
			votes.put("useful", 0);
			votes.put("cool", 0);
		}
		while (userIDs.contains(user_id) || user_id == null) {
			count++;
			int a = count;
			this. user_id = Integer.toString(a);
		}
		if (type == null) {
			type = "user";
		}
		if (url == null) {
			url = "http://www.yelp.com/user_details?userid=" + user_id;
		}
	}
	
	private void addID() {
		userIDs.add(user_id);
	}

	public String getUrl() {
		return this.url;
	}

	public HashMap<String, Integer> getVotes() {
		return this.votes;
	}

	public int getFunnyVotes() {
		return (int) this.votes.get("funny");
	}

	public int getUsefulVotes() {
		return (int) this.votes.get("useful");
	}

	public int getCoolVotes() {
		return (int) this.votes.get("cool");
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

	public double getAvgStars() {
		return this.average_stars;
	}
	
	@Override
	public String toString() {
		JsonObject Votes = Json.createObjectBuilder()
				.add("funny", this.getFunnyVotes())
				.add("useful", this.getUsefulVotes())
				.add("cool", this.getCoolVotes()).build();

		JsonObject user = Json.createObjectBuilder()
				.add("url", this.url)
				.add("votes", Votes)
				.add("review_count", 0)
				.add("type", "user")
				.add("user_id", this.user_id)
				.add("name", this.name)
				.add("average_stars", this.average_stars).build();
		return user.toString();
	}
}
