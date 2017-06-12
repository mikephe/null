import java.util.Random;

public class FIFO {
	static PageProcess[] physicalMemory = new PageProcess[4];
	static PageProcess[] pages = new PageProcess[10];
	static boolean added = false;
	static int nextRef = 0;
	static int r = 0;
	static double hit = 0;
	static boolean doPrint;
	static int quanta = 0;
	
	public static void calculate(boolean print) {
		hit = 0;
		doPrint = print;
		for (int i = 0; i < physicalMemory.length; i++) {
			PageProcess p = new PageProcess();
			p.id = 'n';
			physicalMemory[i] = p;
		}
		char c = '0';
		for (int i = 0; i < pages.length; i++) {
			PageProcess p = new PageProcess();
			p.id = c;
			pages[i] = p;
			c++;
		}
		System.out.println("WorkingSet/Quanta/PageToBeAdded");
		fifo();
	}
	
	static boolean checkPhysicalMemory(int index) {
		for (int i = 0; i < physicalMemory.length; i++) {
			if (pages[index].id == physicalMemory[i].id) {
				return true;
			}
		}
		return false;
	}
	
	static void swapEmpty() {
		for (int i = 0; i < physicalMemory.length; i++) {
			if (!added) {
				if (physicalMemory[0].id == 'n') {
					replaceEmpty(0);
				} else if (physicalMemory[1].id == 'n') {
					replaceEmpty(1);
				} else if (physicalMemory[2].id == 'n') {
					replaceEmpty(2);
				} else if (physicalMemory[3].id == 'n') {
					replaceEmpty(3);
				} 
			}
		}
	}
	
	static void fifo() {
		quanta = 0;
		nextRef = randInt(0, 9);
		while (quanta < 100) {
			quanta++;
			added = false;
			calcNextRef();
			swapEmpty();
			
			int pageFault = findFirstReference();	
			if (!added) {
				if (checkPhysicalMemory(r)) {
					added = true;
					hit++;
					print(quanta);
				} else {
					if (doPrint) {
						System.out.println("--------------------------------------------");
						System.out.print("Page " + pages[r].id + " pages in");
						System.out.print(" / Page " + physicalMemory[pageFault].id + " evicted. Current hit: " + hit + "\n");
						System.out.println("--------------------------------------------");
					}
					pages[r].loadedMemoryLocation = quanta;
					physicalMemory[pageFault] = pages[r];
					added = true;
					print(quanta);
				}
			}
		}		
	}
	
	static void calcNextRef() {
		//System.out.println("R: " + r + " NextRef " + nextRef);
		r = nextRef;
		if (r >= 0 && r < 7) {
			int random = randInt(-1, 1);
			//System.out.println("Random: " + random);
			nextRef += random;
			if (nextRef < 0) {
				nextRef = 0;
			}
		}
		if (r >= 7 && r <= 9) {
			int random = randInt(2, 8);
			//System.out.println("Random: " + random);
			nextRef += random;
			if (nextRef > 9) {
				nextRef = 0;
			}
		}
		//System.out.println("Next Reference: " + nextRef + " id: " + pages[nextRef].id);
	}
	
	static void replaceEmpty(int i) {
		added = true;
		if (i == 0) {
			if (doPrint) {
				System.out.println("--------------------------------------------");
				System.out.print("Page " + pages[r].id + " pages in\n");
				System.out.println("--------------------------------------------");
			}
			pages[r].loadedMemoryLocation = quanta;
			physicalMemory[i] = pages[r];
			print(quanta);
		} else {
			if (checkPhysicalMemory(r)) {
				hit++;
				if (doPrint) {
					print(quanta);
				}
			} else {
				pages[r].loadedMemoryLocation = quanta;
				if (doPrint) {
				System.out.println("--------------------------------------------");
				System.out.print("Page " + pages[r].id + " pages in");
				System.out.print(" / Page " + physicalMemory[i].id + " evicted. Current hit: " + hit + "\n");
				System.out.println("--------------------------------------------");
				}
				physicalMemory[i] = pages[r];
				print(quanta);
			}
		}
	}
	
	static void print(int quanta) {
		if (doPrint) {
			System.out.print("Working Set: ");
			for (int i = 0; i < physicalMemory.length; i++) {
				System.out.print(physicalMemory[i].id);
			}
			System.out.print(" | Quanta: " + quanta + " | Page Frame: " + pages[r].id + " |\n");
		}
	}
	
	static int findFirstReference() {
		int index = 0;
		for (int i = 0; i < physicalMemory.length; i++) {
			if (physicalMemory[i].loadedMemoryLocation < physicalMemory[index].loadedMemoryLocation) {
				index = i;
			}
		}
		return index;
	}
	
	static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}
