/**
 * @file: Server.java
 * 
 * @author: Chinmay Kamat <chinmaykamat@cmu.edu>
 * 
 * @date: Feb 15, 2013 1:13:37 AM EST
 * 
 */

package cmu.webserver.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import cmu.webserver.handler.HTTPHandler;

public class Server {
	private static ServerSocket srvSock;

	public static void main(String args[]) {
		String buffer = null;
		int port = 8080;
		BufferedReader inStream = null;
		DataOutputStream outStream = null;
		StringBuffer stringBuffer = null;
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
			try {
				inStream = new BufferedReader(new InputStreamReader(
						clientSock.getInputStream()));
				outStream = new DataOutputStream(clientSock.getOutputStream());
				stringBuffer = new StringBuffer();
				/* Read until end of stream */
				String input;
				//buffer = inStream.readLine();
				while(inStream.ready() && (input=inStream.readLine())!=null) {
						stringBuffer.append(input);
						stringBuffer.append("\r\n");
				}
				/* Parse the request */
				System.out.println(stringBuffer.toString());
				System.out.println("Read from client "
						+ clientSock.getInetAddress() + ":"
						+ clientSock.getPort() + " " + stringBuffer.toString());
				/*
				 * Echo the data back and flush the stream to make sure that the
				 * data is sent immediately
				 */
				outStream.writeBytes(stringBuffer.toString());
				outStream.flush();
				/* Interaction with this client complete, close() the socket */
				clientSock.close();
			} catch (IOException e) {
				clientSock = null;
				continue;
			}
		}
	}
}
