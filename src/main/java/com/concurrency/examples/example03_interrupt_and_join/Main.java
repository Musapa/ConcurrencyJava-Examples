package com.concurrency.examples.example03_interrupt_and_join;

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
				try {
					anotherThread.join(2000);
					//This message will appear when AnotherThread finished after that message.
					System.out.println(ANSI_RED + "AnotherThread terminated, or timed out, so I'm running again.");
				} catch (InterruptedException e) {
					//If we interrupt myRunnableThread this will appear.
					System.out.println(ANSI_RED + "I couldn't wait after all. I was interrupted.");
				}
			}
		});
		myRunnableThread.start();
		
		//interrupt AnotherThread or MyRunnableThread
		//anotherThread.interrupt();
		//myRunnableThread.interrupt();

		System.out.println(ANSI_RESET + "Hello again from the main thread.");

	}
}
