package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import hr.java.vjezbe.iznimke.NiskaTemperaturaException;
import hr.java.vjezbe.iznimke.Unos;
import hr.java.vjezbe.iznimke.VisokaTemperaturaException;

public class Glavna {

	public static final int BROJ_POSTAJA = 3;
	public static final int BROJ_SENZORA = 3;
	public static final Logger logger = LoggerFactory.getLogger(Glavna.class);

	public static void main(String[] args) throws InterruptedException {

		Scanner scan = new Scanner(System.in);

		MjernaPostaja[] mjernePostaje = new MjernaPostaja[BROJ_POSTAJA];

		for (int i = 0; i < BROJ_POSTAJA; i++) {

			System.out.println("Unesite " + getBrojPostaje(i + 1) + " mjernu postaju:");
			if(i < BROJ_POSTAJA-1) mjernePostaje[i] = unosMjernePostaje(scan);
			else mjernePostaje[i] = unosRadioSondazneMjernePostaje(scan);
			Glavna.logger.info("Unos " + getBrojPostaje(i+1) + " mjerne postaje.");
		}
		
		for(int i=0; i<BROJ_POSTAJA; i++) {
			if(mjernePostaje[i] instanceof RadioSondaznaMjernaPostaja) {
				ispisRadioSondazneMjernePostaje((RadioSondaznaMjernaPostaja) mjernePostaje[i]);
			}
			else {
				ispisMjernePostaje(mjernePostaje[i]);
			}			
		}
		
		do {
			for(int i=0; i<BROJ_POSTAJA; i++) {
				for(int j=0; j<BROJ_SENZORA; j++) {
					if(mjernePostaje[i].getSenzori()[j] instanceof SenzorTemperature) {
						try {
							((SenzorTemperature)mjernePostaje[i].getSenzori()[j]).generirajVrijednost();							
						} catch (VisokaTemperaturaException e) {
							System.out.println(e.getMessage() + "Postaja " + mjernePostaje[i].getNaziv() + " javlja temperaturu od " + mjernePostaje[i].getSenzori()[j].getVrijednostMjerenja() + " °C");
							logger.error(e.getMessage(), e);
						} catch (NiskaTemperaturaException e) {							
							System.out.println(e.getMessage() + "Postaja " + mjernePostaje[i].getNaziv() + " javlja temperaturu od " + mjernePostaje[i].getSenzori()[j].getVrijednostMjerenja() + " °C");
							logger.error(e.getMessage(), e);
						}
					}
				}
			}
			Thread.sleep(1000);
		} while(true);
				
		//scan.close();

	}
	
	/**
	 * Metoda za unos radio mjerne postaje. Unosi se jedan parametar, String nazivPostaje. 
	 * @param scan tipa Scanner kojeg prima pri pozivu metode
	 * @return objekt tipa MjernaPostaja
	 */
	
	public static MjernaPostaja unosMjernePostaje(Scanner scan) {

		System.out.println("Unesite naziv mjerne postaje: ");
		String nazivPostaje = scan.nextLine();
		MjernaPostaja mjernaPostaja = new MjernaPostaja(nazivPostaje, unosMjesta(scan), unosGeografskeTocke(scan), unosSenzora(scan));

		return mjernaPostaja;
	}
	
	/**
	 * Metoda za unos radio sondazne mjerne postaje. Unose se dva parametra, String nazivPostaje i int visinaPostaje. 
	 * @param scan tipa Scanner kojeg prima pri pozivu metode
	 * @return objekt tipa RadioSondaznaMjernaPostaja
	 */
	
	public static MjernaPostaja unosRadioSondazneMjernePostaje(Scanner scan) {

		System.out.println("Unesite naziv radio sondazne mjerne postaje: ");
		String nazivPostaje = scan.nextLine();
		System.out.println("Unesite visinu radio sondazne mjerne postaje: ");
		int visinaPostaje = Unos.unesiInt(scan);
		scan.nextLine();
	
		RadioSondaznaMjernaPostaja mjernaPostaja = new RadioSondaznaMjernaPostaja(nazivPostaje, unosMjesta(scan), unosGeografskeTocke(scan), unosSenzora(scan), visinaPostaje);

		return mjernaPostaja;
	}
	
