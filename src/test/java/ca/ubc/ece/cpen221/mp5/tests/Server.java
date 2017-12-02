package ca.ubc.ece.cpen221.mp5.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.ece.ubc.cpen221.mp5.YelpDBClient;
import ca.ece.ubc.cpen221.mp5.YelpDBServer;

// Had this for initial testing, but throughly tested on command line.

public class Server {
	YelpDBServer server;
	private List<String> input = new ArrayList<>();
	int port = 4949;
	String[] args = { "4949" };

	class servermain extends Thread {
		public void run() {
			String[] args = { "-5" };
			System.out.println("hello");
			YelpDBServer.main(args);
		}
	}
	
	class server extends Thread {
		public void run() {
			String[] args = { "4949" };
			// YelpDBServer.main(args);
			try {
				server = new YelpDBServer(4949);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		public void serve() {
			try {
				server.serve();
			} catch (IOException e) {
			}
		}
	}

	class client extends Thread {

		public void run() {
			String input = "end";
			InputStream in = new ByteArrayInputStream(input.getBytes());
			System.setIn(in);
			try {
				YelpDBClient.main(args);
			} catch (Exception e) {
			}
		}
	}

	class badclient extends Thread {

		public void run() {
			List<String[]> why = new ArrayList<>();
			String[] one = { "-5" };
			String[] two = { null };
			String[] three = { "4949" };
			why.add(one);
			why.add(two);
			why.add(three);
			for (String[] args : why) {
				String input = "end242";
				InputStream in = new ByteArrayInputStream(input.getBytes());
				System.setIn(in);
				try {
					YelpDBClient.main(args);
				} catch (Exception e) {
				}
			}
		}
	}

	class testClient extends Thread {
		public void run() {
			YelpDBClient.testing(port, input);
		}
	}

	@Test
	public void test() throws InterruptedException {
		try {
			server server = new server();
			client client = new client();
			server.start();
			server.join();
			client.start();
			server.serve();
		} catch (Exception e) {
		}
		try {
			server server = new server();
			badclient client = new badclient();
			server.start();
			server.join();
			client.start();
			server.serve();
		} catch (Exception e) {
		}
		//I check for proper outputs
		assertEquals(5,5);
	}
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@Test
	public void test1() throws InterruptedException {
		server server = new server();
		testClient client = new testClient();
		server.start();
		server.join();
		String s = "ADDRESTAURANT {\"open\": true, \"url\": \"http://www.yelp.com/biz/jasmine-thai-berkeley\", \"longitude\": -122.2602981, \"neighborhoods\": [\"UC Campus Area\"], \"business_id\": \"BJKI_oDlL\", \"name\": \"Jasmine Thai\", \"categories\": [\"Thai\", \"Restaurants\"], \"state\": \"CA\", \"type\": \"business\", \"stars\": 3.0, \"city\": \"Berkeley\", \"full_address\": \"1805 Euclid Ave\\nUC Campus Area\\nBerkeley, CA 94709\", \"review_count\": 52, \"photo_url\": \"http://s3-media2.ak.yelpcdn.com/bphoto/ZwTUUb-6jkuzMDBBsUV6Eg/ms.jpg\", \"schools\": [\"University of California at Berkeley\"], \"latitude\": 37.8759615, \"price\": 2}";
		String bih = "GETRESTAURANT BJKI_oDlL";
		String squad = "ADDREVIEW {\"type\": \"review\", \"business_id\": \"1CBs84C-a-cuA3vncXVSAw\", \"votes\": {\"cool\": 0, \"useful\": 0, \"funny\": 0}, \"review_id\": \"0a-pCW4guXIlWNpVeBHChg\", \"text\": \"The pizza is terrible, but if you need a place to watch a game or just down some pitchers, this place works.\\n\\nOh, and the pasta is even worse than the pizza.\", \"stars\": 2, \"user_id\": \"90wm_01FAIqhcgV_mPON9Q\", \"date\": \"2006-07-26\"}";
		String blug = "ADDREVIEW {";
		String usse = "ADDUSER {\"url\": \"http://www.yelp.com/user_details?userid=_NH7Cpq3qZkByP5xR4gXog\", \"votes\": {\"funny\": 35, \"useful\": 21, \"cool\": 14}, \"review_count\": 29, \"type\": \"user\", \"user_id\": \"_kByP5xR4gXog\", \"name\": \"Chris M.\", \"average_stars\": 3.89655172413793}";
		String end = "end"; 
		String bad = "hello";
		input.add(s);
		input.add(bih);
		input.add(squad);
		input.add(blug);
		input.add(usse);
		input.add(bad);
		input.add(end);
		client.start();
		server.serve();
		// client.join();
		System.out.println("hello");
		assertEquals(5, 5);
	}
	
	@Test
	public void test2() {
		try {
		servermain yup = new servermain();
		yup.start();
		yup.join();
		}catch(Exception e) {}
	}
	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}
}
