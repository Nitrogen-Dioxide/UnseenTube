package net.nitro;

import java.util.Scanner;

public class Main extends Messages {

	public static void main(String[] args) {
		String search;
		int views;
		Scanner input = new Scanner(System.in);
		UnseenTubeConsole utc = new UnseenTubeConsole(false);

		System.out.println("Insert the search.");
		search = input.nextLine();
		search = search.replace(" ", "");
		
		if (search.length() == 0){
			System.out.println("Invalid search! using \"Youtube\" instead.");
			search = "Youtube";
		}

		System.out.println("Insert the maximun views: ");
		try {
			views = input.nextInt();
			System.out.println(views);
		} catch (java.util.InputMismatchException e) {
			System.out.println("\nInvalid value! Using 200 instead.");
			views = 200;
		}

		System.out.println("Searching...");
		
		utc.set(search, views);
		utc.run();

		if (utc.found == 0) {
			System.out
					.println("\nNo videos found! :(\nMake sure you have internet connection!");
		}

		if (utc.found > 0)
			if (views == 0)
				if (utc.found == 1)
					System.out.println("\nSuccesfully found " + utc.found
							+ " video with " + views + " views.");
				else
					System.out.println("\nSuccesfully found " + utc.found
							+ " videos with " + views + " views.");
			else if (views > 0)
				if (utc.found == 1)
					System.out.println("\nSuccesfully found " + utc.found
							+ " video with " + views + " or less views.");
				else
					System.out.println("\nSuccesfully found " + utc.found
							+ " videos with " + views + " or less views.");

		input.close();
	}

}
