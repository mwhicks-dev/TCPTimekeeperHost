package com.abstractionalpha.network.tcp.timekeeper.host;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Host {
	
	/** Socket for communication with client */
	private Socket socket = null;
	
	/** Input stream for socket communication */
	private DataInputStream input = null;
	
	/** Output stream for socket communication */
	private DataOutputStream output = null;
	
	/**
	 * Handles Client instances accepted in Host#run.
	 * 
	 * @param socket
	 */
	private void handleClient(Socket socket) {
		// TODO Implement method
	}
	
	/**
	 * Accepts Client instances.
	 * 
	 * @param args -- command-line arguments
	 */
	private void run(String[] args) {
		// TODO Implement method
	}
	
	/**
	 * Main method. Initializes and runs a Host instance.
	 * 
	 * @param args -- command-line arguments
	 */
	public static void main(String[] args) {
		// TODO Implement method
	}
	
}
