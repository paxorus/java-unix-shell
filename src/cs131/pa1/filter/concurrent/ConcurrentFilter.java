package cs131.pa1.filter.concurrent;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;


public abstract class ConcurrentFilter extends Filter implements Runnable {
	
	protected BlockingQueue<String> input;
	protected BlockingQueue<String> output;
	protected String command;
	
	@Override
	public void setPrevFilter(Filter prevFilter) {
		prevFilter.setNextFilter(this);
	}
	
	@Override
	public void setNextFilter(Filter nextFilter) {
		if (nextFilter instanceof ConcurrentFilter){
			ConcurrentFilter concurrentNext = (ConcurrentFilter) nextFilter;
			this.next = concurrentNext;
			concurrentNext.prev = this;
			if (this.output == null){
				this.output = new LinkedBlockingQueue<String>();
			}
			concurrentNext.input = this.output;
		} else {
			throw new RuntimeException("Should not attempt to link dissimilar filter types.");
		}
	}
	
	public void run() {
		String line;
		try {
			while ((line = input.take()) != PoisonPill.X){
				String processedLine = processLine(line);
				if (processedLine != null){
					output.put(processedLine);
				}
			}
			finish();
		} catch (Exception ex) {
		}
	}
	
	public void finish() {
		// poison pill protocol
		try {
			if (output != null) {
				output.put(PoisonPill.X);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
		
	protected abstract String processLine(String line);
	
	// added by Prakhar, see ConcurrentREPL:execute() for the exception handler
	protected void error(Message message) {
		throw new RuntimeException(message.with_parameter(command));
	}
	
}
