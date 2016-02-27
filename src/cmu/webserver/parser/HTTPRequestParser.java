/**
 * 
 */
package cmu.webserver.parser;

import java.util.ArrayList;
import java.util.List;

import cmu.webserver.exception.InvalidHttpRequestException;
import cmu.webserver.exception.UnsupportedMethodException;
import cmu.webserver.model.HTTPMethod;
import cmu.webserver.model.HTTPRequestDetails;

/**
 * @author apurv
 *
 * Class responsible for parsing HTTPRequest that is received from the client.
 */
public class HTTPRequestParser {
	HTTPRequestDetails requestDetails = new HTTPRequestDetails();
	
	public static void main(String args[]) throws InvalidHttpRequestException, UnsupportedMethodException {
		HTTPRequestParser details = new HTTPRequestParser();
		List<String> requestLines = new ArrayList<>();
		requestLines.add("GET /something/asd/index/ HTTP/1.0");
		requestLines.add("HOST: yahoo.com");
		requestLines.add("SOMETHING: somethingelse");
		System.out.println(details.parseRequest(requestLines));
	}
	
	/**
	 * Method responsible for parsing the HTTP request made to the server. Also responsible for 
	 * doing basic syntax checking of the request.
	 * 
	 * @param requestLines
	 * @return
	 * @throws InvalidHttpRequestException
	 * @throws UnsupportedMethodException
	 */
	public HTTPRequestDetails parseRequest(List<String> requestLines) throws 
									InvalidHttpRequestException, UnsupportedMethodException {
		if(requestLines==null || requestLines.size()==0) {
			throw new InvalidHttpRequestException("Status line is absent.");
		}
		String statusLine = requestLines.get(0);
		String[] statusLineArray = statusLine.split(" ");
		/* The status line should contain all three parameters */
		if(statusLineArray.length!=3) {
			throw new InvalidHttpRequestException("Number of arguments in the status line is incorrect.");
		}
		
		System.out.println(statusLineArray[0]);
		requestDetails.setMethod(HTTPMethod.fromString(statusLineArray[0]));
		String relativePath = statusLineArray[1];
		int lastIndex = 0;
		/* If there are more than one "/" that means that we have an additional relative path */
		if((lastIndex=relativePath.lastIndexOf("/"))>0) {
			requestDetails.setRelativePath(relativePath.substring(0, lastIndex));
			requestDetails.setWebPage(relativePath.substring(lastIndex));
		} else {
			requestDetails.setWebPage(relativePath);
		}
		requestLines.remove(0);
		
		/* Parse the HTTP headers */
		for(String requestLine:requestLines) {
			String header[] = requestLine.split(":");
			requestDetails.addHTTPHeader(header[0].trim(), header[1].trim());
		}
		return requestDetails;
	}
}
