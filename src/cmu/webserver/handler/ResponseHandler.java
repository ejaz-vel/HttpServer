package cmu.webserver.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import cmu.webserver.constants.ServerConstants;
import cmu.webserver.model.HTTPErrorResponse;
import cmu.webserver.model.HTTPMethod;
import cmu.webserver.model.HTTPRequestDetails;
import cmu.webserver.model.HTTPResponse;
import cmu.webserver.model.HTTPResponseCode;
import cmu.webserver.model.HTTPVersion;
import cmu.webserver.socket.GetMime;

public class ResponseHandler {

	public long getFileSize(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			return file.length();
		}
		return 0;
	}
	
	public HTTPResponse handleRequest(HTTPRequestDetails request) throws IOException {
		HTTPResponse response = new HTTPResponse();
		response.setVersion(HTTPVersion.HTTP1_0);
		response.addField("Server", ServerConstants.SERVER_NAME);
		String fileName;
		if (request.getRelativePath() != null) {
			fileName = request.getRelativePath() + request.getWebPage();
		} else {
			fileName = request.getWebPage();
		}

		if (!request.getMethod().equals(HTTPMethod.GET)
				&& !request.getMethod().equals(HTTPMethod.HEAD)) {
			response.setResponseCode(HTTPResponseCode.HTTP_501);
			return response;
		}

		try {
			response.addField("Content-Length", String.valueOf(getFileSize(fileName)));
			response.addField("Content-Type", GetMime.getMimeType(fileName));
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			if (request.getMethod().equals(HTTPMethod.GET)) {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();
				while (line != null) {
					sb.append(line + "\n");
					line = br.readLine();
				}
				response.setBody(sb.toString());
			}
			br.close();
			response.setResponseCode(HTTPResponseCode.HTTP_200);
			response.addField("Connection", "close");
		} catch (IOException e) {
			response = new HTTPErrorResponse(HTTPResponseCode.HTTP_404, "The requested page is not found.");
			e.printStackTrace();
		} catch (Exception e) {
			throw e;
		}
		return response;
	}

}
