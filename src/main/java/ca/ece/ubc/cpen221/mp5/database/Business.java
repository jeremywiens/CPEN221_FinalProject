package ca.ece.ubc.cpen221.mp5.database;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.json.*;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

/**
* Business - This is a representation of a business in a database.
* It takes in a Json formatted string and parses it to store all the information in a business.
* A business can be called to get different information for each instance of a business.
* 
* Business can be subtyped EX: Restaurant/
*/
public class Business {
	
	// Abstraction function: Business' exists as a combination of Strings,
	// integers, List of Strings, and doubles to store the information contained
	// in a JSON formatted string representing a business.
	
	// Rep Invariant: Each business must have a review count associated with it which must be greater than 0
	private int review_count = 0;
	// must have a unique photo_url which is a string associated with it which correlates to a
	// real web page and is not null
	private String photo_url = null;
	// must have a state which the business is located in and cannot be null
	private String state = null;
	// must have an address of the business in the form of a string and cannot be null
	private String full_address = null;
	// must have a type which is business
	private String type = "business";
	// must have a url which is a string that maps to a real web page and is not null
	private String url = null;
	// must be a string which is not null
	private String city = null;
	// must be a type of boolean
	private Boolean open;
	// must have a list of Strings representing the neighborhoods the Business is located in and
	// no string is null and the list is not empty
	private ArrayList<String> neighborhoods = new ArrayList<String>();
	// must have a price associated to it of type int and be greater than 0 and less than 5
	private int price = 0;
	// must have a representation of the number of stars the business is rated at of type double between
	// 1.0 and 5.0 inclusive
	private double stars = 0;
	// must have a business name associated with it which is represented by a non-null string
	private String name = null;
	// must have a unique business_id which is a non-null string
	private String business_id = null;
	// must have a longitude of type double between -180.0 and 180.0
	private Double longitude = null;
	private Double latitude = null;
	
