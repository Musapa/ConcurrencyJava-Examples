package com.concurrency.examples.example13_live_locks;

public class SharedResource {

	private Worker owner;

	public SharedResource(Worker owner) {
		this.owner = owner;
	}

	public Worker getOwner() {
		return owner;
	}

	public synchronized void setOwner(Worker owner) {
		this.owner = owner;
	}
}
