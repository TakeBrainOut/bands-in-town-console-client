package bandsintown;

import java.util.Scanner;

public class Start {
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		while(true){
		System.out.print("Enter artist's name: ");
		String artistName = sc.nextLine();
		Artist artist = new Artist(artistName);
		System.out.println(artist);
		}
		
		}

}
