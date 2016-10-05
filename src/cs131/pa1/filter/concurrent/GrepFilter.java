package cs131.pa1.filter.concurrent;

import cs131.pa1.filter.Message;

public class GrepFilter extends ConcurrentFilter {
	
	private String pattern;
	
	public GrepFilter(String command) {
		this.command = command;
		String[] params = command.split(" ");
		if (params.length == 1) {
			error(Message.REQUIRES_PARAMETER);
		} else if (params.length > 2) {
			error(Message.INVALID_PARAMETER);
		}
		pattern = params[1];
	}
	
	public void process() {
		if (input == null) {
			error(Message.REQUIRES_INPUT);
		}
		super.process();
	}
	
	protected String processLine(String line) {
		if (line.contains(pattern)) {
			return line;
		}
		return null;
	}
}
