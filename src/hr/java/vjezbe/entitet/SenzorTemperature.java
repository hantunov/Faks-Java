package hr.java.vjezbe.entitet;

public class SenzorTemperature extends Senzor {
	
	private static final String MJERNA_JEDINICA = "°C"; 
	private static final byte PRECIZNOST = 2;
			
	private String nazivElektroKomponente;
	
	public SenzorTemperature(String nazivElektroKomponente) {
		super(MJERNA_JEDINICA, PRECIZNOST);
		this.nazivElektroKomponente = nazivElektroKomponente;
	}

	@Override
	public String dohvatiVrijednost() {
		String str = "Komponenta: " + this.nazivElektroKomponente + ", vrijednost: " + this.getVrijednostMjerenja() + super.getMjernaJedinica();
		return str;
	}
}
