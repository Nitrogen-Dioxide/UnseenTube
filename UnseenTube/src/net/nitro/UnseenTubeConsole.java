package net.nitro;

public class UnseenTubeConsole extends Messages {
	public final boolean DEBUG;
	
	private String link;
	private int page = 2;
	private int maxView;
	
	public int found = 0;

	UnseenTubeConsole(boolean doDebug) {
		DEBUG = doDebug;
	}

	public boolean run() {
		Scraper scr = new Scraper(DEBUG);
		Converter cvt = new Converter(DEBUG);
		boolean get = true;

		try {
			scr.connect(link + page);
			get = scr.getViews();
			get = scr.getLinks();
			get = scr.getTitles();
			cvt.onlyViews(scr.views);
			cvt.setInt(scr.views);
			cvt.getMin(); // find the first videos.
			do {
				if (maxView >= cvt.min && get == true) { // prints the video data (if minview matches)
					System.out.println("\n-----------------------------\n"
							+ "Title: " + scr.titles[cvt.minIndex]
							+ "\nViews: " + cvt.views[cvt.minIndex]
							+ "\nLink: " + scr.links[cvt.minIndex]
							+ "\n-----------------------------");
					if (DEBUG)
						notify("GOT");
					cvt.views[cvt.minIndex] = maxView * 2 + 1;
					cvt.getMin();
					found++;
				}

				if (maxView < cvt.min) { // go to the next page, and reload the video data
					page++;
					scr.connect(link + page);
					if (!scr.getViews())
						get = false;
					scr.getLinks();
					scr.getTitles();
					cvt.onlyViews(scr.views);
					cvt.setInt(scr.views);
					cvt.getMin();
					if (DEBUG)
						notify("LOOP");
				}
			} while (get == true);
			if (DEBUG)
				notify("Terminated succesfully");
			return true;
		} catch (Exception e) {
			if (DEBUG)
				error("ERROR RUNNING: ", e);
			return false;
		}

	}
	
	public void set(String search, int views){ // used to set and reload the UTC object
		link = "https://www.youtube.com/results?filters=video%2C+year&search_query="
				+ search + "&page=";
		maxView = views;
		page = 2;
	}
}
