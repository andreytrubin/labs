package org.trubin;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExperiment {
	ThreadGroup threadGroup = new ThreadGroup("threadPoolGroup");
	ExecutorService execServ = Executors.newSingleThreadExecutor();
	
	private int numThreads;
	private int threadPoolSize;
	
	private CountDownLatch latch; // initialize later
	
	private class ThreadPoolCallable implements Callable<Number> {
		private String name;


		public ThreadPoolCallable(String name) {
			this.name = name;
		}
		
		@Override
		public Number call() throws Exception {
			try	{
				// imitate calculations
				Number res = new Random().nextGaussian();
				System.out.println(String.format("Thread %s waits for %d seconds", name, 5));
				Thread.sleep(5000);
				
				return res;
			} catch (InterruptedException e) {
				
			} finally {
				latch.countDown();
			}
			
			return null;
		}
	}

	public ThreadPoolExperiment(int numThreads, int threadPoolSize) {
		this.numThreads = numThreads;
		this.threadPoolSize = threadPoolSize;
	}
	
	public void execute() {
		latch = new CountDownLatch(numThreads);
		
		final AtomicInteger counter = new AtomicInteger();
		
		// Prepare executors (potentially more than the threads in the pool)
		List<ThreadPoolCallable> callables = new LinkedList<ThreadPoolCallable>();
		
		for (int i = 0; i < numThreads; i++) {
			callables.add(new ThreadPoolCallable("Thread#"+i));
		}
		
		// Create threads pool and execute via Futures
		ExecutorService es = Executors.newFixedThreadPool(threadPoolSize);
		
		try {
			List<Future<Number>> futures = es.invokeAll(callables);
			for (Future<Number> f : futures) {
				try {
					System.out.println(String.format("Calculated: %f:", f.get()));
				} catch (ExecutionException e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		} finally {
			es.shutdown();
		}
		
		System.out.println("ThreadPool experiment thread completed");
	}
}
