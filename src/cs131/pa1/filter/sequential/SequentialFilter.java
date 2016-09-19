package cs131.pa1.filter.sequential;
import java.util.LinkedList;
import java.util.Queue;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;


public abstract class SequentialFilter extends Filter {
	
	protected Queue<String> input;
	protected Queue<String> output;
	protected String command;
	
	@Override
	public void setPrevFilter(Filter prevFilter) {
		prevFilter.setNextFilter(this);
	}
	
	@Override
	public void setNextFilter(Filter nextFilter) {
		if (nextFilter instanceof SequentialFilter){
			SequentialFilter sequentialNext = (SequentialFilter) nextFilter;
			this.next = sequentialNext;
			sequentialNext.prev = this;
			if (this.output == null){
				this.output = new LinkedList<String>();
			}
			sequentialNext.input = this.output;
		} else {
			throw new RuntimeException("Should not attempt to link dissimilar filter types.");
		}
	}
	
	public void process(){
		while (!input.isEmpty()){
			String line = input.poll();
			String processedLine = processLine(line);
			if (processedLine != null){
				output.add(processedLine);
			}
		}	
	}
	
	@Override
	public boolean isDone() {
		return input.size() == 0;
	}
	
	protected abstract String processLine(String line);
	
	// added by Prakhar, see SequentialREPL:execute() for the exception handler
	protected void error(Message message) {
		throw new RuntimeException(message.with_parameter(command));
	}
	
}
