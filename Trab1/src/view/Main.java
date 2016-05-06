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
	
	public BigInteger nextRandom(int bits){
		return new BigInteger(bits, xor);
	}
	
	public boolean isPrime(BigInteger n){
		return test.isPrime(n, 10);
	}
	
	public void generatePrimes(int nbits){
		BigInteger n = null;
		do{
			n = nextRandom(nbits);
		}while(!isPrime(n));
		System.out.println("Numero primo: " +n+ " - Quantidade de bits: " +nbits);
	}
	
	public static void main(String[] args) {
		Main m = new Main();
		
		Scanner scan = new Scanner(System.in);
		int[] bits = {128, 256, 512, 1024, 2048};
		
		int option = -1;
		long tmp = 0;
		boolean test = false;
		while(option != 4){
			System.out.println("Menu:");
			System.out.println("\t1 - Gerar numero.");
			System.out.println("\t2 - Testar primo.");
			System.out.println("\t3 - Gerar numeros aleatorios.");
			System.out.println("\t4 - Sair.");
			System.out.println("Escolha uma opcao: ");
			
			option = scan.nextInt();
			
			if(option == 1){
				System.out.println("\nEntre com a quantidade de bits: ");
				tmp = scan.nextInt();
				System.out.println("Numero gerado: " + m.nextRandom((int)tmp));
			}else if(option == 2){
				System.out.println("\nEntre com o numero a ser testado: ");
				tmp = scan.nextLong();
				test = m.isPrime(BigInteger.valueOf(tmp));
				System.out.println("O numero " +tmp+ (test?" eh primo!":" nao eh primo!"));
			}else if(option == 3){
				for(int i = 0; i<bits.length; i++){
					m.generatePrimes(bits[i]);
				}
			}
			System.out.println();
		}
		scan.close();
	}

}
