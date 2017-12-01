package ca.ece.ubc.cpen221.mp5;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import ca.ece.ubc.cpen221.mp5.YelpDBServer;

/**
 * Source: The Skeleton for this code is from the CPEN 221 course website. The
 * github repo can be found at https://github.com/CPEN-221/FibonacciServer
 * 
 * YelpDBClient connects to the YelpDBServer. The client can provide simple
 * requests and will be returned JSON formatted strings as a response. See
 * YelpDBServer for more information.
 */
public class YelpDBClient {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	// Rep invariant: socket, in, out != null

	/**
	 * Make a YelpDBClient and connect it to a server running on hostname at the
	 * specified port.
	 * 
	 * @throws IOException
	 *             if can't connect
	 */
	public YelpDBClient(String hostname, int port) throws IOException {
		socket = new Socket(hostname, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	}

	/**
	 * Send a request to the server. Requires this is "open".
	 * 
	 * @param line
	 * @throws IOException
	 *             if network or server failure
	 */
	public void sendRequest(String line) throws IOException {
		out.print(line + "\n");
		out.flush();
	}

	/**
	 * Get a reply from the next request that was submitted. Requires this is
	 * "open".
	 * 
	 * @return The string sent by the server based on what option was selected. An
	 *         error will be returned if the delivered string had an improper
	 *         format. See YelpDBServer specs for more details
	 * @throws IOException
	 *             if network or server failure
	 */
	public String getReply() throws IOException {
		String reply = in.readLine();
		if (reply == null) {
			throw new IOException("connection terminated unexpectedly");
		}

		try {
		} catch (NumberFormatException nfe) {
			throw new IOException("misformatted reply: " + reply);
		}
		return reply;
	}

	/**
	 * Closes the client's connection to the server. This client is now "closed".
	 * Requires this is "open".
	 * 
	 * @throws IOException
	 *             if close fails
	 */
	public void close() throws IOException {
		in.close();
		out.close();
		socket.close();
	}

	public static void testing(int port, List<String> input) {
		try {
			YelpDBClient client = new YelpDBClient("localhost", port);
			// users need just a name, cant have duplicate id
			// restaurants need latitude, longtitude, and name, cant have duplicate id
			// reviews need busniess id, user id, and stars

			for(String s: input) {
				client.sendRequest(s.trim());
				// collect the replies
				String reply = client.getReply();
				System.out.println(reply);
			}
			client.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Start a Client running on the port # given in the args. Client will not
	 * connect if the port number is not active.
	 * 
	 * @param args
	 *            is the port, requires 0 <= port <= 65535.
	 */
	public static void main(String[] args) {
		int port = 0;
		boolean bools = true;
		// ensure port is valid
		try {
			port = Integer.parseInt(args[0]);
			// requires 0 <= port <= 65535
			if (port < 0 || port > 65535) {
				System.out.println("requires 0 <= port <= 65535");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			YelpDBClient client = new YelpDBClient("localhost", port);
			// users need just a name, cant have duplicate id
			// restaurants need latitude, longtitude, and name, cant have duplicate id
			// reviews need busniess id, user id, and stars
			while (bools) {

				Scanner scanner = new Scanner(System.in);
				String line = scanner.nextLine();
				if (line.trim().equals("close")) {
					bools = false;
					System.out.println("Closing");
					client.sendRequest("end");
					client.getReply();
				} else {
					client.sendRequest(line.trim());

					// collect the replies
					String reply = client.getReply();
					System.out.println(reply);
				}
			}
			client.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
