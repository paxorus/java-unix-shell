package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;
import java.util.List;
import java.util.Scanner;

public class SequentialREPL {

	static String currentWorkingDirectory;
	static final String EXIT_COMMAND = "exit";
	
	public static void main(String[] args){
		print(Message.WELCOME);
		
		Scanner read = new Scanner(System.in);
		currentWorkingDirectory = System.getProperty("user.dir");
		
		while (true) {
			print(Message.NEWCOMMAND);
			String command = read.nextLine();
			if (command.trim().equals(EXIT_COMMAND)) {
				print(Message.GOODBYE);
				break;
			} else {
				execute(command);
			}
		}
		read.close();
	}
	
	public static void execute(String command) {
		try {
			List<SequentialFilter> chain = SequentialCommandBuilder.createFiltersFromCommand(command);
			for (SequentialFilter sf : chain) {
				sf.process();
			}
		} catch (Throwable ex) {
			print(ex.getMessage());
		}
	}
	
	// alias
	public static void print(Object o) {
		System.out.print(o);
	}

}
