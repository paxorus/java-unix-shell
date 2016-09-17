package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;
import cs131.pa1.filter.Filter;

public class PwdFilter extends SequentialFilter {
	
	public PwdFilter(String command) {

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
		throw new RuntimeException(Message.CANNOT_HAVE_INPUT.toString());
	}
}
