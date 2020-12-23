package com.concurrency.examples.example06_notify_notifyAll;

import java.util.Random;

import com.concurrency.examples.example05_synchronisation_multiple_threads.ThreadColor;

public class NotifyAll {

	public static void main(String[] args) {
		Message message = new Message();
		(new Thread(new Writer(message))).start();
		(new Thread(new Reader(message))).start();
	}

}

//
class Message {
	private String message;
	private boolean empty = true;

	// read
	public synchronized String read() {
		System.out.println(ThreadColor.ANSI_YELLOW + "READ EMPTY " + empty);
		//empty = true
		while (empty) { 
			try {
				System.out.println(ThreadColor.ANSI_GREEN + "READ wait");
				wait();
			} catch (InterruptedException e) {

			}
		}
		//empty = false
		empty = true;
		System.out.println(ThreadColor.ANSI_CYAN + "Read notify");
		notifyAll();
		return message;
	}

	// write
	public synchronized void write(String message) {
		System.out.println(ThreadColor.ANSI_YELLOW + "WRITE EMPTY " + empty);
		//empty = false
		while (!empty) {
			try {
				System.out.println(ThreadColor.ANSI_GREEN + "WRITE wait");
				wait();
			} catch (InterruptedException e) {

			}
		}
		//empty = true
		empty = false;
		this.message = message;
		System.out.println(ThreadColor.ANSI_CYAN + "Write notify");
		notifyAll();
	}
}

//writer
class Writer implements Runnable {
	private Message message;

	public Writer(Message message) {
		this.message = message;
	}

	@Override
	public void run() {
		String messages[] = { ThreadColor.ANSI_RESET + "Humpty Dumpty set on a wall",
				ThreadColor.ANSI_RESET + "Humpty Dumpty had a great fall",
				ThreadColor.ANSI_RESET + "All the king's horses and all the king's men",
				ThreadColor.ANSI_RESET + "Couldn't put Humpty together again" };

		Random random = new Random();

		for (int i = 0; i < messages.length; i++) {
			message.write(messages[i]);
			try {
				Thread.sleep(random.nextInt(2000));
			} catch (InterruptedException e) {

			}
		}
		message.write("Finished");
		System.out.println(ThreadColor.ANSI_RED + "Finisherd writer");
	}
}

//reader
class Reader implements Runnable {
	private Message message;

	public Reader(Message message) {
		this.message = message;
	}

	@Override
	public void run() {
		Random random = new Random();

		for (String latestMessage = message.read(); !latestMessage.equals("Finished"); latestMessage = message.read()) {
			System.out.println(latestMessage);
			try {
				Thread.sleep(random.nextInt(2000));
			} catch (InterruptedException e) {

			}
		}
		System.out.println(ThreadColor.ANSI_RED + "Finished reader");
	}

}
