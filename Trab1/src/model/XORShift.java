package model;

import java.util.Random;

@SuppressWarnings("serial")
public class XORShift extends Random {
	
	private long x;
	
	public XORShift(){
		this(System.nanoTime());
	}
	
	public XORShift(long seed){
		super(seed);
		this.x = seed;
	}
	
	/**
	 * Função que gera o proximo numero aleatório para ser usado
	 *  na maioria das outras funções da classe Random.
	 *  
	 *  @param bits		Numero de bits do numero gerado.
	 *  @return			Inteiro aleatório com n bits.
	 */
	@Override
	protected int next(int bits) {
		long x = this.nextLong();
		//Deslocamento em bits
		x &= ((1L << bits) -1);
		return (int) x;
	}
	
	/**
	 * Gera um numero long aleatório usando o método
	 *  XORShift.
	 * 
	 * @return	Numero aleatório.
	 */
	@Override
	public long nextLong(){
		x ^= (x << 21);
	    x ^= (x >>> 35);
	    x ^= (x << 4);
	    return x;
	}
}
