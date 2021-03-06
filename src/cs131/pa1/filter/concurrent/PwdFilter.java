package cs131.pa1.filter.concurrent;

import cs131.pa1.filter.Message;
import cs131.pa1.filter.Filter;

public class PwdFilter extends ConcurrentFilter {
	
	public PwdFilter(String command) {
		this.command = command;
		if (command.split(" ").length > 1) {
			error(Message.CANNOT_HAVE_PARAMETER);
		}
	}
	
	@Override
	public void run() {
		String pwd = ConcurrentREPL.currentWorkingDirectory;
		try {
			output.put(pwd);
			finish();
		} catch (Exception ex) {
			System.out.println(ex);
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
