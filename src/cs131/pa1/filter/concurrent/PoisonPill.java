package cs131.pa1.filter.concurrent;

/**
 * This class provides a static constant for each filter to communicate the end
 * of input to the next filter.
 * 
 * @author Prakhar Sahay
 * @see ConcurrentFilter
 */

public class PoisonPill {
	// created with constructor instead of a string literal to avoid interning
	public static final String X = new String("poison pill");
}
