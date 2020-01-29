package hr.java.vjezbe.entitet;

public class SenzorBrzineVjetra extends Senzor {
	
	public static final String MJERNA_JEDINICA = "m/s";
	public static final byte PRECIZNOST = 1;

	public SenzorBrzineVjetra() {
		super(MJERNA_JEDINICA, PRECIZNOST);
	}

	@Override
	public String dohvatiVrijednost() {
		String str = "Vrijednost: " + this.getVrijednostMjerenja() + " " + super.getMjernaJedinica();
		return str;
	}

}
