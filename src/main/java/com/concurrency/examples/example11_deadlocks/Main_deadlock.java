package com.concurrency.examples.example11_deadlocks;

public class Main_deadlock {

	public static Object lock1 = new Object();
	public static Object lock2 = new Object();

	public static void main(String[] args) {
		
		Thread1 thread1 = new Thread1();
		Thread2 thread2 = new Thread2();
		
		thread1.start();
		thread2.start();

	}

	private static class Thread1 extends Thread {
		public void run() {
			synchronized (lock1) {
				System.out.println(ThreadColor.ANSI_YELLOW + "Thread 1: Has lock1");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

				}
				System.out.println(ThreadColor.ANSI_YELLOW + "Thread 1: Waiting for lock2");
				synchronized (lock2) {
					System.out.println(ThreadColor.ANSI_YELLOW + "Thread 1: Has lock1 and lock2");
				}
				System.out.println(ThreadColor.ANSI_YELLOW + "Thread 1: Realased lock2");
			}
			System.out.println(ThreadColor.ANSI_YELLOW + "Thread 1: Realised lock1. Exiting...");
		}
	}
	
	
	private static class Thread2 extends Thread {
		public void run() {
			synchronized (lock2) {
				System.out.println(ThreadColor.ANSI_RED + "Thread 2: Has lock2");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

				}
				System.out.println(ThreadColor.ANSI_RED + "Thread 2: Waiting for lock1");
				synchronized (lock1) {
					System.out.println(ThreadColor.ANSI_RED + "Thread 2: Has lock2 and lock1");
				}
				System.out.println(ThreadColor.ANSI_RED + "Thread 2: Realased lock1");
			}
			System.out.println(ThreadColor.ANSI_RED + "Thread 2: Realised lock2. Exiting...");
		}
	}

}
