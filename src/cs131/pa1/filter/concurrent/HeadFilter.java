package cs131.pa1.filter.concurrent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class HeadFilter extends ConcurrentFilter {
	
	private final int DEFAULT_HEAD_SIZE = 10;
	private int headSize;
	private String fileName;
	
	public HeadFilter(String command) {
		this.command = command;
		String[] params = command.split(" ");
		
		headSize = DEFAULT_HEAD_SIZE;
		
		if (params.length == 3) {
			String sizeArg = params[1].substring(1);
			try {
				headSize = Integer.parseInt(sizeArg);
			} catch (Exception ex) {
				error(Message.INVALID_PARAMETER);
			}
			fileName = params[2];
		} else if (params.length == 2) {
			fileName = params[1];
			if (fileName.startsWith("-")) {
				error(Message.REQUIRES_PARAMETER);
			}
		} else if (params.length == 1){
			error(Message.REQUIRES_PARAMETER);
		} else {
			error(Message.INVALID_PARAMETER);
		}
		
	}
	
	@Override
	public void run() {
		
		String pwd = ConcurrentREPL.currentWorkingDirectory + FILE_SEPARATOR;
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(pwd + fileName)))) {
			
			String line;
			for (int i = 0; i < headSize && ((line = reader.readLine()) != null); i ++) {
				output.put(line);
			}
			finish();
		} catch (FileNotFoundException fnfex) {
			error(Message.FILE_NOT_FOUND);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	protected String processLine(String line) {
		return null;
	}
	
	@Override
	public void setPrevFilter(Filter f) {
		error(Message.CANNOT_HAVE_INPUT);
	}
}
