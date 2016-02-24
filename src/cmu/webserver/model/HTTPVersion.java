package cmu.webserver.model;

public enum HTTPVersion {
	HTTP1_0("HTTP/1.0"),
	HTTP1_1("HTTP/1.1"),
	HTTP2_0("HTTP/2.0");
	
	private String versionNumber;
	
	HTTPVersion(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getVersionNumber() {
		return versionNumber;
	}
	
}
