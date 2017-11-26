package ca.ece.ubc.cpen221.mp5.database;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.json.*;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.json.stream.JsonParserFactory;

public class Restaurant {

	private int review_count;
	private String photo_url;
	private String state;
	private String full_address;
	private String type;
	private String url;
	private String city;
	private Boolean open;
	private ArrayList<String> neighborhoods = new ArrayList<String>();
	private int price;
	private ArrayList<String> schools = new ArrayList<String>();
	private BigDecimal stars;
	private String name;
	private String business_id;
	private BigDecimal longitude;
	private List<String> categories = new ArrayList<String>();
	private BigDecimal latitude;

	public Restaurant(String Restaurant) {
		JsonParser parser = Json.createParser(new StringReader(Restaurant));
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
	            	if (key.equals("State")) {
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
	            	if (key.equals("schools")) {
	            		this.schools.add(parser.getString());
	            	}
	                 break;
	            case VALUE_NUMBER:
	            	if (key.equals("price")) {
	            		this.price = parser.getInt();
	            	}
	            	if (key.equals("latitude")) {
	            		this.latitude = parser.getBigDecimal();
	            	}
	            	if (key.equals("review_count")) {
	            		this.review_count = parser.getInt();
	            	}
	            	if (key.equals("stars")) {
	            		this.stars = parser.getBigDecimal();
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

	public BigDecimal getStars() {
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
	
	public List<String> getSchools() {
		return this.schools;
	}

}