	/**
	 * Metoda za unos tri vrste senzora u polje senzora.
	 * @param scan tipa Scanner kojeg prima pri pozivu metode
	 * @return polje objekata tipa Senzor[] popunjeno sa tri senzora tipa SenzorTemperature, SenzorVlage i SenzorBrzineVjetra.
	 */
	
	public static Senzor[] unosSenzora(Scanner scan) {
		
		Glavna.logger.info("Unos senzora.");
		System.out.println("Unesite elektronicku komponentu za senzor temperature: ");
		SenzorTemperature senzorTemperature = new SenzorTemperature(scan.nextLine());
		System.out.println("Unesite vrijednost senzora temperature: ");
		senzorTemperature.setVrijednostMjerenja(Unos.unesiBigDecimal(scan));
		scan.nextLine();
		
		System.out.println("Unesite vrijednost senzora vlage: ");
		SenzorVlage senzorVlage = new SenzorVlage();
		senzorVlage.setVrijednostMjerenja(Unos.unesiBigDecimal(scan));
		scan.nextLine();

		System.out.println("Unesite vrijednost senzora brzine vjetra: ");
		SenzorBrzineVjetra senzorBrzineVjetra = new SenzorBrzineVjetra();
		senzorBrzineVjetra.setVrijednostMjerenja(Unos.unesiBigDecimal(scan));
		scan.nextLine();
		
		Senzor[] senzori = new Senzor[BROJ_SENZORA];
		senzori[0] = senzorTemperature;
		senzori[1] = senzorVlage;
		senzori[2] = senzorBrzineVjetra;
		
		return senzori;
	}

	/**
	 * Metoda za unos mjesta, unosi se jedan parametar tipa String za naziv
	 * @param scan tipa Scanner kojeg prima pri pozivu metode
	 * @return objekt tipa Mjesto
	 */
	
	public static Mjesto unosMjesta(Scanner scan) {

		System.out.println("Unesite naziv mjesta:");
		String nazivMjesta = scan.nextLine();
		Mjesto mjesto = new Mjesto(nazivMjesta, unosZupanije(scan));

		return mjesto;
	}

	/**
	 * Metoda za unos zupanije, unosi se jedan parametar tipa String za naziv. 
	 * @param scan tipa Scanner kojeg prima pri pozivu metode
	 * @return objekt tipa Zupanija
	 */
	
	public static Zupanija unosZupanije(Scanner scan) {

		System.out.println("Unesite naziv zupanije:");
		String nazivZupanije = scan.nextLine();
		Zupanija zupanija = new Zupanija(nazivZupanije, unosDrzave(scan));

		return zupanija;
	}
	
	/**
	 * Metoda za unos drzave, unose se dva parametra tipa String i BigDecimal za naziv i povrsinu, koji se zatim prosljedjuju konstruktoru klase Drzava i stvara se novi objekt kojeg metoda vraca.
	 * @param scan tipa Scanner kojeg prima pri pozivu metode
	 * @return objekt tipa Drzava
	 */

	public static Drzava unosDrzave(Scanner scan) {

		System.out.println("Unesite naziv drzave:");
		String nazivDrzave = scan.nextLine();
		System.out.println("Unesite povrsinu drzave:");
		BigDecimal povrsina = Unos.unesiBigDecimal(scan);
		scan.nextLine();
		Drzava drzava = new Drzava(nazivDrzave, povrsina);

		return drzava;
	}
	
	/**
	 * Metoda za unos geografske tocke, unose se dva parametra tipa BigDecimal i to za koordinatu x i y. Nakon unosa stvara se novi objekt tipa GeografskaTocka i parametri se predaju u konstruktor.
	 * Metoda zatim vraca taj stvoreni objekt.
	 * 
	 * @param scan tipa Scanner kojeg prima pri pozivu metode
	 * @return objekt tipa GeografskaTocka
	 */
	
