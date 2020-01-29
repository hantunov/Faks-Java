package hr.java.vjezbe.entitet;

public class RadioSondaznaMjernaPostaja extends MjernaPostaja implements RadioSondazna {

	private int visinaRadioSondaznePostaje;

	public RadioSondaznaMjernaPostaja(String naziv, Mjesto mjesto, GeografskaTocka geografskaTocka, Senzor[] senzori,
			int visinaRadioSondaznePostaje) {
		super(naziv, mjesto, geografskaTocka, senzori);
		this.visinaRadioSondaznePostaje = visinaRadioSondaznePostaje;
	}

	@Override
	public void podesiVisinuPostaje(int visina) {
		this.visinaRadioSondaznePostaje = visina;
	}

	@Override
	public int dohvatiVisinuPostaje() {
		return this.visinaRadioSondaznePostaje;
	}

}
