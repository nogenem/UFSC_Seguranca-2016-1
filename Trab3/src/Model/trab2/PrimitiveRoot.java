package Model.trab2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PrimitiveRoot {
	
	private static final BigInteger ZERO = BigInteger.ZERO;
	private static final BigInteger ONE = BigInteger.ONE;
	private static final BigInteger TWO = BigInteger.valueOf(2);
	
	public PrimitiveRoot(){}
	
	/**
	 * Implementação interativa do algoritmo de MDC.
	 * 
	 * @param a			Numero a.
	 * @param b			Numero b.
	 * @return			MDC(a, b).
	 */
	private BigInteger gcd(BigInteger a, BigInteger b){
		BigInteger c = ZERO;
		while(b.compareTo(ZERO) > 0){
			c = a.mod(b);
			a = b;
			b = c;
		}
		return a;
	}
	
	/**
	 * Retorna se o numero passado é primo ou não.
	 * 
	 * @param n		Numero a se checar.
	 * @return		TRUE caso n seja primo
	 * 		   </br>FALSE caso contrario.
	 */
	private boolean isPrime(BigInteger n){
		return n.isProbablePrime(1);
	}
	
	/**
	 * Calcula uma raiz primitiva modulo p.
	 * 
	 * @param p						Numero que se quer as raízes.
	 * @param ignoreTwo				É para ignorar a raiz 2?
	 * @return						Uma raiz primitiva modulo p.
	 * @throws PrimitiveRootNotFoundException	
	 * 				Caso não consiga encontrar uma raiz primitiva.	
	 */
	public BigInteger getPrimitiveRoot(BigInteger p, boolean ignoreTwo) throws Exception {
		if(!isPrime(p))
			throw new NotPrimeException();
		
		Set<BigInteger> factors = new HashSet<>();
		
		// compute phi(p)
		BigInteger phi = p.subtract(ONE);//phi(p) = p-1 [if p is prime]
		BigInteger n = phi;
		
		// find prime factors of phi
		for(BigInteger i = TWO; 
				(i.multiply(i).compareTo(n) <= 0); 
				 i = i.add(ONE)){
			// n % i == 0? [i divide n?]
			while(n.mod(i).compareTo(ZERO) == 0){
				factors.add(i);// 'factors' é um 'Set', ou seja, 
							   //  ele não adiciona elementos repetidos
				n = n.divide(i);// n /= i;				
			}
		}
		
		if(n.compareTo(ONE) > 0)
			factors.add(n);
		
		// for every element of Zn* [Zn* = {1,2, ..., p-1}]
		for(BigInteger m = ONE; 
				m.compareTo(p) < 0;
				m = m.add(ONE)){
			boolean isRoot = true;
			// for every prime factor of phi(p)
			for(BigInteger pi : factors){
				// check if: m ^ (phi/pi) mod p != 1
				isRoot &= m.modPow(phi.divide(pi), p).compareTo(ONE) != 0;
				//true & true = true, todo resto = false
			}
			if(isRoot && (ignoreTwo?m.intValue()!=2:true)) return m;
		}
		throw new PrimitiveRootNotFoundException();
	}
	
	/**
	 * Retorna todas as raízes primitivas modulo p. 
	 * 
	 * @param p						Numero que se quer as raízes.
	 * @return						Uma raiz primitiva modulo p.
	 * @throws NotPrimeException	Caso o numero passado não seja primo.	
	 */
	public Set<BigInteger> getAllPrimitiveRoots(BigInteger p) throws Exception {
		ArrayList<BigInteger> rp = new ArrayList<>();
		Set<BigInteger> result = new HashSet<>();
		
		BigInteger root = getPrimitiveRoot(p,false);
		BigInteger phi = p.subtract(ONE);//phi(p) = p-1 [if p is prime]
		
		result.add(root);
		//for i in {2, ..., phi-1}
		for(BigInteger i = TWO; 
				i.compareTo(phi) < 0;
				i = i.add(ONE)){
			if(gcd(i, phi).compareTo(ONE) == 0)//gcd(i, phi) == 1
				rp.add(i);
		}
		
		for(BigInteger i : rp)
			result.add( root.modPow(i, p) );//g^i mod p
		
		return result;
	}
}
