package model;

public class XORShift {
	
	private long x;
	
	public XORShift(){
		this(System.nanoTime());
	}
	
	public XORShift(long seed){
		this.x = seed;
	}
	
	/**
	 * Gera um numero long aleatório com n bits.
	 * 
	 * @param nbits		Quantidade de bits do numero gerado.
	 * @return			Numero aleatorio com nbits.
	 */
	public long nextLong(int nbits){
		long x = nextLong();
		//Deslocamento em nbits
		x &= ((1L << nbits) -1);
		return x;
	}
	
	/**
	 * Gera um numero long aleatório usando o método
	 *  XORShift.
	 * 
	 * @return	Numero aleatório.
	 */
	public long nextLong(){
		x ^= (x << 21);
	    x ^= (x >>> 35);
	    x ^= (x << 4);
	    return x;
	}
}
