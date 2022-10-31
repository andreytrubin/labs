package org.trubin;

import java.util.Scanner;

public class ThreadPoolExperimentParams {
	private int numThreads;
	private int threadPoolSize;

	public void readParams() {
		Scanner params = new Scanner(System.in);
		
		System.out.print( "Enter numThreads: ");
		numThreads = params.nextInt();
		
		System.out.print( "Enter threadPoolSize: ");
		threadPoolSize = params.nextInt();
	}
	
	public int getNumThreads() {
		return numThreads;
	}
	
	public int getThreadPoolSize() {
		return threadPoolSize;
	}
}
