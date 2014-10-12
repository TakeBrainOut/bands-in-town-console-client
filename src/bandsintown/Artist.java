package bandsintown;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Artist {
	
	private String artist_name;
	private String urlArtist;
	private String fileName;
	
	private ArrayList<Event> events = new ArrayList<Event>();
	
	public Artist(String artist_name)
	{
		this.artist_name = artist_name;
		this.urlArtist = getURL(artist_name);
		this.fileName = "file.xml";
		System.out.println(urlArtist);
		Save();
		Read();
	}
	
	public String getHTML(String urlToRead) {
	      URL url;
	      HttpURLConnection conn;
	      BufferedReader rd;
	      String line;
	      String result = "";
	      try {
	         url = new URL(urlToRead);
	         conn = (HttpURLConnection) url.openConnection();
	         conn.setRequestMethod("GET");
	         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         while ((line = rd.readLine()) != null) {
	            result += line;
	         }
	         rd.close();
	      } catch (Exception e) {
	         System.out.println("No internet connection");
	         
	      }
	      return result;
	   }
	
	public void Read(){
		try
        {
            File xmlFile = new File(this.fileName);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();
 
            for (int i=0; i < document.getDocumentElement().getChildNodes().getLength(); i++)
            {
            	Node event_node = document.getDocumentElement().getChildNodes().item(i);
            	
            	if(event_node.getNodeType() == Node.ELEMENT_NODE)
                 {
            	Element event_element = (Element) event_node;
            	Node venue_node = event_element.getElementsByTagName("venue").item(0);
            	Element venue_element = (Element) venue_node;
            	events.add(new Event(event_element.getElementsByTagName("formatted_datetime").item(0).getTextContent(), event_element.getElementsByTagName("ticket_url").item(0).getTextContent(), venue_element.getElementsByTagName("name").item(0).getTextContent(), venue_element.getElementsByTagName("city").item(0).getTextContent(), venue_element.getElementsByTagName("region").item(0).getTextContent(), venue_element.getElementsByTagName("country").item(0).getTextContent()));
                 }
            }            
        }
        catch (Exception e)
        {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
	}
	
	public void Save()
	{
		File file = new File(this.fileName);
		 
	    try {
	        file.createNewFile();
	        PrintWriter out = new PrintWriter(file.getAbsoluteFile());
	 
	        try {
	        	String text = getHTML(this.urlArtist);
	            out.print(text);
	        } finally {
	            out.close();
	        }
	    } catch(IOException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	public String toString()
	{
		//this.date, this.ticket_url, this.venue_name, this.city, this.region, this.country
		String result = "==================================" + "\n"
		+ this.artist_name + "\n" + "==================================\n";
		
		for(Event event:events)
		{
			result += event + "-----------------------------\n";
		}
		
		return result;
	}
	public String getURL(String artist)
	{
		artist = artist.replace(" ", "%20");
		artist = artist.replace("/", "%252F");
		artist = artist.replace("?", "%253F");
		return "http://api.bandsintown.com/artists/" + artist + "/events.xml?api_version=2.0&app_id=consoleclient";
	}

}
