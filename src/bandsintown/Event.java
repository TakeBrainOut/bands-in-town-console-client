package bandsintown;

public class Event {
	private String date;
	private String ticket_url;
	private String venue_name;
	private String city;
	private String region;
	private String country;
	
	public Event(String date, String ticket_url, String venue_name, String city, String region, String country){
		this.date = date;
		this.ticket_url = ticket_url;
		this.venue_name = venue_name;
		this.city = city;
		this.region = region;
		this.country = country;
	}

	
	public String toString()
	{
		String result = "Date: " + this.date + "\n"
				+ "Ticket URL: " + this.ticket_url + "\n"
				+ "Venue: " + this.venue_name + ", " + this.city + ", " + this.region + ", " + this.country + ".\n";
		return result;
	}
}
