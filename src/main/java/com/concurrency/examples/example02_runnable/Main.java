package com.concurrency.examples.example02_runnable;

import static com.concurrency.examples.example02_runnable.ThreadColor.ANSI_PURPLE;
import static com.concurrency.examples.example02_runnable.ThreadColor.ANSI_RED;
import static com.concurrency.examples.example02_runnable.ThreadColor.ANSI_RESET;

public class Main {

	public static void main(String[] args) {

		System.out.println("Hello from main thread.");

		Thread anotherThread = new AnotherThread();
		//setting name for AnotherThread.java
		anotherThread.setName("== Another Thread ==");
		anotherThread.start();

		new Thread() {
			public void run() {
				System.out.println(ANSI_PURPLE + "Hello from the anonymous class thread.");
			}
		}.start();

		//running MyRunnableThread and @Override MyRunnableThread.java
		Thread myRunnableThread = new Thread(new MyRunnable() {
			@Override
			public void run() {
				System.out.println(ANSI_RED + "Hello from the anonymous class's implementation of run().");
			}
		});
		myRunnableThread.start();

		System.out.println(ANSI_RESET + "Hello again from the main thread.");

	}
}
