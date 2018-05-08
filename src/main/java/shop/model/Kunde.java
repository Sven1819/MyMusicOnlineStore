package shop.model;

import java.util.List;

public class Kunde {

	public long kundeId;

	public String vorname;

	public String nachname;

	public String straﬂe;

	public int hausnummer;

	public String anschriftZusatz;

	public String ort;

	public int plz;

	public String email;

	public List<Long> einkaufId;
	
	public List<Long> playlistId;
}
