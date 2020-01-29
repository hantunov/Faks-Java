package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.util.Scanner;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.Zupanija;

public class Glavna {

	public static final int BROJ_POSTAJA = 3;

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		MjernaPostaja[] mjernaPostaja = new MjernaPostaja[BROJ_POSTAJA];

		for (int i = 0; i < BROJ_POSTAJA; i++) {

			System.out.println("Unesite " + getBrojPostaje(i + 1) + " mjernu postaju:");
			mjernaPostaja[i] = unosMjernePostaje(scan);

		}
		
		for (int i = 0; i < BROJ_POSTAJA; i++) {
			ispisMjernePostaje(mjernaPostaja[i]);
		}

		scan.close();

	}

	public static MjernaPostaja unosMjernePostaje(Scanner scan) {

		System.out.println("Unesite naziv mjerne postaje:");
		String nazivPostaje = scan.nextLine();
		MjernaPostaja mjernaPostaja = new MjernaPostaja(nazivPostaje, unosMjesta(scan), unosGeografskeTocke(scan));

		return mjernaPostaja;
	}

	public static Mjesto unosMjesta(Scanner scan) {

		System.out.println("Unesite naziv mjesta:");
		String nazivMjesta = scan.nextLine();
		Mjesto mjesto = new Mjesto(nazivMjesta, unosZupanije(scan));

		return mjesto;
	}

	public static Zupanija unosZupanije(Scanner scan) {

		System.out.println("Unesite naziv županije:");
		String nazivZupanije = scan.nextLine();
		Zupanija zupanija = new Zupanija(nazivZupanije, unosDrzave(scan));

		return zupanija;
	}

	public static Drzava unosDrzave(Scanner scan) {

		System.out.println("Unesite naziv države:");
		String nazivDrzave = scan.nextLine();
		System.out.println("Unesite površinu države:");
		BigDecimal povrsina = scan.nextBigDecimal();
		scan.nextLine();
		Drzava drzava = new Drzava(nazivDrzave, povrsina);

		return drzava;
	}

	public static GeografskaTocka unosGeografskeTocke(Scanner scan) {
		System.out.println("Unesite Geo koordinatu X:");
		BigDecimal koordinataX = scan.nextBigDecimal();
		System.out.println("Unesite Geo koordinatu Y:");
		BigDecimal koordinataY = scan.nextBigDecimal();
		scan.nextLine();
		GeografskaTocka geografskaTocka = new GeografskaTocka(koordinataX, koordinataY);

		return geografskaTocka;
	}
	
	public static void ispisMjernePostaje(MjernaPostaja mjernaPostaja) {
		
		System.out.println("\n\n--------------------\n");
		System.out.println("Naziv mjerne postaje: " + mjernaPostaja.getNaziv());
		System.out.println("\nPostaja se nalazi u mjestu " + mjernaPostaja.getMjesto().getNaziv()
							+ ", županiji " + mjernaPostaja.getMjesto().getZupanija().getNaziv()
							+ ", državi " + mjernaPostaja.getMjesto().getZupanija().getDrzava().getNaziv());
		System.out.println("\nTočne koordinate postaje su x:" + mjernaPostaja.getGeografskaTocka().getX()
							+ " y:" + mjernaPostaja.getGeografskaTocka().getY());		
	}

	public static String getBrojPostaje(int i) {
		switch (i) {
		case 1:
			return "prvu";
		case 2:
			return "drugu";
		case 3:
			return "treću";
		case 4:
			return "četvrtu";
		case 5:
			return "petu";
		case 6:
			return "šestu";
		case 7:
			return "sedmu";
		case 8:
			return "osmu";
		case 9:
			return "devetu";
		case 10:
			return "desetu";
		default:
			return i + ".";
		}
	}
}