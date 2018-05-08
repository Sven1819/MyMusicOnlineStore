package shop.model;

import java.net.URI;
import java.util.List;

public class Album {

	public long albumId;
	
	public String name;
	
	public String datum;
	
	public URI cover;
	
	public URI pfad;
	
	public double preis;
	
	public List<Long> kuenstlerId;
	
	public List<Long> titelId;
	
	public List<Bewertung> bewertungen;
	
}
