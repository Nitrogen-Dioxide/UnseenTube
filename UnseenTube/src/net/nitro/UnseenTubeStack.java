package net.nitro;

public class UnseenTubeStack extends Messages {
	final boolean DEBUG;
	final int MIN_VIEW;
	final String LINK;
	int page = 2;
	int set = 0;
	public int last;
	public String[] titles = new String[200];
	public String[] links = new String[200];
	public int[] views = new int[200];

	UnseenTubeStack(String search, boolean doDebug, int minView) {
		LINK = "https://www.youtube.com/results?filters=video%2C+year&search_query="
				+ search + "&page=";
		DEBUG = doDebug;
		MIN_VIEW = minView;
	}

	public boolean run() {
		Scraper scr = new Scraper(DEBUG);
		Converter cvt = new Converter(DEBUG);
		boolean get = true;

		try {
			scr.connect(LINK + page);
			get = scr.getViews();
			get = scr.getLinks();
			get = scr.getTitles();
			cvt.onlyViews(scr.views);
			cvt.setInt(scr.views);
			cvt.getMin();
			do {
				if (MIN_VIEW >= cvt.min && get == true) {
					titles[set] = scr.titles[cvt.minIndex];
					views[set] = cvt.views[cvt.minIndex];
					links[set] = scr.links[cvt.minIndex];
					if (DEBUG)
						notify("Succesfully added.");
					set++;
					if (set > ((titles.length + links.length + views.length) / 3) - 1) {
						if (DEBUG)
							notify("Too many videos!");
						break;
					}
					;
					cvt.views[cvt.minIndex] = MIN_VIEW * 2 + 1;
					cvt.getMin();
				}

				if (MIN_VIEW < cvt.min) {
					page++;
					scr.connect(LINK + page);
					if (!scr.getViews())
						get = false;
					scr.getLinks();
					scr.getTitles();
					cvt.onlyViews(scr.views);
					cvt.setInt(scr.views);
					cvt.getMin();
					if (DEBUG)
						notify("Looped");
				}
			} while (get == true);
			last = set;
			notify("Terminated succesfully");
			return true;
		} catch (Exception e) {
			if (DEBUG)
				error("ERROR RUNNING: ", e);
			return false;
		}

	}

}
