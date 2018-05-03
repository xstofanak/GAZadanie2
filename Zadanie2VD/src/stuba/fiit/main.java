package stuba.fiit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class main {

	
	
	public static void main(String[] args) {
		
		//stable marriage problem
		
		//variables
		int numberOfN = 0;
		ArrayList<boss>  bosses = null;
		ArrayList<worker> workers = null;
		ArrayList<Integer> priorityHelper = null;
		boss bossHelper = null;
		worker workerHelper = null;
		
		//enter input
		Scanner scannerForInput = new Scanner(System.in);
		System.out.println("Enter a number of n: ");
		numberOfN = scannerForInput.nextInt();
		scannerForInput.close();
		
		//create and generate bosses and workers
		bosses = new ArrayList<boss>();
		workers = new ArrayList<worker>();
		bossHelper = new boss();
		workerHelper = new worker();
		
		for(int i = 0; i < numberOfN; i++ ) {
			
			//set bossHelper for adding it to arraylist
			//set id
			bossHelper.setID(i);
			//set random linearPriority
			priorityHelper = new ArrayList<Integer>();
			for(int j = 0; j < numberOfN; j++ ) {
				priorityHelper.add(j + numberOfN);
				Collections.shuffle(priorityHelper);
			}
			bossHelper.setLinearPriority(priorityHelper);
			//set workerHelper for adding it to arraylist
			//set id
			workerHelper.setID(i + numberOfN);
			//set random linearPriority
			priorityHelper = new ArrayList<Integer>();
			for(int j = 0; j < numberOfN; j++ ) {
				priorityHelper.add(j);
				Collections.shuffle(priorityHelper);
			}
			workerHelper.setLinearPriority(priorityHelper);
			
			//add worker and boss to arraylist
			bosses.add(bossHelper);
			workers.add(workerHelper);
		}
	}
	
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}
