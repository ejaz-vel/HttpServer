package cmu.webserver.model;

import cmu.webserver.constants.ServerConstants;

public class HTTPErrorResponse extends HTTPResponse {
	public HTTPErrorResponse(HTTPResponseCode responseCode, String message) {
		this.setVersion(HTTPVersion.HTTP1_0);
		this.setResponseCode(responseCode);
		this.setBody(constructErrorBody(message));
		setHeaders();
	}
	
	public String constructErrorBody(String message) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<html><body>");
		stringBuffer.append(message);
		stringBuffer.append("</body></html>");
		return stringBuffer.toString();
	}
	
	public void setHeaders() {
		this.addField("Server", ServerConstants.SERVER_NAME);
		this.addField("Content-Type","text/html");
		this.addField("Content-Length", this.getBody().length()+"");
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

}
