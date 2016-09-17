package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;

public class SequentialCommandBuilder {
	
	private static final String[] NO_OUTPUT = {"cd"};
	
	public static List<SequentialFilter> createFiltersFromCommand(String command){
		LinkedList<SequentialFilter> filters = new LinkedList<SequentialFilter>();
			
		command = adjust(command);
		
		SequentialFilter prev = null;
		for (String subCommand : command.split(" \\| ")) {
			SequentialFilter filter = constructFilterFor(subCommand);
			if (prev != null) {
				// this ensures a method is called on each filter
				filter.setPrevFilter(prev);
			}
			filters.add(filter);
			prev = filter;
		}
		return filters;
	}
	
	private static String adjust(String command) {
		command = command.trim();// leading, trailing whitespace removed
		command = command.replaceAll("\\|", " | ");// so sub-commands don't need trimming
		command = command.replaceAll("\\s+", " ");// interior whitespace corrected
		return setOutputStream(command);// also ensures a space between ">" and the output stream
	}
		
	private static String setOutputStream(String command) {
		int idx = command.lastIndexOf(">");
		if (idx > -1) {
			command = command.substring(0, idx) + " | > " + command.substring(idx + 1);
			return command;
		}	
		if (needsOutputStream(command)) {
			// no file specified, target to System.out
			return command + " | > %";
		}
		
		// should not get the default stream
		return command;
	}
	
	private static boolean needsOutputStream(String command){
		// determine whether the command will have output
		String[] commands = command.split(" \\| ");
		String lastCommand = commands[commands.length - 1];
		String lastOp = lastCommand.split(" ")[0].toLowerCase();
		
		return !Arrays.asList(NO_OUTPUT).contains(lastOp);
	}
	
	private static SequentialFilter constructFilterFor(String subCommand) {
		String[] parts = subCommand.split(" ");
		String op = parts[0].toLowerCase();
		switch (op) {
			case "cd":
//				check
				return new CdFilter(subCommand);
			case "ls":
				return new LsFilter(subCommand);
			case "pwd":
				return new PwdFilter(subCommand);
			case ">":
				return new StreamFilter(subCommand);
			default:
				throw new RuntimeException(Message.COMMAND_NOT_FOUND.with_parameter(parts[0]));
		}
	}

//	private static void linkFilters(List<SequentialFilter> filters){
//		for (int i = 0; i < filters.size() - 1; i ++) {
//			filters.get(i).setNextFilter(filters.get(i + 1));
//		}
//	}
}
