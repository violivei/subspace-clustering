package optimization.searchspace;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;

import optimization.operator.population.Performance;
import optimization.sampling.*;
import optimization.tools.*;

/**
 * Class to model a population of candidate solutions
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Population {
	public static final int NUMERIAL = 0;
	public static final int NOMINAL = 1;
    
    /**
     * Storage for pool of individuals for current generation 
     * */
    public Vector<Individual> individuals; 
    /** 
     * Storate of best found solution for each restart 
     * */
    public Vector<Individual> bestpool;
    /** 
     * Storage for pool of experience by individuals 
     * */
    public Vector<Individual> prelimIndiv;
    /** 
     * Index of fittest chromosome in current generation 
     * */
    public int bestFitnessChromIndex; 
    /** 
     * Selected pool/reproduction size 
     * */
	public int poolSize;
    /** 
     * Index of least fit chromosome in current generation 
     * */
    public int worstFitnessChromIndex;     
    /** 
     * Statistic-- measure of performance 
     * */
    public Performance measure = null;
    /** 
     * Compute statistics for each generation during evolution 
     * */ 
    public boolean computeStatistics; 
    /** 
     * Label set to each chromosome (for classification task if any) 
     * */
    public String [] labels;
    /**
     * Bytes to indicate types of chromosomes in one individual's genome
     */
    public byte [] chromTypes;
	 /** 
	  * Dimension of chromosome (number of genes/chromosome). Note that
	  * for the first chromosome, chromosomeDim[0] = number of decision variables 
	  * */
	public int[] chromosomeDim;
	/** 
     * Number of individuals in the population  
     */
    public int populationDim;
	/**
	 * Constructor
	 * @param chromDim Dimension [] of each chromosome
	 * @param chromType Types [] of chromosomes
	 * @param populationDim Number of chromosomes in the population
	 */
    public Population(int [] chromDim, byte [] chromType, int populationDim) {
        this.chromosomeDim = chromDim;
        this.chromTypes = chromType;
        this.populationDim = populationDim;
        this.poolSize = populationDim;
        
        this.labels = new String[populationDim];
        
        this.individuals = new Vector<Individual>();
        this.prelimIndiv = new Vector<Individual>();
        this.bestpool = new Vector<Individual>(); 
        
    }
    /**
     * Constructor, i.e., a population wrapper for a vector of Individuals
     * @param srcPop Given vector of Individuals
     */
    public Population(Vector<Individual> srcPop) {
    	this.populationDim = srcPop.size();
    	this.poolSize = populationDim;
    	this.labels = new String[populationDim];
    	this.individuals = srcPop;
    	this.prelimIndiv = new Vector<Individual>();
    	this.bestpool = new Vector<Individual>();
    	
    	Individual temp = srcPop.elementAt(0);
    	int nChroms = temp.genome.size();
    	this.chromosomeDim = new int[nChroms];
    	this.chromTypes = new byte[nChroms];
    	
    	for (int i = 0 ; i < nChroms; i++) {
    		chromosomeDim[i] = temp.genome.elementAt(0).n_Dim;
    		chromTypes[i] = temp.genome.elementAt(0).type;
    	}
    }
    /** 
     * Initialize the population (chromosomes) to random values 
     * */
    public void initPopulation() {
    	individuals.clear();
    	bestpool.clear();
    	
    	for (int i = 0; i < populationDim; i++) {
    		Vector<Chromosome> temp = new Vector<Chromosome>();
    		
    		for (int j = 0; j < chromTypes.length; j++) 
    			temp.addElement(new Chromosome(chromTypes[j], chromosomeDim[j], true));
    		
    		Individual tempInd = new Individual(temp);
    		individuals.addElement(tempInd);
    	}
    }
    /**
     * Initialize the population (chromosomes) to random values within ranges
     * @param upperBound Upper bound (Effective use in real-coded domain)
     * @param lowerBound Lower bound (Effective use in real-coded domain)
     */
    public void initPopulation(double [] lowerBound, double [] upperBound) {
    	individuals.clear();
    	bestpool.clear();
    	
    	for (int i = 0; i < populationDim; i++) {
    		Vector<Chromosome> temp = new Vector<Chromosome>();
    		
    		for (int j = 0; j < chromTypes.length; j++) 
    			temp.addElement(new Chromosome(chromTypes[j], chromosomeDim[j], 
    							lowerBound, upperBound, true));
    		
    		Individual tempInd = new Individual(temp);
    		individuals.addElement(tempInd);
    	}
    }
    /**
     * Re-initialize/restart the population (chromosomes) to random values within ranges
     */
    public void reInitPopulation() {  	
    	for (int i = 0; i < populationDim; i++) {
    		Individual temp = individuals.elementAt(i);
    		
    		for (int j = 0; j < chromTypes.length; j++) 
    			temp.getChromAtIndex(j).initialize();
    	}
    }
    /**
     * Import the population (chromosomes) from specified file 
     * (e.g. used in real-world applications, or continue one search)
     * @param upperBound Upper bound (Effective use in real-coded domain)
     * @param lowerBound Lower bound (Effective use in real-coded domain)
     * @param fileName 
     */
    public void initPopulation(double [] lowerBound, double [] upperBound, String fileName) {
    	individuals.clear();
    	bestpool.clear();
    	try {
    		FileReader fr = new FileReader(fileName);
    		BufferedReader input = new BufferedReader(fr);
    		String s = null;
    		s = input.readLine();
    		int popDim = Integer.parseInt(s);
    		if (popDim != populationDim) {
    			System.out.println("Pop size mismatched!");
    			System.exit(0);
    		}
    		s = input.readLine();
    		int indDim = Integer.parseInt(s);
    		if (indDim != lowerBound.length) {
    			System.out.println("Dim size mismatched!");
    			System.exit(0);
    		}
	    	for (int i = 0; i < populationDim; i++) {
	    		Vector<Chromosome> temp = new Vector<Chromosome>();
	    		s = input.readLine();
	    		String [] values = s.split(",");
	    		for (int j = 0; j < chromTypes.length; j++) { 
	    			temp.addElement(new Chromosome(chromTypes[j], chromosomeDim[j], 
	    							lowerBound, upperBound, false));
	    			/* Read from file */
	    			for (int k = 0; k < indDim; k++) {
	    				double val = Double.parseDouble(values[k]);
	    				temp.elementAt(j).setGene(k, val);
	    			}
	    		}
	    		
	    		Individual tempInd = new Individual(temp);
	    		individuals.addElement(tempInd);
	    	}
	    	fr.close();
    	}
    	catch(Exception e) {
    		System.out.println("Can not initialize population: " + e.getMessage());
    		System.exit(0);
    	}
    }
    /**
     * Function to get a type of solution string  
     * (generally 1st chromosome represent solution string)
     * @return Type code
     */
    public byte getPopulationType() 
    {
    	return this.getIndividualAtIndex(0).getChromAtIndex(0).type;
    }
    /**
     * Get population as a sample points in matrix [][] (for real-coded optimization).
     * First chromosome is considered as solution string
     */
    public double [][] getPopSamples()
    {
    	double [][] res = new double[populationDim][chromosomeDim[0]];
    	for (int i = 0; i < populationDim; i++) 
    	{
    		Chromosome chrom = this.getIndividualAtIndex(i).getChromAtIndex(0);
    		for (int j = 0; j < chromosomeDim[0]; j++)
    			res[i][j] =	chrom.getGeneAsDouble(j);
    	}
    	return res;
    }
    
    /**
     * Check if population is a instance of stratified sampling
     * within its ranges
     * @return TRUE if stratified sampling, FALSE if otherwise
     */
    public boolean isStratifiedSampling()
    {
    	
    	boolean res = false;
    	if (getPopulationType()== Chromosome.BINARY)
    			return res;
    	
    	double [][] samples = getPopSamples();
    	int nSample = samples.length;
		int nDim = samples[0].length;
    	double [] lowB = new double[nDim];
		double [] highB = new double[nDim];
		for (int i = 0; i < nDim; i++)
		{
			lowB[i] = Double.MAX_VALUE;
			highB[i] = -Double.MAX_VALUE;
		}
		for (int i = 0; i < nSample; i++)
			for (int j = 0; j < nDim; j++)
			{
				if (Utils.le(samples[i][j], lowB[j]))
					lowB[j] = samples[i][j];
				if (Utils.ge(samples[i][j], highB[j]))
					highB[j] = samples[i][j];
			}
		// Number of bins is nDim/2
		res = SampleCheck.isStratifiedSample(samples, lowB, highB, nDim/2);
    	
    	return res;
    }
    /**
     * Check if population is a instance of Latin-hypercube sampling
     * within its ranges
     */
    public boolean isLHSampling()
    {
    	boolean res = false;
    	if (getPopulationType()== Chromosome.BINARY)
			return res;
    	double [][] samples = getPopSamples();
    	int nSample = samples.length;
		int nDim = samples[0].length;
    	double [] lowB = new double[nDim];
		double [] highB = new double[nDim];
		for (int i = 0; i < nDim; i++)
		{
			lowB[i] = Double.MAX_VALUE;
			highB[i] = -Double.MAX_VALUE;
		}
		for (int i = 0; i < nSample; i++)
			for (int j = 0; j < nDim; j++)
			{
				if (Utils.le(samples[i][j], lowB[j]))
					lowB[j] = samples[i][j];
				if (Utils.ge(samples[i][j], highB[j]))
					highB[j] = samples[i][j];
			}
    	res = SampleCheck.isLHSample(samples, lowB, highB);
    	return res;
    }
    /**
     * Add new individual into the population. Add by reference.
     * @param indi
     */
    public void addIndividual(Individual indi)
    {
    	this.individuals.addElement(indi);
    	this.populationDim++;
    }
    /**
     * Remove individual from the population
     */
    public void removeIndividual(Individual indi)
    {
    	this.individuals.removeElement(indi);
    	this.populationDim--;
    }
    /**
     * Return the first chromosome (i.e. solution string) from an individual 
     */
    public Chromosome getSimpleIndividual(int index) {
    	return this.individuals.elementAt(index).genome.elementAt(0);
    }
    /**
     * Gets the number of genes on each chromosome
     */
    public int [] getChromosomeDim()
    {
        return chromosomeDim;
    }
    /**
     * Gets the number of individuals in the population
     */
    public int getPopulationDim()
    {
        return populationDim;
    }
    /**
     * Returns whether statistics will be computed for this evolution run
     */
    public boolean getComputeStatistics()
    {
        return computeStatistics;
    }
    /**
     * Returns the fittest individual in the population
     */
    public Individual getFittestIndividual()
    {
        return (individuals.elementAt(bestFitnessChromIndex));
    }
    /**
     * Get individual at specified index
     */
    public Individual getIndividualAtIndex(int index) {
    	return individuals.elementAt(index);
    }
    /**
     * Gets the best fitness value in the population 
     */
    public double getFittestIndividualsFitness()
    {
        return individuals.elementAt(bestFitnessChromIndex).getFitnessValue();
    }
    /**
     * Go through all chromosomes and calculate the average fitness (of this generation)
     */
    public double getAvgFitness()
    {
        double rSumFitness = 0.0;

        for (int i = 0; i < populationDim; i++)
            rSumFitness += individuals.elementAt(i).getFitnessValue();
        return (rSumFitness / populationDim);
    }
    /**
     * Calculate the ranking of the parameter "fitness" with respect to 
     * the current generation. If the fitness is high (BAD), the corresponding 
     * fitness ranking will be high. 
     * 
     * For example, if the fitness passed in is higher than any fitness value 
     * for any chromosome in the current generation, the fitnessRank will 
     * equal the (populationDim-1). And if the fitness is lower than any 
     * fitness value for any chromosome in the current generation, 
     * the fitnessRank will equal zero.
     * @param fitness Fitness of the individual
     * @return The fitness ranking
     */
    public int getFitnessRank(double fitness)
    {
        int fitnessRank = -1;
        boolean firstOccur = true;
        
        for (int i = 0; i < populationDim; i++)
        {
            if (fitness > individuals.elementAt(i).getFitnessValue())
                fitnessRank++;
            else if (fitness == individuals.elementAt(i).getFitnessValue())
            {
            	// insure different ranking for chromosomes 
            	// of the same value
            	if (individuals.elementAt(i).fitnessRank > -1)
            		fitnessRank++;
            	else if (individuals.elementAt(i).fitnessRank == -1)
            	{
            		if (firstOccur) 
            		{
            			fitnessRank++;
            			firstOccur = false;
            		}
            	}
            }
        }

        return (fitnessRank);
    }
    /**
     * Function to return the index of the first chromosome
     * in the list of a specified rank.
     * Note that this function MUST be called after 
     * computeFitnessRankings() 
     * @param ranking
     * @return the index of the chromosome, otherwise 
     * return -1 as NOT_FOUND status
     */
    public int getIndexOfRank(int ranking)
    {
    	int chromIndex = -1;
    	for (int i = 0; i < populationDim; i++)
    	{
    		if (ranking == individuals.elementAt(i).fitnessRank)
    			chromIndex = i;
    	}
    	return chromIndex;
    }
    /**
     * Compute ranking of individuals based on fitness
     *
     */
    public void computeRankings()
    {
        // reset ranking
        for (int i = 0; i < populationDim; i++)
        	individuals.elementAt(i).fitnessRank = -1;
        for (int i = 0; i < populationDim; i++)
        	individuals.elementAt(i).fitnessRank = 
        		getFitnessRank(individuals.elementAt(i).getFitnessValue());

        for (int i = 0; i < populationDim; i++)
        {
            if (individuals.elementAt(i).fitnessRank == 0)
            {
                this.bestFitnessChromIndex = i;
            }
            if (individuals.elementAt(i).fitnessRank == populationDim - 1)
            {
                this.worstFitnessChromIndex = i;
            }
        }	
    }
    /**
     * Compute ranking of individuals based on fitness
     * High ranking numbers denote high fitness individiuals.
     */
    public void computeFitnessRankings()
    {
    	this.computeRankings();
    }
    
    /**
     * Get label (HIGH/LOW) of individual i-th
     */
    public String getLabel(int i)
    {
    	return (this.labels[i]);
    }
    /**
     * Set label for individual iGene-th
     * @param iGene
     * @param label
     */
    public void setLabel(int iGene, String label) {
    	this.labels[iGene] = label;
    }
    
    public String toString()
    {
    	String res = "";
    	for (int i = 0; i < populationDim; i++ )
    	{
    		res += "Chromosome " + i +
    			": " + individuals.elementAt(i).toString() + "\n";
    		res += "Fitness: " + individuals.elementAt(i).getFitnessValue() + "; ";
    		res += "Rank: " + individuals.elementAt(i).fitnessRank + "\n";
    	}
    	return res;
    }
    /**
     * Get general information of population 
     */
    public String getPopInfo()
    {
    	String res = "";
    	res += "Population size: " + populationDim + "\n";
    	for (int i = 0; i < this.chromosomeDim.length; i++)
    		res += i + "-Dimension: " + chromosomeDim[i] + "\n";
    	res += "Population bounds: ";
    	Chromosome chrom = this.getIndividualAtIndex(0).getChromAtIndex(0);
    	if (chrom.type == Chromosome.FLOAT) {
	    	for (int i =0; i < chrom.lBound.length; i++)
	    	{
	    		res+= "[" + chrom.lBound[i] + "," + chrom.uBound[i]+"] ";
	    	}
    	}
     	return res;
    }
    /**
     * Write genome of individuals (i.e., solution strings) to file in CSV format
     * @param filename Output filename
     */
    public void toFile(String filename)
    {
    	/* prepare log file */
    	PrintStream MyOutput = null;
    	try {
    	       MyOutput = new PrintStream(new FileOutputStream(filename));
    	   }
    	catch (IOException e)
    	   {
    	      System.out.println("OOps");
    	   }
    	// output all population
    	
    	MyOutput.println(this.populationDim);
    	for (int i = 0; i < chromosomeDim.length; i++)
    		if (i == chromosomeDim.length - 1)
    			MyOutput.println(this.chromosomeDim[i]);
    		else
    			MyOutput.print(this.chromosomeDim[i] + ", ");
    	for (int i = 0; i< populationDim; i++)
    	{
    		MyOutput.println(individuals.elementAt(i).toString()
    				+ ", " + individuals.elementAt(i).getFitnessValue());
    	}
    	
    }
    /**
     * Write genome of individuals (i.e., solution strings) & classified labels
     * to file in CSV format
     * @param filename Output filename
     */
    public void toClassifiedFile(String filename)
    {
    	/* prepare log file */
    	PrintStream MyOutput = null;
    	try {
    	       MyOutput = new PrintStream(new FileOutputStream(filename));
    	   }
    	catch (IOException e)
    	   {
    	      System.out.println("OOps");
    	   }

		int countPop = 0;
		String temp="";
		for (int i = 0; i< populationDim; i++)
			if (this.labels[i].compareTo("N.A")!= 0) 
				{
					countPop++;
					temp += individuals.elementAt(i).toString()+ 
							", " +individuals.elementAt(i).getFitnessValue()+ 
							", " + labels[i] + "\n";
				}
		MyOutput.println(countPop);
		for (int i = 0; i < chromosomeDim.length; i++)
    		if (i == chromosomeDim.length - 1)
    			MyOutput.println(this.chromosomeDim[i]);
    		else
    			MyOutput.print(this.chromosomeDim[i] + ", ");
		MyOutput.println(temp);
    }
}
