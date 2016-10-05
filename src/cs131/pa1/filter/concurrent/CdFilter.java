package cs131.pa1.filter.concurrent;

import cs131.pa1.filter.Message;

import java.io.File;
import java.io.IOException;

import cs131.pa1.filter.Filter;

public class CdFilter extends ConcurrentFilter {
	
	private String path;
	
	public CdFilter(String command) {
		this.command = command;
		
		// check args
		String[] params = command.split(" ");
		if (params.length > 2) {
			error(Message.INVALID_PARAMETER);
		} else if (params.length == 1) {
			error(Message.REQUIRES_PARAMETER);
		}
		
		path = params[1].replaceAll("\\*", " ");
	}
	
	@Override
	public void process() {

		String pwd = ConcurrentREPL.currentWorkingDirectory;

		File f = new File(pwd + FILE_SEPARATOR + path);
		if (!f.isDirectory()) {
			error(Message.DIRECTORY_NOT_FOUND);
		}
		try {
			ConcurrentREPL.currentWorkingDirectory = f.toPath().toRealPath().toString();
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
