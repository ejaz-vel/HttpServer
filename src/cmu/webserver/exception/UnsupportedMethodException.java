/**
 * 
 */
package cmu.webserver.exception;

/**
 * @author apurv
 *
 * Exception to be thrown if the method is anything else except 
 * for GET or HEAD
 */
public class UnsupportedMethodException extends Exception {

	/**
	 * @param message
	 */
	public UnsupportedMethodException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public UnsupportedMethodException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UnsupportedMethodException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public UnsupportedMethodException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
