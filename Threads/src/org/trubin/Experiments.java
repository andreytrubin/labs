package org.trubin;

import java.util.Scanner;

public class Experiments {
	public static void main(String[] args) {
		Experiments experiments = new Experiments();
		
		do {
			experiments.printMenu();
			experiments.readInputAndRunExperiment();
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
				mutexExperiment();
				break;
			case 2:
				semaphorExperiment();
				break;
				
			case 3:
				atomicExperiment();
				break;
				
			case 4:
				threadPoolExperiment();
				break;
			default: System.out.println("Unrecognized option");
		}
	}
	
	private void mutexExperiment() {
		ExperimentParams params = new ExperimentParams();
		params.readParams();
		new MutexExperiment(params.getNumThreads(), params.getT1(), params.getT2()).execute();
	}
	
	
	private void semaphorExperiment() {
		ExperimentParams params = new ExperimentParams();
		params.readParams();
		new SemaphoreExperiment(params.getNumThreads(), params.getT1(), params.getT2()).execute();
	}
	
	private void atomicExperiment() {
		System.out.println("Under construction");
	}
	
	private void threadPoolExperiment() {
		ThreadPoolExperimentParams params = new ThreadPoolExperimentParams();
		params.readParams();
		new ThreadPoolExperiment(params.getNumThreads(), params.getThreadPoolSize()).execute();
	}
	
}
