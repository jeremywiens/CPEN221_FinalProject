package ca.ubc.ece.cpen221.mp5.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;

import ca.ece.ubc.cpen221.mp5.database.YelpDB;

public class JsonTest {

	@Test
	public void test() throws IOException {
		YelpDB thisYelp = new YelpDB("data/restaurants.json", "", "");
		
	}

}
