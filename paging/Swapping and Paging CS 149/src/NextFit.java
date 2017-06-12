import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class NextFit {	
	static int numberOfProcess = 0;
	static int modifiedMemory = 0;
	static boolean doCompact = false;
	
	public static void calculate() {
		char[] mm = new char[100];
		LinkedList<SwapProcess> rq = new LinkedList<SwapProcess>();
		SwapAlgorithm.initialize(mm, rq);
		nextFit(mm, rq, false);
	}
	
	public static void print(boolean compact) {
		doCompact = compact;
		char[] mm = new char[100];
		LinkedList<SwapProcess> rq = new LinkedList<SwapProcess>();
		SwapAlgorithm.initialize(mm, rq);
		nextFit(mm, rq, true);
	}
	
	static void nextFit(char[] mm, LinkedList<SwapProcess> rq, boolean doPrint) {
		char[] mainMemory = new char[100];
		mainMemory = Arrays.copyOf(mm, mm.length);
		LinkedList<SwapProcess> readyQueue = new LinkedList<SwapProcess>();
		readyQueue.addAll(rq);		
		numberOfProcess = 0;
		ArrayList<SwapProcess> processing = new ArrayList<SwapProcess>();
		SwapHole h = new SwapHole();
		int quanta = 0;
		while (quanta < 60) {
			boolean added = false;
			boolean foundFirstHole = false;
			boolean foundHole = false;			
			if (h.lastHoleIndex >= 99) {
				h.lastHoleIndex = 0;
			}
			SwapProcess p = readyQueue.peek();			
			if (!processing.isEmpty() && !added) {
				for (int i = 0; i < processing.size(); i++) {
					if (processing.get(i) != null) {
						if (processing.get(i).nextFitDuration <= quanta - processing.get(i).arrivalTime) {
							for (int j = 0; j < mainMemory.length; j++) {
								if (mainMemory[j] == processing.get(i).name) {
									mainMemory[j] = '.';
								}
							}
							if (doPrint) {
								System.out.println("QUANTA " + quanta + ": " +processing.get(i).name + " left main memory.");
							}
							processing.remove(processing.get(i));
						}

					}
				}
			}
			
			for (int i=h.lastHoleIndex; i < mainMemory.length; i++) {
				if (!foundHole && p != null) {
					if (mainMemory[i] == '.') {						
						if (!foundFirstHole) {
							h.firstHoleIndex = i;
							foundFirstHole = true;
						} 						
						h.lastHoleIndex = i;						
						if ((h.lastHoleIndex - h.firstHoleIndex) >= p.processSize-1) {							
							foundHole = true;
							if (doPrint) {
								System.out.println("QUANTA " + quanta + ": " + p.name + " entered main memory. Size: " + p.processSize + " Duration: " + p.processDuration);
							}
							added = true;
							if (!processing.contains(p)) {
								if (p != null) {
								p.arrivalTime = quanta;
								}
								processing.add(p);
							}
							readyQueue.remove();
							numberOfProcess++;
						}
					} else {
						foundFirstHole = false;
					}
				}
			}
			
			if (foundHole) {
				//System.out.println("Swapping at Quanta: " + quanta);
				for (int j = h.firstHoleIndex, o = 0; o < p.processSize; o++, j++) {
					mainMemory[j] = p.name;
				}
				if (doPrint) {
					printFit(mainMemory);
				}
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
		System.out.println("Next Fit: Number of Process Processed: " + numberOfProcess);
	}
}
