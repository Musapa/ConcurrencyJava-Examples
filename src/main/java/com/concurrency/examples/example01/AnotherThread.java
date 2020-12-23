package com.concurrency.examples.example01;

import static com.concurrency.examples.example01.ThreadColor.ANSI_GREEN;

public class AnotherThread extends Thread{
	
	@Override
	public void run() {
		System.out.println(ANSI_GREEN + "Hello from another thread.");
	}
	
}
