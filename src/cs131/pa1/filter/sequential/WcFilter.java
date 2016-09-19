package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;

public class WcFilter extends SequentialFilter {
	
	private int lineCount;
	private int wordCount;
	private int charCount;
	
	public WcFilter(String command) {
		this.command = command;
	}
	
	@Override
	public void process() {
		if (input == null) {
			error(Message.REQUIRES_INPUT);
		}
		
		super.process();
		String out = lineCount + " " + wordCount + " " + charCount;
		output.add(out);
	}
	
	@Override
	protected String processLine(String line) {
		lineCount ++;
		wordCount += line.split(" ").length;
		charCount += line.length();
		return null;
	}
}
