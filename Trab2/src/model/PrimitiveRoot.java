package model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import view.MainWindow;

/*
 * 17359
 * 135799
 * 1357969
 * 13579697
 * 1735112503
 * 
 * http://pt.numberempire.com/primenumbers.php
 */
public class PrimitiveRoot implements Runnable {
	
	private static final BigInteger ZERO = BigInteger.ZERO;
	private static final BigInteger ONE = BigInteger.ONE;
	private static final BigInteger TWO = BigInteger.valueOf(2);
	
	private Thread t;
	private boolean running;
	
	private MainWindow window;
	private BigInteger currentP;
	private boolean calculateAll;
	
	public PrimitiveRoot(MainWindow window){
		this.window = window;
		this.calculateAll = true;
		
		this.running = false;
		this.t = new Thread(this);
		this.t.start();
	}
	
	/**
	 * Habilita a thread para calcular as raízes primitivas 
	 *  modulo p.
	 * 
	 * @param p				Numero que se quer saber as raízes.
	 * @param calculateAll	É para calcular todas as raízes ou só 
	 * 						 a primeira?
	 */
	public void calculate(BigInteger p, boolean calculateAll){
		this.currentP = p;
		this.calculateAll = calculateAll;
		this.running = true;
	}
	
	/**
	 * Quando o usuário clica em um dos botões,
	 *  a MainWindows 'destrava' esta Thread para
	 *  que ela calcule a(s) raiz(es) primitiva(s)
	 *  e retorna o resultado a MainWindow. 
	 */
	public void run(){
		while(true){
			while(!running){ Thread.yield(); }
			try{
				Set<BigInteger> result = null;
				
				long inicio = System.currentTimeMillis();
				if(calculateAll)
					result = getAllPrimitiveRoots(currentP);
				else{
					result = new HashSet<>();
					result.add(getPrimitiveRoot(currentP));
				}
				long fim = System.currentTimeMillis();
				
				// envia os resultados para a MainWindow
				window.setResult(result, ((fim-inicio)/1000.0));
			}catch(Exception e){
				window.showError(e.getMessage());
			}finally{
				this.running = false;
			}
		}
	}
	
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
	 * @return						Uma raiz primitiva modulo p.
	 * @throws PrimitiveRootNotFoundException	
	 * 				Caso não consiga encontrar uma raiz primitiva.	
	 */
	private BigInteger getPrimitiveRoot(BigInteger p) throws Exception {
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
			if(isRoot) return m;
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
	private Set<BigInteger> getAllPrimitiveRoots(BigInteger p) throws Exception {
		ArrayList<BigInteger> rp = new ArrayList<>();
		Set<BigInteger> result = new HashSet<>();
		
		BigInteger root = getPrimitiveRoot(p);
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
