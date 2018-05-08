package shop.model;

import java.net.URI;
import java.time.Duration;
import java.util.List;

public class Titel {

	public long titelId;
	
	public String name;
	
	public double preis;
	
	public URI pfad;
	
	public Duration laenge;
	
	public Genre genre;
	
	public List<Long> kuenstlerId;
	
	public List<Long> albumId;
	
	public List<Bewertung> bewertungen;
}
