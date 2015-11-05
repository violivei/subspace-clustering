package optimization.operator.population;

import optimization.searchspace.*;
import optimization.tools.*;
import java.util.Vector;
/**
 * Implementation of crossover operator on simple solution string representation.<p></p>
 * (Population-based Operator)
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Crossover extends OperatorTemplate
{
    /** one point crossover */
    public static final int ctOnePoint = 0;
    /** two point crossover */
    public static final int ctTwoPoint = 1;
    /** uniform crossover */
    public static final int ctUniform = 2;
    /** roulette crossover (either one point, two point, or uniform) */
    public static final int ctArithmetic = 4;
    
    /**
     * type: either One-Point, Two-Point crossover
     */
    private int type; 
    /**
     * poolSize: number of individuals created through crossover
     */
    private int poolSize;
    /**
     * crossoverProb: crossover probability
     */
    private double crossoverProb;
    RandomGenerator rnd;
//    private int numBitsCode = GAProfile.numBitsCode;
//    private boolean binaryCoded = GAProfile.binaryCoded;
    /**
     * Constructor
     */
    public Crossover()
    {
    	super();
    	rnd = new RandomGenerator();
    }
    /**
     * Set type of crossover
     * @param val
     */
    public void setXType(int val) { this.type = val;}
    /**
     * Set number of offspring reproduced
     * @param val
     */
    public void setPoolSize(int val) { this.poolSize = val;}
    /**
     * Set crossover probability
     * @param val
     */
    public void setXProb(double val) {this.crossoverProb = val;}
    
    public Vector<Individual> doProcess(Vector<Individual> indivs)
    {
    	Vector<Individual> res = new Vector<Individual>();
    	
    	int iCnt, iIndex;
        int indexParent1 = -1, indexParent2 = -1;
        Individual indiv1, indiv2;

        /* Mating process */
        int [][] mateIndexes = selectTwoParents(poolSize);
        /* Reproduction */
        iCnt = 0;      
        iIndex = 0;
        //int iXCount = 0;
        do
        {
            indexParent1 = mateIndexes[iIndex][0];
            indexParent2 = mateIndexes[iIndex][1];
            iIndex++;
            indiv1 = new Individual(indivs.elementAt(indexParent1));
            indiv2 = new Individual(indivs.elementAt(indexParent2));
            if (rnd.coinToss(crossoverProb)) //do crossover
            {
            	
            	// copy to new individuals of parents
                Vector<Individual> input = new Vector<Individual>();
                input.addElement(indiv1); input.addElement(indiv2);
                
            	Vector<Individual> output = null;
                if (type == Crossover.ctOnePoint)
                {
                	output = doOnePtCrossover(input);
                }
                else if (type == Crossover.ctArithmetic)
                {
                	output = doArithmeticCrossover(input);
                }
                else if (type == Crossover.ctTwoPoint) {
                	output = doTwoPtCrossover(input);
                }
                else if (type == Crossover.ctUniform ) {
                	output = doUniformCrossover(input);
                }
                // Doing nothing
                else if (type < 0)
                	output = input;
                for (int count = 0; count < output.size(); count++) {
                	if (iCnt < poolSize)
                    {
                		res.addElement(output.elementAt(count));
                		iCnt++;
                	}
                }
                //iXCount += output.size();
            }
            else //if no crossover, then copy this parent chromosome "as is" into the offspring
            {
            	if (iCnt < poolSize) {
            		res.addElement(indiv1); iCnt++;
            	}
            	if (iCnt < poolSize) {
            		res.addElement(indiv2); iCnt++;
            	}
            }
        }
        while (iCnt < poolSize);
    	//System.out.println(iXCount + " individuals are cross-over...");
    	return res;
    }
	 /**
	 * Create matching poolSize/2 pair of indices 
	 * which indicate two parents from population, 
	 * @param poolSize
	 * @return poolSize/2 pairs of indices 
	 */
    public int [][] selectTwoParents(int poolSize)
    {
    	int numPairs = (int)Math.ceil(poolSize/2.0);
        int [][] res = new int[numPairs][2];
        
        int j = 0;
        /* Randomized
        Permutation permGen = new Permutation();
        int [] perm = permGen.getPerm(2*numPairs);
        for (int i = 0; i < numPairs; i++)
        {
        	res[i][0] = (perm[j]-1)%poolSize; j++;
        	res[i][1] = (perm[j]-1)%poolSize; j++;
        }
        */
        /* Simple scheme using consecutive pairs (1,2), (3,4)...*/
        for (int i = 0; i < numPairs; i++)
        {
        	res[i][0] = j%poolSize; j++;
        	res[i][1] = j%poolSize; j++;
        }
        return res;
    }
    
    /**
     * Function to perform One-Point Crossover between 2 parents
     * @param indivs of 2 parents
     * @return 2 offsprings
     */
    public Vector<Individual> doOnePtCrossover(Vector<Individual> indivs)
    {
    	Vector<Individual> res = null;
    	if (indivs.size() != 2) return null;
    	Individual indi1 = indivs.elementAt(0);
		Individual indi2 = indivs.elementAt(1);
    	Chromosome Chrom1 = indi1.genome.elementAt(0);
    	Chromosome Chrom2 = indi2.genome.elementAt(0);
    	int chromosomeDim = Chrom1.n_Dim;
    	// Check for valid crossover
    	if (chromosomeDim == 1)
    		return indivs;
    	// Continue
        byte type = Chrom1.type;
        /* Binary crossover */
        
    	res = new Vector<Individual>();
    	int crossoverPt = 0;   	
        
        if (type == Chromosome.BINARY) 
        {
        	while (crossoverPt == 0)
            	crossoverPt = rnd.getRandom(chromosomeDim);
        	for (int i = crossoverPt; i < chromosomeDim; i++)
        	{
    			byte temp1 = ((Byte)Chrom1.chromosome.elementAt(i)).byteValue();
    			byte temp2 = ((Byte)Chrom2.chromosome.elementAt(i)).byteValue();
    			Chrom1.setGene(i, temp2);
    			Chrom2.setGene(i, temp1);
        	}
        }
    	else if (type == Chromosome.FLOAT) 
    	{	
//    		if (binaryCoded) {
//	    		/* */
//	    		// Convert to bitstring
//	    		boolean [] chromStr1 = RealCoding.encodingArray(
//	    				Chrom1.getDoubleArray(), Chrom1.lBound, Chrom1.uBound, numBitsCode);
//	    		boolean [] chromStr2 = RealCoding.encodingArray(
//	    				Chrom2.getDoubleArray(), Chrom2.lBound, Chrom2.uBound, numBitsCode);
//	    		// Crossover
//	    		int chromStrDim = chromStr1.length; 
//	    		while (crossoverPt == 0)
//	            	crossoverPt = rnd.getRandom(chromStrDim-1);
//	    		for (int i = crossoverPt; i < chromStrDim; i++)
//	    		{
//	    			boolean temp1 = chromStr1[i];
//	    			boolean temp2 = chromStr2[i];
//	    			chromStr1[i] = temp2;
//	    			chromStr2[i] = temp1;
//	    		}
//	    		// Convert to real
//	    		double [] chromD1 = RealCoding.decodingArray(chromStr1, 
//	    				Chrom1.lBound, Chrom1.uBound);
//	    		double [] chromD2 = RealCoding.decodingArray(chromStr2, 
//	    				Chrom2.lBound, Chrom2.uBound);
//	    		for (int i = 0; i < chromosomeDim; i++)
//	    		{
//	    			Chrom1.setGene(i, chromD1[i]);
//					Chrom2.setGene(i, chromD2[i]);
//	    		}
//    		}
//    		else
    		{
    		/* */
	    		while (crossoverPt == 0)
	            	crossoverPt = rnd.getRandom(chromosomeDim-1);
	            for (int i = crossoverPt; i < chromosomeDim; i++)
	        	{
					double temp1 = ((Double)Chrom1.chromosome.elementAt(i)).doubleValue();
					double temp2 = ((Double)Chrom2.chromosome.elementAt(i)).doubleValue();
					Chrom1.setGene(i, temp2);
					Chrom2.setGene(i, temp1);
				}
    		}
    	}
        // new individuals are not evaluated
        indi1.evaluated = false; indi2.evaluated = false;
		res.addElement(indi1); res.addElement(indi2);
        return res;
    }
    
    /**
     * Function to perform Two-Point Crossover
     * @param indivs of 2 parents
     * @return 2 offsprings
     */
    public Vector<Individual> doTwoPtCrossover(Vector<Individual> indivs)
    {
    	Vector<Individual> res = null;
    	if (indivs.size() != 2) return null;
    	Individual indi1 = indivs.elementAt(0);
		Individual indi2 = indivs.elementAt(1);
    	Chromosome Chrom1 = indi1.genome.elementAt(0);
    	Chromosome Chrom2 = indi2.genome.elementAt(0);
    	int chromosomeDim = Chrom1.n_Dim;
    	// Check for valid crossover
    	if (chromosomeDim == 1)
    		return indivs;
    	// Continue
        byte type = Chrom1.type;
        /* Random point */
    	res = new Vector<Individual>();
    	int crossoverPt1 = 0, crossoverPt2 = 0;   	
        int low, high;
        
        if (type == Chromosome.BINARY) {
        	while (crossoverPt1 == 0)
            	crossoverPt1 = rnd.getRandom(chromosomeDim-1);
            while (crossoverPt2 == 0 || crossoverPt2== crossoverPt1)
            	crossoverPt2 = rnd.getRandom(chromosomeDim-1);
            if (crossoverPt2 > crossoverPt1)
            {
            	low = crossoverPt1;
            	high = crossoverPt2;
            }
            else {
            	low = crossoverPt2;
            	high = crossoverPt1;
            }
        	for (int i = low; i <= high; i++)
        	{
    			byte temp1 = ((Byte)Chrom1.chromosome.elementAt(i)).byteValue();
    			byte temp2 = ((Byte)Chrom2.chromosome.elementAt(i)).byteValue();
    			Chrom1.setGene(i, temp2);
    			Chrom2.setGene(i, temp1);
    		}
        }
    	else if (type == Chromosome.FLOAT) {
//    		if (binaryCoded) {
//	    		/* */
//	    		// Convert to bitstring
//	    		boolean [] chromStr1 = RealCoding.encodingArray(
//	    				Chrom1.getDoubleArray(), Chrom1.lBound, Chrom1.uBound, numBitsCode);
//	    		boolean [] chromStr2 = RealCoding.encodingArray(
//	    				Chrom2.getDoubleArray(), Chrom2.lBound, Chrom2.uBound, numBitsCode);
//	    		// Crossover
//	    		int chromStrDim = chromStr1.length; 
//	    		while (crossoverPt1 == 0)
//	            	crossoverPt1 = rnd.getRandom(chromStrDim-1);
//	            while (crossoverPt2 == 0 || crossoverPt2== crossoverPt1)
//	            	crossoverPt2 = rnd.getRandom(chromStrDim-1);
//	            if (crossoverPt2 > crossoverPt1)
//	            {
//	            	low = crossoverPt1;
//	            	high = crossoverPt2;
//	            }
//	            else {
//	            	low = crossoverPt2;
//	            	high = crossoverPt1;
//	            }
//	    		for (int i = low; i <= high; i++)
//	    		{
//	    			boolean temp1 = chromStr1[i];
//	    			boolean temp2 = chromStr2[i];
//	    			chromStr1[i] = temp2;
//	    			chromStr2[i] = temp1;
//	    		}
//	    		// Convert to real
//	    		double [] chromD1 = RealCoding.decodingArray(chromStr1, 
//	    				Chrom1.lBound, Chrom1.uBound);
//	    		double [] chromD2 = RealCoding.decodingArray(chromStr2, 
//	    				Chrom2.lBound, Chrom2.uBound);
//	    		for (int i = 0; i < chromosomeDim; i++)
//	    		{
//	    			Chrom1.setGene(i, chromD1[i]);
//					Chrom2.setGene(i, chromD2[i]);
//	    		}
//    		}
//    		else 
    		{
	    		/*  */
    			while (crossoverPt1 == 0)
                	crossoverPt1 = rnd.getRandom(chromosomeDim-1);
                while (crossoverPt2 == 0 || crossoverPt2== crossoverPt1)
                	crossoverPt2 = rnd.getRandom(chromosomeDim-1);
                if (crossoverPt2 > crossoverPt1)
                {
                	low = crossoverPt1;
                	high = crossoverPt2;
                }
                else {
                	low = crossoverPt2;
                	high = crossoverPt1;
                }
	    		for (int i = low; i <= high; i++)
	        	{
	    			double temp1 = ((Double)Chrom1.chromosome.elementAt(i)).doubleValue();
	    			double temp2 = ((Double)Chrom2.chromosome.elementAt(i)).doubleValue();
	    			Chrom1.setGene(i, temp2);
	    			Chrom2.setGene(i, temp1);
	    		}
	    		
    		}
    	}
		indi1.evaluated = false; indi2.evaluated=false;
		res.addElement(indi1); res.addElement(indi2);
        return res;
    }
    /**
     * Function to perform Uniform Crossover
     * @param indivs of 2 parents
     * @return 2 offsprings
     */
    public Vector<Individual> doUniformCrossover(Vector<Individual> indivs)
    {
    	Vector<Individual> res = null;
    	if (indivs.size() != 2) return null;
    	Individual indi1 = indivs.elementAt(0);
		Individual indi2 = indivs.elementAt(1);
    	Chromosome Chrom1 = indi1.genome.elementAt(0);
    	Chromosome Chrom2 = indi2.genome.elementAt(0);
    	int chromosomeDim = Chrom1.n_Dim;
    	// Check for valid crossover
    	if (chromosomeDim == 1)
    		return indivs;
    	// Continue
        byte type = Chrom1.type;
        /* Binary crossover */
        
    	res = new Vector<Individual>();
        
        if (type == Chromosome.BINARY) 
        {
        	for (int i = 0; i < chromosomeDim; i++)
        	{
        		if (rnd.coinToss(0.5)) 
        		{
	    			byte temp1 = ((Byte)Chrom1.chromosome.elementAt(i)).byteValue();
	    			byte temp2 = ((Byte)Chrom2.chromosome.elementAt(i)).byteValue();
	    			Chrom1.setGene(i, temp2);
	    			Chrom2.setGene(i, temp1);
        		}
        	}
        }
    	else if (type == Chromosome.FLOAT) 
    	{	
//    		if (binaryCoded)
//    		{
//	    		/* */
//	    		// Convert to bitstring
//	    		boolean [] chromStr1 = RealCoding.encodingArray(
//	    				Chrom1.getDoubleArray(), Chrom1.lBound, Chrom1.uBound, numBitsCode);
//	    		boolean [] chromStr2 = RealCoding.encodingArray(
//	    				Chrom2.getDoubleArray(), Chrom2.lBound, Chrom2.uBound, numBitsCode);
//	    		// Crossover
//	    		int chromStrDim = chromStr1.length; 
//	    		
//	    		for (int i = 0; i < chromStrDim; i++)
//	    		{
//	    			if (rnd.coinToss(0.5)) {
//		    			boolean temp1 = chromStr1[i];
//		    			boolean temp2 = chromStr2[i];
//		    			chromStr1[i] = temp2;
//		    			chromStr2[i] = temp1;
//	    			}
//	    		}
//	    		// Convert to real
//	    		double [] chromD1 = RealCoding.decodingArray(chromStr1, 
//	    				Chrom1.lBound, Chrom1.uBound);
//	    		double [] chromD2 = RealCoding.decodingArray(chromStr2, 
//	    				Chrom2.lBound, Chrom2.uBound);
//	    		for (int i = 0; i < chromosomeDim; i++)
//	    		{
//	    			Chrom1.setGene(i, chromD1[i]);
//					Chrom2.setGene(i, chromD2[i]);
//	    		}
//    		}
//    		else
    		{
	    		/* */
	            for (int i = 0; i < chromosomeDim; i++)
	        	{
	            	if (rnd.coinToss(0.5)) {
						double temp1 = ((Double)Chrom1.chromosome.elementAt(i)).doubleValue();
						double temp2 = ((Double)Chrom2.chromosome.elementAt(i)).doubleValue();
						Chrom1.setGene(i, temp2);
						Chrom2.setGene(i, temp1);
	            	}
				}
    		}
    	}
        
		indi1.evaluated = false; indi2.evaluated = false;
		res.addElement(indi1); res.addElement(indi2);
        return res;
    }
    /**
     * Do arithmetic crossover
     * @param indivs of 2 parents
     * @return 2 offsprings
     */
    public Vector<Individual> doArithmeticCrossover(Vector<Individual> indivs)
    {
    	Vector<Individual> res = null;
    	if (indivs.size() != 2) return null;
    	Individual indi1 = indivs.elementAt(0);
		Individual indi2 = indivs.elementAt(1);
    	Chromosome Chrom1 = indi1.genome.elementAt(0);
    	Chromosome Chrom2 = indi2.genome.elementAt(0);
    	int chromosomeDim = Chrom1.n_Dim;
        byte type = Chrom1.type;
        
        if (type == Chromosome.FLOAT) {
        	res = new Vector<Individual>();
        	double R1 = rnd.getRandom(1.0);
        	double R2 = rnd.getRandom(1.0);
        	/* Copy configuration of original chromosome */
        	for (int i = 0; i < chromosomeDim; i++)
        	{
        		double a = ((Double)Chrom1.chromosome.elementAt(i)).doubleValue();
        		double b = ((Double)Chrom2.chromosome.elementAt(i)).doubleValue();
        		Chrom1.setGene(i, a*R1 + (1-R1)*b);
        		Chrom2.setGene(i, a*R2 + (1-R2)*b);
        	}
        	indi1.evaluated = false; indi2.evaluated = false;
        	res.addElement(indi1); res.addElement(indi2);
        }
        return res;
    }
    static public void main(String[] argv)
    {
    	double [] x1 = {2,5,3};
    	double [] x2 = {9,4,2};
    	double [] l = {1,1,1};
    	double [] h = {11,11,11};
    	Chromosome chrom1 = new Chromosome(
    			Chromosome.FLOAT, x1, l, h);
    	Chromosome chrom2 = new Chromosome(
    			Chromosome.FLOAT, x2, l, h);
    	Individual indiv1 = new Individual(chrom1);
    	Individual indiv2 = new Individual(chrom2);
    	Vector<Individual> indivs = 
    		new Vector<Individual>();
    	indivs.addElement(indiv1);
    	indivs.addElement(indiv2);
    	for (int i =0 ; i < indivs.size(); i++)
    		System.out.println(indivs.elementAt(i).toString());
    	
    	Crossover operator = new Crossover();
    	indivs = operator.doUniformCrossover(indivs);
    	for (int i =0 ; i < indivs.size(); i++)
    		System.out.println(indivs.elementAt(i).toString());
    }
}
