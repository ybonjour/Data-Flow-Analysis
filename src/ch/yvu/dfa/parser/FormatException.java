package ch.yvu.dfa.parser;

public class FormatException extends Exception {
	public FormatException(Throwable t) {
		super(t);
	}
	
	public FormatException(){
		super();
	}
}
