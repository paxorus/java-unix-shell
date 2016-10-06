package cs131.pa1.filter.concurrent;

import cs131.pa1.filter.Message;
import java.util.Scanner;

public class ConcurrentREPL {

	static String currentWorkingDirectory;
	static final String EXIT_COMMAND = "exit";
	static final String REPL_JOBS_COMMAND = "repl_jobs";
	
	public static void main(String[] args){
		print(Message.WELCOME);
		
		Scanner read = new Scanner(System.in);
		currentWorkingDirectory = System.getProperty("user.dir");
		JobManager jobs = new JobManager();
		
		// REP loop
		while (true) {
			print(Message.NEWCOMMAND);
			String command = read.nextLine().trim();// read
			if (command.equals(EXIT_COMMAND)) {
				print(Message.GOODBYE);
				break;
			} else if (command.equals(REPL_JOBS_COMMAND)) {
				jobs.dump();
			} else {
				jobs.add(command);// eval-print
			}
		}
		read.close();
	}
	
	// alias
	public static void print(Object o) {
		System.out.print(o);
	}
}
