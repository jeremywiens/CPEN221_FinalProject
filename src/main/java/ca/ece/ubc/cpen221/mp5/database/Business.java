package ca.ece.ubc.cpen221.mp5.database;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.json.*;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class Business {

	private int review_count = 0;
	private String photo_url = null;
	private String state = null;
	private String full_address = null;
	private String type = null;
	private String url = null;
	private String city = null;
	private Boolean open;
	private ArrayList<String> neighborhoods = new ArrayList<String>();
	private int price;
	private ArrayList<String> schools = new ArrayList<String>();
	private int stars = 0;
	private String name = null;
	private String business_id = null;
	private BigDecimal longitude = null;
	private List<String> categories = new ArrayList<String>();
	private BigDecimal latitude = null;

	public Business() {}
	
	public Business(String business) {
		JsonParser parser = Json.createParser(new StringReader(business));
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
				if (key.equals("neighborhoods")) {
					this.neighborhoods.add(parser.getString());
				}
				if (key.equals("business_id")) {
					this.business_id = parser.getString();
				}
				if (key.equals("name")) {
					this.name = parser.getString();
				}
				if (key.equals("categories")) {
					this.categories.add(parser.getString());
				}
				if (key.equals("state")) {
					this.state = parser.getString();
				}
				if (key.equals("type")) {
					this.type = parser.getString();
				}
				if (key.equals("city")) {
					this.city = parser.getString();
				}
				if (key.equals("full_address")) {
					this.full_address = parser.getString();
				}
				if (key.equals("photo_url")) {
					this.photo_url = parser.getString();
				}

				break;
			case VALUE_NUMBER:
				if (key.equals("latitude")) {
					this.latitude = parser.getBigDecimal();
				}
				if (key.equals("review_count")) {
					this.review_count = parser.getInt();
				}
				if (key.equals("stars")) {
					this.stars = parser.getInt();
				}
				if (key.equals("longitude")) {
					this.longitude = parser.getBigDecimal();
				}
				break;
			case VALUE_TRUE:
				if (key.equals("open")) {
					this.open = (true);
				}
				break;
			case VALUE_FALSE:
				if (key.equals("open")) {
					this.open = (false);
				}
				break;
			}
		}
		parser.close();
		if (notNull(this.business_id, this.categories, this.city, this.full_address, this.latitude, this.longitude,
				this.name, this.neighborhoods, this.categories, this.schools, this.url, this.type, this.photo_url,
				this.state)) {
			throw new IllegalArgumentException();
		}
	}

	private boolean notNull(Object... args) {
		for (Object arg : args) {
			if (arg != null) {
				return false;
			} else {
				System.out.println("hello");
			}
		}
		return true;
	}

	public Boolean getOpen() {
		return this.open;
	}

	public String getUrl() {
		return this.url;
	}

	public BigDecimal getLatitude() {
		return this.latitude;
	}

	public BigDecimal getLongitude() {
		return this.longitude;
	}

	public List<String> getNeighborhoods() {
		return this.neighborhoods;
	}

	public String getBusinessID() {
		return this.business_id;
	}

	public String getName() {
		return this.name;
	}

	public List<String> getCategories() {
		return this.categories;
	}

	public String getState() {
		return this.state;
	}

	public String getType() {
		return this.type;
	}

	public int getStars() {
		return this.stars;
	}

	public String getCity() {
		return this.city;
	}

	public String getAddress() {
		return this.full_address;
	}

	public int getNumReviews() {
		return this.review_count;
	}

	public String getPhotoURL() {
		return this.photo_url;
	}

	public int getPrice() {
		return this.price;
	}

	@Override
	public String toString() {
		JsonArrayBuilder Neighborhood = Json.createArrayBuilder();
		JsonArrayBuilder Categories = Json.createArrayBuilder();

		for (String str : this.neighborhoods) {
			Neighborhood.add(str);
		}

		for (String str : this.categories) {
			Categories.add(str);
		}

		JsonObject rest = Json.createObjectBuilder().add("open", this.open).add("url", this.url)
				.add("longitude", this.longitude).add("neighborhoods", Neighborhood)
				.add("business_id", this.business_id).add("name", this.name).add("categories", Categories)
				.add("state", this.state).add("stars", this.stars).add("city", this.city)
				.add("full_address", this.full_address).add("review_count", this.review_count)
				.add("photo_url", this.photo_url).add("latitude", this.latitude)
				.add("price", this.price).add("type", this.type).build();

		return rest.toString();
	}

}