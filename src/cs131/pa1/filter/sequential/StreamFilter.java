package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.PrintStream;
import cs131.pa1.filter.Message;

public class StreamFilter extends SequentialFilter {
	
	private PrintStream printer;
	
	public StreamFilter(String command) {
		// check args
		String[] params = command.split(" ");
		if (params.length > 2) {
			throw new RuntimeException(Message.INVALID_PARAMETER.with_parameter(">"));
		}
		if (params.length == 0) {
			throw new RuntimeException(Message.REQUIRES_PARAMETER.with_parameter(">"));
		}
		
		initOutputStream(params[1]);
	}
	
	private void initOutputStream(String target) {
		if (target.equals("%")) {
			printer = System.out;
		} else {
			File f = new File(target);
			try {
				f.createNewFile();
				printer = new PrintStream(f);
			} catch (Exception ex) {
				// IO, Security exception
			}
		}
	}
		
	protected String processLine(String line) {
		printer.println(line);
		return null;
	}
}
