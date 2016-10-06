package cs131.pa1.filter.concurrent;

import java.io.File;
import java.io.PrintStream;
import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class StreamFilter extends ConcurrentFilter {
		
	private PrintStream printer;
	
	public StreamFilter(String command) {
		this.command = command;
		// check args
		String[] params = command.split(" ");
		if (params.length > 2) {
			error(Message.INVALID_PARAMETER);
		} else if (params.length == 1) {
			error(Message.REQUIRES_PARAMETER);
		}
	}
	
	private void initOutputStream(String target) {
		if (target.equals("%")) {
			printer = System.out;
		} else if (target.equals("%%")) {
			printer = null;
		} else {
			String pwd = ConcurrentREPL.currentWorkingDirectory + FILE_SEPARATOR;
			File f = new File(pwd + target);
			try {
				f.createNewFile();
				printer = new PrintStream(f);
			} catch (Exception ex) {
			}
		}
	}
	
	@Override
	public void run() {
		String target = command.split(" ")[1];
		initOutputStream(target);

		if (input == null) {
			error(Message.REQUIRES_INPUT);
		}
		super.run();
	}
	
	@Override
	public void finish() {

		if (printer != System.out && printer != null) {
			printer.close();
		}
	}
		
	protected String processLine(String line) {
		// background job with no output file
		if (printer != null) {
			printer.println(line);
		}
		return null;
	}
	
	public void setNextFilter(Filter f) {
		error(Message.CANNOT_HAVE_OUTPUT);
	}
}
