package view;

import java.math.BigInteger;
import java.util.Scanner;

import model.RSA;

public class Main {
	
	public static void main(String[] args) {
		RSA rsa = new RSA();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Entre com a quantidade de bits: ");
		// quantidade de bits utilizada para gerar as chaves
		int nbits = sc.nextInt();
		
		System.err.println("\nProcessando......");
		long inicio = System.currentTimeMillis();
		
		// gera p
		BigInteger p = rsa.getRandomPrime(nbits);
		//System.out.println("p: " +p);
		// gera q
		BigInteger q = rsa.getRandomPrime(nbits);
		//System.out.println("q: " +q);
		
		// calcula n = p*q
		BigInteger n = p.multiply(q);
		//System.out.println("n: " +n);
		
		// calcula phi(n) = (p-1)*(q-1)
		BigInteger phi = p.subtract(BigInteger.ONE).
					multiply(q.subtract(BigInteger.ONE));
		
		// chama a função que calcula o valor de 'e'
		BigInteger e = rsa.getE(phi, nbits);
		System.out.println("e: " +e);
		
		// acha o inverso multiplicativo de 'e' mod phi(n)
		BigInteger d = e.modInverse(phi);
		System.out.println("d: " +d);
		
		long fim = System.currentTimeMillis();
		System.err.println("\nTempo levado para processar: " +((fim-inicio)/1000.0) +"s");
		
		// usuario entra com um numero para ser cifrado
		BigInteger num = null;
		boolean isNumber = false;
		while(!isNumber){
			try{
				System.out.println("\nEntre com o numero para ser cifrado:");
				num = new BigInteger(sc.next());
				
				// 1 < m < n-1
				if(num.compareTo(BigInteger.ONE) <= 0 || 
						num.compareTo(n.subtract(BigInteger.ONE)) > 0)
					System.err.println("Seu numero eh muito grande ou eh um numero negativo!");
				else
					isNumber = true;
			}catch(Exception ex){
				System.err.println("A mensagem deve ser um numero!");
			}
		}
		
		// chama a função que cifra a mensagem usando o 'e' e 'n' 
		// previamente calculados
		BigInteger encrypted = rsa.encrypt(num, e, n);
		System.out.println("\nNumero cifrado:\n" +encrypted);
		
		// chama a função que decifra a mensagem cifrada
		// usando o 'd' e 'n' previamente calculados
		BigInteger decrypted = rsa.decrypt(encrypted, d, n);
		System.out.println("\nNumero decrifrado:\n" +decrypted);
		
		sc.close();
	}

}
