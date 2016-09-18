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
		if (FILE_SEPARATOR.equals("/")) {
			path = path.replaceAll("\\\\", "/");
		} else if (FILE_SEPARATOR.equals("\\")) {
			path = path.replaceAll("/", "\\\\");
		}
		if (!path.startsWith(FILE_SEPARATOR)) {
			// "poo/" --> "/poo/"
			path = FILE_SEPARATOR + path;
		}
		if (path.endsWith(FILE_SEPARATOR)) {
			// "/poo/" --> "/poo"
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
		
		pwd = compact(pwd + path);
		if (!(new File(pwd)).isDirectory()) {
			throw new RuntimeException(Message.DIRECTORY_NOT_FOUND.with_parameter("cd"));
		}
		
		SequentialREPL.currentWorkingDirectory = pwd;
	}
	
	private String compact(String path) {

		// eliminate all "/."
		path = path.replaceAll(getSep() + "\\." + getSep(), getSep());
		if (path.endsWith(FILE_SEPARATOR + ".")) {
			System.out.println("here");
			path = path.substring(0, path.length() - FILE_SEPARATOR.length() - 1);
		}
		
		// eliminate all "/x/.."
		int idx = path.indexOf("..");
		while (idx != -1) {
			// path is ".../x/../..."
			String inspect = path.substring(0, idx - FILE_SEPARATOR.length());// ".../x"
			int endIdx = inspect.lastIndexOf(FILE_SEPARATOR);
			path = path.substring(0, endIdx) + path.substring(idx + 2);// "..." + "/..."
			idx = path.indexOf("..");
		}
		

		
		return path;
	}
	
	private String getSep() {
		// regex form of the separator, introduces double-escape
		return (FILE_SEPARATOR.equals("\\")? "\\\\" : "/");
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
