# java-unix-shell
A multi-threaded Unix-ish shell implemented in Java.

## Interface
* cd
* grep
* head
* ls
* pwd
* wc
End a command with a `> file.txt` to redirect output or '&' to run it in the background.

## Architecture
* Job is an abstraction of a set of threads running the user's input.
* JobManager is an abstraction of all currently running Jobs.
* ConcurrentREPL contains the main method and client-facing REPL.
* The 7 commands all extend ConcurrentFilter, abstract class that implements Filter.

## Design Choices
* ConcurrentCommandBuilder takes a computational linguistics-style approach and corrects and massages the original input string in a series of steps before processing it.
* Job sets an exception handler on every thread (one for each Unix command in the input). When a command class runs into an error, it throws a RuntimeException using the Message enum, then the handler prints the Message and interrupts every thread it owns.
* StreamFilter (for > commands) is still created if the output will not be redirected. A `%` marks STDOUT as the target. `%%` marks that it's a background job so nothing will be printed.
* I chose not to use isDone(). I use the poison pill technique, in which each thread upon completion places a special string in its outbox marking the end of the input.
* In CdFilter, I was able to handle all combinations of `.` and `..` cross-platform trivially with just a few lines of Java:
    FILE_SEPARATOR = System.getProperty("file.separator");
    File f = new File(pwd + FILE_SEPARATOR + path)
    f.toPath().toRealPath().toString()
* Instead of continuously updating its list of completed background jobs, JobManager clears completed background jobs when the repl_jobs command requests the list of active jobs (each Job checks the state of its last thread).
* For commands that don't take input or output, I override the setter methods with a method that will throw a Runtime exception.

## Project Organization
This is an Eclipse Java 8 project.  
4 JUnit tests enforce the external behavior.  
The cs131.pa1.filter.concurrent package contains the classes for 7 commands, as well as PoisonPill, Job, JobManager, ConcurrentCommandBuilder (a static class), and ConcurrentREPL.