package hr.java.vjezbe.entitet;

/**
 * Sadrzi metode koje se izvode nad objektima tipa RadioSondaznaMjernaPostaja.
 * podesiVisinuPostaje i dohvatiVisinuPostaje su apstraktne metode koje se implementiraju kao set i get u klasi koja implementira interface.
 * 
 * @author Hrvoje
 *
 */

public interface RadioSondazna {

	public void podesiVisinuPostaje(int visina);

	public int dohvatiVisinuPostaje();

	/**
	 * Metoda dohvaca visinu postaje, provjerava da li je visina veća od 1000 i ako jest postavlja je na 1000.
	 */
	private void provjeriVisinu() {
		if (dohvatiVisinuPostaje() > 1000)
			podesiVisinuPostaje(1000);
	}
	
	/**
	 * Metoda povecava visinu postaje za +1, a zatim poziva metodu provjeriVisinu koja je spušta na 1000 ako je taj broj premasen.
	 */
	
	public default void povecajVisinu() {
		podesiVisinuPostaje(dohvatiVisinuPostaje() + 1);
		provjeriVisinu();
	}

}
