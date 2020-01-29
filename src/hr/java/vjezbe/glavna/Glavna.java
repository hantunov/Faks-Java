package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.util.Scanner;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.RadioSondaznaMjernaPostaja;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorBrzineVjetra;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorVlage;
import hr.java.vjezbe.entitet.Zupanija;

public class Glavna {

	public static final int BROJ_POSTAJA = 3;
	public static final int BROJ_SENZORA = 3;

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		MjernaPostaja[] mjernePostaje = new MjernaPostaja[BROJ_POSTAJA];

		for (int i = 0; i < BROJ_POSTAJA; i++) {

			System.out.println("Unesite " + getBrojPostaje(i + 1) + " mjernu postaju:");
			if(i < BROJ_POSTAJA-1) mjernePostaje[i] = unosMjernePostaje(scan);
			else mjernePostaje[i] = unosRadioSondazneMjernePostaje(scan);
		}
		
		for(int i=0; i<BROJ_POSTAJA; i++) {
			if(mjernePostaje[i] instanceof RadioSondaznaMjernaPostaja) {
				ispisRadioSondazneMjernePostaje((RadioSondaznaMjernaPostaja) mjernePostaje[i]);
			}
			else {
				ispisMjernePostaje(mjernePostaje[i]);
			}			
		}
			
		scan.close();

	}

	public static MjernaPostaja unosMjernePostaje(Scanner scan) {

		System.out.println("Unesite naziv mjerne postaje: ");
		String nazivPostaje = scan.nextLine();
		MjernaPostaja mjernaPostaja = new MjernaPostaja(nazivPostaje, unosMjesta(scan), unosGeografskeTocke(scan), unosSenzora(scan));

		return mjernaPostaja;
	}
	
	public static MjernaPostaja unosRadioSondazneMjernePostaje(Scanner scan) {

		System.out.println("Unesite naziv radio sondažne mjerne postaje: ");
		String nazivPostaje = scan.nextLine();
		System.out.println("Unesite visinu radio sondažne mjerne postaje: ");
		int visinaPostaje = scan.nextInt();
		scan.nextLine();
	
		RadioSondaznaMjernaPostaja mjernaPostaja = new RadioSondaznaMjernaPostaja(nazivPostaje, unosMjesta(scan), unosGeografskeTocke(scan), unosSenzora(scan), visinaPostaje);

		return mjernaPostaja;
	}
	
	public static Senzor[] unosSenzora(Scanner scan) {
		
		System.out.println("Unesite elektroničku komponentu za senzor temperature: ");
		SenzorTemperature senzorTemperature = new SenzorTemperature(scan.nextLine());
		System.out.println("Unesite vrijednost senzora temperature: ");
		senzorTemperature.setVrijednostMjerenja(scan.nextBigDecimal());
		scan.nextLine();
		
		System.out.println("Unesite vrijednost senzora vlage: ");
		SenzorVlage senzorVlage = new SenzorVlage();
		senzorVlage.setVrijednostMjerenja(scan.nextBigDecimal());
		scan.nextLine();

		System.out.println("Unesite vrijednost senzora brzine vjetra: ");
		SenzorBrzineVjetra senzorBrzineVjetra = new SenzorBrzineVjetra();
		senzorBrzineVjetra.setVrijednostMjerenja(scan.nextBigDecimal());
		scan.nextLine();
		
		Senzor[] senzori = new Senzor[BROJ_SENZORA];
		senzori[0] = senzorTemperature;
		senzori[1] = senzorVlage;
		senzori[2] = senzorBrzineVjetra;
		
		return senzori;
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
		System.out.println("Vrijednosti senzora postaje su: ");
		for(int i=0; i<BROJ_SENZORA; i++) {
			System.out.println(mjernaPostaja.dohvatiSenzore()[i].dohvatiVrijednost());
		}
	}
	
	public static void ispisRadioSondazneMjernePostaje(RadioSondaznaMjernaPostaja mjernaPostaja) {
		
		System.out.println("\n\n--------------------\n");
		System.out.println("Naziv mjerne postaje: " + mjernaPostaja.getNaziv());
		System.out.println("Postaja je radio sondažna");
		System.out.println("Visina radio sondažne mjerne postaje: " + mjernaPostaja.dohvatiVisinuPostaje());
		System.out.println("\nPostaja se nalazi u mjestu " + mjernaPostaja.getMjesto().getNaziv()
			+ ", županiji " + mjernaPostaja.getMjesto().getZupanija().getNaziv()
			+ ", državi " + mjernaPostaja.getMjesto().getZupanija().getDrzava().getNaziv());
		System.out.println("\nTočne koordinate postaje su x:" + mjernaPostaja.getGeografskaTocka().getX()
			+ " y:" + mjernaPostaja.getGeografskaTocka().getY());
		System.out.println("Vrijednosti senzora postaje su: ");
		for(int i=0; i<BROJ_SENZORA; i++) {
			System.out.println(mjernaPostaja.dohvatiSenzore()[i].dohvatiVrijednost());
		}
		//mjernaPostaja.povecajVisinu();
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