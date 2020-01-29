package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MjernePostaje<T extends MjernaPostaja> {

	private List<T> mjernePostaje;

	public MjernePostaje() {
		mjernePostaje = new ArrayList<T>();
	}

	public MjernaPostaja get(Integer i) {

		T mjernaPostaja;
		mjernaPostaja = this.mjernePostaje.get(i);

		return mjernaPostaja;
	}

	public void add(T mjernaPostaja) {
		mjernePostaje.add(mjernaPostaja);
	}

	public List<T> getSortedList() {
		Collections.sort(mjernePostaje, (p1, p2) -> p1.getNaziv().compareTo(p2.getNaziv()));
		return mjernePostaje;
	}

	public List<T> getMjernePostaje() {
		return mjernePostaje;
	}
	
	
}
