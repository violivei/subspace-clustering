package optimization.operator.individual;

import optimization.searchspace.Chromosome;
import optimization.searchspace.Individual;
import mytest.evaluation.FitnessFunction;
import mytest.evaluation.binary.RoyalRoadFunc;
import optimization.tools.*;
/**
 * Random mutation hill-climbing (RMHC) local search for binary string
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class RMHC extends IndivSearch{
	
	RandomGenerator rnd;
	/**
	 * Constructor
	 * @param fc Fitness function
	 * @param iteration Maxinum number of search iteration
	 */
	public RMHC(FitnessFunction fc, int iteration) {
		super(fc);
		rnd = new RandomGenerator();
		iterMax = iteration;
	}
	/**
	 * RMHC: assume that input individual has been evaluated
	 * @param indiv Initial individual
	 */
	public void search(Individual indiv) 
	{
		Chromosome chrom = indiv.getChromAtIndex(0);
		int nDim = chrom.n_Dim;
		boolean stopCond = false;
		int i = 0;
		long startEval = evalFunc.getEvalCount();
		while (!stopCond)
		{
			int flipIndex = rnd.getRandom(nDim);
			Chromosome tempChrom = new Chromosome(chrom);
			// Flip one bit 
			byte temp = tempChrom.getGeneAsByte(flipIndex);
			if (temp == 0)
				tempChrom.setGene(flipIndex, (byte)1);
			else
				tempChrom.setGene(flipIndex, (byte)0);
			Individual tempIndiv = new Individual(tempChrom);
			// Evaluate
			this.evalFunc.evaluate(tempIndiv);
			
			//System.out.print(tempIndiv.toString() + ". ");
			// if improve
			if (indiv.getFitnessValue() >= tempIndiv.getFitnessValue())
			{
				indiv.copyIndividual(tempIndiv);
				chrom = indiv.getChromAtIndex(0);
			}
			else
			{
				if (this.StopIfConverge)
					stopCond = true;
				//System.out.println("No improvement. Keep old...");
			}
			i++;
			long endEval = evalFunc.getEvalCount();
			if (((endEval - startEval) > this.evalMax) && (!this.StopIfConverge))
				stopCond = true;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FitnessFunction fc = new RoyalRoadFunc(0, 64, 8);
		RMHC operator = new RMHC(fc,1000000);
		int numRuns = 1;
		for (int runID = 0; runID < numRuns; runID++)
		{
			fc.resetEvalCount();
			//fc.setOutputFile(runID);
			Chromosome chrom = new Chromosome(Chromosome.BINARY, 64, true);
			Individual indiv = new Individual(chrom);
			fc.evaluate(indiv);
			System.out.println("Before optimize... ");
			System.out.println(indiv.toString());
		
			operator.search(indiv);
			// System.out.println(x[0] +" " + x[1] + " " + x[2] + "-> " + fc.getFitnessFunc(x));
			System.out.println("After optimize... ");
			System.out.println(indiv.toString());
		}
	}

}
