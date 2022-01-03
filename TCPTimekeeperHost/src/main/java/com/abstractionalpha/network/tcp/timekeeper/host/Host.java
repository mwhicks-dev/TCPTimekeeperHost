package com.abstractionalpha.network.tcp.timekeeper.host;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Host {
	
	public static final int PORT_NUMBER = 25600;
	
	public static final String TRASNFORM_STRING = "AES/ECB/PKCS5Padding";
	
	private void handleClient(Socket socket) {
		// TODO implement method
	}
	
	private void run(String[] args) {
		ServerSocket serverSocket = null;
		
		// Set up
		try {
			// NOTE Add client info when implemented
			
			// Open ServerSocket
			serverSocket = new ServerSocket(PORT_NUMBER);
		} catch(Exception e) {
			System.err.println(String.format("Can't initialize host: %s", e));
			e.printStackTrace();
			System.exit(1);
		}
		
		// Accept and read new connections
		while (true) {
			try {
				// Accept new client connection
				Socket sock = serverSocket.accept();
				
				// Handle client
				Thread t = new Thread() {
					public void run() {
						handleClient(sock);
					}
				};
				t.start();
			} catch (IOException e) {
				System.err.println(String.format("Can't accept client %s", e));
			}
		}
	}
	
	public static void main(String[] args) {
		// Make a Host object to use non-statics
		Host host = new Host();
		host.run(args);
	}

}
