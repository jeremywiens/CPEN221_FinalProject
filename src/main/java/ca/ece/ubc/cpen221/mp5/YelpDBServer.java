package ca.ece.ubc.cpen221.mp5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import ca.ece.ubc.cpen221.mp5.database.YelpDB;

/**
*
 */
public class YelpDBServer {

	private ServerSocket serverSocket;
	private YelpDB thisYelp;
	protected static int port = 0;

	// Rep invariant: serverSocket != null
	//
	// Thread safety argument:
	// TODO serverSocket
	// TODO socket objects
	// TODO readers and writers in handle()
	// TODO data in handle()

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

		try {
			// each request is a single line containing a number
			for (String line = in.readLine(); line != null; line = in.readLine()) {
				System.err.println("request: " + line);
				String[] newArgs = line.split(" ");
				try {
					String command = newArgs[0];
					
					if(command.trim().toUpperCase().equals("GETRESTAURANT")) {
	//					thisYelp.GetRestaurant(line.substring(13, line.length()).trim());
					}
					else if(command.trim().toUpperCase().equals("ADDUSER")) {
			//			thisYelp.AddUser(line.substring(7, line.length()).trim());
					}
					else if(command.trim().toUpperCase().equals("ADDRESTAURANT")) {
			//			thisYelp.AddRestaurant(line.substring(13, line.length()).trim());
					}
					else if(command.trim().toUpperCase().equals("ADDREVIEW")) {
			//			thisYelp.AddReview(line.substring(9, line.length()).trim());
					}
					else {
						System.err.println("ERR: ILLEGAL_REQUEST");
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
	 * Start a Server running on the port #.
	 */
	public static void main(String[] args) {
		port = 0;
		try {
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

		try {
			YelpDBServer server = new YelpDBServer(port);
			server.serve();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}