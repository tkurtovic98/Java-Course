/**
 * 	Paket sadrži sve zadatke prve domaće zadaće
 */
package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Razred sadrži metode za računanje opsega i površine pravokutnika 
 * Metode unutar razredamogu raditi s bilo kojim pozitivnim decimalnim 
 * brojevima. 
 *  
 * @author Tomislav Kurtović
 * 
 */
public class Rectangle {
	
	/**
	 * Metoda prima argumente za širinu i visinu preko naredbenog retka
	 * ili poziva metodu za dohvaćanje dimenzija preko tipkovnice.
	 * Nakon toga računa površinu i opseg i na konzolu ispisuje iste vrijednosti,
	 * kao i vrijednosti širine i visine pravokutnika
	 * 
	 * @param args širina i visina koje korisnik unosi preko naredbene linije
	 * 
	 */

	public static void main(String[] args) {
		double widthValue;
		double heightValue;
		
		if (args.length > 0) {
		    if(args.length == 2) {
				widthValue = Double.parseDouble(args[0]);
				heightValue = Double.parseDouble(args[1]);
			}
			else {
				System.out.println("Krivi broj argumenata. Prekidam s radom.");
				return;
			}
		}
		else {
			Scanner sc = new Scanner(System.in);
			widthValue = getDimension("širinu", sc);
			heightValue = getDimension("visinu",sc);
			sc.close();
		}
		String widthValueidth = Double.toString(widthValue);
		String height = Double.toString(heightValue);
		String circ = Double.toString(2*(widthValue+heightValue));
		String area = Double.toString(widthValue*heightValue);
	
		System.out.println("Pravokutnik širine " + widthValueidth + " i visine " + height + " ima površinu " + area + " te opseg " + circ);
	}
	
	/**
	 * Metoda dohvaća dimenzije pravokutnika preko tipkovnice
	 * Prima naziv dimenzije koju treba dohvatiti i Scanner 
	 * kojim dohvaća korisnikov unos preko tipkovnice
	 * Vrijednost dimenzije mora biti pozitivan broj
	 * veći od nule jer se u suprotnom ispisuje greška
	 * 
	 * @param dim ime dimenzije koja se dohvaća
	 * @param sc Scanner kojim dohvaćamo korisnikov unos
	 * 
	 * @return dohvaćena vrijednost za odgovarajuću dimenziju
	 * 
	 */

	private static double getDimension(String dim,Scanner sc) {
		double dimension = 0;
		
		while (true) {
			System.out.printf("Unesite " + dim + " > ");
			String entry = sc.nextLine();
			if (entry.isEmpty()) {
				System.out.println("Morate unijeti " + dim + "!");
			} else {
				try {
					dimension = Double.parseDouble(entry);
					if (dimension <= 0) {
						System.out.println("Ne možete unijeti negativnu vrijednost ili nulu.");
					} else {
						break;
					}
				} catch (IllegalArgumentException ex) {
					System.out.println("'" + entry + "' nije broj");
				}
			}
		}
		return dimension;
	}
}
