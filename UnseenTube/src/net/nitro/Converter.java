package net.nitro;

import net.nitro.Utils;

public class Converter extends Messages {
	public final boolean DEBUG;

	public int[] views = new int[20];
	public int x;
	public int minIndex;
	public int min;

	public Converter(boolean doDebug) {
		DEBUG = doDebug;
	}

	public boolean onlyViews(String[] array) {
		try {
			for (int x = 0; x < array.length; x++) {
				array[x] = array[x].replaceAll("[^\\d]", "");
				if (!Utils.haveNumbers(array[x]))
					array[x] = "0";
			}
			if (DEBUG)
				notify("Succesfully converted the array.");
			return true;
		} catch (Exception e) {
			if (DEBUG)
				error("ERROR CONVERTING TO ONLY VIEWS: ", e);
			return false;
		}
	}

	public boolean setInt(String[] array) {

		try {
			for (x = 0; x < array.length; x++) {
				views[x] = Integer.parseInt(array[x]);
			}
			if (DEBUG)
				notify("Succesfully parsed to int array.");
			return true;

		} catch (Exception e) {
			if (DEBUG)
				error("ERROR TRANSFORMING TO INT: ", e);
			return false;
		}
	}

	public boolean getMin() {
		try {
			min = views[0];
			for (x = 0; x < views.length; x++) {
				if (views[x] <= min) {
					min = views[x];
					minIndex = x;
				}
			}
			if (DEBUG)
				notify("Got " + min + " at index of " + minIndex + ".");
			return true;
		} catch (Exception e) {
			if (DEBUG)
				error("ERROR GETING MINIMUN VALUE: ", e);
			return false;
		}
	}

}
