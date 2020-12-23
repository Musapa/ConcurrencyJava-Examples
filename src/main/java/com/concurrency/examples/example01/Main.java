package com.concurrency.examples.example01;

import static com.concurrency.examples.example01.ThreadColor.ANSI_PURPLE;
import static com.concurrency.examples.example01.ThreadColor.ANSI_RED;

public class Main {

	public static void main(String[] args) {

		System.out.println("Hello from main thread.");

		Thread anotherThread = new AnotherThread();
		anotherThread.start();

		new Thread() {
			public void run() {
				System.out.println(ANSI_PURPLE + "Hello from anonymous class thread.");
			}
		}.start();
		
		System.out.println(ANSI_RED + "Hello again from the main thread.");
		
	}
}
