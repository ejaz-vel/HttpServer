package cmu.webserver.model;

public enum HTTPResponseCode {
	HTTP_200(200, "OK"),
	HTTP_400(400, "Bad Request"),
	HTTP_404(404, "Resource Not Found"),
	HTTP_500(500, "Internal Server Error"),
	HTTP_501(501, "Not Implemented"),
	HTTP_503(503, "Service Unavailable");
	
	private Integer httpCode;

	private String httpMessage;
	
	HTTPResponseCode(Integer httpCode, String httpMessage) {
		this.httpCode = httpCode;
		this.httpMessage = httpMessage;
	}
	
	public Integer getHttpCode() {
		return httpCode;
	}

	public String getHttpMessage() {
		return httpMessage;
	}

}
