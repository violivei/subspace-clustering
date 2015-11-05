package optimization.search;

import mytest.evaluation.FitnessFunction;
import optimization.searchspace.Population;
import optimization.tools.*;
/**
 * Abstract class for general population-based search
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public abstract class Search {
	/** Code for GA */
    public static final int GA_MODE = 0;
    /** Code for PSO */
    public static final int PSO_MODE = 1;
    /** Code for LEM */
    public static final int RULE_MODE = 2;
    /** Code for Simple Random Sampling */
    public static final int SRS_MODE = 3;
    /** Code for Evolution Strategy */
    public static final int ES_MODE = 7;
    /** Code for Memetic Algorithm */
    public static final int MA_MODE = 8;
    /**
     * Objective function to be optimized
     */
    protected FitnessFunction evalFunc;
    /**
     * Random generator used in main search procedure
     */
    protected RandomGenerator rnd;
    /**
     * Constructor
     * @param fc Objective function to be optimized
     */
    public Search(FitnessFunction fc) {
    	evalFunc = fc;
		rnd = new RandomGenerator();
    }
	/**
	 * Abstract search function
	 * @param pop Initial population
	 */
    abstract public void search(Population pop);
}
