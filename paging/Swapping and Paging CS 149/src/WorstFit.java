import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class WorstFit {
	static int numberOfProcess = 0;
	static int modifiedMemory = 0;
	static boolean doCompact = false;
	public static void calculate() {
		char[] mm = new char[100];
		LinkedList<SwapProcess> rq = new LinkedList<SwapProcess>();
		SwapAlgorithm.initialize(mm, rq);
		worstFit(mm, rq, false);
	}
	
	public static void print(boolean compact) {
		doCompact = compact;
		char[] mm = new char[100];
		LinkedList<SwapProcess> rq = new LinkedList<SwapProcess>();
		SwapAlgorithm.initialize(mm, rq);
		worstFit(mm, rq, true);
	}
	
	static void worstFit(char[] mm, LinkedList<SwapProcess> rq, boolean doPrint) {
		char[] mainMemory = new char[100];
		mainMemory = Arrays.copyOf(mm, mm.length);
		LinkedList<SwapProcess> readyQueue = new LinkedList<SwapProcess>();
		readyQueue.addAll(rq);
		ArrayList<SwapHole> holeList = new ArrayList<SwapHole>();
		
		numberOfProcess = 0;
		ArrayList<SwapProcess> processing = new ArrayList<SwapProcess>();
		
		int quanta = 0;
		while (quanta < 60) {
			boolean added = false;
			boolean foundFirstHole = false;
			SwapHole h = new SwapHole();
			SwapProcess p = readyQueue.peek();
			
			if (!processing.isEmpty()) {
				for (int i = 0; i < processing.size(); i++) {
					if (processing.get(i) != null) {
						if (processing.get(i).worstFitDuration <= quanta - processing.get(i).arrivalTime) {				
							for (int j = 0; j < mainMemory.length; j++) {
								if (mainMemory[j] == processing.get(i).name) {
									mainMemory[j] = '.';
								}
							}
							if (doPrint) {
								System.out.println("QUANTA " + quanta + ": " +processing.get(i).name + " left main memory.");
								printFit(mainMemory);
							}
							processing.remove(processing.get(i));
						}
					}
				}
			}			

			for (int i=0; i < mainMemory.length; i++) {
				if (mainMemory[i] == '.') {
					if (!foundFirstHole) {
						h.firstHoleIndex = i;
						foundFirstHole = true;
					} 
					h.lastHoleIndex = i;
					if (h.lastHoleIndex >= 99) {
						SwapHole newHole = new SwapHole();
						newHole.firstHoleIndex = h.firstHoleIndex;
						newHole.lastHoleIndex = h.lastHoleIndex;
						holeList.add(newHole);
					}
				} else {
					if (foundFirstHole) {
						foundFirstHole = false;
						SwapHole newHole = new SwapHole();
						newHole.firstHoleIndex = h.firstHoleIndex;
						newHole.lastHoleIndex = h.lastHoleIndex;
						holeList.add(newHole);
					}
				}
			}
			
			if (!holeList.isEmpty()) {				
				SwapHole largestHole = new SwapHole();
				largestHole.firstHoleIndex = holeList.get(0).firstHoleIndex;
				largestHole.lastHoleIndex = holeList.get(0).lastHoleIndex;
				int largestHoleSize = holeList.get(0).lastHoleIndex - holeList.get(0).firstHoleIndex;
				if (largestHoleSize < p.processSize) {
					largestHoleSize = 0;
				}
				for (int i = 0; i < holeList.size(); i++) {
					SwapHole currentHole = holeList.get(i);
					int currentHoleSize = (currentHole.lastHoleIndex - currentHole.firstHoleIndex);
					if (currentHoleSize >= largestHoleSize  && h.lastHoleIndex - h.firstHoleIndex >= p.processSize) {
						largestHole = currentHole; 
					}
				}
				if (largestHole.lastHoleIndex - largestHole.firstHoleIndex >= p.processSize-1) {
					//System.out.println("Swapping at Quanta: " + quanta);
					for (int j = largestHole.firstHoleIndex, o = 0; o < p.processSize; o++, j++) {
						mainMemory[j] = p.name;
					}
					numberOfProcess++;
					holeList.clear();
					if (doPrint) {
						System.out.println("QUANTA " + quanta + ": " + p.name + " entered main memory. Size: " + p.processSize + " Duration: " + p.processDuration);
						printFit(mainMemory);
					}
					if (!processing.contains(p)) {
						if (p != null) {
						p.arrivalTime = quanta;
						}
						processing.add(p);
					}
					added = true;
					readyQueue.remove();
				}
				holeList.clear();
			}
			if (!added) {
				quanta++;
				if ((quanta == 30 || quanta == 59) && doCompact) {
					modifiedMemory = SwapAlgorithm.memCompact(mainMemory);
				}
			}
		}
		if (doPrint) {
			printNumberProc(mainMemory);
		}
	}
	
	static void printFit(char[] mainMemory) {
		for (int k = 0; k < mainMemory.length; k++) {
			System.out.print(mainMemory[k]);
		}
		System.out.println();
	}
	
	static void printNumberProc(char[] mainMemory) {
		System.out.println();
		System.out.println("Worst Fit: Number of Process Processed: " + numberOfProcess);
	}
}
