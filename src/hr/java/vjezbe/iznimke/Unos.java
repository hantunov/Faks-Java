package hr.java.vjezbe.iznimke;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

import hr.java.vjezbe.glavna.Glavna;

/**
 * Klasa staticnih metoda koje se koriste za upis BigDecimal i Int brojeva uz hvatanje pogresnog upisa i ispis greske na konzoli / upis u logback. 
 * 
 * @author Hrvoje
 *
 */

public class Unos {

	/**
	 * Metoda pokusava ucitati BigDecimal vrijednost sa scannera, ako ne uspije ispisuje gresku na konzoli i u logu i vraca se na upis toliko dugo dok se ne upise ispravan broj.
	 * @param scan tipa Scanner kojeg prima pri pozivu metode
	 * @return BigDecimal
	 */
	
	public static BigDecimal unesiBigDecimal(Scanner scan) {
		
		while(true) {
			try {
				return scan.nextBigDecimal();
			}
			catch(InputMismatchException e) {
				scan.nextLine();
				System.out.println("Potrebno je unijeti broj! (koristite zarez za decimalne brojeve)");
				Glavna.logger.error("Pogreska kod unosenja BigDecimal broja");
			}
		}
	}
	
	/**
	 * Metoda pokusava ucitati int vrijednost sa scannera, ako ne uspije ispisuje gresku na konzoli i u logu i vraca se na upis toliko dugo dok se ne upise ispravan broj.
	 * @param scan tipa Scanner kojeg prima pri pozivu metode
	 * @return int
	 */
	
	public static int unesiInt(Scanner scan) {
			
			while(true) {
				try {
					return scan.nextInt();
				}
				catch(InputMismatchException e) {
					scan.nextLine();
					System.out.println("Potrebno je unijeti cjelobrojnu vrijednost!");
					Glavna.logger.error("Pogreska kod unosenja Int broja");
				}
			}
		}
}
