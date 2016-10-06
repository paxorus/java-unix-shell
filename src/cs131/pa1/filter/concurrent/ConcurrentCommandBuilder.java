package cs131.pa1.filter.concurrent;

import cs131.pa1.filter.Message;
import java.util.Arrays;
import java.util.LinkedList;

public class ConcurrentCommandBuilder {
	
	private static final String[] NO_OUTPUT = {"cd"};
	
	/**
	 * The only public method in this class, mapping raw user input to a Job.
	 * 
	 * @param command
	 * @return Job
	 * @see JobManager
	 */
	public static Job createJob(String command){

		Job job = new Job(command);
		command = correct(command);
		if (command.endsWith(" &")) {
			command = command.substring(0, command.length() - 2);
			job.isBackground(true);
		}
		command = setOutputStream(command, job.isBackground());

		LinkedList<Thread> filters = new LinkedList<Thread>();
		ConcurrentFilter prev = null;
		for (String subCommand : command.split(" \\| ")) {
			ConcurrentFilter filter = constructFilterFor(subCommand);
			if (prev != null) {
				// this ensures a method is called on each filter
				filter.setPrevFilter(prev);
			}
			filters.add(new Thread(filter));
			prev = filter;
		}
		job.setChain(filters);
		return job;
	}
	
	/**
	 * Conform all whitespace to single spaces before and after pipes,
	 * commands, and parameters.
	 * 
	 * @param command
	 * @return
	 */
	private static String correct(String command) {
		command = command.trim();// leading, trailing whitespace removed
		command = command.replaceAll("\\|", " | ");// so sub-commands don't need trimming
		command = command.replaceAll("\\s+", " ");// interior whitespace corrected
		return command;
	}

	private static String setOutputStream(String command, boolean isBackground) {
		// also ensures a space between ">" and the output stream
		int idx = command.lastIndexOf(">");
		if (idx == 0) {
			throw new RuntimeException(Message.REQUIRES_INPUT.with_parameter(command));
		}
		if (idx > -1) {
			command = command.substring(0, idx) + " | > " + command.substring(idx + 1);
			command = command.replaceFirst(">\\s+", "> ");// > interior whitespace correction
			return command;
		}
		if (!needsOutputStream(command)) {
			// should not get the default stream
			return command;
		}
		// no file specified, target to System.out or null
		if (isBackground) {
			return command + " | > %%";
		}
			
		return command + " | > %";
	}
	
	/**
	 * Determines whether the command will have output by looking at
	 * the final filter in the command. The NO_OUTPUT registry variable 
	 * needs to be maintained manually.
	 * 
	 * @param command
	 * @return whether command generates output
	 */
	private static boolean needsOutputStream(String command) {
		String[] commands = command.split(" \\| ");
		String lastCommand =  commands[commands.length - 1];
		String lastOp = lastCommand.split(" ")[0].toLowerCase();
		
		return !Arrays.asList(NO_OUTPUT).contains(lastOp);
	}
	
	
	/**
	 * Maps a sub-command to the proper object.
	 * 
	 * @param subCommand - string representing one command containing no pipes
	 * @return filter object
	 */
	private static ConcurrentFilter constructFilterFor(String subCommand) {
		String[] parts = subCommand.split(" ");
		String op = parts[0].toLowerCase();
		switch (op) {
			case "cd":
				return new CdFilter(subCommand);
			case "grep":
				return new GrepFilter(subCommand);
			case "head":
				return new HeadFilter(subCommand);
			case "ls":
				return new LsFilter(subCommand);
			case "pwd":
				return new PwdFilter(subCommand);
			case "wc":
				return new WcFilter(subCommand);
			case ">":
				return new StreamFilter(subCommand);
			default:
				throw new RuntimeException(Message.COMMAND_NOT_FOUND.with_parameter(subCommand));
		}
	}
}
