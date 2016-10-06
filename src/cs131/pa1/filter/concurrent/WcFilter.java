package cs131.pa1.filter.concurrent;

import cs131.pa1.filter.Message;

public class WcFilter extends ConcurrentFilter {
	
	private int lineCount, wordCount, charCount;
	
	public WcFilter(String command) {
		this.command = command;
	}
	
	@Override
	public void run() {
		if (input == null) {
			error(Message.REQUIRES_INPUT);
		}
		super.run();
	}
	
	@Override
	public void finish() {
		String out = lineCount + " " + wordCount + " " + charCount;
		try {
			output.put(out);
			super.finish();
		} catch (Exception ex) {
		}
	}
	
	@Override
	protected String processLine(String line) {
		lineCount ++;
		wordCount += line.split(" ").length;
		charCount += line.length();
		return null;
	}
}
