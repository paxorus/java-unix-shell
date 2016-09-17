package cs131.pa1.filter;

public abstract class Filter {
    
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	protected Filter next;
	protected Filter prev;
	
	public abstract void setNextFilter(Filter next);
	
	public abstract void setPrevFilter(Filter next);
	
	public abstract boolean isDone();
	
}
