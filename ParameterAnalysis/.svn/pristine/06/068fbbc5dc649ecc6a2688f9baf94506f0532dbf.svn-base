package optimization.operator.individual;

import mytest.evaluation.FitnessFunction;
import optimization.searchspace.Individual;
/**
 * Abstract class for implementing individual learning strategy
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
abstract public class IndivSearch {
	/**
	 * Fitness function used in search - or the search landscape
	 */
	public FitnessFunction evalFunc;
	/**
	 * Maximum number of search iteration allowed. Default is unlimited
	 */
	protected int iterMax = Integer.MAX_VALUE;
	/**
	 * Maximum number of search iteration allowed. Default is 300
	 */
	public int evalMax = 300;
	/**
	 * TRUE if search until convergence, FALSE if otherwise. Default is FALSE
	 */
	public boolean StopIfConverge = false;
	/**
	 * Initial step size (continuous search)
	 */
	public double StepSize = 0.5;
	/**
	 * Accuracy = stopping criteria for step size when run under limited budget
	 */
	public double ACC = 1.0E-5;
	/**
	 * Convergence accuracy, i.e., |x'' - x'| < epsilon when run till convergence
	 */
	public double CONV_ACC = 1.0E-4;
	/**
	 * Reliability factor in convergence test, number of event |x'' - x'| < epsilon
	 */
	public int MAX_COUNT = 1; 
	
	/**
	 * Constructor using default max number of evaluations  = 300
	 * @param fc 
	 */
	IndivSearch(FitnessFunction fc) {
		this.evalFunc = fc;
	}
	/**
	 * Constructor
	 * @param fc Fitness function
	 * @param eval Maxinum number of fitness evaluations
	 */
	IndivSearch(FitnessFunction fc, int eval) {
		this.evalFunc = fc;
		this.evalMax = eval;
		if (this.evalMax < 0) 
			StopIfConverge = true;
	}
	//abstract public double search(double[] xInit);
	/**
	 * Abstract class for individual search. 
	 * Search is performed on starting solution, and directly updates chromosome's values
	 * @param indiv Starting solution
	 */
	abstract public void search(Individual indiv);
	/**
	 * Numerical search procedure
	 * @param xInit Vector of inputs 
	 * @param yInit Fitness evaluation f(xInit)
	 * @return Resultant fitness value after learning
	 */
	public double search(double[] xInit, double yInit)
	{
		return 0;
	}
}
