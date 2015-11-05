package mytest.evaluation.binary;

import java.util.Vector;

import mytest.evaluation.FitnessFunction;
import optimization.searchspace.Chromosome;
import optimization.searchspace.Individual;
import optimization.searchspace.Population;
/**
 * Implementation of Royal Road function 
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class RoyalRoadFunc extends FitnessFunction {
	// Number of buiding blocks in RR funcion
	int nBlocks;
	// Number of bits
	int nDim;
	// Smallest block size
	int base = 2;
	// Fitness contribution when each build block matched
	double [] schemaWeight;
	// List of building blocks, i.e., schemas
	Vector<Schema> buildingBlocks = new Vector<Schema>();
	public String getName() {return "RoyalRoad";}
	/**
	 * Constructor
	 * @param initCount Initial fitness evaluation count
	 * @param nDim Number of bits
	 * @param base Smallest block size
	 */
	public RoyalRoadFunc(long initCount, int nDim, int base) 
	{
		super(initCount);
		this.nDim = nDim;
		this.base = base;
		// Build set of building blocks
		while (base <= nDim) {
			int index = 0;
			while (index + base <= nDim)
			{
				int [] loci = new int [base];
				byte [] allele = new byte[base];
				for (int i = 0; i < base; i++)
				{
					loci[i] = index + i;
					allele[i] = 1;
				}
				Schema s = new Schema(base, loci, allele);
				buildingBlocks.addElement(s);
				index = index + base;
			}
			base = 2*base;
		}
		// record number of blocks
		this.nBlocks = buildingBlocks.size();
		
		// Build weights for blocks
		schemaWeight = new double[nBlocks];
		for (int i = 0; i < schemaWeight.length; i++)
			schemaWeight[i] = buildingBlocks.elementAt(i).order;
	}
	/**
	 * Calculate fitness contributed by each build block matched
	 * @param byteInput Solution string
	 */
	public double [] getPhenotype(byte [] byteInput)
	{	
		if (nDim!= byteInput.length)
		{
			System.err.println("RoyalRoad: Disagreement in dimensions...");
			System.exit(0);
		}
		double [] input = new double[nBlocks];
		for (int i = 0; i < nBlocks; i++)
		{
			Schema s = buildingBlocks.elementAt(i);
			if (s.isInSchema(byteInput)) 
				input[i] = schemaWeight[i];
			else 
				input[i] = 0;
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
		return (-res);
	}
	/**
	 * 
	 */
	public String toString()
	{
		String res = "";
		res += "Royal Road Landscape - N = " + nDim + ", Based Block = " + base + "\n";
		
		res += "Set of building blocks \n";
		for (int r = 0; r < nBlocks; r++) {
			res += buildingBlocks.elementAt(r).toString(nDim);
			res += " C(s) = " + schemaWeight[r] + "\n";
		}
		res += "\n";
		
		return res;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int nDim = 8;
		RoyalRoadFunc f = new RoyalRoadFunc(0, nDim, 2);
		System.out.println(f.toString());
		
		byte [] a = {0, 1, 1, 1,1,1,1,1};
		double [] v = f.getPhenotype(a);
		System.out.println("Evaluated value = " + f.calculate(v));
	}

}

