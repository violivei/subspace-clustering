package mytest.evaluation.binary;

import mytest.evaluation.FitnessFunction;
import optimization.searchspace.Chromosome;
import optimization.searchspace.Individual;
import optimization.searchspace.Population;
import optimization.tools.RealCoding;

/**
 * Binary-interface for problems in continuous domain: decision variable in bit-string,
 * then converted to continous value & evaluate fitness from there
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class BinaryFitnessFunc extends FitnessFunction
{
	/**
	 * Upper & lower Bound for converting binary <-> continuous
	 */
	public double [] lBound=null, uBound=null;
	/**
	 * Fitness function in continuous domain
	 */
	FitnessFunction feval = null;
	
	/**
	 * Constructor
	 * @param initCount Initial fitness evaluation 
	 * @param f Fitness function in continuous domain
	 * @param low Lower bound of decision variables
	 * @param high Upper bound of decision variables
	 */
	public BinaryFitnessFunc(long initCount, FitnessFunction f, 
									double [] low, double [] high) 
	{
		super(initCount);
		feval = f;
		lBound = low;
		uBound = high;
	}
	public String getName() {return "BinaryFitnessFunc";}
	/**
	 * Convert from bit-string to real-coded vector
	 * @param byteInput
	 */
	public double []  getPhenotype(byte [] byteInput)
	{
		boolean [] boolInput = new boolean[byteInput.length];
		for (int i = 0; i <  byteInput.length; i++)
		{
			if (byteInput[i] == 0)
				boolInput[i] = false;
			else 
				boolInput[i] = true;
		}
		double [] input = RealCoding.decodingArray(boolInput, lBound, uBound);
		return input;
	}
	/**
	 * Override function - Evaluate a chromosome
	 * @param chrom
	 * @return Fitness value
	 */
	public double evaluate(Chromosome chrom) {
		double res = 0;
		byte [] byteInput = chrom.getByteArray();
		double [] input = getPhenotype(byteInput);
		if (input != null)
			res = getFitnessFunc(input);
		return res;
	}
	/**
	 * Override function - Evaluate an individual
	 * @param indiv
	 */
	public void evaluate(Individual indiv) {
		if (!indiv.evaluated) {
			Chromosome chrom = indiv.genome.elementAt(0);
			if (chrom != null) {
				indiv.setFitnessValue(evaluate(chrom));
			}
		}
	}
	/**
	 * Override function - Evaluate a population
	 * @param pop
	 */
	public void evaluate(Population pop) {
		for (int i = 0; i < pop.populationDim; i++)
			evaluate(pop.individuals.elementAt(i));
	}
	/**
	 * Negate fitness value to turn minimization into maximization combinatorial problem
	 */
	public double calculate(double [] inputs)
	{
		return -feval.calculate(inputs);
	}
}
