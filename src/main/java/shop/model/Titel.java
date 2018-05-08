package shop.model;

import java.net.URI;
import java.time.Duration;
import java.util.List;

public class Titel {

	public String titelId;
	
	public String name;
	
	public double preis;
	
	public String pfad;
	
	public String laenge;
	
	public String genre;
	
	public List<String> kuenstlerId;
	
	public List<String> albumId;
	
	public List<Bewertung> bewertungen;
}
