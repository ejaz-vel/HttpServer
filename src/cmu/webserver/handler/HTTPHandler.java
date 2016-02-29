package cmu.webserver.handler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
			PrintWriter outStream = new PrintWriter(new OutputStreamWriter(clientSock.getOutputStream()));
			List<String> requestLines = getRequestLines(inStream);
			System.out.println(requestLines);
			HTTPRequestDetails request = new HTTPRequestParser().parseRequest(requestLines);
			HTTPResponse response = new ResponseHandler().handleRequest(request);
			outStream.println(response.toString());
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
			if (containsAlphaNumCharacters(input)) {
				requestLines.add(input.replaceAll("\\p{C}", ""));
			}
		}
		return requestLines;
	}

	private boolean containsAlphaNumCharacters(String input) {
		for (char ch: input.toCharArray()) {
			if (Character.isLetterOrDigit(ch)) {
				return true;
			}
		}
		return false;
	}
}