	public Business() {
		
	}
	/**
	 * Business Constructor - creates a Business from a String in proper JSON Format which must 
	 * contain all Business information excluding type, review_count and stars.
	 * If any of the tokens except for review_count, stars, or type don't exist in the string Restaurant 
	 * will throw an IllegalArgumentException. If type is not business it will throw an exception as well.
	 * If type, is not assigned it is given declared as business. If stars or review count is not included
	 * it is given the value of 0. If extra information is included in JSON format, it is ignored.
	 * 
	 * @param Business
	 * 			JSON String containing Business information excluding type, review count and stars.
	 * 			The string must be in proper JSON format. For example: {"open": true, "url": 
	 * 			"http://www.yelp.com/biz/cafe-3-berkeley", "longitude": -122.260408, "neighborhoods": 
	 * 			["Telegraph Ave", "UC Campus Area"], "business_id": "gclB3ED6uk6viWlolSb_uA", "name": 
	 * 			"Cafe 3", "categories": ["Cafes", "Restaurants"], "state": "CA", "type": "business", 
	 * 			"stars": 2.0, "city": "Berkeley", "full_address": "2400 Durant Ave\nTelegraph Ave\nBerkeley,
	 * 			 CA 94701", "review_count": 9, "photo_url": 
	 * 			"http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg", "schools": 
	 * 			["University of California at Berkeley"], "latitude": 37.867417, "price": 1}
	 */
	public Business(String Business) {
		JsonParser parser = Json.createParser(new StringReader(Business));
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
				if ("neighborhoods".equals(key)) {
					this.neighborhoods.add(parser.getString());
				}
				if ("business_id".equals(key)) {
					this.business_id = parser.getString();
				}
				if ("name".equals(key)) {
					this.name = parser.getString();
				}
				if ("state".equals(key)) {
					this.state = parser.getString();
				}
				if ("type".equals(key)) {
					this.type = parser.getString();
				}
				if ("city".equals(key)) {
					this.city = parser.getString();
				}
				if ("full_address".equals(key)) {
					this.full_address = parser.getString();
				}
				if ("photo_url".equals(key)) {
					this.photo_url = parser.getString();
				}
				break;
			case VALUE_NUMBER:
				if ("price".equals(key)) {
					this.price = parser.getInt();
				}
				if ("latitude".equals(key)) {
					this.latitude = parser.getBigDecimal().doubleValue();
				}
				if ("review_count".equals(key)) {
					this.review_count = parser.getInt();
				}
				if ("stars".equals(key)) {
					this.stars = parser.getBigDecimal().doubleValue();
				}
				if ("longitude".equals(key)) {
					this.longitude = parser.getBigDecimal().doubleValue();
				}
				break;
			case VALUE_TRUE:
				if ("open".equals(key)) {
					this.open = (true);
				}
				break;
			case VALUE_FALSE:
				if ("open".equals(key)) {
					this.open = (false);
				}
				break;
			}
		}
		parser.close();
		if (Null(this.city, this.full_address, this.latitude, this.longitude,
				this.name, this.neighborhoods, this.type, this.state) 
				|| this.price < 1 || this.price > 4) {
			throw new IllegalArgumentException("ERR: INVALID_BUSINESS_STRING.");
		}
		if (!type.equals("business")) {
			throw new IllegalArgumentException("ERR: INVALID_BUSINESS_STRING.");
		}
		if (url == null) {
			this.url = "http://www.yelp.com/biz/" + name.toLowerCase().replace(" ", "-") + "-"+ 
			city.toLowerCase().replace(" ", "-");
		}
	}

	//checks to see if an object is notNull
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
	 * getOpen returns the boolean value of open for this business
	 * 
	 * @return the boolean value of open for this restaurant
	 */
	public Boolean getOpen() {
		return this.open;
	}

	/**
	 * getUrl returns the String of the url for this business
	 * 
	 * @return the string of the url for this restaurant
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * getLatitude returns the double value of the latitude where this business is located
	 * 
	 * @return a double value of the longitude where the restaurant is located
	 */
	public double getLatitude() {
		return this.latitude;
	}

	/**
	 * getLongitude returns the double value of the longitude where this business is located
	 * 
	 * @return a double value of the longitude where the restaurant is located
	 */
	public double getLongitude() {
		return this.longitude;
	}

	public List<String> getNeighborhoods() {
		return this.neighborhoods;
	}

	/**
	 * getBusinessID returns the String of the business_id for this business
	 * 
	 * @return the string of the business_id for this restaurant
	 */
	public String getBusinessID() {
		return this.business_id;
	}

	/**
	 * getName returns the String of the name for this business
	 * 
	 * @return the string of the name for this restaurant
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * getState returns the String of the state for this business
	 * 
	 * @return the string of the state for this restaurant
	 */
	public String getState() {
		return this.state;
	}

	/**
	 * getType returns the String of the type for this business
	 * 
	 * @return the string of the type for this restaurant
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * getStars returns the double value of the number of stars for this business
	 * 
	 * @return the double value of the number of stars for this business
	 */
	public double getStars() {
		return this.stars;
	}

	/**
	 * getCity returns the String of the city for this business
	 * 
	 * @return the string of the city for this restaurant
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * getAddress returns the String of the full_address for this business
	 * 
	 * @return the string of the full_address for this business
	 */
	public String getAddress() {
		return this.full_address;
	}

	/**
	 * getNumReviews returns the int value of the review_count for this business
	 * 
	 * @return the int value of the review_count for this business
	 */
	public int getNumReviews() {
		return this.review_count;
	}

	/**
	 * getPrice returns the int value of the price for this business
	 * 
	 * @return the int value of the price for this business
	 */
	public int getPrice() {
		return this.price;
	}

	
	/**
	 * toString() returns the information contained in this restaurant back into JSON string format.
	 * For example: {"open": true, "url": "http://www.yelp.com/biz/cafe-3-berkeley", 
	 * "longitude": -122.260408, "neighborhoods": ["Telegraph Ave", "UC Campus Area"], 
	 * "business_id": "gclB3ED6uk6viWlolSb_uA", "name": "Cafe 3", 
	 * "categories": ["Cafes", "Restaurants"], "state": "CA", "type": "business", "stars": 2.0, 
	 * "city": "Berkeley", "full_address": "2400 Durant Ave\nTelegraph Ave\nBerkeley, CA 94701", 
	 * "review_count": 9, "photo_url": "http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg",
	 *  "schools": ["University of California at Berkeley"], "latitude": 37.867417, "price": 1}
	 * 
	 * @return the information contained in this restaurant as a string in JSON format
	 */
	@Override
	public String toString() {
		JsonArrayBuilder Neighborhood = Json.createArrayBuilder();
		JsonArrayBuilder Categories = Json.createArrayBuilder();
		JsonArrayBuilder Schools = Json.createArrayBuilder();

		for (String str : this.neighborhoods) {
			Neighborhood.add(str);
		}


		JsonObject rest = Json.createObjectBuilder()
				.add("open", this.open)
				.add("url", this.url)
				.add("longitude", this.longitude)
				.add("neighborhoods", Neighborhood)
				.add("business_id", this.business_id)
				.add("name", this.name)
				.add("categories", Categories)
				.add("state", this.state)
				.add("stars", this.stars)
				.add("city", this.city)
				.add("full_address", this.full_address)
				.add("review_count", this.review_count)
				.add("photo_url", this.photo_url)
				.add("schools", Schools)
				.add("latitude", this.latitude)
				.add("price", this.price)
				.add("type", this.type).build();

		return rest.toString();
	}
	
	/**
	 * changeID - changes the businessID for this review to the newID provided
	 * 
	 * @param newID must be a non-null string
	 */
	public void changeID(String newID) {
		this.business_id = newID;
	}
	
	/**
	 * addReview - calculates the new average star rating for this business with the number of stars passed.
	 * It increases the review_count of this restaurant by one.
	 * 
	 * 
	 * @param addStars must be an int between 0 and 5 inclusive
	 */
	public void addReview(int addStars) {
		this.stars = (this.stars*this.review_count + addStars)/++this.review_count;
	}
	
	

}
