package org.trubin;

import java.util.Scanner;

public class ExperimentParams {
	private int numThreads;
	private int t1;
	private int t2;

	public void readParams() {
		// Read number of threads, min and mas duration
		Scanner params = new Scanner(System.in);
		
		System.out.println( "Enter numThreads: " );
		numThreads = params.nextInt();
		
		System.out.println( "Enter t1: " );
		t1 = params.nextInt();
		
		System.out.println("Enter t2: ");
		t2 = params.nextInt();
		
		if (t1 >= t2) {
			System.out.println("t1 should be less than t2");
			return;
		}
	}
	
	public int getNumThreads() {
		return numThreads;
	}
	
	public int getT1() {
		return t1;
	}
	
	public int getT2() {
		return t2;
	}
}