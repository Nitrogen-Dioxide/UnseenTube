package net.nitro;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper extends Messages {

	final boolean DEBUG;
	Document doc;
	Elements urls;
	Elements codes;
	Elements names;
	public String[] views = new String[20];
	public String[] links = new String[20];
	public String[] titles = new String[20];
	int times = 0;

	public Scraper(boolean doDebug) {
		DEBUG = doDebug;
	}

	public boolean connect(String link) {
		try {
			doc = Jsoup
					.connect(link)
					.userAgent(
							"Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
					.get();
			if (DEBUG)
				notify("Succesfully connected to " + link);
			return true;
		} catch (Exception e) {
			if (DEBUG)
				error("ERROR CONNECTING: ", e);
		}
		return false;
	}

	public boolean getViews() {
		while (true) {
			try {
				times = 0;
				urls = doc
						.select("div.yt-lockup-content > div:eq(2) > ul.yt-lockup-meta-info > li:eq(1)");

				if (urls.size() < 20) {
					if (DEBUG)
						notify("End of the youtube pages.");
					break;
				}

				if (DEBUG)
					notify("Got " + urls.size() + " views elements.");

				for (Element url : urls) {
					views[times] = url.text();
					times++;
				}

				return true;

			} catch (Exception e) {
				if (DEBUG)
					error("ERROR SCRAPING VIEWS: ", e);
				return false;
			}

		}
		return false;
	}

	public boolean getLinks() {
		while (true) {
			try {
				times = 0;
				codes = doc.select("h3.yt-lockup-title > a");
				if (DEBUG)
					notify("Got " + codes.size() + " /watch?v= elements.");

				if (codes.size() < 20) {
					if (DEBUG)
						notify("End of the youtube pages.");
					break;
				}

				for (Element url : codes) {
					links[times] = url.attr("href");
					times++;
				}

				return true;

			} catch (Exception e) {
				if (DEBUG)
					error("ERROR SCRAPING LINKS: ", e);
				return false;
			}

		}
		return false;
	}

	public boolean getTitles() {
		while (true) {
			try {
				times = 0;
				names = doc.select("h3.yt-lockup-title > a");
				if (DEBUG)
					notify("Got " + names.size() + " video titles.");

				if (names.size() < 20) {
					if (DEBUG)
						notify("End of the youtube pages.");
					break;
				}

				for (Element url : codes) {
					titles[times] = url.text();
					times++;
				}

				return true;

			} catch (Exception e) {
				if (DEBUG)
					error("ERROR SCRAPING TITLES: ", e);
				return false;
			}

		}
		return false;
	}
}
