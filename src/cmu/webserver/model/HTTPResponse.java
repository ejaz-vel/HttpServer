package cmu.webserver.model;

import java.util.HashMap;
import java.util.Map;

public class HTTPResponse {
	private HTTPVersion version;
	private HTTPResponseCode responseCode;
	private Map<String, String> responseFields = new HashMap<>();
	private String body;
	
	
	public HTTPResponse() {
		super();
	}

	public HTTPResponse(HTTPVersion version, HTTPResponseCode responseCode, String body) {
		super();
		this.version = version;
		this.responseCode = responseCode;
		this.body = body;
	}

	public HTTPVersion getVersion() {
		return version;
	}
	
	public void setVersion(HTTPVersion version) {
		this.version = version;
	}
	
	public HTTPResponseCode getResponseCode() {
		return responseCode;
	}
	
	public void setResponseCode(HTTPResponseCode responseCode) {
		this.responseCode = responseCode;
	}
	
	public String getValue(String key) {
		return responseFields.get(key);
	}
	
	public void addField(String key, String value) {
		this.responseFields.put(key,value);
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(version.getVersionNumber() + " ");
		sb.append(responseCode.getHttpCode() + " ");
		sb.append(responseCode.getHttpMessage());
		sb.append("\r\n");
		
		for (String key: responseFields.keySet()) {
			sb.append(key + ": ");
			sb.append(responseFields.get(key));
			sb.append("\r\n");
		}
		sb.append("\r\n");
		sb.append(body);
		return sb.toString();
	}
	
}
