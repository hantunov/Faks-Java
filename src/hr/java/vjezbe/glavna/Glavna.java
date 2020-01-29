package hr.java.vjezbe.glavna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.MjernePostaje;

public class Glavna {

	public static final int BROJ_POSTAJA = 3;
	public static final int BROJ_SENZORA = 3;
	public static MjernePostaje<MjernaPostaja> mjernePostaje = new MjernePostaje<MjernaPostaja>();
	public static final Logger logger = LoggerFactory.getLogger(Glavna.class);

}