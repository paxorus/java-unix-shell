package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;
import cs131.pa1.filter.Filter;

public class PwdFilter extends SequentialFilter {
	
	public PwdFilter(String command) {
		this.command = command;
		if (command.split(" ").length > 1) {
			error(Message.CANNOT_HAVE_PARAMETER);
		}
	}
	
	@Override
	public void process() {
		String pwd = SequentialREPL.currentWorkingDirectory;
		output.add(pwd);
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
