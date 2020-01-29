package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.MjernePostaje;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.RadioSondaznaMjernaPostaja;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorBrzineVjetra;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorVlage;
import hr.java.vjezbe.entitet.Senzori;
import hr.java.vjezbe.entitet.VrstaMjesta;
import hr.java.vjezbe.entitet.Zupanija;
import hr.java.vjezbe.iznimke.NiskaTemperaturaException;
import hr.java.vjezbe.iznimke.Unos;
import hr.java.vjezbe.iznimke.VisokaTemperaturaException;
import hr.java.vjezbe.sortiranje.ZupanijaSorter;

public class Glavna {

	public static final int BROJ_POSTAJA = 3;
	public static final int BROJ_SENZORA = 3;
	public static MjernePostaje<MjernaPostaja> mjernePostaje = new MjernePostaje<MjernaPostaja>();
	public static final Logger logger = LoggerFactory.getLogger(Glavna.class);

	public static void main(String[] args) throws InterruptedException {

		Scanner scan = new Scanner(System.in);		

		for (int i = 0; i < BROJ_POSTAJA; i++) {

			System.out.println("Unesite " + getBrojPostaje(i + 1) + " mjernu postaju:");
			if(i < BROJ_POSTAJA-1) mjernePostaje.add(unosMjernePostaje(scan));
			else mjernePostaje.add(unosRadioSondazneMjernePostaje(scan));
			Glavna.logger.info("Unos " + getBrojPostaje(i+1) + " mjerne postaje.");
		}
		
		
		mjernePostaje.getMjernePostaje().stream().filter(postaja -> !(postaja instanceof RadioSondaznaMjernaPostaja)).forEach(postaja -> ispisMjernePostaje(postaja));
		mjernePostaje.getMjernePostaje().stream().filter(postaja -> postaja instanceof RadioSondaznaMjernaPostaja).forEach(postaja -> ispisRadioSondazneMjernePostaje((RadioSondaznaMjernaPostaja) postaja));
				
		/*
		for(int i=0; i<BROJ_POSTAJA; i++) {
			if(mjernePostaje.get(i) instanceof RadioSondaznaMjernaPostaja) {
				ispisRadioSondazneMjernePostaje((RadioSondaznaMjernaPostaja) mjernePostaje.get(i));
			}
			else {
				ispisMjernePostaje(mjernePostaje.get(i));
			}			
		}
		*/
		
		//Upisivanje svih zabiljezenih zupanija u posebni set, pretvaranje seta u listu, sortiranje liste s Comparator klasom i ispis
		Set<Zupanija> setZupanija = new HashSet<>();
		for(int i = 0; i<BROJ_POSTAJA; i++) {
			setZupanija.add(mjernePostaje.get(i).getMjesto().getZupanija());
		}
		
		List<Zupanija> listZupanija = new ArrayList<>(setZupanija);
		Collections.sort(listZupanija, new ZupanijaSorter());
		
		System.out.println("Lista zupanija: ");
		for(Zupanija z : listZupanija) {
			System.out.println(z.getNaziv());
		}
		
		//Kreiranje nove mape unesenih senzora gdje je key Mjesto, a value je ArrayList senzora. Foreach petlja da se prodje po svim mjernim postajama.
		
		Map<Mjesto, List<Senzor>> mapaUnesenihSenzora = new HashMap<>();
		for (MjernaPostaja p : mjernePostaje.getMjernePostaje()) {
			mapaUnesenihSenzora.put(p.getMjesto(), p.getSenzori().getSenzori());
		}
		
		//Ispis mape upisanih senzora
		
		for(Mjesto m : mapaUnesenihSenzora.keySet()) {
			System.out.println("U mjestu " + m.getNaziv() + " se nalaze slijedeci senzori: ");
			for(Senzor s : mapaUnesenihSenzora.get(m)) {
				if(s instanceof SenzorBrzineVjetra) {
					System.out.println("Senzor brzine vjetra");
				}
				if(s instanceof SenzorVlage) {
					System.out.println("Senzor vlage");
				}
				if(s instanceof SenzorTemperature) {
					System.out.println("Senzor temperature");
				}
			}
			System.out.println();
		}
		
		//Generiranje vrijednosti na senzoru temperature
		do {
			for(int i=0; i<BROJ_POSTAJA; i++) {
				for(int j=0; j<BROJ_SENZORA; j++) {
					if(mjernePostaje.get(i).getSenzori().get(j) instanceof SenzorTemperature) {
						try {
							((SenzorTemperature) mjernePostaje.get(i).getSenzori().get(j)).generirajVrijednost();							
						} catch (VisokaTemperaturaException e) {
							System.out.println(e.getMessage() + "Postaja " + mjernePostaje.get(i).getNaziv() + " javlja temperaturu od " + mjernePostaje.get(i).getSenzori().get(j).getVrijednostMjerenja() + " °C");
							logger.error(e.getMessage(), e);
						} catch (NiskaTemperaturaException e) {							
							System.out.println(e.getMessage() + "Postaja " + mjernePostaje.get(i).getNaziv() + " javlja temperaturu od " + mjernePostaje.get(i).getSenzori().get(j).getVrijednostMjerenja() + " °C");
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
	
	public static Senzori<Senzor> unosSenzora(Scanner scan) {

		Glavna.logger.info("Unos senzora.");

		SenzorTemperature senzorTemperature = unosSenzoraTemperature(scan);
		SenzorVlage senzorVlage = unosSenzoraVlage(scan);
		SenzorBrzineVjetra senzorBrzineVjetra = unosSenzoraBrzineVjetra(scan);
		
		Senzori<Senzor> senzori = new Senzori<Senzor>();		
		senzori.getSenzori().add(senzorTemperature);
		senzori.getSenzori().add(senzorVlage);
		senzori.getSenzori().add(senzorBrzineVjetra);

		return senzori;
	}
	
	/**
	 * Metoda za unos senzora brzine vjetra.
	 * @param scan Scanner iz main metode
	 * @return SenzorBrzineVjetra
	 */

	public static SenzorBrzineVjetra unosSenzoraBrzineVjetra(Scanner scan) {
		System.out.println("Unesite vrijednost senzora brzine vjetra: ");
		SenzorBrzineVjetra senzorBrzineVjetra = new SenzorBrzineVjetra();
		senzorBrzineVjetra.setVrijednostMjerenja(Unos.unesiBigDecimal(scan));
		scan.nextLine();
		senzorBrzineVjetra.setRadSenzora(postavljanjeRadaSenzora(scan));
		return senzorBrzineVjetra;
	}
	
	/**
	 * Metoda za unos senzora vlage.
	 * @param scan Scanner iz main metode
	 * @return SenzorVlage
	 */
	

	public static SenzorVlage unosSenzoraVlage(Scanner scan) {
		System.out.println("Unesite vrijednost senzora vlage: ");
		SenzorVlage senzorVlage = new SenzorVlage();
		senzorVlage.setVrijednostMjerenja(Unos.unesiBigDecimal(scan));
		scan.nextLine();
		senzorVlage.setRadSenzora(postavljanjeRadaSenzora(scan));
		return senzorVlage;
	}
	
	/**
	 * Metoda za unos senzora temperature.
	 * @param scan Scanner iz main metode
	 * @return SenzorTemperature
	 */

	public static SenzorTemperature unosSenzoraTemperature(Scanner scan) {
		System.out.println("Unesite elektronicku komponentu za senzor temperature: ");
		SenzorTemperature senzorTemperature = new SenzorTemperature(scan.nextLine());
		System.out.println("Unesite vrijednost senzora temperature: ");
		senzorTemperature.setVrijednostMjerenja(Unos.unesiBigDecimal(scan));
		scan.nextLine();
		senzorTemperature.setRadSenzora(postavljanjeRadaSenzora(scan));
		return senzorTemperature;
	}
	
	/**
	 * Metoda služi za upis i postavljanje načina rada senzora u objektima tipa Senzor koristeći Enum klasu RadSenzora.
	 * 
	 * @param scan Scanner iz main metode
	 * @return vrstaMjesta, objekt tipa RadSenzora;
	 */
	
	public static RadSenzora postavljanjeRadaSenzora(Scanner scan) {

		for (int i = 0; i < RadSenzora.values().length - 1; i++) {
			System.out.println((i + 1) + ". " + RadSenzora.values()[i]);
		}
		Integer redniBrojSenzora = null;
		while (true) {
			System.out.print("Odabir rada senzora >> ");
			try {
				redniBrojSenzora = scan.nextInt();
				scan.nextLine();
				break;
			} catch (InputMismatchException ex) {
				scan.nextLine();
				System.out.println("Neispravan unos!");
				logger.error("Neispravan unos rada senzora!", ex);
			}
		}
		RadSenzora radSenzora = null;

		if (redniBrojSenzora >= 1 && redniBrojSenzora < RadSenzora.values().length) {
			radSenzora = RadSenzora.values()[redniBrojSenzora - 1];
		} else {
			radSenzora = RadSenzora.OSTALO;
		}

		return radSenzora;
	}
	

	/**
	 * Metoda za unos mjesta, unosi se jedan parametar tipa String za naziv
	 * @param scan tipa Scanner kojeg prima pri pozivu metode
	 * @return objekt tipa Mjesto
	 */
	
	public static Mjesto unosMjesta(Scanner scan) {

		System.out.println("Unesite naziv mjesta:");
		String nazivMjesta = scan.nextLine();
		
		Optional<MjernaPostaja> mjernaPostaja = mjernePostaje.getMjernePostaje().stream().filter(p -> p.getMjesto().getNaziv().equals(nazivMjesta)).findFirst();
		if (mjernaPostaja.isPresent()) return mjernaPostaja.get().getMjesto();
		
		Mjesto mjesto = new Mjesto(nazivMjesta, unosZupanije(scan));
		mjesto.setVrstaMjesta(postavljanjeVrsteMjesta(scan));

		return mjesto;
	}
	
	/**
	 * Metoda služi za upis i postavljanje vrste mjesta u objektima tipa Mjesto koristeći Enum klasu VrstaMjesta.
	 * 
	 * @param scan Scanner iz main metode
	 * @return vrstaMjesta, objekt tipa VrstaMjesta;
	 */
	
	public static VrstaMjesta postavljanjeVrsteMjesta(Scanner scan) {

		for (int i = 0; i < VrstaMjesta.values().length - 1; i++) {
			System.out.println((i + 1) + ". " + VrstaMjesta.values()[i]);
		}
		Integer odabirVrsteMjesta = null;
		while (true) {
			System.out.print("Odabir vrste mjesta >> ");
			try {
				odabirVrsteMjesta = scan.nextInt();
				scan.nextLine();
				break;
			} catch (InputMismatchException ex) {
				scan.nextLine();
				System.out.println("Neispravan unos!");
				logger.error("Neispravan unos vrste mjesta!", ex);
			}
		}
		VrstaMjesta vrstaMjesta = null;

		if (odabirVrsteMjesta >= 1 && odabirVrsteMjesta < VrstaMjesta.values().length) {
			vrstaMjesta = VrstaMjesta.values()[odabirVrsteMjesta - 1];
		} else {
			vrstaMjesta = VrstaMjesta.OSTALO;
		}

		return vrstaMjesta;
	}
	

	/**
	 * Metoda za unos zupanije, unosi se jedan parametar tipa String za naziv. 
	 * @param scan tipa Scanner kojeg prima pri pozivu metode
	 * @return objekt tipa Zupanija
	 */
	
	public static Zupanija unosZupanije(Scanner scan) {

		System.out.println("Unesite naziv zupanije:");
		String nazivZupanije = scan.nextLine();
		
		Optional<MjernaPostaja> mjernaPostaja = mjernePostaje.getMjernePostaje().stream().filter(p -> p.getMjesto().getZupanija().getNaziv().equals(nazivZupanije)).findFirst();
		if (mjernaPostaja.isPresent()) return mjernaPostaja.get().getMjesto().getZupanija();
		
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
		
		Optional<MjernaPostaja> mjernaPostaja = mjernePostaje.getMjernePostaje().stream().filter(p -> p.getMjesto().getZupanija().getDrzava().getNaziv().equals(nazivDrzave)).findFirst();
		if (mjernaPostaja.isPresent()) return mjernaPostaja.get().getMjesto().getZupanija().getDrzava();
		
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
			+ "(" + mjernaPostaja.getMjesto().getVrstaMjesta() + ")"
			+ ", županiji " + mjernaPostaja.getMjesto().getZupanija().getNaziv()
			+ ", državi " + mjernaPostaja.getMjesto().getZupanija().getDrzava().getNaziv());
		System.out.println("\nTocne koordinate postaje su x:" + mjernaPostaja.getGeografskaTocka().getX()
			+ " y:" + mjernaPostaja.getGeografskaTocka().getY());
		System.out.println("Vrijednosti senzora postaje su: ");
		mjernaPostaja.dohvatiSenzore().stream().forEach(senzor -> System.out.println(senzor.dohvatiVrijednost()));
		
		/*
		for(int i=0; i<BROJ_SENZORA; i++) {
			System.out.println(mjernaPostaja.dohvatiSenzore().get(i).dohvatiVrijednost());
		}
		*/
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
			+ "(" + mjernaPostaja.getMjesto().getVrstaMjesta() + ")"
			+ ", županiji " + mjernaPostaja.getMjesto().getZupanija().getNaziv()
			+ ", državi " + mjernaPostaja.getMjesto().getZupanija().getDrzava().getNaziv());
		System.out.println("\nTocne koordinate postaje su x:" + mjernaPostaja.getGeografskaTocka().getX()
			+ " y:" + mjernaPostaja.getGeografskaTocka().getY());
		System.out.println("Vrijednosti senzora postaje su: ");
		mjernaPostaja.dohvatiSenzore().stream().forEach(senzor -> System.out.println(senzor.dohvatiVrijednost()));
		
		/*
		for(int i=0; i<BROJ_SENZORA; i++) {
			System.out.println(mjernaPostaja.dohvatiSenzore().get(i).dohvatiVrijednost());
		}
		*/
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