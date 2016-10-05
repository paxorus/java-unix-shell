package cs131.pa1.filter.concurrent;

import cs131.pa1.filter.Message;
import java.util.List;
import java.util.Scanner;

public class ConcurrentREPL {

	static String currentWorkingDirectory;
	static final String EXIT_COMMAND = "exit";
	
	public static void main(String[] args){
		print(Message.WELCOME);
		
		Scanner read = new Scanner(System.in);
		currentWorkingDirectory = System.getProperty("user.dir");
		
		// REP loop
		while (true) {
			print(Message.NEWCOMMAND);
			String command = read.nextLine();// read
			if (command.trim().equals(EXIT_COMMAND)) {
				print(Message.GOODBYE);
				break;
			} else {
				execute(command);// eval-print
			}
		}
		read.close();
	}
	
	public static void execute(String command) {
		try {
			List<ConcurrentFilter> chain = ConcurrentCommandBuilder.createFiltersFromCommand(command);
			for (ConcurrentFilter sf : chain) {
				sf.process();
			}
		} catch (Throwable ex) {
			// safely print Message
			print(ex.getMessage());
		}
	}
	
	// alias
	public static void print(Object o) {
		System.out.print(o);
	}

}