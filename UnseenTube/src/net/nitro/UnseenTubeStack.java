package net.nitro;

import java.util.ArrayList;
import java.util.List;

public class UnseenTubeStack extends Messages {
	final boolean DEBUG;
	final int MIN_VIEW;
	final String LINK;
	int page = 2;
	public int last = 0;
	public List<Integer> views = new ArrayList<Integer>();
	public List<String> titles = new ArrayList<String>();
	public List<String> links = new ArrayList<String>();

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
					titles.add(scr.titles[cvt.minIndex]);
					views.add(cvt.views[cvt.minIndex]);
					links.add(scr.links[cvt.minIndex]);
					if (DEBUG)
						notify("Succesfully added.");
					cvt.views[cvt.minIndex] = MIN_VIEW * 2 + 1;
					cvt.getMin();
					last++;
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
			notify("Terminated succesfully");
			return true;
		} catch (Exception e) {
			if (DEBUG)
				error("ERROR RUNNING: ", e);
			return false;
		}

	}

}
