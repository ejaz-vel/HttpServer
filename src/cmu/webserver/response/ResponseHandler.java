package cmu.webserver.response;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cmu.webserver.model.HTTPMethod;
import cmu.webserver.model.HTTPRequestDetails;
import cmu.webserver.model.HTTPResponse;
import cmu.webserver.model.HTTPResponseCode;
import cmu.webserver.model.HTTPVersion;
import cmu.webserver.socket.GetMime;

public class ResponseHandler {

	private static final String SERVER_NAME = "Simple/1.0";
	
	public HTTPResponse handleRequest(HTTPRequestDetails request) throws IOException {
		HTTPResponse response = new HTTPResponse();
		response.setVersion(HTTPVersion.HTTP1_0);
		response.addField("Server", SERVER_NAME);
		String fileName = request.getRelativePath() + request.getWebPage();
		
		if (!request.getMethod().equals(HTTPMethod.GET)
				&& !request.getMethod().equals(HTTPMethod.HEAD)) {
			response.setResponseCode(HTTPResponseCode.HTTP_400);
			return response;
		}
		
		try {
			response.addField("Content-Type", GetMime.getMimeType(fileName));
			if (request.getMethod().equals(HTTPMethod.GET)) {
				StringBuilder sb = new StringBuilder();
				BufferedReader br = new BufferedReader(new FileReader(fileName));
				String line = br.readLine();
				while (line != null) {
					sb.append(line);
					line = br.readLine();
				}
				br.close();
				response.setBody(sb.toString());
			}
			response.setResponseCode(HTTPResponseCode.HTTP_200);
		} catch (IOException e) {
			response.setResponseCode(HTTPResponseCode.HTTP_404);
			e.printStackTrace();
		}
		
		return response;
	}

}
