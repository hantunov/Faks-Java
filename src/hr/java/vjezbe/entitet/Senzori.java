package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Senzori<T extends Senzor> {

	private List<T> senzori;
	
	public Senzori() {
		senzori = new ArrayList<T>();
	}

	public Senzor get(Integer i) {

		T senzor;
		senzor = this.senzori.get(i);

		return senzor;
	}

	public void add(T senzor) {
		senzori.add(senzor);
	}

	public List<T> getSortedList() {
		Collections.sort(senzori, (p1, p2) -> p1.getMjernaJedinica().compareTo(p2.getMjernaJedinica()));
		return senzori;
	}

	public List<T> getSenzori() {
		return senzori;
	}
	
	public Optional<T> pretraziPoID(int id){
		
		Optional<T> optSenzor = senzori.stream().filter(s -> s.getId() == id).findAny();
		
		return optSenzor;
	}
	
}
