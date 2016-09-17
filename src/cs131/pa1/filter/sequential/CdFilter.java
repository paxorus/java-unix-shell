package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;

import java.io.File;

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
		path = params[1];
		
		// correct path
		path = path.replaceAll("[\\/]", FILE_SEPARATOR);
		if (!path.startsWith(FILE_SEPARATOR)) {
			path = FILE_SEPARATOR + path;
		}
		if (path.endsWith(FILE_SEPARATOR)) {
			path = path.substring(0, path.length() - 1);
		}
	}
	
	@Override
	public void process() {
		if (path.equals(FILE_SEPARATOR + ".")) {
			return;
		}

		String pwd = SequentialREPL.currentWorkingDirectory;

		if (path.equals(FILE_SEPARATOR + "..")) {
			int lowestDir = pwd.lastIndexOf(FILE_SEPARATOR);
			path = pwd.substring(0, lowestDir);
			SequentialREPL.currentWorkingDirectory = path;
			return;
		}
		
		pwd += path;
		
		if (!(new File(pwd)).isDirectory()) {
			throw new RuntimeException(Message.DIRECTORY_NOT_FOUND.with_parameter("cd"));
		}
		
		SequentialREPL.currentWorkingDirectory = pwd;
	}
	
	@Override
	protected String processLine(String s) {
		return null;
	}
	
	@Override
	public void setPrevFilter(Filter f) {
		throw new RuntimeException(Message.CANNOT_HAVE_INPUT.with_parameter("cd"));
	}
	
	@Override
	public void setNextFilter(Filter f) {
		throw new RuntimeException(Message.CANNOT_HAVE_OUTPUT.with_parameter("cd"));
	}
}
