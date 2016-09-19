package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.PrintStream;
import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class StreamFilter extends SequentialFilter {
		
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
		} else {
			String pwd = SequentialREPL.currentWorkingDirectory + FILE_SEPARATOR;
			File f = new File(pwd + target);
			try {
				f.createNewFile();
				printer = new PrintStream(f);
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
	}
	
	public void process() {
		String target = command.split(" ")[1];
		initOutputStream(target);

		if (input == null) {
			error(Message.REQUIRES_INPUT);
		}
		super.process();
		if (printer != System.out) {
			printer.close();
		}
	}
		
	protected String processLine(String line) {
		printer.println(line);
		return null;
	}
	
	public void setNextFilter(Filter f) {
		error(Message.CANNOT_HAVE_OUTPUT);
	}
}
