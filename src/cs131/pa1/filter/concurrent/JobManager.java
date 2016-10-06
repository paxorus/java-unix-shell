package cs131.pa1.filter.concurrent;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;


public class JobManager {
	
	private Queue<Job> jobQueue;

	public JobManager() {
		this.jobQueue = new LinkedList<Job>();
	}
	
	public void add(String command) {

		try {
			Job job = ConcurrentCommandBuilder.createFiltersFromCommand(command);// Message exceptions
			jobQueue.add(job);
			
			job.start();
			if (!job.isBackground()) {
				job.waitUntilDone();
			}
		} catch (Throwable ex) {
			// safely print Message
			print(ex.getMessage());
		}
	}
	
	public void dump() {
		// prints to console
		Iterator<Job> it = jobQueue.iterator();
		int i = 1;
		while (it.hasNext()) {
			Job job = it.next();
			if (job.isDone()) {
				it.remove();
			} else {
				System.out.printf("\t%d. %s", i, job);// 4. ls > hello.txt &
				i ++;
			}
		}
	}
	
	// alias
	public static void print(Object o) {
		System.out.print(o);
	}
}
