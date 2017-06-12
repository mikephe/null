import java.util.Random;

public class SwapProcess {
	public int processDuration;
	public int firstFitDuration;
	public int nextFitDuration;
	public int bestFitDuration;
	public int worstFitDuration;
	public int processSize;
	public char name;
	public int arrivalTime;
	
	public SwapProcess() {
		processDuration = randInt(1, 5);
		firstFitDuration = processDuration;
		nextFitDuration = processDuration;
		bestFitDuration = processDuration;
		worstFitDuration = processDuration;
		int seedSize = randInt(1, 4);
		if (seedSize == 1)
			processSize = 5;
		if (seedSize == 2)
			processSize = 11;
		if (seedSize == 3)
			processSize = 17;
		if (seedSize == 4)
			processSize = 31;
	}
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}
