package Model.trab2;

@SuppressWarnings("serial")
public class PrimitiveRootNotFoundException extends Exception {
	
	public PrimitiveRootNotFoundException() {
		super("Primitive root not found.");
	}
	
	public PrimitiveRootNotFoundException(String msg){
		super(msg);
	}
}
