package org.trubin;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class SemaphoreExperiment {
	ThreadGroup threadGroup = new ThreadGroup("semaphorGroup");
	private static final int MAX_THREADS = 4;
	private Semaphore semaphore = new Semaphore(MAX_THREADS, true);
	
	private int numThreads;
	private int t1;
	private int t2;
	
	private CountDownLatch latch; // initialize later
	
	private class SemaphorThread extends Thread {
		private int waitTime;

		public SemaphorThread(String name, int waitTime) {
			super(threadGroup, name);
			this.waitTime = waitTime;
		}
				
		@Override
		public void run() {
			try	{
				System.out.println(String.format("Thread %s is waiting for permit", getName()));
				semaphore.acquire();
				System.out.println(String.format("Thread %s is gets a permit", getName()));
				
            	System.out.println(String.format("Thread %s sleeps for %d sec", getName(), waitTime));
				sleep(waitTime * 1000);
				
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			} finally {
				System.out.println(String.format("Thread %s releases permit after %d sec", getName(), waitTime));
				semaphore.release();
				
				latch.countDown();
			}
		}
	}

	public SemaphoreExperiment(int numThreads, int t1, int t2) {
		this.numThreads = numThreads;
		this.t1 = t1;
		this.t2 = t2;
	}
	
	public void execute() {
		IntStream ints = new Random().ints(numThreads, t1, t2);
		
		latch = new CountDownLatch(numThreads);
		
		final AtomicInteger counter = new AtomicInteger();
		
		ints.forEach(waitTime -> {
			String name = String.format("SemaphoreThread# %d", counter.getAndIncrement());
			SemaphorThread m = new SemaphorThread(name, waitTime);
			
			m.start();
		});
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
		
		System.out.println("Semaphore experiment thread completed");
	}
}
