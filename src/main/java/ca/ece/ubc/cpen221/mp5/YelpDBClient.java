package ca.ece.ubc.cpen221.mp5;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;

import ca.ece.ubc.cpen221.mp5.YelpDBServer;

/**
 * No clue what is going on;
 */
public class YelpDBClient {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	// Rep invariant: socket, in, out != null

	/**
	 * Make a FibonacciClient and connect it to a server running on hostname at the
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
		out.flush(); // important! make sure x actually gets sent
	}

	/**
	 * Get a reply from the next request that was submitted. Requires this is
	 * "open".
	 * 
	 * @return the requested Fibonacci number
	 * @throws IOException
	 *             if network or server failure
	 */
	public String getReply() throws IOException {
		String reply = in.readLine();
		if (reply == null) {
			throw new IOException("connection terminated unexpectedly");
		}

		try {
			// return new BigInteger(reply);
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

	private static final int N = 100;

	/**
	 * Use a FibonacciServer to find the first N Fibonacci numbers.
	 */
	public static void main(String[] args) {
		int port = 0;
		boolean bools = true;
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
			YelpDBClient client = new YelpDBClient("localhost", port);
			// users need just a name, cant have duplicate id
			// restaurants need latitude, longtitude, and name, cant have duplicate id
			// reviews need busniess id, user id, and stars
			System.out.println("connected");
			while (bools) {
				Scanner scanner = new Scanner(System.in);
				String line = scanner.nextLine();
				if (line.trim().equals("close")) {
					bools = false;
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
