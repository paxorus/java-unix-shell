package cs131.pa1.filter.sequential;

import java.io.File;
import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;


public class LsFilter extends SequentialFilter {
	
	public LsFilter(String command) {
		this.command = command;
		if (command.split(" ").length > 1) {
			error(Message.CANNOT_HAVE_PARAMETER);
		}
	}
	
	@Override
	public void process() {
		File dir = new File(SequentialREPL.currentWorkingDirectory);
		for (String file : dir.list()) {
			output.add(file);
		}
	}
	
	@Override
	protected String processLine(String line) {
		return null;
	}
	
	@Override
	public void setPrevFilter(Filter f) {
		error(Message.CANNOT_HAVE_INPUT);
	}
}
