package net.nitro;

public class Messages {

	public void error(String msg, Exception e) {
		System.out.println("[ ERROR ] " + msg + e.getMessage());
	}
	
	public void error(String msg) {
		System.out.println("[ ERROR ] " + msg);
	}

	public void notify(String msg) {
		System.out.println("[ NOTIFY ] " + msg);
	}
}
