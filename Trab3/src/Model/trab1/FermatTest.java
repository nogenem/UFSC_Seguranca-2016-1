package Model.trab1;

import java.math.BigInteger;

public class FermatTest {
	
	private static final BigInteger ONE = BigInteger.ONE;
	private XORShift random;
	
	public FermatTest(){
		this.random = new XORShift();
	}
	
	public FermatTest(XORShift r){
		this.random = r;
	}
	
	/**
	 * Utiliza o 'teste de primalidade de Fermat' para verificar 
	 *  se um dado numero é primo ou não.
	 * 
	 * @param n				Numero para ser testado.
	 * @param maxInteration	Numero máximo de interações para checagem
	 * 						 do numero. 
	 * @return				TRUE caso o numero n seja primo,
	 *                 </br>FALSE caso contrario.
	 */
	public boolean isPrime(BigInteger n, int maxInteration){
		if(n.longValue() <= 1)
			return false;
		if(n.intValue() == 2)
			return true;
		//Numeros pares não são primos
		if(n.intValue() % 2 == 0)
			return false;
		
		BigInteger n1 = n.subtract(ONE), a = null;
		for(int i = 0; i<maxInteration; i++){
			// Numero aleatório com nbits
			a = new BigInteger(n.bitLength(), random).abs();
			// a % (n-1) + 1 => Limita em [1, n-1]
			a = a.mod( n.subtract(ONE) ).add(ONE);
			// a ^ (n-1) % n != 1
			if(a.modPow(n1, n).compareTo(ONE) != 0)
				return false;
		}
		return true;
	}
}
