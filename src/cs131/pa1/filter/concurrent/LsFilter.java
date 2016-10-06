package cs131.pa1.filter.concurrent;

import java.io.File;
import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;


public class LsFilter extends ConcurrentFilter {
	
	public LsFilter(String command) {
		this.command = command;
		if (command.split(" ").length > 1) {
			error(Message.CANNOT_HAVE_PARAMETER);
		}
	}
	
	@Override
	public void run() {
		File dir = new File(ConcurrentREPL.currentWorkingDirectory);
		try {
			for (String file : dir.list()) {
				output.put(file);
			}
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
