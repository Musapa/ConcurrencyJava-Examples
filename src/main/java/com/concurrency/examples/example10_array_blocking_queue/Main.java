package com.concurrency.examples.example10_array_blocking_queue;

import static com.concurrency.examples.example07_thread_interference.Main.EOF;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import org.omg.CORBA.PUBLIC_MEMBER;

public class Main {

	public static final String EOF = "EOF";

	public static void main(String[] args) {
		ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<String>(6);

		// An Executor that provides methods to manage termination and methods that can
		// produce a Future for tracking progress of one or more asynchronous tasks.
		ExecutorService executorService = Executors.newFixedThreadPool(3);

		MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_YELLOW);
		MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE);
		MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN);

		executorService.execute(producer);
		executorService.execute(consumer1);
		executorService.execute(consumer2);

		// Method submit extends base method Executor.execute(Runnable) by creating and
		// returning a Future that can be used to cancel execution and/or wait for
		// completion.
		Future<String> future = executorService.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println(ThreadColor.ANSI_WHITE + "I'm beign printed from the Callable class");
				return "This is callable result";
			}
		});

		try {
			System.out.println(future.get());
		} catch (ExecutionException e) {
			System.out.println("Something went wrong");
		} catch (InterruptedException e) {
			System.out.println("Thread running the task was interrupted");
		}

		// The shutdown method will allow previously submitted tasks to execute before
		// terminating
		executorService.shutdown();
		// The shutdownNow method prevents waiting tasks from starting and attempts to
		// stop currently executing tasks.
		// executorService.shutdownNow();

		/*
		 * Upon termination, an executor has no tasks actively executing, no tasks
		 * awaiting execution, and no new tasks can be submitted. An unused
		 * ExecutorService should be shut down to allow reclamation of its resources.
		 */
	}

}

class MyProducer implements Runnable {

	private ArrayBlockingQueue<String> buffer;
	private String color;

	public MyProducer(ArrayBlockingQueue<String> buffer, String color) {
		super();
		this.buffer = buffer;
		this.color = color;
	}

	public void run() {
		Random random = new Random();
		String[] nums = { "1", "2", "3", "4", "5" };

		for (String num : nums) {
			try {
				System.out.println(color + "Adding..." + num);
				buffer.put(num);

				Thread.sleep(random.nextInt(100));
			} catch (InterruptedException e) {
				System.out.println("Producer was interrupted");
			}
		}

		System.out.println(color + "Adding EOF and exiting...");

		try {
			buffer.put("EOF");
		} catch (InterruptedException e) {

		}
	}
}

class MyConsumer implements Runnable {

	private ArrayBlockingQueue<String> buffer;
	private String color;

	public MyConsumer(ArrayBlockingQueue<String> buffer, String color) {
		super();
		this.buffer = buffer;
		this.color = color;
	}

	public void run() {

		while (true) {
			synchronized (buffer) {
				try {
					if (buffer.isEmpty()) {
						continue;
					}
					if (buffer.peek().equals(EOF)) {
						System.out.println(color + "Exiting");
						break;
					} else {
						System.out.println(color + "Removed " + buffer.take());
					}
				} catch (InterruptedException e) {

				}
			}
		}
	}

}
