package com.concurrency.examples.example13_live_locks;

public class Worker {

	private String name;
	private boolean active;

	public Worker(String name, boolean active) {
		this.name = name;
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public boolean isActive() {
		return active;
	}

	public synchronized void work(SharedResource sharedResource, Worker otherWorker) {

		while (active) {
			if (sharedResource.getOwner() != this) {
				try {
					wait(10);
				} catch (InterruptedException e) {

				}
				continue;
			}

			if (otherWorker.isActive()) {
				System.out.println(ThreadColor.ANSI_CYAN + getName() + " : give the resource to the worker "
						+ otherWorker.getName());
				sharedResource.setOwner(otherWorker);
				continue;
			}

			System.out.println(ThreadColor.ANSI_GREEN + getName() + ": working on the common resource");
			active = false;
			sharedResource.setOwner(otherWorker);
		}
	}
}
