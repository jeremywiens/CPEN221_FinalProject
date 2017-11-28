package ca.ece.ubc.cpen221.mp5;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;

import ca.ece.ubc.cpen221.mp5.YelpDBServer.YelpDBServer;

/**
 * No clue what is going on;
 */
public class YelpDBClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    // Rep invariant: socket, in, out != null
    
    /**
     * Make a FibonacciClient and connect it to a server running on
     * hostname at the specified port.
     * @throws IOException if can't connect
     */
    public YelpDBClient(String hostname, int port) throws IOException {
        socket = new Socket(hostname, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
    
    /**
     * Send a request to the server. Requires this is "open".
     * @param x 
     * @throws IOException if network or server failure
     */
    public void sendRequest(int x) throws IOException {
        out.print(x + "\n");
        out.flush(); // important! make sure x actually gets sent
    }
    
    /**
     * Get a reply from the next request that was submitted.
     * Requires this is "open".
     * @return the requested Fibonacci number
     * @throws IOException if network or server failure
     */
    public BigInteger getReply() throws IOException {
        String reply = in.readLine();
        if (reply == null) {
            throw new IOException("connection terminated unexpectedly");
        }
        
        try {
            return new BigInteger(reply);
        } catch (NumberFormatException nfe) {
            throw new IOException("misformatted reply: " + reply);
        }
    }

    /**
     * Closes the client's connection to the server.
     * This client is now "closed". Requires this is "open".
     * @throws IOException if close fails
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
		try {
			port = Integer.parseInt(args[0]);
			// requires 0 <= port <= 65535
			if (port < 0 || port > 65535) {
				System.out.println("requires 0 <= port <= 65535");
				System.exit(0);
			}
			if(port != YelpDBServer.port) {
				System.out.println("Invalid port, try: " + YelpDBServer.port);
				System.exit(0);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
    	
    	try {
            YelpDBClient client = new YelpDBClient("localhost", YelpDBServer.port);

            // send the requests to find the first N Fibonacci numbers
            for (int x = 1; x <= N; ++x) {
                client.sendRequest(x);
                System.out.println("fibonacci("+x+") = ?");
            }
            
            // collect the replies
            for (int x = 1; x <= N; ++x) {
                BigInteger y = client.getReply();
                System.out.println("fibonacci("+x+") = "+y);
            }
            
            client.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
