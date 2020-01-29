package hr.java.vjezbe.entitet;

public class SenzorVlage extends Senzor {

	private static final String MJERNA_JEDINICA = "% RV"; 
	private static final byte PRECIZNOST = 1;
	
	public SenzorVlage() {
		super(MJERNA_JEDINICA, PRECIZNOST);
	}

	@Override
	public String dohvatiVrijednost() {
		String str = "Vrijednost: " + this.getVrijednostMjerenja() + super.getMjernaJedinica();
		return str;
	}

}
