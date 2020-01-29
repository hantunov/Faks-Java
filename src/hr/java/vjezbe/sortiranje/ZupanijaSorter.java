package hr.java.vjezbe.sortiranje;

import java.util.Comparator;

import hr.java.vjezbe.entitet.Zupanija;

/**
 * Sluzi sortiranju objekata tipa Zupanija pomocu Comparator sucelja i to tako da se zupanije sortiraju po imenu, abecedno, uzlazno.
 * 
 * 
 * @author Hrvoje
 *
 */

public class ZupanijaSorter implements Comparator<Zupanija> {

	@Override
	public int compare(Zupanija z1, Zupanija z2) {

		if (z1.getNaziv().compareTo(z2.getNaziv()) > 0) {
			return 1;
		} else if (z1.getNaziv().compareTo(z2.getNaziv()) < 0) {
			return -1;
		} else {
			return 0;
		}
	}
	

}
