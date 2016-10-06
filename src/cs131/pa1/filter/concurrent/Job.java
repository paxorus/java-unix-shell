package cs131.pa1.filter.concurrent;

import java.util.List;

/**
 * This class is an abstraction of a bundle of threads. In this application,
 * we expect the bundle to represent a single line of piped commands into the
 * shell, each linked shell command object (e.g., GrepFilter, LsFilter) a
 * distinct thread.
 * 
 * @author Prakhar Sahay
 * @see JobManager
 */

public class Job {
	
	private String command;
	private List<Thread> chain;
	private Thread finalThread;
	private boolean isBackground;
	
	public Job(String command) {
		this.command = command;
	}
	
	/**
	 * Sets the internal bundle of threads.
	 * 
	 * @param chain - bundle of threads
	 * @see ConcurrentCommandBuilder
	 */
	public void setChain(List<Thread> chain) {
		this.chain = chain;
		this.finalThread = chain.get(chain.size() - 1);
	}
	
	/**
	 * Returns whether the job is alive or terminated.
	 * Ideally it would check that at least one thread is alive, but in this 
	 * application it is sufficient to check just the state of the last thread.
	 * 
	 * @return whether job is alive
	 */
	public boolean isAlive() {
		return finalThread.isAlive();
	}
	
	/**
	 * Utility method for printing the job.
	 */
	public String toString() {
		return command;
	}
	
	/**
	 * Sets the isBackground flag.
	 * 
	 * @param val - new value of background flag.
	 * @see ConcurrentCommandBuilder
	 */
	public void isBackground(boolean val) {
		this.isBackground = val;
	}
	
	/**
	 * Gets the isBackground flag.
	 * 
	 * @return whether this job should be run in the background
	 */
	public boolean isBackground() {
		return this.isBackground;
	}
	
	/**
	 * Starts each job thread.
	 */
	public void start() {
		Thread.UncaughtExceptionHandler eh = new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread x, Throwable ex) {
				// safely print Message, then interrupt all threads
				System.out.print(ex.getMessage());
				for (Thread t : chain) {
					t.interrupt();
				}
			}
		};
		
		for (Thread t : chain) {
			t.setUncaughtExceptionHandler(eh);
			t.start();
		}
	}
	
	/**
	 * Waits for all job threads to die.
	 * @see JobManager
	 */
	public void waitUntilDone() {
		try {
			for (Thread t : chain) {
				t.join();
			}
		} catch (InterruptedException ie) {
			// silently return to REPL
		}
	}
}
