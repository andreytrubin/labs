package org.trubin;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class MutexTest {
	ThreadGroup threadGroup = new ThreadGroup("mutexGroup");
	
	private int numThreads;
	private int t1;
	private int t2;
	
	private CountDownLatch latch; // initialize later
	
	private class MutexThread extends Thread {
		private int waitTime;
		private ReentrantLock mutex = new ReentrantLock();

		public MutexThread(String name, int waitTime) {
			super(threadGroup, name);
			this.waitTime = waitTime;
		}
				
		@Override
		public void run() {
			try	{
				System.out.println(String.format("Thread %s locks", getName()));
				mutex.lock();
				System.out.println(String.format("Thread %s is waiting for %d sec", getName(), waitTime));
				sleep(waitTime * 1000);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			} finally {
				System.out.println(String.format("Thread %s unlocked after %d sec of waiting", getName(), waitTime));
				mutex.unlock();
				
				latch.countDown();
			}
		}
	}

	public MutexTest(int numThreads, int t1, int t2) {
		this.numThreads = numThreads;
		this.t1 = t1;
		this.t2 = t2;
	}
	
	public void execute() {
		IntStream ints = new Random().ints(numThreads, t1, t2);
		
		latch = new CountDownLatch(numThreads);
		
		final AtomicInteger counter = new AtomicInteger();
		
		ints.forEach(waitTime -> {
			String name = String.format("MutexThread# %d", counter.getAndIncrement());
			MutexThread m = new MutexThread(name, waitTime);
			
			m.start();
		});
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
		
		System.out.println(String.format("Mutex experiment main thread exiting after spawning %d threads", counter.get()));
	}
}
