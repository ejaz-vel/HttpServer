/**
 * 
 */
package cmu.webserver.exception;

/**
 * @author apurv
 * 
 * Exception to be thrown if the version of the request is 
 * anything else except for HTTP/1.0
 */
public class UnsupportedHTTPVersion extends Exception {

	/**
	 * @param message
	 */
	public UnsupportedHTTPVersion(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public UnsupportedHTTPVersion(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UnsupportedHTTPVersion(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public UnsupportedHTTPVersion(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
