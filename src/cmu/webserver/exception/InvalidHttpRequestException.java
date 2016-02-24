/**
 * 
 */
package cmu.webserver.exception;

/**
 * @author apurv
 *
 * Exception to be thrown if the request is syntactically
 * incorrect
 */
public class InvalidHttpRequestException extends Exception {

	/**
	 * @param message
	 */
	public InvalidHttpRequestException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public InvalidHttpRequestException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidHttpRequestException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InvalidHttpRequestException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
