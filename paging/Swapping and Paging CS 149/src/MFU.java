import java.util.Random;

public class MFU {
	static PageProcess[] physicalMemory = new PageProcess[4];
	static PageProcess[] pages = new PageProcess[10];
	static boolean added = false;
	static int nextRef = 0;
	static int r = 0;
	static double hit;
	static boolean doPrint;
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
	
	static void fifo() {
		int quanta = 0;

		while (quanta < 100) {
			quanta++;
			added = false;
			//print();
			for (int i = 0; i < physicalMemory.length; i++) {
				if (!added) {
					//System.out.println(added);
					if (physicalMemory[0].id == 'n') {
						replaceEmpty(0, quanta);
					} else if (physicalMemory[1].id == 'n') {
						replaceEmpty(1, quanta);
					} else if (physicalMemory[2].id == 'n') {
						replaceEmpty(2, quanta);
					} else if (physicalMemory[3].id == 'n') {
						replaceEmpty(3, quanta);
					} else {
						calcNextRef();
						pages[r].useCount++;
						int firstReference = findHighestUseCount();
						if (checkPhysicalMemory(r)) {							
							hit++;
							added = true;
							print(quanta);
						} else {
							if (doPrint) {
								System.out.println("--------------------------------------------");
								System.out.print("Page " + pages[r].id + " pages in");
								System.out.print(" / Page " + physicalMemory[firstReference].id + " evicted.\n");
								System.out.println("--------------------------------------------");
							}
							physicalMemory[firstReference] = pages[r];
							added = true;
							print(quanta);
						}
					}
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
	
	static void replaceEmpty(int i, int quanta) {
		if (i == 0) {
			if (doPrint) {
				System.out.println("--------------------------------------------");
				System.out.print("Page " + pages[r].id + " pages in\n");
				System.out.println("--------------------------------------------");
			}
			r = randInt(0, 9);
			if (r >= 0 && r < 7) {
				int random = randInt(-1, 2);
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
			added = true;
			pages[r].loadedMemoryLocation = quanta;
			physicalMemory[i] = pages[r];
			pages[r].useCount++;
			print(quanta);
		} else {
			calcNextRef();
			added = true;
			//System.out.println("Page: " + pages[r].id + " PM: " + physicalMemory[i].id + " added: " + added);
			//System.out.println("Is page " + pages[r].id + " at " + r  +  " the same as " + physicalMemory[i].id);
			pages[r].useCount++;
			if (checkPhysicalMemory(r)) {
				pages[r].loadedMemoryLocation = quanta;				
				hit++;
				print(quanta);
			} else {
				if (doPrint) {
					System.out.println("--------------------------------------------");
					System.out.print("Page " + pages[r].id + " pages in");
					System.out.print(" / Page " + physicalMemory[i].id + " evicted.\n");
					System.out.println("--------------------------------------------");
				}
				pages[r].loadedMemoryLocation = quanta;
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
	
	static int findHighestUseCount() {
		int index = 0;
		for (int i = 0; i < physicalMemory.length; i++) {
			//System.out.println("Is " + physicalMemory[i].loadedMemoryLocation + " smaller than " + physicalMemory[index].loadedMemoryLocation + "?");
			if (physicalMemory[i].useCount > physicalMemory[index].useCount) {
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
