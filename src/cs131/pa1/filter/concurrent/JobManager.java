package cs131.pa1.filter.concurrent;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class is an abstraction of the Job class. It implements add() to take
 * new user input to create a new job and add it to the internal job queue and
 * dump() to display the contents of the queue.
 * 
 * @author Prakhar Sahay
 * @see Job
 */

public class JobManager {
	
	private Queue<Job> jobQueue;

	public JobManager() {
		this.jobQueue = new LinkedList<Job>();
	}
	
	/**
	 * Adds a job to the internal job queue
	 * 
	 * @param command - raw user input string
	 */
	public void add(String command) {

		try {
			Job job = ConcurrentCommandBuilder.createJob(command);// Message exceptions
			jobQueue.add(job);
			
			// core implementation of background job functionality
			job.start();
			if (!job.isBackground()) {
				job.waitUntilDone();
			}
		} catch (Throwable ex) {
			// safely print Message
			System.out.print(ex.getMessage());
		}
	}
	
	/**
	 * Removes all completed jobs and prints alive jobs to console
	 */
	public void dump() {
		Iterator<Job> it = jobQueue.iterator();
		int i = 1;
		while (it.hasNext()) {
			Job job = it.next();
			if (job.isAlive()) {
				System.out.printf("\t%d. %s\n", i, job);// 4. ls > hello.txt &
				i ++;
			} else {
				it.remove();
			}
		}
	}
}
