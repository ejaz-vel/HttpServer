package cmu.webserver.handler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import cmu.webserver.model.HTTPRequestDetails;
import cmu.webserver.model.HTTPResponse;
import cmu.webserver.parser.HTTPRequestParser;

public class HTTPHandler implements Runnable {

	private Socket clientSock;
	
	public HTTPHandler(Socket clientSock) {
		this.clientSock = clientSock;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Entered Thread");
			BufferedReader inStream = new BufferedReader(new InputStreamReader(
					clientSock.getInputStream()));
			DataOutputStream outStream = new DataOutputStream(clientSock.getOutputStream());
			List<String> requestLines = getRequestLines(inStream);
			System.out.println(requestLines);
			HTTPRequestDetails request = new HTTPRequestParser().parseRequest(requestLines);
			HTTPResponse response = new ResponseHandler().handleRequest(request);
			outStream.writeBytes(response.toString());
			outStream.flush();
			inStream.close();
			outStream.close();
			clientSock.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<String> getRequestLines(BufferedReader inStream) throws IOException {
		List<String> requestLines = new ArrayList<>();
		String input;
		while(inStream.ready() && (input=inStream.readLine())!=null) {
			requestLines.add(input);
		}
		return requestLines;
	}
}
