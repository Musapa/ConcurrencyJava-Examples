package com.concurrency.examples.example2_runnable;

import static com.concurrency.examples.example2_runnable.ThreadColor.ANSI_GREEN;

public class AnotherThread extends Thread{
	
	@Override
	public void run() {
		System.out.println(ANSI_GREEN + "Hello from " + currentThread().getName());
		try {
			//AnotherThread sleeps for 3 sec
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.out.println(ANSI_GREEN + "Another thread woke me up.");
		}
		
		System.out.println(ANSI_GREEN + "Three seconds have passed and I'm awake.");
	}
	
}
