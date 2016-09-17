package cs131.pa1.test;


import cs131.pa1.filter.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import cs131.pa1.filter.sequential.SequentialREPL;


public class RedirectionTests {

	@Test
	public void testHeadRedirected(){
		testInput("head hello-world.txt > new-hello-world.txt\nexit");
		SequentialREPL.main(null);
		assertFileContentsEquals("new-hello-world.txt", "hello\nworld\n");
		assertOutput(Message.NEWCOMMAND.toString());
		AllSequentialTests.destroyFile("new-hello-world.txt");
	}
	
	@Test
	public void testComplexRedirection(){
		testInput("head -10005 fizz-buzz-10000.txt | grep F | wc > trial-file.txt\nexit");
		SequentialREPL.main(null);
		assertFileContentsEquals("trial-file.txt", "3334 3334 16004\n");
		assertOutput(Message.NEWCOMMAND.toString());
		AllSequentialTests.destroyFile("trial-file.txt");
	}
	
	@Test
	public void testDirectoryShiftedRedirection(){
		testInput("cd dir1\nls > folder-contents.txt\nexit");
		SequentialREPL.main(null);
		assertFileContentsEquals("dir1/folder-contents.txt", "dir2\nf1.txt\n");
		assertOutput(Message.NEWCOMMAND.toString() + Message.NEWCOMMAND.toString());
		AllSequentialTests.destroyFile("dir1/folder-contents.txt");
	}
	
	private static void assertFileContentsEquals(String fileName, String expected){
		File f = new File(fileName);
		try {
			Scanner scan = new Scanner(f);
			String result = "";
			while (scan.hasNextLine()){
				result += scan.nextLine() + "\n";
			}
			scan.close();
			assertEquals(expected, result);
		} catch (FileNotFoundException e) {
			assertTrue(false);
		}
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
