package cmu.webserver.model;

public enum HTTPMethod {
	GET("GET"),
	HEAD("HEAD");

	private String methodName;

	HTTPMethod(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public static HTTPMethod fromString(String text) {
		if (text != null) {
			for (HTTPMethod method : HTTPMethod.values()) {
				if (text.equalsIgnoreCase(method.methodName)) {
					return method;
				}
			}
		}
		return null;
	}
}
