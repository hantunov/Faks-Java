package hr.java.vjezbe.entitet;

/**
 * Klasa koja nasljedjuje Senzor, sluzi za mjerenje vlaznosti zraka, mjerna jedinica je % relativne vlaznosti (RV), preciznost je 1.
 * 
 * @author Hrvoje
 *
 */

public class SenzorVlage extends Senzor {

	private static final String MJERNA_JEDINICA = "% RV"; 
	private static final byte PRECIZNOST = 1;
	
	public SenzorVlage() {
		super(MJERNA_JEDINICA, PRECIZNOST);
	}

	/**
	 * Metoda dohvaca vrijednost senzora 
	 * @return String koji sadrzi vrijednost i mjernu jedinicu
	 */
	
	@Override
	public String dohvatiVrijednost() {
		String str = "Vrijednost: " + this.getVrijednostMjerenja() + super.getMjernaJedinica() + ", način rada: " + super.getRadSenzora();
		return str;
	}

}
