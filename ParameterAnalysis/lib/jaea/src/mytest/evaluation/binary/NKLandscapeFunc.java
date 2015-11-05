package mytest.evaluation.binary;

import java.util.Vector;

import mytest.evaluation.FitnessFunction;
import optimization.searchspace.Chromosome;
import optimization.searchspace.Individual;
import optimization.searchspace.Population;
import optimization.tools.*;

/**
 * Implementation of NK Landscape maximization problem 
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class NKLandscapeFunc extends FitnessFunction
{
	/**
	 * Static indicator of adjent/random NK-landscape
	 */
	static final public int ADJENT_NEIGHBORS = 0;
	static final public int RANDOM_NEIGHBORS = 1;
	RandomGenerator rnd;
	/* K: neighborhood size
	 * N: string length
	 * nType: adjent or random neighborhood structure 
	 */
	int K, N, nType;
	/* Specify fitness contribution of bit i & the configuration of K-neighborhood */
	double [][] locusAddin; // size N x 2^K
	/* Specify neighborhood structure at each loci	 */
	byte [][] neighborStruct;// size N x K
	
	/**
	 * Constructor
	 * @param initCount Initial fitness evaluation count
	 * @param Ndim Number of bits
	 * @param Kneighbor Number of neighbor bits
	 * @param neighborType Adjent or random neighbor structure
	 */
	public NKLandscapeFunc(long initCount, int Ndim, int Kneighbor, int neighborType) 
	{
		super(initCount);
		rnd = new RandomGenerator();
		K = Kneighbor;
		N = Ndim;
		nType = neighborType;
		int nNeighborStruct = (new Double(Math.pow(2, K+1))).intValue();
		// Assign the neighborhood structure
		neighborStruct = new byte[N][K];
		genNeighborStruct(neighborStruct, nType);
		// Assign the contribution of each bit
		locusAddin = new double[N][nNeighborStruct];
		genBitContribution(locusAddin);
	}
	public String getName() {return "NKLandscape";}
	/**
	 * To generate matrix of neighbor bits for each loci
	 * @param nStruct
	 * @param type
	 */
	private void genNeighborStruct(byte [][] nStruct, int type)
	{
		int picked_index = 0;
		if (type == NKLandscapeFunc.ADJENT_NEIGHBORS)
		{
			for (int locus = 0; locus < N; locus++)
			{
				int nleft, nright;
				nleft = nright = K/2;
				picked_index = 0;
				if ((K % 2) == 1) {
					if (rnd.coinToss(0.5))
						nleft++;
					else 
						nright++;
				}
				/* Get the loci to the left, watch for the edge of the world. */
			    if (locus >= nleft)
			    {
			    	for (int i = locus - nleft; i < locus; i++){
			    		nStruct[locus][picked_index] = (new Integer(i)).byteValue();
			    		picked_index++;
			    	} 
			    }
			    else 
			    {
			    	for (int i = 0; i < locus; i++){
			    		nStruct[locus][picked_index] = (new Integer(i)).byteValue();
			    		picked_index++;
			    	} 
				
			    	for (int i = N - (nleft - locus); i < N; i++){
			    		nStruct[locus][picked_index] = (new Integer(i)).byteValue();
			    		picked_index++;
			    	}
			    }
			    int i;
			    /* Get the loci to the right, watch for the edge of the world. */
			    if (locus + nright < N){
			    	for (i = locus + 1; i <= locus + nright; i++){
			    		nStruct[locus][picked_index] = (new Integer(i)).byteValue();
			    		picked_index++;
			    	}
			    }
			    else 
			    {
			    	for (i = locus + 1; i < N; i++)
			    	{
			    		nStruct[locus][picked_index] = (new Integer(i)).byteValue();
			    		picked_index++;
			    	}
				
			    	for (i = 0; i <= locus + nright - N; i++)
			    	{
			    		nStruct[locus][picked_index] = (new Integer(i)).byteValue();
			    		picked_index++;
			    	}
			    }
			}
		}
		else if (type == NKLandscapeFunc.RANDOM_NEIGHBORS)
		{
			Vector<Integer> numBasket = new Vector<Integer>();
			for (int locus = 0; locus < N; locus++)
			{
				picked_index = 0;
				numBasket.clear();
				for (int j = 0; j < N; j++)
					if (j != locus) numBasket.addElement(new Integer(j));
				for (int count = 0; count < K; count++)
				{
					int index = rnd.getRandom(numBasket.size());
					nStruct[locus][picked_index++] = numBasket.elementAt(index).byteValue();
					numBasket.removeElementAt(index);
				}
			}
		}
	}
	/**
	 * Randomly assign the contribution of the bit
	 * @param addin
	 */
	private void genBitContribution(double [][] addin)
	{
		int row = addin.length;
		int col = addin[0].length;
		for (int i = 0; i < row; i++)
			for (int j = 0; j <  col; j++)
				addin[i][j] = rnd.getRandom(1.0);
	}
	/**
	 * Convert from byte to boolean
	 * @param byteInput
	 * @return
	 */
	private boolean [] Byte2Bool(byte [] byteInput)
	{
		boolean [] boolInput = new boolean[byteInput.length];
		for (int i = 0; i <  byteInput.length; i++)
		{
			if (byteInput[i] == 0)
				boolInput[i] = false;
			else 
				boolInput[i] = true;
		}
		return boolInput;
	}
	/**
	 * Get vector of fitness contributed by each loci with regard to neighbor bits
	 * @param byteInput
	 */
	public double [] getPhenotype(byte [] byteInput)
	{	
		if (N != byteInput.length)
		{
			System.err.println("Disagreement in dimensions...");
			System.exit(0);
		}
		// create the neighbor array
		byte[][] neighbors = new byte[N][K+1];
		for (int i = 0; i < N; i++)
		{
			neighbors[i][0] = byteInput[i];
			for (int j = 0; j < K; j++)
				neighbors[i][j+1] = byteInput[neighborStruct[i][j]];
		}
		// get the contribution of each bit
		double [] input = new double[N];
		for (int i = 0; i < N; i++)
		{
			int index = RealCoding.decodingArray(Byte2Bool(neighbors[i]));
			input[i] = locusAddin[i][index];
		}
		return input;
	}
	
	public double evaluate(Chromosome chrom) {
		double res = 0;
		byte [] byteInput = chrom.getByteArray();
		double [] input = getPhenotype(byteInput);
		if (input != null)
			res = getFitnessFunc(input);
		return res;
	}
	
	public void evaluate(Individual indiv) {
		if (!indiv.evaluated) {
			Chromosome chrom = indiv.genome.elementAt(0);
			if (chrom != null) {
				indiv.setFitnessValue(evaluate(chrom));
			}
		}
	}
	
	public void evaluate(Population pop) {
		for (int i = 0; i < pop.populationDim; i++)
			evaluate(pop.individuals.elementAt(i));
	}
	/**
	 * Implementation of abstract class: negate positive fitness
	 * to turn maximization into minimization problem
	 */
	public double calculate(double [] inputs)
	{
		double res = 0;
		for (int i = 0; i < inputs.length; i++)
			res += inputs[i];
		return (-res/N);
	}
	/**
	 * 
	 */
	public String toString()
	{
		String res = "";
		res += "NK Landscape - N = " + N + ", K = " + K + "\n";
		int row, col;
		
		res += "Neighborhood structure \n";
		row = neighborStruct.length;
		col = neighborStruct[0].length;
		for (int r = 0; r < row; r++) {
			res += r + " ";
			for (int c = 0; c < col; c++)
				res += neighborStruct[r][c] + " ";
			res += "\n";
		}
		res += "\n";
		/* Contribution matrix
		res += "Contribution matrix \n";
		row = locusAddin.length;
		col = locusAddin[0].length;
		for (int r = 0; r < row; r++) 
		{
			for (int c = 0; c < col; c++)
				res += locusAddin[r][c] + " ";
			res += "\n";
		}
		res += "\n";
		*/
		return res;
	}
	public static void main(String [] arvg) 
	{
		NKLandscapeFunc f = new NKLandscapeFunc(0, 5, 2, NKLandscapeFunc.RANDOM_NEIGHBORS);
		System.out.println(f.toString());
		
		byte [] a = {0, 1, 0, 0,1};
		double [] v = f.getPhenotype(a);
		System.out.println("Evaluated value = " + f.calculate(v));
		
	}
}
