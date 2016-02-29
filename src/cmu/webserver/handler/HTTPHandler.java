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
import cmu.webserver.model.HTTPResponseCode;
import cmu.webserver.model.HTTPVersion;
import cmu.webserver.parser.HTTPRequestParser;

public class HTTPHandler implements Runnable {

	private Socket clientSock;

	public HTTPHandler(Socket clientSock) {
		this.clientSock = clientSock;
	}

	@Override
	public void run() {
		BufferedReader inStream = null;
		PrintWriter outStream = null;
		HTTPResponse response;
		try {
			System.out.println("Entered Thread");
			inStream = new BufferedReader(new InputStreamReader(
					clientSock.getInputStream()));
			outStream = new PrintWriter(new OutputStreamWriter(clientSock.getOutputStream()));
			List<String> requestLines = getRequestLines(inStream);
			HTTPRequestDetails request = new HTTPRequestParser().parseRequest(requestLines);
			response = new ResponseHandler().handleRequest(request);
		} catch (Exception e) {
			response = new HTTPResponse();
			response.setResponseCode(HTTPResponseCode.HTTP_500);
			response.setVersion(HTTPVersion.HTTP1_0);
			response.setBody(e.getMessage());
		}
		
		try {
			outStream.println(response.toString());
			outStream.flush();
			inStream.close();
			outStream.close();
			clientSock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
