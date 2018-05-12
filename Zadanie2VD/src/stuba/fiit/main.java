package stuba.fiit;

import stuba.fiit.entity.Boss;
import stuba.fiit.entity.Entity;
import stuba.fiit.entity.Pair;
import stuba.fiit.entity.Worker;
import stuba.fiit.parallel.SMPSolver;
import stuba.fiit.parallel.SMPSolverBoss;

import java.util.*;

public class main {

	public static void main(String[] args) {
		
		//stable marriage problem
		
		//variables
		int numberOfN = 0;
		ArrayList<Boss>  bosses = null;
		ArrayList<Worker> workers = null;
		ArrayList<Integer> priorityHelper = null;
		
		double lStartTime = 0;
    	double lEndTime = 0;
        double finalTime = 0;
		
		//enter input
		Scanner scannerForInput = new Scanner(System.in);
		System.out.println("Enter a number of n: ");
		numberOfN = scannerForInput.nextInt();
		scannerForInput.close();
		
		//create and generate bosses and workers
		bosses = new ArrayList<Boss>();
		workers = new ArrayList<Worker>();
		
		for(int i = 0; i < numberOfN; i++ ) {
            // initialization
		    Boss bossHelper = new Boss();
            Worker workerHelper = new Worker();

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
			
			//add Worker and Boss to arraylist
			bosses.add(bossHelper);
			workers.add(workerHelper);
		}

		for(int i = 0; i < numberOfN; i++ ) {
			
			//print generated values for boss
			System.out.print("Boss: " + bosses.get(i).getID() + ", Priority: ");
			
			for(int j = 0; j < numberOfN; j++ ) {
				System.out.print(bosses.get(i).getLinearPriority().get(j) + "|");
			}
			System.out.println();
		}
		

		System.out.println();
		
		for(int i = 0; i < numberOfN; i++ ) {
			
			//print generated values for worker
			System.out.print("Worker: " + workers.get(i).getID() + ", Priority: ");
			
			for(int j = 0; j < numberOfN; j++ ) {
				System.out.print(workers.get(i).getLinearPriority().get(j) + "|");
			}

			System.out.println();
		}		

		System.out.println();
		
		lStartTime = System.nanoTime();
		// test
        SMPSolver smpSolver = new SMPSolverBoss(new HashSet<>(workers), new HashSet<>(bosses), 2);
        Set<Pair<Entity, Entity>> stablePairs = smpSolver.findStablePairs();
        stablePairs.stream()
                .sorted(Comparator.comparingInt(o -> o.getxElement().getID()))
                .forEachOrdered(entityEntityPair -> {
                    System.out.println("Boss: " + entityEntityPair.getxElement().getID() + " - "
                            + "Worker: " + entityEntityPair.getyElement().getID());
                });
        lEndTime = System.nanoTime();
        finalTime = lEndTime - lStartTime;
        
        System.out.println();
        System.out.println("Elapsed time in seconds: " + finalTime / 1000000000);
	}
}
