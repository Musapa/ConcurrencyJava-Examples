package com.concurrency.examples.example03_interrupt_and_join;

import static com.concurrency.examples.example02_runnable.ThreadColor.ANSI_RED;

public class MyRunnable implements Runnable {

	@Override
	public void run() {
		System.out.println(ANSI_RED + "Hello from MyRunnable implementation of run().");

	}

}