	public static GeografskaTocka unosGeografskeTocke(Scanner scan) {
		System.out.println("Unesite Geo koordinatu X:");
		BigDecimal koordinataX = Unos.unesiBigDecimal(scan);
		System.out.println("Unesite Geo koordinatu Y:");
		BigDecimal koordinataY = Unos.unesiBigDecimal(scan);
		scan.nextLine();
		GeografskaTocka geografskaTocka = new GeografskaTocka(koordinataX, koordinataY);

		return geografskaTocka;
	}
	
	/**
	 * Metoda za ispis "obicne" mjerne postaje. Ispisuje se naziv, mjesto, zupanija, drzava, koordinate i vrijednosti senzora.
	 * @param mjernaPostaja objekt tipa MjernaPostaja
	 */
	
	public static void ispisMjernePostaje(MjernaPostaja mjernaPostaja) {
		
		System.out.println("\n\n--------------------\n");
		System.out.println("Naziv mjerne postaje: " + mjernaPostaja.getNaziv());
		System.out.println("\nPostaja se nalazi u mjestu " + mjernaPostaja.getMjesto().getNaziv()
			+ ", županiji " + mjernaPostaja.getMjesto().getZupanija().getNaziv()
			+ ", državi " + mjernaPostaja.getMjesto().getZupanija().getDrzava().getNaziv());
		System.out.println("\nTocne koordinate postaje su x:" + mjernaPostaja.getGeografskaTocka().getX()
			+ " y:" + mjernaPostaja.getGeografskaTocka().getY());
		System.out.println("Vrijednosti senzora postaje su: ");
		for(int i=0; i<BROJ_SENZORA; i++) {
			System.out.println(mjernaPostaja.dohvatiSenzore()[i].dohvatiVrijednost());
		}
	}
	
	/**
	 * Metoda za ispis radio sondazne mjerne postaje. Ispisuje se naziv, visina, mjesto, zupanija, drzava, koordinate i vrijednosti senzora.
	 * @param mjernaPostaja objekt tipa RadioSondaznaMjernaPostaja
	 */
	
	public static void ispisRadioSondazneMjernePostaje(RadioSondaznaMjernaPostaja mjernaPostaja) {
		
		System.out.println("\n\n--------------------\n");
		System.out.println("Naziv mjerne postaje: " + mjernaPostaja.getNaziv());
		System.out.println("Postaja je radio sondazna");
		System.out.println("Visina radio sondazne mjerne postaje: " + mjernaPostaja.dohvatiVisinuPostaje());
		System.out.println("\nPostaja se nalazi u mjestu " + mjernaPostaja.getMjesto().getNaziv()
			+ ", županiji " + mjernaPostaja.getMjesto().getZupanija().getNaziv()
			+ ", državi " + mjernaPostaja.getMjesto().getZupanija().getDrzava().getNaziv());
		System.out.println("\nTocne koordinate postaje su x:" + mjernaPostaja.getGeografskaTocka().getX()
			+ " y:" + mjernaPostaja.getGeografskaTocka().getY());
		System.out.println("Vrijednosti senzora postaje su: ");
		for(int i=0; i<BROJ_SENZORA; i++) {
			System.out.println(mjernaPostaja.dohvatiSenzore()[i].dohvatiVrijednost());
		}
	}
	
	/**
	 * Metoda sluzi pretvorbi broja u rijec za mjesto
	 * @param i prima iz metode koja pozvala
	 * @return String koji odgovara rednom mjestu
	 */

	public static String getBrojPostaje(int i) {
		switch (i) {
		case 1:
			return "prvu";
		case 2:
			return "drugu";
		case 3:
			return "trecu";
		case 4:
			return "cetvrtu";
		case 5:
			return "petu";
		case 6:
			return "sestu";
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