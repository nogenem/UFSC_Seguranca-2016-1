package model;

@SuppressWarnings("serial")
public class NotPositiveInteger extends Exception {
	
	public NotPositiveInteger(){
		super("This isn't a positive integer.");
	}
	
	public NotPositiveInteger(String string) {
		super(string);
	}
}
