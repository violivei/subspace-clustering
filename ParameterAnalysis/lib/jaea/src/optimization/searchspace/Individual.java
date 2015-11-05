package optimization.searchspace;

import java.util.Vector;

/**
 * Individual class - basic representation of (evaluated) solution strings
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Individual {
	/**
	 * List of chromosomes constitute the solution 
	 */
	public Vector<Chromosome> genome = new Vector<Chromosome>();
	/**
	 * Number of chromosomes
	 */
	public int n_Chroms = 0;
    /** absolute (not relative) fitness value */
    private double fitness = 0; 
    /** 0 = worst fit, PopDim = best fit */
    public int fitnessRank = 0;
    /**
     * TRUE if individual has been evaluated, FALSE if otherwise
     */
    public boolean evaluated = false;
    
    /**
     * Constructor: perform cloning of given Individual (copy by value), 
     * including fitness/rank, etc...
     * @param a Individual to be cloned
     */
    public Individual(Individual a) {
    	for (int i = 0; i < a.genome.size(); i++)
    		genome.add(new Chromosome(a.genome.elementAt(i)));
    	n_Chroms = a.n_Chroms;
    	this.fitness = a.fitness;
    	this.fitnessRank = a.fitnessRank;
    	this.evaluated = a.evaluated;
    }
    /**
     * Cloning constructor.
     * Used if genome has one chromosome, make a clone (copy by value)
     * @param a Chromosome to be cloned
     */
    public Individual(Chromosome a) {
    	Chromosome b = new Chromosome(a);
    	genome.addElement(b);
    	n_Chroms = 1;
    }
    /**
     * Cloning constructor.
     * Used if genome has more than one chromosomes
     * @param a Genome to be cloned
     */
    public Individual(Vector<Chromosome> a) {
    	for (int i = 0; i < a.size(); i++)
    		genome.add(new Chromosome(a.elementAt(i)));
    	n_Chroms = a.size();
    }
    /** 
     * Get the genes as a string */
    public String toString() {
    	String resStr = "";
    	for (int i = 0; i < genome.size(); i++)
    		if (i == genome.size()-1)
    			resStr += genome.elementAt(i).toString();
    		else
    			resStr += genome.elementAt(i).toString()+ "\n";
    	return resStr;
    }
    /**
     * Function to copy the genome, fitness, rank from the input individual 
     * @param srcInd
     */
    public void copyIndividual(Individual srcInd) {
    	this.genome = new Vector<Chromosome>();
    	
    	for (int i = 0; i < srcInd.genome.size(); i++) {
    		Chromosome temp = new Chromosome(srcInd.genome.elementAt(i));
    		genome.addElement(temp);
    	}
    	
    	this.fitness = srcInd.fitness;
    	this.fitnessRank = srcInd.fitnessRank;
    }
    /**
     * Get function
     */
    public Chromosome getChromAtIndex(int index) {
    	return genome.elementAt(index);
    }
    /**
     * Set function/ (Copy by value)
     */
    public void setChromAtIndex(int index, Chromosome chrom) {
    	genome.setElementAt(new Chromosome(chrom), index);
    }
    /**
     * 
     * @return Fitness of the individual
     */
    public double getFitnessValue() { return this.fitness; }
    /**
     * 
     * @return Fitness rank of the individual in the population
     */
    public int getFitnessRank() { return this.fitnessRank; }
    public void setFitnessValue(double value) {
    	this.fitness = value;
    	this.evaluated = true;
    }
    public void setFitnessRank(int value) {
    	this.fitnessRank = value;
    }
}
