package hr.java.vjezbe.entitet;

/**
 * Sadrzi jednu clansku varijablu - int visinaRadioSondaznePostaje koja ima public get i set metode.
 * 
 * Konstruktor prima String naziv, Mjesto mjesto, GeografskaTocka geografskaTocka, Senzor[] senzori i int visinaRadioSondaznePostaje. Prva cetiri argumenta salje u konstruktor nadklase.  
 * 
 * @author Hrvoje
 *
 */

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
