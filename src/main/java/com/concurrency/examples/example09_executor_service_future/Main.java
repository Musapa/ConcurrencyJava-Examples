package com.concurrency.examples.example09_executor_service_future;

import static com.concurrency.examples.example07_thread_interference.Main.EOF;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
		List<String> buffer = new ArrayList<String>();
		ReentrantLock bufferLock = new ReentrantLock();

		//An Executor that provides methods to manage termination and methods that can produce a Future for tracking progress of one or more asynchronous tasks. 
		ExecutorService executorService = Executors.newFixedThreadPool(3);

		MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_YELLOW, bufferLock);
		MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE, bufferLock);
		MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN, bufferLock);

		executorService.execute(producer);
		executorService.execute(consumer1);
		executorService.execute(consumer2);

		
		//Method submit extends base method Executor.execute(Runnable) by creating and returning a Future that can be used to cancel execution and/or wait for completion.
		Future<String> future = executorService.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println(ThreadColor.ANSI_WHITE + "I'm beign printed from the Callable class");
				return ThreadColor.ANSI_WHITE + "This is callable result";
			}
		});

		try {
			System.out.println(future.get());
		} catch (ExecutionException e) {
			System.out.println("Something went wrong");
		} catch (InterruptedException e) {
			System.out.println("Thread running the task was interrupted");
		}

		//The shutdown method will allow previously submitted tasks to execute before terminating
		executorService.shutdown();
		//The shutdownNow method prevents waiting tasks from starting and attempts to stop currently executing tasks.
		//executorService.shutdownNow();
		
		
		/* Upon termination, an executor has no tasks actively executing, no tasks awaiting execution, and no new tasks can be submitted. 
		An unused ExecutorService should be shut down to allow reclamation of its resources. */
	}

}

class MyProducer implements Runnable {

	private List<String> buffer;
	private String color;
	private ReentrantLock bufferLock;

	public MyProducer(List<String> buffer, String color, ReentrantLock bufferLock) {
		super();
		this.buffer = buffer;
		this.color = color;
		this.bufferLock = bufferLock;
	}

	public void run() {
		Random random = new Random();
		String[] nums = { "1", "2", "3", "4", "5" };

		for (String num : nums) {
			try {
				System.out.println(color + "Adding..." + num);
				bufferLock.lock();
				try {
					buffer.add(num);
				} finally {
					bufferLock.unlock();
				}
				Thread.sleep(random.nextInt(100));
			} catch (InterruptedException e) {
				System.out.println("Producer was interrupted");
			}
		}

		System.out.println(color + "Adding EOF and exiting...");
		bufferLock.lock();
		try {
			buffer.add("EOF");
		} finally {
			bufferLock.unlock();
		}
	}
}

class MyConsumer implements Runnable {

	private List<String> buffer;
	private String color;
	private ReentrantLock bufferLock;

	public MyConsumer(List<String> buffer, String color, ReentrantLock bufferLock) {
		super();
		this.buffer = buffer;
		this.color = color;
		this.bufferLock = bufferLock;
	}

	public void run() {
		int counter = 0;

		while (true) {
			if (bufferLock.tryLock()) {
				try {
					if (buffer.isEmpty()) {
						continue;
					}
					System.out.println(color + "The counter = " + counter);
					counter = 0;
					if (buffer.get(0).equals(EOF)) {
						System.out.println(color + "Exiting");
						break;
					} else {
						System.out.println(color + "Removed " + buffer.remove(0));
					}
				} finally {
					bufferLock.unlock();
				}
			} else {
				counter++;
			}
		}
	}

}
