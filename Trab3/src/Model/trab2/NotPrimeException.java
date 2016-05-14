package Model.trab2;

@SuppressWarnings("serial")
public class NotPrimeException extends Exception {
	
	public NotPrimeException(){
		super("This isn't a prime number.");
	}
	
	public NotPrimeException(String string) {
		super(string);
	}

}
