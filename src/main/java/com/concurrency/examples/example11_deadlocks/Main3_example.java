package com.concurrency.examples.example11_deadlocks;

public class Main3_example {

	public static void main(String[] args) {
		final PolitePerson jane = new PolitePerson("Jane");
		final PolitePerson john = new PolitePerson("John");

		new Thread(new Runnable() {	
			@Override
			public void run() {
				jane.sayHello(john);
			}
		}).start();
		
		new Thread(new Runnable() {	
			@Override
			public void run() {
				john.sayHello(jane);
			}
		}).start();
	}

	static class PolitePerson {
		private final String name;

		public PolitePerson(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public synchronized void sayHello(PolitePerson person) {
			System.out.format(ThreadColor.ANSI_CYAN + "%s: %s" + " has said hello to me!%n", this.name,
					person.getName());
			person.sayHelloBack(this);
		}

		public synchronized void sayHelloBack(PolitePerson person) {
			System.out.format(ThreadColor.ANSI_PURPLE + "%s: %s" + " has said hello back to me!%n", this.name,
					person.getName());
		}
	}

}
