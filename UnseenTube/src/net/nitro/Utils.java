package net.nitro;

public class Utils {
	public static void printArray(String[] array) {
		for (int x = 0; x < array.length; x++) {
			if (array[x] != null)
				System.out.println(array[x]);
		}
	}

	public static void printArray(int[] array) {
		for (int x = 0; x < array.length; x++) {
			System.out.println(array[x]);
		}
	}

	public static boolean haveNumbers(String str) {
		return str.matches(".*\\d+.*");
	}

	public static void parseInt(String[] array) {

	}
}
