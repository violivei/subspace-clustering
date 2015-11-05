package optimization.operator.population;

import java.util.Vector;
import optimization.searchspace.Individual;
import optimization.tools.*;
/**
 * Operator to merge parents and offspring to a new population
 * Different selection schemes are supported. <p></p>
 * (Population-based Operator)
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Merging extends OperatorTemplate{
	/** Bias roulette wheel selection */
	public static final int RouletteWheel = 0;
	/** Rank selection */
	public static final int Ranking = 1;
	/** Tournament selection */
	public static final int Tournament = 2;
	/** Random selection */
	public static final int Random = 3;
	/** Elitism merging */
	public static final int Elitism = 4;
	/** Copy */
	public static final int Non_overlap = 5;
	/** Stochastic Universal */
	public static final int SUS = 6;
	/** Uniform random generator */
	RandomGenerator uniRnd = null;
	/**
	 * Employ selection operator
	 */
	Selection selector = new Selection();
	/**
	 * selection type & size of next population
	 */
	private int type, poolsize;
	/**
	 * List of parent solutions
	 */
	private Vector<Individual> parents;
	public void setParents(Vector<Individual> p) { this.parents = p;}
	public void setType(int val) { this.type = val;}
    public void setPoolSize(int val) { this.poolsize = val;}
    /**
     * Constructor
     */
	public Merging()
	{
		super();
		uniRnd = new RandomGenerator();
	}
	/**
     * For merging offspring & parents
     * @param offsprings
     * @return Population for the next generation
     */
    public Vector<Individual> doProcess(Vector<Individual> offsprings){
    	Vector<Individual> res = null;
    	try {
    	if (type == Selection.RouletteWheel) {
    		for (int i = 0; i <  parents.size(); i++)
    			offsprings.addElement(parents.elementAt(i));
    		res = selector.doRWSelection(offsprings, poolsize);
    	}
    	else if (type == Selection.SUS) {
    		for (int i = 0; i <  parents.size(); i++)
    			offsprings.addElement(parents.elementAt(i));
    		res = selector.doSUSelection(offsprings, poolsize);
    	}
    	else if (type == Selection.Ranking) {
    		for (int i = 0; i <  parents.size(); i++)
    			offsprings.addElement(parents.elementAt(i));
    		res = selector.doRankingSelection(offsprings, poolsize);
    	}
    	else if (type == Selection.Random) {
    		for (int i = 0; i <  parents.size(); i++)
    			offsprings.addElement(parents.elementAt(i));
    		res = selector.doRandomSelection(offsprings, poolsize);
    	}
    	else if (type == Selection.Tournament) {
    		for (int i = 0; i <  parents.size(); i++)
    			offsprings.addElement(parents.elementAt(i));
    		res = selector.doTournamentSelection(offsprings, poolsize);
    	}
    	else if (type == Selection.Elitism) {
    		res = doElitismMerging(offsprings, parents, poolsize);
    	}
    	// Just copy offspring to next generation
    	else if (type == Selection.Non_overlap)
    		res = offsprings;
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
    	return res;
    }
    /**
     * Function to perform merging with elitism: get k-best from parent and
     * the rest from offspring
     * @param offsprings
     * @param parents
     * @param poolSize
     */
    public Vector<Individual> doElitismMerging(Vector<Individual> offsprings, 
    		Vector<Individual> parents, int poolSize)
    {
    	int index;
    	int overlap = 1;
    	int sizeOff, sizePar;
    	Vector<Individual> res = new Vector<Individual>();
    	/* Get ranking */
    	sizeOff = offsprings.size();
    	double [] fitOff = new double[sizeOff];
    	int [] idxOff = new int[sizeOff];
    	for (int i = 0; i < sizeOff; i++) {
    		fitOff[i] = offsprings.elementAt(i).getFitnessValue();
    		idxOff[i] = i;
    	}
    	Utils.bubbleSort(fitOff, idxOff);
    	
    	sizePar = parents.size();
    	double [] fitPar = new double[sizePar];
    	int [] idxPar = new int[sizePar];
    	for (int i = 0; i < sizePar; i++) {
    		fitPar[i] = parents.elementAt(i).getFitnessValue();
    		idxPar[i] = i;
    	}
    	Utils.bubbleSort(fitPar, idxPar);
    	/* Elitism Selection */
    	Individual temp;
    	for (int i = 0; i < poolSize; i++)
    	{
    		/* Get best from parents */
    		if (i < overlap) {
    			index = idxPar[i];
    			temp = new Individual(parents.elementAt(index));
    		}
    		else {
    			index = idxOff[i-overlap];
    			temp = new Individual(offsprings.elementAt(index));
    		}
    		res.add(temp);
    	}
    	return res;
    }
    static public void main(String [] argv)
    {
    	Selection sel = new Selection();
    	double [] v = {1, 5, 6, 8,10, 3, 2.15, 4};
    	//double [] v = {0, 0, 0, 0,0, 0, 0, 0};
    	int poolSize = v.length-2;
    	int [] indexes = sel.SUSelection(v, poolSize);
    	System.out.println("Current pop");
    	for (int i = 0; i < v.length; i++)
    		System.out.print(v[i] + " ");
    	System.out.println();
    	
    	System.out.println("Selected indexes");
    	for (int i = 0; i < poolSize; i++)
    		System.out.print(indexes[i] + " ");
    	System.out.println();
    	
    	System.out.println("Selected pop");
    	for (int i = 0; i < poolSize; i++)
    		System.out.print(v[indexes[i]] + " ");
    	System.out.println();
    }
}
