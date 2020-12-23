package com.concurrency.examples.example03_interrupt_and_join;

import static com.concurrency.examples.example02_runnable.ThreadColor.ANSI_GREEN;

public class AnotherThread extends Thread{
	
	@Override
	public void run() {
		System.out.println(ANSI_GREEN + "Hello from " + currentThread().getName());
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.out.println(ANSI_GREEN + "Another thread woke me up.");
			//AnotherThread is Interrupted and it's returning message.
			return;
		}
		
		System.out.println(ANSI_GREEN + "Three seconds have passed and I'm awake.");
	}
	
}
