package optimization.operator.population;

import java.util.Random;
import java.util.Vector;

import optimization.searchspace.Chromosome;
import optimization.searchspace.Individual;
import optimization.tools.*;
/**
 * Operator to perform mutation on a vector of Individuals. <p></p>
 * (Population-based Operator) 
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Mutation extends OperatorTemplate{
	//private int numBitsCode = GAProfile.numBitsCode;
    private double mutProb = 0;
    private int type;
    private double radius = 1.0;
	/** Bit flip mutation */
	public static final int uniform = 0;
	/** Gaussian mutation */
	public static final int gaussian = 1;
	/** Random generator */
	Random rd = null;
	RandomGenerator uniRnd = null;
	/**
	 * Constructor
	 */
	public Mutation() {
        rd = new Random(System.currentTimeMillis());
        uniRnd = new RandomGenerator();
	}
	/**
	 * 
	 * @return mutation probability
	 */
	public double getMutProb() 
	{
		return this.mutProb;
	}
	/**
	 * Set mutation probability
	 * @param prob
	 */
	public void setMutProb(double prob)
	{
		this.mutProb= prob;
	}
	/**
	 * Set mutation type: Gaussian or uniform distribution
	 * @param val
	 */
	public void setMutType(int val)
	{
		this.type = val;
	}
	/**
	 * Set variance of Gassian mutation
	 * @param val
	 */
	public void setMutRadius(double val) 
	{
		this.radius = val;
	}
	
    public Vector<Individual> doProcess(Vector<Individual> indivs)
    {
    	Vector<Individual> res = new Vector<Individual>();
    	Individual temp = null;
    	int populationDim = indivs.size();
    	if (type == Mutation.gaussian) {
    		//int iXCount = 0;
    		for (int i = 0; i < populationDim; i++)
    		{
    			Individual indiv = new Individual(indivs.elementAt(i));
    			/*
    			if (uniRnd.coinToss(mutProb)) {
    				temp = doGaussianMutation(indiv);
    				//iXCount++;
    			}
    			else
    				temp = indiv;
    				*/
    			temp = doGaussianMutation(indiv);
    			res.addElement(temp);
    		}
    		//System.out.println(iXCount + " individuals are mutated...");
    	}
    	else if (type == Mutation.uniform)
    	{
    		for (int i = 0; i < populationDim; i++) {
    			Individual indiv = new Individual(indivs.elementAt(i));
    			temp = doUniformMutation(indiv);
    			res.addElement(temp);
    		}
    	}
    	// Do nothing
    	else if (type < 0)
    	{
    		res = indivs;
    	}
    	return res;
    }
	/** WRONG ONE
	 * Perform Gaussian mutation on an individual
	 * @param indiv
	 * @return
	 
    public Individual doGaussianMutation(Individual indiv)
    {
    	Individual res = null;
    	
    	Chromosome Chrom = indiv.genome.elementAt(0);
    	int chromosomeDim = Chrom.n_Dim;
        byte type = Chrom.type;
        
        if (type == Chromosome.FLOAT) {
        	for (int iGene = 0; iGene < chromosomeDim; iGene++) {
        		double rNewGene = 
        			((Double)Chrom.chromosome.elementAt(iGene)).doubleValue();
        		  
        		//double range = Chrom.uBound[iGene]-Chrom.lBound[iGene];
        		//rNewGene += (range/160) * rd.nextGaussian();
        		
        		rNewGene += rd.nextGaussian();
        		
        		Chrom.setGene(iGene, rNewGene);
        		indiv.evaluated = false;
        	}
        	res = indiv;
        }
        
        return res;
    }
    */
    /**
     * Correct Mutation operator
     */
    public Individual doGaussianMutation(Individual indiv)
    {
    	Individual res = null;
    	
    	Chromosome Chrom = indiv.genome.elementAt(0);
    	int chromosomeDim = Chrom.n_Dim;
        byte type = Chrom.type;
        
        if (type == Chromosome.FLOAT) {
        	for (int iGene = 0; iGene < chromosomeDim; iGene++) {
        		if (uniRnd.coinToss(mutProb)) {
        			double rNewGene = 
            			((Double)Chrom.chromosome.elementAt(iGene)).doubleValue();
        			//	double range = Chrom.uBound[iGene]-Chrom.lBound[iGene];
        			//	rNewGene += (range/160) * rd.nextGaussian();
        			double temp = 0;
        			do {
        				temp = rNewGene + this.radius * rd.nextGaussian();
        			} while (Utils.g(temp, Chrom.uBound[iGene]) ||
        					Utils.l(temp, Chrom.lBound[iGene]));
        			
        			rNewGene = temp;        			
        			Chrom.setGene(iGene, rNewGene);
        		
        			indiv.evaluated = false;
        		}
        		
        	}
        	res = indiv;
        }
        else {
        	System.out.println("Invalid mutation operator...!");
        	System.exit(0);
        }
        
        return res;
    }
    
    public Individual doUniformMutation(Individual indiv)
    {
    	Individual res = null;
    	Chromosome Chrom = indiv.genome.elementAt(0);
    	int chromosomeDim = Chrom.n_Dim;
        byte type = Chrom.type;
        
        if (type == Chromosome.FLOAT) 
        {
//        	if (GAProfile.binaryCoded) 
//        	{
//	        	// Convert to bitstring
//	    		boolean [] chromStr = RealCoding.encodingArray(
//	    				Chrom.getDoubleArray(), Chrom.lBound, Chrom.uBound, numBitsCode);
//	
//	    		// Mutation
//	    		int chromStrDim = chromStr.length; 
//	    		
//	    		for (int i = 0; i < chromStrDim; i++)
//	    		{
//	    			if (uniRnd.coinToss(mutProb)) 
//	    			{
//	    				chromStr[i] = !chromStr[i];
//	    				indiv.evaluated = false;
//	    			}
//	    		}
//	    		// Convert to real
//	    		double [] chromD = RealCoding.decodingArray(chromStr, 
//	    				Chrom.lBound, Chrom.uBound);
//	
//	    		for (int i = 0; i < chromosomeDim; i++)
//	    		{
//	    			Chrom.setGene(i, chromD[i]);
//	    		}
//	        	res = indiv;
//        	}
//        	else 
        	{
        		for (int i=0; i < chromosomeDim; i++)
        		{
        			if (uniRnd.coinToss(mutProb))
        			{
        				double value = Chrom.lBound[i] + 
        						uniRnd.getRandom(Chrom.uBound[i]-Chrom.lBound[i]);
        				Chrom.setGene(i, value);
        				indiv.evaluated = false;
        			}
        		}
        		res = indiv;
        	}
        }
        else if (type == Chromosome.BINARY){
        	for (int i = 0; i < chromosomeDim; i++)
        	{
        		if (uniRnd.coinToss(mutProb))
    			{
    				byte value = Chrom.getGeneAsByte(i);
    				if (value == 0) value = 1;
    				else value = 0;
    				Chrom.setGene(i, value);
    				indiv.evaluated = false;
    			}
        	}
        	res = indiv;
        }
    	return res;
    }
    static public void main(String[] argv)
    {
    	double [] x1 = {4};
    	double [] l = {1};
    	double [] h = {8};
    	Chromosome chrom1 = new Chromosome(
    			Chromosome.FLOAT, x1, l, h);
    	Individual indiv1 = new Individual(chrom1);
    	System.out.println(indiv1.toString());
    	
    	Mutation operator = new Mutation();
    	operator.setMutProb(0.3);
    	indiv1 = operator.doUniformMutation(indiv1);
    	
    	System.out.println(indiv1.toString());
    }
}
