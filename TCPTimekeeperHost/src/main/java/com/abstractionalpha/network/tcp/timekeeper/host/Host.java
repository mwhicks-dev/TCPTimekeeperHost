package com.abstractionalpha.network.tcp.timekeeper.host;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Host {
	
	/** Socket for communication with client */
	private ServerSocket serverSocket = null;
	
	/** Input stream for socket communication */
	private DataInputStream input = null;
	
	/** Output stream for socket communication */
	private DataOutputStream output = null;
	
	/** Port used for TCP connection */
	private static final int PORT = 26500;
	
	/**
	 * Handles Client instances accepted in Host#run.
	 * 
	 * @param socket
	 */
	private void handleClient(Socket socket) {
		// Establish IO streams
		try {
			input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException ioe) {
			// Handle error
			System.err.println(String.format("Failed to open IO streams: %s", ioe));
			ioe.printStackTrace();
			// Clean up
			try {
				socket.close();
			} catch (IOException e) {
				// Do nothing
			}
			System.exit(1);
		}
		
		// Read client input
		String line = null;
		String end = "done";
		
		while (!line.equals(end)) {
			// Read Client input
			try {
				line = input.readUTF();
				output.writeUTF(line);
			} catch (IOException ioe) {
				// Handle error
				System.err.println(String.format("Failed to read client input: %s", ioe));
				ioe.printStackTrace();
				// Clean up
				try {
					socket.close();
				} catch (IOException e) {
					// Do nothing
				}
			}
		}
		
		// Close connection
		try {
			socket.close();
			input.close();
			output.close();
		} catch (IOException e) {
			// Do nothing
		}
	}
	
	/**
	 * Accepts Client instances.
	 * 
	 * @param args -- command-line arguments
	 */
	private void run(String[] args) {
		// Open ServerSocket
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (Exception e) {
			// Handle error
			System.err.println(String.format("Failed to open server: %s", e));
			e.printStackTrace();
			// Clean up
			System.exit(1);
		}
		
		// Accept client connections
		try {
			while (true) {
				final Socket socket = serverSocket.accept();
				
				Thread thread = new Thread() {
					public void run() {
						handleClient(socket);
					}
				};
				thread.start();
			}
		} catch (IOException ioe) {
			// Handle error
			System.err.println(String.format("Failed to accept client: %s", ioe));
			ioe.printStackTrace();
			// Clean up
			try {
				serverSocket.close();
			} catch (IOException e) {
				// Do nothing
			}
			System.exit(1);
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				// Do nothing
			}
		}
	}
	
	/**
	 * Main method. Initializes and runs a Host instance.
	 * 
	 * @param args -- command-line arguments
	 */
	public static void main(String[] args) {
		Host host = new Host();
		host.run(args);
	}
	
}
