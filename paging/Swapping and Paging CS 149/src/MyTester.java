import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class MyTester {
	
	
	public static void main(String[] args) throws FileNotFoundException {

		// TODO Auto-generated method stub
		System.setOut(new PrintStream(new FileOutputStream("swapoutput.txt")));
		SwapTest();
		System.setOut(new PrintStream(new FileOutputStream("pageoutput.txt")));
		PageTest();
		//RandomPick.calculate();
	
	}
	
	public static void SwapTest() {
		int averageFF = 0;
		int averageNF = 0;
		int averageBF = 0;
		int averageWF = 0;
		
		
		int averageFF2 = 0;
		int averageNF2 = 0;
		int averageBF2 = 0;
		int averageWF2 = 0;
		int averageFFMM = 0;
		int averageNFMM = 0;
		int averageBFMM = 0;
		int averageWFMM = 0;
		
		
		
		
		
		
		
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("---------------FIRST FIT MAP:------------------");
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		//FirstFit.print();
		for (int i = 0; i < 5; i++) {
			System.out.println("===================Run: " + (i+1) + "====================" );
			System.out.println();
			FirstFit.print(false);
			averageFF += FirstFit.numberOfProcess;
		}
		System.out.println();

		
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("---------------NEXT FIT MAP:------------------");
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		//NextFit.print();
		for (int i = 0; i < 5; i++) {
			System.out.println("===================Run: " + (i+1) + "====================" );
			System.out.println();
			NextFit.print(false);
			averageNF += NextFit.numberOfProcess;		
		}
		System.out.println();

		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("---------------BEST FIT MAP:------------------");
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		//BestFit.print();
		for (int i = 0; i < 5; i++) {
			System.out.println("===================Run: " + (i+1) + "====================" );
			System.out.println();
			BestFit.print(false);
			averageBF += BestFit.numberOfProcess;
		}
		System.out.println();

		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("---------------WORST FIT MAP:------------------");
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		//WorstFit.print();
		for (int i = 0; i < 5; i++) {
			System.out.println("===================Run: " + (i+1) + "====================" );
			System.out.println();
			WorstFit.print(false);
			averageWF += WorstFit.numberOfProcess;
		}
		System.out.println();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("---------------FIRST FIT MAP:------------------");
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		//FirstFit.print();
		for (int i = 0; i < 5; i++) {
			System.out.println("===================Run: " + (i+1) + "====================" );
			System.out.println();
			FirstFit.print(true);
			averageFF2 += FirstFit.numberOfProcess;
			averageFFMM += FirstFit.modifiedMemory;
		}
		System.out.println();

		
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("---------------NEXT FIT MAP:------------------");
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		//NextFit.print();
		for (int i = 0; i < 5; i++) {
			System.out.println("===================Run: " + (i+1) + "====================" );
			System.out.println();
			NextFit.print(true);
			averageNF2 += NextFit.numberOfProcess;		
			averageNFMM += NextFit.modifiedMemory;
		}
		System.out.println();

		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("---------------BEST FIT MAP:------------------");
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		//BestFit.print();
		for (int i = 0; i < 5; i++) {
			System.out.println("===================Run: " + (i+1) + "====================" );
			System.out.println();
			BestFit.print(true);
			averageBF2 += BestFit.numberOfProcess;
			averageBFMM += BestFit.modifiedMemory;
		}
		System.out.println();

		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("---------------WORST FIT MAP:------------------");
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		//WorstFit.print();
		for (int i = 0; i < 5; i++) {
			System.out.println("===================Run: " + (i+1) + "====================" );
			System.out.println();
			WorstFit.print(true);
			averageWF2 += WorstFit.numberOfProcess;
			averageWFMM += WorstFit.modifiedMemory;
		}
		System.out.println();
		
		System.out.println("---------------WITHOUT MEMORY COMPACTION---------------");
		System.out.println("Average Processes Swapped First Fit: " + averageFF / 5);
		System.out.println("Average Processes Swapped Next Fit: " + averageNF / 5);
		System.out.println("Average Processes Swapped Best Fit: " + averageBF / 5);
		System.out.println("Average Processes Swapped Worst Fit: " + averageWF / 5);
		
		
		System.out.println("\n---------------WITH MEMORY COMPACTION---------------");
		System.out.println("Average Process Swapped First Fit (With memory compaction): " + averageFF2 / 5);
		System.out.println("Average Process Swapped Next Fit (With memory compaction): " + averageNF2 / 5);
		System.out.println("Average Process Swapped Best Fit (With memory compaction): " + averageBF2 / 5);
		System.out.println("Average Process Swapped Worst Fit (With memory compaction): " + averageWF2 / 5);
		System.out.println("Average Memory Copied First Fit: " + averageFFMM / 5);
		System.out.println("Average Memory Copied Next Fit: " + averageNFMM / 5);
		System.out.println("Average Memory Copied Best Fit: " + averageBFMM / 5);
		System.out.println("Average Memory Copied Worst Fit: " + averageWFMM / 5);
	}
	
	public static void PageTest() {
		double averageFIFO = 0;
		double averageLRU = 0;
		double averageLFU = 0;
		double averageMFU = 0;
		double averageRP = 0;
		
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("---------------------FIFO----------------------");
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		//FIFO.calculate(true);
		for (int i = 0; i < 5; i++) {
			System.out.println("===================Run: " + (i+1) + "====================" );
			System.out.println();
			FIFO.calculate(true);
			averageFIFO += FIFO.hit;
			System.out.println();
			System.out.println();
			System.out.println("FIFO Hit Ratio: " + FIFO.hit/100);
			System.out.println();
		}
		
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("----------------------LRU----------------------");
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		//LRU.calculate(true);
		for (int i = 0; i < 5; i++) {
			System.out.println("===================Run: " + (i+1) + "====================" );
			System.out.println();
			LRU.calculate(true);
			averageLRU += LRU.hit;
			System.out.println();
			System.out.println();
			System.out.println("LRU Hit Ratio: " + LRU.hit/100);
			System.out.println();
		}
		
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("----------------------LFU----------------------");
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		//LFU.calculate(true);
		for (int i = 0; i < 5; i++) {
			System.out.println("===================Run: " + (i+1) + "====================" );
			System.out.println();
			LFU.calculate(true);
			averageLFU += LFU.hit;
			System.out.println();
			System.out.println();
			System.out.println("LFU Hit Ratio: " + LFU.hit/100);
			System.out.println();
		}
		
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("----------------------MFU----------------------");
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		//MFU.calculate(true);		
		for (int i = 0; i < 5; i++) {
			System.out.println("===================Run: " + (i+1) + "====================" );
			System.out.println();
			MFU.calculate(true);
			averageMFU += MFU.hit;
			System.out.println();
			System.out.println();
			System.out.println("MFU Hit Ratio: " + MFU.hit/100);
			System.out.println();
		}
		
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("-----------------Random-Pick--------------------");
		System.out.println("-----------------------------------------------");
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println();
		//RandomPick.calculate(true);
		for (int i = 0; i < 5; i++) {
			System.out.println("===================Run: " + (i+1) + "====================" );
			System.out.println();
			RandomPick.calculate(true);
			averageRP += RandomPick.hit;
			System.out.println();
			System.out.println();
			System.out.println("Random Pick Hit Ratio: " + RandomPick.hit/100);
			System.out.println();
		}
		
		System.out.println("Average FIFO Hit Ratio: " + averageFIFO/5/100);
		System.out.println("Average LRU Hit Ratio: " + averageLRU/5/100);
		System.out.println("Average LFU Hit Ratio: " + averageLFU/5/100);
		System.out.println("Average MFU Hit Ratio: " + averageMFU/5/100);
		System.out.println("Average Random Pick Hit Ratio: " + averageRP/5/100);
	}
	 
}
