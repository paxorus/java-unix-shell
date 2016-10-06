package cs131.pa1.filter.concurrent;

import java.util.List;

public class Job {
	
	private String command;
	private List<Thread> chain;
	private Thread finalThread;
	private boolean isBackground;
	
	public Job(String command) {
		this.command = command;
	}
	
	public void setChain(List<Thread> chain) {
		this.chain = chain;
		this.finalThread = chain.get(chain.size() - 1);
	}
	
	public boolean isDone() {
		return finalThread.getState() == Thread.State.TERMINATED;
	}
	
	public String toString() {
		return command;
	}
	
	public void isBackground(boolean val) {
		this.isBackground = val;
	}
	
	public boolean isBackground() {
		return this.isBackground;
	}
	
	public void start() {
		Thread.UncaughtExceptionHandler eh = new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread x, Throwable ex) {
				// safely print Message
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
