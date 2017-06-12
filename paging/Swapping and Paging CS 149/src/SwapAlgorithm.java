import java.util.ArrayList;
import java.util.LinkedList;


class SwapHole {
	public int firstHoleIndex;
	public int lastHoleIndex;
}

public class SwapAlgorithm {

	public static void initialize(char[] mainMemory, LinkedList<SwapProcess> readyQueue) {
		for (int i = 0; i < mainMemory.length; i++) {
			mainMemory[i] = '.';
		}
		int q = 0;
		char processName = 'A';
		//int numProc = 0;
		//int totalTime = 0;
		while (q <= 500) {
			SwapProcess p = new SwapProcess();
			if (processName == 'Z') {
				processName = 'a';
			}
			if (processName == 'z') {
				processName = 'A';
			}
			p.name = processName;
			//System.out.println(p);
			readyQueue.add(p);
			q++;
			processName++;
			//System.out.println("Name: " + p.name + " Duration: " + p.processDuration + " Size: " + p.processSize);
			//numProc++;
			//totalTime += p.processDuration;
		}
		//System.out.println("NumProc: " + numProc + " TotalTime: " + totalTime);
	}
	

	public static int memCompact(char[] main) {
		int count = 0;
		ArrayList<Character> mem = new ArrayList<Character>();
		
		for(int i = 0; i < main.length; i++) {
			if(main[i] == '.')
				count++;
			else
				mem.add(main[i]);
		}

		for(int j = 0; j < count; j++)
			mem.add('.');
		
		int modifiedMemory = 0;
		for (int i = 0; i < main.length; i++) {
			if (main[i] != mem.get(i)) {
				modifiedMemory++;
			}
		}
		
		for (int i = 0; i < main.length; i++) {
			main[i] = mem.get(i);
		}
		System.out.println("MEMORY COMPACTION");
		FirstFit.printFit(main);

		
		return modifiedMemory;
	}
	
}
