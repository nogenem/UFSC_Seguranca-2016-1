package view;

import java.math.BigInteger;
import java.util.Scanner;

import model.FermatTest;
import model.XORShift;

public class Main {
	
	private XORShift xor;
	private FermatTest test;
	
	public Main(){
		xor = new XORShift();
		test = new FermatTest(xor);
	}
	
	public long nextLong(){
		return xor.nextLong();
	}
	
	public long nextLong(int nbits){
		return xor.nextLong(nbits);
	}
	
	public boolean isPrime(long n){
		return test.isPrime(BigInteger.valueOf(n), 10);
	}
	
	public static void main(String[] args) {
		Main m = new Main();
		
		/*for(int nbits = 32; nbits<=34; nbits++){
			System.out.println(m.nextLong(nbits));
		}*/
		System.out.println(m.nextLong(128));
		
		/*Scanner scan = new Scanner(System.in);
		
		int option = -1;
		long tmp = 0;
		boolean test = false;
		while(option != 3){
			System.out.println("Menu:");
			System.out.println("\t1 - Gerar numero.");
			System.out.println("\t2 - Testar primo.");
			System.out.println("\t3 - Sair.");
			System.out.println("Escolha uma opcao: ");
			
			option = scan.nextInt();
			
			if(option == 1){
				System.out.println("Numero gerado: " + m.nextLong());
			}else if(option == 2){
				System.out.println("\nEntre com o numero a ser testado: ");
				tmp = scan.nextLong();
				test = m.isPrime(tmp);
				System.out.println("O numero " +tmp+ (test?" eh primo!":" nao eh primo!"));
			}
			System.out.println();
		}
		scan.close();*/
	}

}
