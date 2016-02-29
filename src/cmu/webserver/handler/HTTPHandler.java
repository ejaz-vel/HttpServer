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

import cmu.webserver.exception.InvalidHttpRequestException;
import cmu.webserver.exception.UnsupportedMethodException;
import cmu.webserver.model.HTTPErrorResponse;
import cmu.webserver.model.HTTPRequestDetails;
import cmu.webserver.model.HTTPResponse;
import cmu.webserver.model.SynchronizedCounter;
import cmu.webserver.model.HTTPResponseCode;
import cmu.webserver.parser.HTTPRequestParser;

public class HTTPHandler implements Runnable {

	private Socket clientSock;
	PrintWriter outStream = null;
	BufferedReader inStream = null;
	
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
			outStream.println(response.toString());
			outStream.flush();
		} catch(InvalidHttpRequestException e) {
			e.printStackTrace();
			String message = "Bad request - The request was Malformed.";
			HTTPErrorResponse errorResponse = new HTTPErrorResponse(HTTPResponseCode.HTTP_400,message);
			outStream.println(errorResponse.toString());
			outStream.flush();
		} catch(UnsupportedMethodException e) {
			e.printStackTrace();
			String message = e.getMessage();
			HTTPErrorResponse errorResponse = new HTTPErrorResponse(HTTPResponseCode.HTTP_501,message);
			outStream.println(errorResponse.toString());
			outStream.flush();
		} catch (Throwable e) {
			e.printStackTrace();
			String message = "The server encountered an unexpected error.";
			HTTPErrorResponse errorResponse = new HTTPErrorResponse(HTTPResponseCode.HTTP_500,message);
			outStream.println(errorResponse.toString());
			outStream.flush();
		} finally {
		    try {
		    	if(inStream!=null)
		    		inStream.close();
		    	if(outStream!=null)
		    		outStream.close();
		    	if(clientSock!=null)
		    		clientSock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    /* Increase number of threads */
		    //SynchronizedCounter.getInstance().increment();
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
