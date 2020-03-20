/**
 * 	Paket sadrži sve zadatke prve domaće zadaće
 *  za java tečaj
 */

package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Razred omogućuje računanje faktorijela brojeva između 3 i 20
 * Sadrži metodu za račuanje faktorijela brojeva između 0 i 20
 * te ako primi neispravnu vrijednost prikazuje odgovarajuću poruku
 * 
 * @author Tomislav Kurtović
 * 
 */

public class Factorial {

	/**
	 * Metoda ne prima argumente preko naredbenog retka, već ih
	 * prima preko tipkovnice. Korisnik može unositi brojeve 
	 * sve dok ne unese 'kraj' , tada program završava s radom.
	 * Rezultat se neće ispisati ukoliko je unesena vrijednost
	 * koja nije cijeli broj
	 * 
	 */

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			System.out.printf("Unesite broj > ");
			String entry = sc.nextLine();

			if (entry.equalsIgnoreCase("kraj")) {
				System.out.println("Doviđenja.");
				break;
			}
			try{
				int number  = Integer.parseInt(entry);
				long result = calculateFactorial(number);
				if(number >=3 && number <=20) {
					System.out.printf("%d! = %d%n",number,result );
				}else {
					System.out.println("Broj mora biti između 3 i 20");
				}
			} catch(NumberFormatException ex) {
				System.out.println("'" + entry + "' nije cijeli broj");
			} catch(IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
			} 
		}
		sc.close();
	}

   /**
	 * 
	 * Metoda prima cijeli broj kojem računa faktorijel
	 * Ako metoda primi broj koji je manji od 0 ili veći od 20
	 * baca iznimku jer ti brojevi nisu u rasponu
	 *  
	 * @param number broj kojem se računa faktorijel
	 * @throws IllegalArgumentException
	 * @return result rezultat izračuna faktorijela predanog broja
	 */
	
	public static long calculateFactorial(int number)  {
		long factorial = 1;
		
		if(number < 0 || number > 20) {
			throw new IllegalArgumentException("Broj '"+ number + "' nije u dozvoljenom rasponu brojeva");
		}
		for (int i = 1; i <= number; i++) {
			factorial *= i;
		}
		return factorial;
	}

}
