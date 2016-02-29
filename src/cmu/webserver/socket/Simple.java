/**
 * @file: Server.java
 * 
 * @author: Chinmay Kamat <chinmaykamat@cmu.edu>
 * 
 * @date: Feb 15, 2013 1:13:37 AM EST
 * 
 */

package cmu.webserver.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cmu.webserver.constants.ServerConstants;
import cmu.webserver.handler.HTTPHandler;

public class Simple {
	private static ServerSocket srvSock;
	
	public static void main(String args[]) {
		int port = 8080;
		/* Parse parameter and do args checking */
		if (args.length < 1) {
			System.err.println("Usage: java Server <port_number>");
			System.exit(1);
		}

		try {
			port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			System.err.println("Usage: java Server <port_number>");
			System.exit(1);
		}

		if (port > 65535 || port < 1024) {
			System.err.println("Port number must be in between 1024 and 65535");
			System.exit(1);
		}
		
		if(args.length==1) {
			ServerConstants.PATH =  System.getProperty("user.dir");
			ServerConstants.PATH += "/www";
		} else {
			ServerConstants.PATH = args[1];
		}

		try {
			/*
			 * Create a socket to accept() client connections. This combines
			 * socket(), bind() and listen() into one call. Any connection
			 * attempts before this are terminated with RST.
			 */
			srvSock = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Unable to listen on port " + port);
			System.exit(1);
		}

		while (true) {
			Socket clientSock;
			try {
				/*
				 * Get a sock for further communication with the client. This
				 * socket is sure for this client. Further connections are still
				 * accepted on srvSock
				 */
				System.out.println("\nWaiting for client request");
				clientSock = srvSock.accept();
				System.out.println("Accpeted new connection from "
						+ clientSock.getInetAddress() + ":"
						+ clientSock.getPort());
				Thread thread = new Thread(new HTTPHandler(clientSock));
				thread.start();
			} catch (IOException e) {
				continue;
			}
		}
	}
}
