package model;

import java.math.BigInteger;

public class RSA {
	
	private XORShift xor;
	private static BigInteger ONE = BigInteger.ONE;
	
	public RSA(){
		xor = new XORShift();
	}
	
	/**
	 * Utiliza a função 'probablePrime' da classe 'BigInteger'
	 *  para achar um numero aleatório provavelmente primo.
	 * Utiliza tambem o algorimot XorShift feito no trabalho 1.
	 * 
	 * @param nbits		Numero de bits do numero aleatório.
	 * @return			Numero aleatório provavelmente primo.
	 */
	public BigInteger getRandomPrime(int nbits){
		return BigInteger.probablePrime(nbits, xor);
	}
	
	/**
	 * Acha um valor 'e' para o algoritmo RSA.
	 * 
	 * @param phi		Valor phi(n) previamente calculado.
	 * @param nbits		Numero de bits para ser gerado randomicamente.
	 * @return			Valor 'e' para o algoritmo RSA.
	 */
	public BigInteger getE(BigInteger phi, int nbits){
		//3, 5, 17, 257 and 65537
		BigInteger e = null;
		BigInteger phi1 = phi.subtract(ONE);
		do{
			e = new BigInteger(nbits,xor).abs();
			// e % (phi-1) + 2 => Limita em [2, phi-1]
			e = e.mod( phi1 ).add(ONE).add(ONE);
		}while(e.gcd(phi).compareTo(ONE) != 0);
		
		return e;
	}
	
	/**
	 * Cifra um numero usando um 'e' e 'n' previamente calculados.
	 * 
	 * @param num	Numero que se quer cifrar.
	 * @param e		Valor 'e' previamente calculado.
	 * @param n		Valor 'n' previamente calculado.
	 * @return		Numero 'num' cifrado.
	 */
	public BigInteger encrypt(BigInteger num, BigInteger e, BigInteger n) {
		return num.modPow(e, n);
	}
	
	/**
	 * Decifra um numero usando um 'd' e 'n' previamente calculados.
	 * 
	 * @param num	Numero que se quer decifrar.
	 * @param d		Valor 'd' previamente calculado.
	 * @param n		Valor 'n' previamente calculado.
	 * @return		Numero 'num' decifrado.
	 */
	public BigInteger decrypt(BigInteger num, BigInteger d, BigInteger n) {
		return num.modPow(d, n);
	}
}
