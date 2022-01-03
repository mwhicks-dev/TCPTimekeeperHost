package com.abstractionalpha.network.tcp.timekeeper.host;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Host {
	
	public static final int PORT_NUMBER = 25600;
	
	public static final String TRASNFORM_STRING = "AES/ECB/PKCS5Padding";
	
	private byte[] getMessage(DataInputStream input) throws IOException {
		int len = input.readInt();
		byte[] msg = new byte[len];
		input.readFully(msg);
		return msg;
	}
	
	private void putMessage(DataOutputStream output, byte[] msg) throws IOException {
		// Write length of given message followed by its contents
		output.writeInt(msg.length);
		output.write(msg, 0, msg.length);
		output.flush();
	}
	
	private void handleClient(Socket socket) {
		try {
			// Get socket IO streams
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			
			// Get first client command
			String request = new String( getMessage( input ) );
			
			// Process request
			while (!request.equals("quit")) {
				// Initialize StringBuilder
				StringBuilder reply = new StringBuilder();
				// String invalid = "Invalid command\n";
				
				// Process command
				// TODO add actual command handling
				
				// Build reply string
				reply.append(request);
				reply.append('\n');
				
				// Send reply and get next command
				putMessage( output, reply.toString().getBytes() );
				request = new String( getMessage( input ) );
			}
		} catch (IOException ioe) {
			System.err.println(String.format("IO Error: %s", ioe));
		} catch (Exception e) {
			System.err.println(String.format("General Error: %s", e));
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
				// Do nothing
			}
		}
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
		/*while (true) {
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
		}*/
		try {
			while (true) {
				// Accept new client connection
				final Socket sock = serverSocket.accept();
				
				// Handle client
				Thread t = new Thread() {
					public void run() {
						handleClient(sock);
					}
				};
				t.start();
			}
		} catch (IOException ioe) {
			System.err.println(String.format("Can't accept client %s", ioe));
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				// Do nothing
			}
		}
	}
	
	public static void main(String[] args) {
		// Make a Host object to use non-statics
		Host host = new Host();
		host.run(args);
	}

}
