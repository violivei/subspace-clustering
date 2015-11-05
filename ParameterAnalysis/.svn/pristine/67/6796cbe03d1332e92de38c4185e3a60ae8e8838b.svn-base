package mytest.evaluation;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import optimization.searchspace.Chromosome;
import optimization.searchspace.Individual;
import optimization.searchspace.Population;

/**
 * General abstract class for fitness function. <p></p>
 * Support simple features: (1) Rotate, shift and 
 * (2) Approximate derivatives (finite differencing)
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public abstract class FitnessFunction {
	//public static final double GLOBAL_MIN = RuntimeConfiguration.globalOptimum;
	//public static final long MAX_EVAL = RuntimeConfiguration.maxEvaluation;
	
	// BENCHMARK FUNCTION CODE
	public static final byte F_Equal = 0;
	public static final byte F_MultiCos = 1;
	public static final byte F_Sphere = 2;
	public static final byte F_Rastrigin = 3;
	public static final byte F_Ackley = 4;
	public static final byte F_Elliptic = 5;
	public static final byte F_Griewank = 6;
	public static final byte F_Rastrigin_NonCon = 7;
	public static final byte F_Weierstrass = 8;
	public static final byte F_Rosenbrock = 9;
	public static final byte F_Sphere_Noise = 10;
	public static final byte F_Schwefel_102 = 11;
	public static final byte F_ExpandedScaffer = 12;
	public static final byte F_ScaledSphere= 13;
	public static final byte F_DeceptiveCore = 14;
	public static final byte F_DeceptiveRastrigin = 15;
	protected PrintStream evaluation = null;
	/**
	 * Total number of fitness evaluation calls
	 */
	protected long NUM_EVAL = 0;
	/**
	 * Best fitness value found. Default = 0
	 */
	protected double bestFound = 0;
	/** 
	 * DefaultRanges[] 0: low bound, 1: high bound 
	 * */
	protected double [] defaultRanges = new double[2];
	/** 
	 * Get lower bound
	 * @return Lower Bound in double (continuous domain)
	 */
	public double getLowBound() {return defaultRanges[0];}
	/** 
	 * Get upper bound
	 * @return Lower Bound in double (continuous domain)
	 */
	public double getUpBound() {return defaultRanges[1];}
	/**
	 * Get function
	 * @return defaultRanges vector
	 */
	public double [] getDefaultRanges()
	{
		return defaultRanges;
	}
	/**
	 * Rotation matrix 
	 */
	protected double [][]transMatrix = null;
	/**
	 *  Shift vector 
	 */
	protected double [] shiftVector = null;
	/**
	 * Offset to fitness value
	 */
	protected double offset = 0;
	/**
	 * Option to print best found evaluation to file
	 */  
	protected boolean writeEvaluation2File = false;
	/**
	 * Abstract function to get problem name
	 */
	abstract public String getName();
	
	/**
	 * Get function
	 * @return writeEvaluation2File
	 */
	public boolean getWriteOption()
	{
		return this.writeEvaluation2File;
	}
	/**
	 * Enable option of write fitness evaluation to text file
	 * @param a TRUE to enable, FALSE otherwise
	 */
	public void setWriteOption(boolean a)
	{
		this.writeEvaluation2File = a;
	}
	/**
	 * Set default ranges for function's variable
	 * @param v double [] vector of lower/upper bound
	 */
	public void setDefaultRanges(double [] v)
	{
		defaultRanges = v;
	}
	/**
	 * Set shift vector x' = x - s
	 * @param s double [] 
	 */
	public void setShiftVector(double [] s)
	{
		this.shiftVector = s;
	}
	/**
	 * Function to normalize input [0,1] to its actual range
	 * used in the calculation
	 * @param lowBound Actual lower bound
	 * @param highBound Actual higher bound
	 */
	public void normalize(double [] lowBound, double [] highBound)
	{
		if (lowBound.length != highBound.length)
			return;
		
		int numDim = lowBound.length;
		shiftVector = new double[numDim];
		transMatrix = new double [numDim][numDim];
		
		for (int i =0; i < numDim; i++) {
			double temp = highBound[i] - lowBound[i];
			if (Math.abs(temp)<1E-16)
			{
				System.err.println("ERROR in boundary setting...");
				System.exit(0);
			}
			else
			{
				shiftVector[i] = -lowBound[i]/temp;
				for (int j = 0; j < numDim; j ++)
				if (j == i)
					transMatrix[i][j] = temp;
				else
					transMatrix[i][j] = 0;
			}
		}
	}
	/**
	 * Abstract class as to calculate the original fitness value of inputs
	 * @param inputs Decision variables in double []
	 * @return original fitness
	 */
	abstract public double calculate(double [] inputs);
	/**
	 * Check if solution is out of specified bound (continuous domain)
	 * @param inputs
	 * @return TRUE if Valid, FALSE if Out of Bound 
	 */
	public boolean checkBound(double [] inputs)
	{
		boolean res = true;
		for (int i = 0; i < inputs.length; i++) {
			if ((inputs[i] < this.defaultRanges[0]) ||
					(inputs[i] > this.defaultRanges[1]))
				return false;
		}
		return res;
	}
	/**
	 * z = S(x-o). Calculate F(x) = f(z)
	 * @param inputs Decision variables x
	 * @return F(z) the shift rotated function
	 */
	public double getFitnessFunc(double [] inputs)
	{
		// increase number of evaluations
		NUM_EVAL++;
		// Check bound
		if (!checkBound(inputs)) 
			return Integer.MAX_VALUE;
		double res = 0;
		double [] sInputs;
		if (shiftVector != null)
			sInputs = shiftX(inputs, shiftVector);
		else 
			sInputs = inputs;
		
		double [] tInputs;
		if (transMatrix != null)
			tInputs = transformX(sInputs, transMatrix);
		else
			tInputs = sInputs;
		
		if (offset != 0)
			res = getFitnessFunc(tInputs, offset);
		else
			res = calculate(tInputs);
		
		/* Update best-found value & print to file*/
		if (writeEvaluation2File) {
			if (res < bestFound) bestFound = res;
			evaluation.println(NUM_EVAL + "," + res);
			evaluation.flush();
		}
		// Print evaluations to line
//		System.out.print(this.NUM_EVAL + " ");
//		for (int i = 0; i < tInputs.length; i++)
//			System.out.print(tInputs[i] + " ");
//		System.out.println(res);
		
		return res;
	}
	/**
	 * Return the gradient vector at a point 
	 * @param inputs Solution P
	 * @param resP Fitness value of solution P
	 * @param g Resultant gradient vector at P
	 */
	public void getFitnessGrad(double [] inputs, double resP, double [] g)
	{
		double delta = 0.001;
		double resPd=0;
		double [] sInputs;
		/* Get origin coordinates */
		if (shiftVector != null)
			sInputs = shiftX(inputs, shiftVector);
		else 
			sInputs = inputs;
		
		double [] tInputs;
		if (transMatrix != null)
			tInputs = transformX(sInputs, transMatrix);
		else
			tInputs = sInputs;
		
		/* Calculate around point */
		int nDim = inputs.length;
		for (int i = 0; i < nDim; i++)
		{
			double temp = tInputs[i];
			tInputs[i] += delta;
			NUM_EVAL++;
			resPd = calculate(tInputs);
			// Finite differencing
			g[i] = (resPd-resP)/delta;
			// Restore original point
			tInputs[i] = temp;
		}
		/* Get true gradients */
		if (transMatrix != null)
			g = transformX(g, transMatrix);
	}
	/**
	 * Constructor
	 * @param initCount Initial fitness evaluation count. Default = 0
	 */
	public FitnessFunction(long initCount) {
		NUM_EVAL = initCount;
		bestFound = Double.MAX_VALUE;
	}
	/**
	 * Constructor
	 * @param initCount Initial fitness evaluation count. Default = 0
	 * @param record TRUE if recording fitness evaluations, otherwise FALSE
	 */
	public FitnessFunction(long initCount, boolean record) {
		NUM_EVAL = initCount;
		bestFound = Double.MAX_VALUE;
		writeEvaluation2File = record;
	}
	/**
	 * Create output stream/file for recording fitness evaluation
	 * @param runID Running index of an algorithm
	 * @param savedDir Directory to save file
	 * @param evalFilename Filename (e.g., function name)
	 */
	public void setOutputFile(int runID, String savedDir, String evalFilename) {
		if (writeEvaluation2File) {
	    	try {
	    		String filename = savedDir + "/" + 
						evalFilename  + "-eval-"+ runID + ".csv";
	    		evaluation = new PrintStream(new FileOutputStream(filename));
	    	}
	    	catch (IOException e) {
	    	      System.out.println("ERROR: can't set fitness" +
	    	      		"evaluation output file");
	    	}
		}
	}
	/**
	 * Evaluate a chromosome
	 * @param chrom Chromosome to be evaluated
	 * @return Fitness value
	 */
	public double evaluate(Chromosome chrom) {
		double res = 0;
		double [] input = chrom.getDoubleArray();
		if (input != null)
			res = getFitnessFunc(input);
		return res;
	}
	/**
	 * Evaluate an individual & assign fitness to individial's property
	 * @param indiv Individual to be evaluated
	 */
	public void evaluate(Individual indiv) {
		if (!indiv.evaluated) {
			double [] input = indiv.genome.elementAt(0).getDoubleArray();
			if (input != null) {
				indiv.setFitnessValue(getFitnessFunc(input));
			}
		}
	}
	/**
	 * Evaluate individuals in the population & assign fitness to individial's property
	 * @param pop Population to be evaluated
	 */
	public void evaluate(Population pop) {
		for (int i = 0; i < pop.populationDim; i++)
			evaluate(pop.individuals.elementAt(i));
	}
	/**
	 * Close output stream
	 */
	public void close() {
		if (evaluation != null)
			evaluation.close();
	}
	/**
	 * Stopping condition using delta-close criteria
	 * @param curr Current best-found fitness
	 * @param init Initial best-found fitness
	 * @param GLOBAL_MIN Known global optimum 
	 * @return delta-close value/ improvement over initial fitness
	 */
	public double delta_close(double curr, double init, double GLOBAL_MIN)
	{
		double res = 0;
		res = Math.abs(GLOBAL_MIN - curr)/
					Math.abs(GLOBAL_MIN - init);
		return res;
	}
	/**
	 * Reset fitness evaluation count
	 */
	public void resetEvalCount()
	{
		NUM_EVAL = 0;
	}
	/**
	 * Increaset fitness evaluation count
	 */
	public void increaseEvalCount()
	{
		NUM_EVAL++;
	}
	/**
	 * Get fitness evaluation count
	 */
	public long getEvalCount()
	{
		return NUM_EVAL;
	}
	/**
	 * Transform vector of inputs x' = Mx
	 * @param inputs 
	 * @param transMatrix Transformation matrix
	 * @return Transformed input
	 */
	protected double [] transformX(double [] inputs,
										double [][] transMatrix)
	{
		// if no transform matrix specified
		if (transMatrix == null )
			return inputs;
		else 
		{
			// duplicate input
			double [] _inputs = new double [ inputs.length];
			// transform
			for (int i = 0; i < inputs.length; i++)
			{
				_inputs[i] = 0;
				for (int j = 0; j < inputs.length; j++)
					if (Math.abs(inputs[j]) > 1E-9 && Math.abs(transMatrix[j][i])> 1E-9)
						_inputs[i] += inputs[j]*transMatrix[j][i];
			}
			return _inputs;
		}
	}
	/**
	 * Shift vector of inputs x' = x - shiftVector
	 * @param inputs
	 * @param shiftVector Shift vector
	 * @return Shited vector
	 */
	protected double [] shiftX(double [] inputs,
									double [] shiftVector)
	{
		if (shiftVector == null) return inputs;
		
		double [] _inputs = new double[inputs.length];
		// shift
		for (int i = 0; i < inputs.length; i++)
		{
			_inputs[i] = inputs[i] - shiftVector[i];
		}
		return _inputs;
	}
	/**
	 * Get fitness value with offset added f' = f + offset
	 * @param inputs
	 * @param offset
	 * @return Original fitness + offset
	 */
	public double getFitnessFunc(double [] inputs, double offset)
	{
		double res = 0;
		res = calculate(inputs) + offset;
		return res;
	}
	/**
	 * Get fitness functions for rotated or scaled inputs f' = f(Mx) + offset
	 * @param inputs
	 * @param offset
	 * @param transMatrix Transformation matrix
	 * @return Fitness from rotated landscape with offset
	 */
	public double getFitnessFunc(double [] inputs, 
								double offset,
								double [][] transMatrix)
	{
		double res = 0;
		double [] _inputs = transformX(inputs, transMatrix);
		res = getFitnessFunc(_inputs, offset);
		return res;
	}
	/**
	 * Get fitness functions for rotated or scaled inputs (like from [0,1])
	 * and shifted by shiftVector []: z = M(x-o), f' = f(z)
	 * @param inputs
	 * @param offset
	 * @param transMatrix
	 * @return F(z)
	 */
	public double getFitnessFunc(double [] inputs, 
			double offset,
			double [][] transMatrix,
			double [] shiftVector)
	{
		double res = 0;
		double [] sInputs = shiftX(inputs, shiftVector);
		double [] tInputs = transformX(sInputs, transMatrix);
		res = getFitnessFunc(tInputs, offset);
		return res;
	}
	
	/* LIST OF BASIC FUNCTIONS */
	/**
	 * Round function - Use the Matlab version for rounding numbers
	 */
	 public double myRound(double x) {
		return (Math.signum(x) * Math.round(Math.abs(x)));
	}
	// 1. "o" is provided
	 public double myXRound(double x, double o) {
		return ((Math.abs(x - o) < 0.5) ? x : (myRound(2.0 * x) / 2.0));
	}
	// 2. "o" is not provided
	 public double myXRound(double x) {
		return ((Math.abs(x) < 0.5) ? x : (myRound(2.0 * x) / 2.0));
	}
}
