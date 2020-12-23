package com.concurrency.examples.example06_notify_notifyAll;

import com.concurrency.examples.example05_synchronisation_multiple_threads.ThreadColor;

public class Notify {

	public static void main(String[] args) throws InterruptedException {

		Geek1 geeks1 = new Geek1();
		Geek2 geeks2 = new Geek2(geeks1);
		Geek3 geeks3 = new Geek3(geeks1);
		Thread t1 = new Thread(geeks1, "Thread-1");
		Thread t2 = new Thread(geeks2, "Thread-2");
		Thread t3 = new Thread(geeks3, "Thread-3");
		t1.start();
		t2.start();
		Thread.sleep(1000);
		t3.start();
	}

}

class Geek1 extends Thread {
	public void run() {
		synchronized (this) {
			System.out.println(ThreadColor.ANSI_GREEN + Thread.currentThread().getName() + "...starts");
			try {
				System.out.println(ThreadColor.ANSI_GREEN + Thread.currentThread().getName() + "...waiting");
				this.wait();
				System.out.println(ThreadColor.ANSI_GREEN + Thread.currentThread().getName() + "...continue");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(ThreadColor.ANSI_GREEN + Thread.currentThread().getName() + "...notified");
		}
	}
}

class Geek2 extends Thread {
	Geek1 geeks1;

	Geek2(Geek1 geeks1) {
		this.geeks1 = geeks1;
	}

	public void run() {
		synchronized (this.geeks1) {
			System.out.println(ThreadColor.ANSI_YELLOW + Thread.currentThread().getName() + "...starts");

			try {
				System.out.println(ThreadColor.ANSI_YELLOW + Thread.currentThread().getName() + "...waiting");
				this.geeks1.wait();
				System.out.println(ThreadColor.ANSI_YELLOW + Thread.currentThread().getName() + "...continue");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(ThreadColor.ANSI_YELLOW + Thread.currentThread().getName() + "...notified2");
		}
	}
}

class Geek3 extends Thread {
	Geek1 geeks1;

	Geek3(Geek1 geeks1) {
		this.geeks1 = geeks1;
	}

	public void run() {
		synchronized (this.geeks1) {
			System.out.println(ThreadColor.ANSI_RED + Thread.currentThread().getName() + "...starts");
			this.geeks1.notify();
			System.out.println(ThreadColor.ANSI_RED + Thread.currentThread().getName() + "...notified");
		}
	}
}