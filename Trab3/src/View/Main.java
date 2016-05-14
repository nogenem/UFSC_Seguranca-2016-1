package View;

import java.math.BigInteger;

import Model.Diffie­Hellman;

public class Main {

	public static void main(String[] args) {
		Diffie­Hellman dh = new Diffie­Hellman();
		int nbitsPrime = 60, //numero de bits do numero primo
			nbitsRandom = 20;//numero de bits dos numeros aleatorios secretos
		
		if(args.length > 0)
			nbitsPrime = Integer.valueOf(args[0]);
		if(args.length > 1)
			nbitsRandom = Integer.valueOf(args[1]);
		
		try{
			System.out.print("Gerando numero primo e calculando raiz...");
			long inicio = System.currentTimeMillis();
			BigInteger p = dh.generatePrime(nbitsPrime);
			BigInteger root = dh.getPrimitiveRoot(p);
			long fim = System.currentTimeMillis();
			System.out.println(" (" +((fim-inicio)/1000.0)+ "s)\n");
			
			System.out.println("------ Public ------");
			System.out.println("Alice gera o numero primo: " +p+ 
					" e encontra a raiz primitiva: " 
					+root+ " e os envia para Bob.");
			System.out.println("--------------------");
			
			System.out.println("\n------ Secret ------");
			BigInteger Xa = dh.nextRandom(nbitsRandom);
			System.out.println("Alice gera o numero aleatorio secreto, Xa: " +Xa);
			BigInteger Xb = dh.nextRandom(nbitsRandom);
			System.out.println("Bob gera o numero aleatorio secreto, Xb: " +Xb);
			System.out.println("--------------------");
			
			System.out.println("\n------ Public ------");
			BigInteger A = root.modPow(Xa, p);//root ^ Xa mod p
			System.out.println("Alice calcula o numero A: " +A+ " e o envia a Bob.");
			BigInteger B = root.modPow(Xb, p);//root ^ Xb mod p
			System.out.println("Bob calcula o numero B: " +B+ " e o envia a Alice.");
			System.out.println("--------------------");
			
			System.out.println("\n------ Secret ------");
			BigInteger sA = B.modPow(Xa, p);//B ^ Xa mod p
			System.out.println("Alice calcula a chave secreta sA: " +sA);
			BigInteger sB = A.modPow(Xb, p);//A ^ Xb mod p
			System.out.println("Bob calcula a chave secreta sB: " +sB);
			System.out.println("--------------------");
			
			System.out.println("\nResultado: Bob e Alice agora possuem uma "
					+ "chave secreta compartilha.");
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

}
