package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import ca.ece.ubc.cpen221.mp5.database.YelpDB;

/**
 * YelpDBServer - is a server that allows a client to access the Yelp database.
 * YelpDBServer can handle multiple concurrent clients. A client can get
 * restaurant information with the proper command followed by its business ID.
 * Ex:
 * 
 * <GETRESTAURANT> <h_we4E3zofRTf4G0JTEF0A>
 * 
 * A client can add a user, restaurant, or review and will be returned the
 * information in JSON format. Add users, restaurants, or reviews with the
 * following commands:
 * 
 * <ADDUSER> (the following is example) <{"name": "Sathish G."}> More
 * information can be provided such as user_Id however an error will be thrown
 * if this ID already exists within the database. If the argument to the ADDUSER
 * command is invalid (not JSON format, missing name, etc.) the message ERR:
 * INVALID_USER_STRING will be returned.
 * 
 * <ADDRESTAURANT> <restaurant information> The restaurant information must
 * include (in JSON format) "open", "url", "longitude", "latitude",
 * "neighborhoods", "name", "categories", "state", "type", "city",
 * "full_address", "photo_url", "schools", "latitude", and "price". Everything
 * except "stars" "business_ID", and "review_count" If the provided string is
 * incomplete or erroneous , the error message will be ERR:
 * INVALID_RESTAURANT_STRING.
 * 
 * <ADDREVIEW> <review information> The review information that must be included
 * is "business_id", "stars", and "user_id" The possible error codes are ERR:
 * INVALID_REVIEW_STRING, ERR: NO_SUCH_USER and ERR: NO_SUCH_RESTAURANT.
 * 
 * An example of the returned JSON string for ADDUSER is {"url":
 * "http://www.yelp.com/user_details?userid=42", "votes": {}, "review_count": 0,
 * "type": "user", "user_id": "42", "name": "Sathish G.", "average_stars": 0}
 */
public class YelpDBServer {

	private ServerSocket serverSocket;
	private YelpDB thisYelp;

	// Rep invariant: serverSocket != null
	//
	// Thread safety argument:
	// TODO serverSocket
	// TODO socket objects
	// TODO readers and writers in handle()
	// TODO data in handle

	/**
	 * Make a YelpDBServer that listens for connections on port.
	 * 
	 * @param port
	 *            port number, requires 0 <= port <= 65535
	 */
	public YelpDBServer(int port) throws IOException {
		thisYelp = new YelpDB("data/restaurants.json", "data/reviews.json", "data/users.json");
		serverSocket = new ServerSocket(port);
	}

	/**
	 * Run the server, listening for connections and handling them.
	 * 
	 * @throws IOException
	 *             if the main server socket is broken
	 */
	public void serve() throws IOException {
		while (true) {
			// block until a client connects
			final Socket socket = serverSocket.accept();
			// create a new thread to handle that client
			Thread handler = new Thread(new Runnable() {
				public void run() {
					try {
						try {
							handle(socket);
						} finally {
							socket.close();
						}
					} catch (IOException ioe) {
						// this exception wouldn't terminate serve(),
						// since we're now on a different thread, but
						// we still need to handle it
						ioe.printStackTrace();
					}
				}
			});
			// start the thread
			handler.start();
		}
	}

	/**
	 * Handle one client connection. Returns when client disconnects.
	 * 
	 * @param socket
	 *            socket where client is connected
	 * @throws IOException
	 *             if connection encounters an error
	 */
	private void handle(Socket socket) throws IOException {
		System.err.println("client connected");

		// get the socket's input stream, and wrap converters around it
		// that convert it from a byte stream to a character stream,
		// and that buffer it so that we can read a line at a time
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// similarly, wrap character=>bytestream converter around the
		// socket output stream, and wrap a PrintWriter around that so
		// that we have more convenient ways to write Java primitive
		// types to it.
		PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		String response = "";
		try {
			// each request is a single line containing a number
			for (String line = in.readLine(); line != null; line = in.readLine()) {
				System.err.println("request: " + line);
				String[] newArgs = line.split(" ");
				response = "";
				try {
					String command = newArgs[0];

					if (command.trim().toUpperCase().equals("GETRESTAURANT")) {
						thisYelp.getRestaurant(line.substring(13, line.length()).trim());
						// make string return first
					} else if (command.trim().toUpperCase().equals("ADDUSER")) {
						response += thisYelp.AddUser(line.substring(7, line.length()).trim());
						System.err.println("reply: " + response);
						out.println(response);
					} else if (command.trim().toUpperCase().equals("ADDRESTAURANT")) {
						response += thisYelp.AddRestaurant(line.substring(13, line.length()).trim());
						System.err.println("reply: " + response);
						out.println(response);
					} else if (command.trim().toUpperCase().equals("ADDREVIEW")) {
						response += thisYelp.AddReview(line.substring(9, line.length()).trim());
						System.err.println("reply: " + response);
						out.println(response);
					} else {
						System.err.println("ERR: ILLEGAL_REQUEST");
						out.print("err: ILLEGAL_REQUEST\n");
					}
				} catch (Exception e) {
					// complain about ill-formatted request
					System.err.println("reply: err");
					out.print("err\n");
				}
				// important! our PrintWriter is auto-flushing, but if it were
				// not:
				// out.flush();
			}
		} finally {
			out.close();
			in.close();
		}
	}

	/**
	 * Start a Server running on the port # given in the args
	 */
	public static void main(String[] args) {
		int port = 0;
		try {
			if (args[0] != null)
				port = Integer.parseInt(args[0]);
			// requires 0 <= port <= 65535
			if (port < 0 || port > 65535) {
				System.out.println("requires 0 <= port <= 65535");
				System.exit(0);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		System.out.println(port);
		try {
			YelpDBServer server = new YelpDBServer(port);
			server.serve();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}