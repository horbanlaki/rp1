package databaseEntrants.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Вспомогательный класс
 */
@XmlRootElement(name = "entrants")
public class EntrantsListWrapper {

	private List<Entrants> entrants;

	@XmlElement(name = "person")
	public List<Entrants> getEntrants() {
		return entrants;
	}

	public void setEntrants(List<Entrants> entrants) {
		this.entrants = entrants;
	}
}
