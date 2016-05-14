package Model;

import java.math.BigInteger;

import Model.trab1.FermatTest;
import Model.trab1.XORShift;
import Model.trab2.PrimitiveRoot;

public class Diffie­Hellman {
	
	private XORShift xor;
	private FermatTest test;
	private PrimitiveRoot pr;
	
	public Diffie­Hellman(){
		xor = new XORShift();
		test = new FermatTest(xor);
		pr = new PrimitiveRoot();
	}
	
	/**
	 * Gera um numero randômico de nbits.
	 * 
	 * @param bits	Quantidade de bits do numero
	 * 				 randômico.
	 * @return		Um numero randômico de nbits.
	 */
	public BigInteger nextRandom(int bits){
		return new BigInteger(bits, xor);
	}
	
	/**
	 * Utiliza o teste de Fermant com no máximo 10 interações
	 *  para verificar se o numero n é primo.
	 * 
	 * @param n		Numero a ser checado.
	 * @return		TRUE caso n seja primo,
	 * 				FALSE caso contrario.
	 */
	private boolean isPrime(BigInteger n){
		return test.isPrime(n, 10);
	}
	
	/**
	 * Gera um numero primo de nbits.
	 * 
	 * @param nbits		Quantidade de bits do numero.
	 * @return			Numero primo de nbits.
	 */
	public BigInteger generatePrime(int nbits){
		BigInteger n = null;
		do{
			n = nextRandom(nbits);
		}while(!isPrime(n));
		return n;
	}
	
	/**
	 * Encontra a primeira raiz primitiva de p sem ser
	 *  o numero 2.
	 * 
	 * @param p		Numero que se quer achar a raiz.
	 * @return		Raiz primitiva modulo p.
	 * @throws Exception
	 */
	public BigInteger getPrimitiveRoot(BigInteger p) throws Exception {
		return pr.getPrimitiveRoot(p, true);
	}
}