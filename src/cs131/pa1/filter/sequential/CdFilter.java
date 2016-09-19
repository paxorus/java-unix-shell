package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;

import java.io.File;
import java.io.IOException;

import cs131.pa1.filter.Filter;

public class CdFilter extends SequentialFilter {
	
	private String path;
	
	public CdFilter(String command) {
		// check args
		String[] params = command.split(" ");
		if (params.length > 2) {
			throw new RuntimeException(Message.INVALID_PARAMETER.with_parameter(">"));
		}
		if (params.length == 0) {
			throw new RuntimeException(Message.REQUIRES_PARAMETER.with_parameter(">"));
		}
		
		path = params[1].replaceAll("\\*", " ");
	}
	
	@Override
	public void process() {

		String pwd = SequentialREPL.currentWorkingDirectory;

		File f = new File(pwd + FILE_SEPARATOR + path);
		if (!f.isDirectory()) {
			error(Message.DIRECTORY_NOT_FOUND);
		}
		try {
			SequentialREPL.currentWorkingDirectory = f.toPath().toRealPath().toString();
		} catch (IOException ioex) {
			System.out.println(ioex);
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
	
	@Override
	public void setNextFilter(Filter f) {
		error(Message.CANNOT_HAVE_OUTPUT);
	}
}
