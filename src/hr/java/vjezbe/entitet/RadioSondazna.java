package hr.java.vjezbe.entitet;

public interface RadioSondazna {

	public void podesiVisinuPostaje(int visina);

	public int dohvatiVisinuPostaje();

	private void provjeriVisinu() {
		if (dohvatiVisinuPostaje() > 1000)
			podesiVisinuPostaje(1000);
	}

	public default void povecajVisinu() {
		podesiVisinuPostaje(dohvatiVisinuPostaje() + 1);
		provjeriVisinu();
	}

}
