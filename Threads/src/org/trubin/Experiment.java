package org.trubin;

import java.util.Scanner;

public class Experiment {
	public static void main(String[] args) {
		Experiment experiment = new Experiment();
		
		do {
			experiment.printMenu();
			experiment.readInputAndRunExperiment();
		} while (true);
	}
	
	private void printMenu() {
		System.out.println("Select experiment:");
		System.out.println("1 - Mutex");
		System.out.println("2 - Semaphor");
		System.out.println("3 - Atomic");
		System.out.println("4 - PullThread");
		System.out.println("Press Ctrl-C to exit");
	}
	
	private void readInputAndRunExperiment() {
		Scanner menuInput = new Scanner(System.in);
		int menuItem = menuInput.nextInt();
		
		switch (menuItem) {
			case 1:
				mutexTest();
				break;
			case 2:
				break;
			default: System.out.println("Unrecognized option");
		}
		
		System.out.println("Experiment completed.\n");
	}
	
	private void mutexTest() {
		// Read number of threads, min and mas duration
		Scanner params = new Scanner(System.in);
		
		System.out.println( "Enter numThreads: " );
		int numThreads = params.nextInt();
		
		System.out.println( "Enter t1: " );
		int t1 = params.nextInt();
		
		System.out.println("Enter t2: ");
		int t2 = params.nextInt();
		
		if (t1 >= t2) {
			System.out.println("t1 should be less than t2");
			return;
		}
		
		new MutexTest(numThreads, t1, t2).execute();
	}
	
	
}
