package cs131.pa1.test;



import cs131.pa1.filter.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cs131.pa1.filter.sequential.SequentialREPL;

public class TextProcessingTests {
	
	// Tests for head command
	
	@Test
	public void testHead(){
		testInput("head hello-world.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "hello\nworld\n");
	}
	
	@Test
	public void testHeadLargerFile() {
		testInput("head ascii.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + " \n!\n\"\n#\n$\n%\n&\n'\n(\n)\n");
	}
	
	@Test
	public void testHeadOptionLess() {
		testInput("head -32 ascii.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + " \n!\n\"\n#\n$\n%\n&\n'\n(\n)\n*\n+\n,\n-\n.\n/\n0\n1\n2\n3\n4\n5\n6\n7\n8\n9\n:\n;\n<\n=\n?\n@\n");
	}
	
	@Test
	public void testHeadOptionMore() {
		testInput("head -32 hello-world.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "hello\nworld\n");
	}
    
    // Test for redirection command 
    
    @Test
	public void testReadWrittenFile(){
		testInput("head ascii.txt > ascii10.txt\nhead -30 ascii10.txt\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND.toString() + Message.NEWCOMMAND.toString() + " \n!\n\"\n#\n$\n%\n&\n'\n(\n)\n");
		AllSequentialTests.destroyFile("ascii10.txt");
	}
    
	// Basic tests for grep command
	
	@Test
	public void testGrep(){
		testInput("head -10005 fizz-buzz-10000.txt | grep 111\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "1111\n1112\n1114\n1117\n1118\n2111\n4111\n5111\n7111\n8111\n");
	}
	
	public void testGrepCaseSensitive() {
		testInput("head -100 ascii.txt | grep a\nhead -100 ascii.txt | grep A\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "a\n" + Message.NEWCOMMAND + "A\n");
	}
	
	public void testGrepSpecialCharacter() {
		testInput("head -100 ascii.txt | grep -\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "-\n");
	}
	
	// Basic test for wc (word count)
	
	@Test
	public void testWcFizzBuzz(){
		testInput("head -10005 fizz-buzz-10000.txt | wc\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "10001 10001 42081\n");
	}
	
	@Test
	public void testWcAscii(){
		testInput("head -100 ascii.txt | wc\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "93 92 93\n");
	}
	
	@Test
	public void testWcEmpty() {
		testInput("head empty.txt | wc\nexit");
		SequentialREPL.main(null);
		assertOutput(Message.NEWCOMMAND + "0 0 0\n");
	}
	
	
	
	// Boilerplate, standard across test case files.
	
	private ByteArrayInputStream inContent;
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	
	public void testInput(String s){
		inContent = new ByteArrayInputStream(s.getBytes());
		System.setIn(inContent);
	}
	
	public void assertOutput(String expected){
		AllSequentialTests.assertOutput(expected, outContent);
	}
	
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
		System.setIn(null);
	    System.setOut(null);
	    System.setErr(null);
	}
}
