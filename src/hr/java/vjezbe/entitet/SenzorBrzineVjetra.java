package hr.java.vjezbe.entitet;

/**
 * Klasa koja nasljedjuje Senzor, sluzi za mjerenje brzine vjetra, mjerna jedinica su m/s, preciznost je 1.
 * 
 * @author Hrvoje
 *
 */

public class SenzorBrzineVjetra extends Senzor {
	
	public static final String MJERNA_JEDINICA = "m/s";
	public static final byte PRECIZNOST = 1;

	public SenzorBrzineVjetra() {
		super(MJERNA_JEDINICA, PRECIZNOST);
	}
	
	/**
	 * Metoda dohvaca vrijednost senzora 
	 * @return String koji sadrzi vrijednost i mjernu jedinicu
	 */
	
	@Override
	public String dohvatiVrijednost() {
		String str = "Vrijednost: " + this.getVrijednostMjerenja() + " " + super.getMjernaJedinica();
		return str;
	}

}
