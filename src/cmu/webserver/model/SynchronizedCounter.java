/**
 * 
 */
package cmu.webserver.model;

/**
 * @author apurv
 *
 */
public class SynchronizedCounter {

	private static int noOfAllowedThreads = 10;
	private static SynchronizedCounter synchronizedCounter = null;
	
	private SynchronizedCounter() {
		
	}
	
	/**
	 * Implementing a singleton model.
	 * 
	 * @return
	 */
	public static SynchronizedCounter getInstance() {
		return synchronizedCounter==null?new SynchronizedCounter():synchronizedCounter;
	}
    public synchronized void increment() {
        noOfAllowedThreads++;
    }

    public synchronized void decrement() {
        noOfAllowedThreads--;
    }

    public synchronized int value() {
        return noOfAllowedThreads;
    }
    
    public synchronized boolean areThreadsAvailable() {
    	return noOfAllowedThreads>0;
    }
}
