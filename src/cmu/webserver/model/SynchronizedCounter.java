/**
 * 
 */
package cmu.webserver.model;

/**
 * @author apurv
 *
 */
public class SynchronizedCounter {

	private static int noOfAllowedThreads = 1000;
	private static SynchronizedCounter synchronizedCounter = null;
	
	private SynchronizedCounter() {
		
	}
	
	/**
	 * Implementing a singleton model.
	 * 
	 * @return
	 */
	public static SynchronizedCounter getInstance() {
		if(synchronizedCounter==null) {
			synchronizedCounter = new SynchronizedCounter();
		}
		return synchronizedCounter;
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
