package optimization.operator.population;

import java.util.Vector;
import optimization.searchspace.Individual;
import optimization.tools.*;
import java.util.Arrays;
/**
 * Operator to select genes pool from the current Population.<p></p>
 * Different selection schemes are supported.<p></p>
 * DEFAULT operation for MINIMIZATION problem: the LOWER, the BETTER.<p></p>
 * (Population-based Operator)
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Selection extends OperatorTemplate{
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
	/** K Best */
	public static final int KBest = 7;
	/** Uniform random generator */
	RandomGenerator uniRnd = null;
	private int type, poolsize;
	public void setType(int val) { this.type = val;}
    public void setPoolSize(int val) { this.poolsize = val;}
    /**
     * Constructor
     */
	public Selection()
	{
		super();
		uniRnd = new RandomGenerator();
	}
	/**
	 * Function to perform selection on given set of individuals
	 * @return RANDOMLY shuffled set of selected individuals 
	 */
    public Vector<Individual> doProcess(Vector<Individual> indivs){
    	Vector<Individual> res = null;
    	try {
    	if (type == Selection.RouletteWheel)
    		res = doRWSelection(indivs, poolsize);
    	else if (type == Selection.Ranking)
    		res = doRankingSelection(indivs, poolsize);
    	else if (type == Selection.Random)
    		res = doRandomSelection(indivs, poolsize);
    	else if (type == Selection.Tournament)
    		res = doTournamentSelection(indivs, poolsize);
    	else if (type == Selection.SUS)
    		res = doSUSelection(indivs, poolsize);
    	else if (type < 0)
    		res = indivs;
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
    	return res;
    }
    
    /**
     * Do Ranking selection on MINIMIZATION problem | the LOWER fitness, the BETTER <p></p>
     * Use fitness rank as the fitness value
     * @param indivs Input vector of Individuals 
     * @param poolSize Number of individuals to be selected
     * @return Selected individuals
     */
    public Vector<Individual> doRankingSelection(Vector<Individual> indivs, int poolSize)
    {
    	int populationDim = indivs.size();
    	Vector<Individual> res = new Vector<Individual>();
    	
    	// find max & min fitness + calculate SUM fitness
    	double [] v = new double[populationDim];
    	  	
    	for (int i = 0; i < populationDim; i++)
    	{
    		v[i] =indivs.elementAt(i).getFitnessRank();
    	}
    	/* Scaling for MINIMIZE problem */
    	Scaling.scaling4selection(v, Scaling.MINIMIZE);
    	/* Selecting based on the probability v */
    	int [] selectedIndex = RWSelection(v, poolSize);
    	for (int i = 0; i < poolSize; i++)
    		res.addElement(
    				new Individual(indivs.elementAt(selectedIndex[i])));
    	return res;
    }
    /**
     * Do RouletteWheel selection on MINIMIZATION problem | the LOWER fitness, the BETTER <p></p>
     * Use actual individual fitness for selection
     * @param indivs Input vector of Individuals 
     * @param poolSize Number of individuals to be selected
     * @return Selected individuals
     */
    public Vector<Individual> doRWSelection(Vector<Individual> indivs, int poolSize)
    {
    	int populationDim = indivs.size();
    	Vector<Individual> res = new Vector<Individual>();
    	
    	// find max & min fitness + calculate SUM fitness
    	double [] v = new double[populationDim];
    	  	
    	for (int i = 0; i < populationDim; i++)
    	{
    		v[i] =indivs.elementAt(i).getFitnessValue();
    	}
    	/* Scaling for MINIMIZE problem */
    	Scaling.scaling4selection(v, Scaling.MINIMIZE);
    	/* Selecting based on the probability v */
    	int [] selectedIndex = RWSelection(v, poolSize);
    	for (int i = 0; i < poolSize; i++)
    		res.addElement(
    				new Individual(indivs.elementAt(selectedIndex[i])));
    	return res;
    }
    /**
     * Core RouletteWheel based on scaled fitness: the HIGHER fitness, the BETTER <p></p>
     * @param v Scaled fitness
     * @param poolSize Number of individual to be selected
     * @return Indices of selected individual
     */
    public int [] RWSelection(double [] v, int poolSize)
    {
    	double sumF = 0;
    	int populationDim = v.length;
    	double [] rndPoints = new double[poolSize];
    	// index of selected individual
    	int [] indexes = new int[poolSize]; 
    	
    	for (int i = 0; i < populationDim; i++)
    		sumF += v[i];
    	for (int i = 0; i < poolSize; i++)
    		rndPoints[i] = uniRnd.getRandom(sumF);
    	Arrays.sort(rndPoints);
    	

    	/** Roulette-wheel selection */
    	int selPos = 0;
    	int indPos = 0;
    	double accumS = 0;
    	/* Shuffle the array */
    	Permutation permGen = new Permutation();
    	int [] perm = permGen.getPerm(poolSize);
    	try {
	    	while(selPos < poolSize)
	    	{
	    		// if the considering point rndPoints[selPos] lies within
	    		// the current segment of individuals[indPos]
	    		if (accumS + v[indPos] > rndPoints[selPos])
	    		{
	    			indexes[perm[selPos]-1] = indPos;
	    			selPos++;
	    		}
	    		else
	    		{
	    			accumS += v[indPos];
	    			indPos++;
	    		}
	    	}
    	} catch (Exception e) {
    		System.out.println("Current scaled fitnesses: ");
    		for (int i = 0; i < populationDim; i++)
    			System.out.print(v[i] + ", ");
    		System.out.println();
    		System.out.println("IndPos: " + indPos + ", SelPos: " + selPos);
    		System.out.println("ERROR in RWSelection: " + e.getMessage());
    		System.exit(0);
    	}

    	return indexes;
    }
    /**
     * Do Stochastic Universal selection on MINIMIZATION problem | the LOWER fitness, the BETTER <p></p>
     * Use actual individual fitness for selection
     * @param indivs Input vector of Individuals 
     * @param poolSize Number of individuals to be selected
     * @return Selected individuals
     */
    public Vector<Individual> doSUSelection(Vector<Individual> indivs, int poolSize)
    {
    	int populationDim = indivs.size();
    	Vector<Individual> res = new Vector<Individual>();
    	
    	// find max & min fitness + calculate SUM fitness
    	double [] v = new double[populationDim];

    	for (int i = 0; i < populationDim; i++)
    	{
    		v[i] =indivs.elementAt(i).getFitnessValue();
    	}
    	/* Scaling for MINIMIZE problem */
    	Scaling.scaling4selection(v, Scaling.MINIMIZE);
    	/* Selecting based on the probability v */
    	try {
    		int [] selectedIndex = SUSelection(v, poolSize);
    		for (int i = 0; i < poolSize; i++)
        		res.addElement(
        				new Individual(indivs.elementAt(selectedIndex[i])));
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		for (int i = 0; i < indivs.size(); i++)
    			System.out.println(indivs.elementAt(i).toString() + ", " + 
    								indivs.elementAt(i).getFitnessValue());
    		System.out.println("ERROR in doSUSelection: " + e.getMessage());
    		System.exit(0);
    		
    	}

    	return res;
    }

    /**
     * Core Stochastic Universal based on scaled fitness: the HIGHER fitness, the BETTER <p></p>
     * @param v Scaled fitness
     * @param poolSize Number of individual to be selected
     * @return Indices of selected individual
     */
    public int [] SUSelection(double [] v, int poolSize)
    {
    	double sumF = 0;
    	int populationDim = v.length;
    	double [] rndPoints = new double[poolSize];
    	// index of selected individual
    	int [] indexes = new int[poolSize]; 
    	
    	for (int i = 0; i < populationDim; i++)
    		sumF += v[i];
    	/* Equal sampling point */
    	rndPoints[0] = uniRnd.getRandom(sumF);
    	double step = sumF/poolSize;
    	for (int i = 1; i < poolSize; i++) {
    		rndPoints[i] = rndPoints[i-1] + step;
    		if (Utils.ge(rndPoints[i], sumF)) 
    			rndPoints[i] = rndPoints[i]-sumF;
    	}
    	Arrays.sort(rndPoints);

    	/** SUS selection */
    	int selPos = 0;
    	int indPos = 0;
    	double accumS = 0;
    	/* Shuffle the array */
    	Permutation permGen = new Permutation();
    	int [] perm = permGen.getPerm(poolSize);
    	//try {
	    	while(selPos < poolSize)
	    	{
	    		// if the considering point rndPoints[selPos] lies within
	    		// the current segment of individuals[indPos]
	    		if (accumS + v[indPos] > rndPoints[selPos])
	    		{
	    			indexes[perm[selPos]-1] = indPos;
	    			selPos++;
	    		}
	    		else
	    		{
	    			accumS += v[indPos];
	    			indPos++;
	    		}
	    	}
	    	/*
    	} catch (Exception e) {
    		System.out.println("Current scaled fitnesses: ");
    		for (int i = 0; i < populationDim; i++)
    			System.out.print(v[i] + ", ");
    		System.out.println();
    		System.out.println("IndPos: " + indPos + ", SelPos: " + selPos);
    		System.out.println("ERROR in SUSelection: " + e.getMessage());
    		//System.exit(0);
    	}
	    	 */
    	return indexes;
    }
    
    /**
     * Do Tournament selection on MINIMIZATION problem | the LOWER fitness, the BETTER <p></p>
     * Use actual individual fitness for selection, no scaling required
     * @param indivs Input vector of Individuals 
     * @param poolSize Number of individuals to be selected
     * @return Selected individuals
     */
    public Vector<Individual> doTournamentSelection(Vector<Individual> indivs, int poolSize)
    {
    	int populationDim = indivs.size();
    	Vector<Individual> res = new Vector<Individual>();
    	
    	double [] v = new double[populationDim];
    	
    	for(int i = 0; i < populationDim; i++)
    		v[i] = indivs.elementAt(i).getFitnessValue();
    	
    	int [] selectedIndex = TournamentSelection(v, poolSize);
    	for (int i = 0; i < poolSize; i++)
    		res.addElement(
    				new Individual(indivs.elementAt(selectedIndex[i])));
    	
    	return res;
    }
    /**
     * Core Tournament Selection based on ORIGINAL fitness: the LOWER fitness, the BETTER <p></p>
     * @param v Fitness
     * @param poolSize Number of individual to be selected
     * @return Indices of selected individuals
     */
    public int[] TournamentSelection(double[] v, int poolSize)
    {
    	int populationDim = v.length;
    	/** Tournament Selection */
    	int [] winCount = new int[populationDim];
    	// index of selected individual
    	int [] res = new int[poolSize]; 
    	/* Fill in the ranking array of each individual by matching with 
    	   competitors */
    	int q = 5;
    	for (int i = 0; i < populationDim; i++)
    	{
    		winCount[i] = 0;
    		double fitnessIndiv = v[i];
    		// Choose q competitors
    		for (int j = 0; j < q; j++)
    		{
    			int ID = uniRnd.getRandom(populationDim);
    			double fitnessComp = v[ID];
    			// the LOWER fitness, the BETTER
    			if (Utils.le(fitnessIndiv, fitnessComp))
    				winCount[i] += 1;
    		}
    	}
    	/* Get the sorted array */
    	int [] indexes = new int[populationDim];
    	for (int i = 0; i < populationDim; i++)
    		indexes[i] = i;
    	Utils.bubbleSort(winCount, indexes);
    	/* Select the most "win" individual */
    	/* Shuffle the array */
    	Permutation permGen = new Permutation();
    	int [] perm = permGen.getPerm(poolSize);
    	for (int i = 0; i < poolSize; i++)
    	{
    		res[perm[i]-1] = indexes[populationDim - i - 1];
    	}
    	return res;
    }
    /**
     * Do Random selection: just select randomly poolsize individidual
     * @param indivs Input vector of Individuals 
     * @param poolSize Number of individuals to be selected
     * @return Selected individuals
     */
    public Vector<Individual> doRandomSelection(Vector<Individual> indivs, int poolSize)
    {
    	int populationDim = indivs.size();
    	Vector<Individual> res = new Vector<Individual>();
    	
    	/** Random Selection */
    	for (int i = 0; i < poolSize; i++)
    	{
    		int index = uniRnd.getRandom(populationDim);
    		Individual temp = new Individual(indivs.elementAt(index));
    		res.add(temp);
    	}
    	return res;
    }
    /**
     * Core K-best Selection/ select individuals based on the LOWER fitness, the BETTER it is
     * @param v Fitness
     * @param poolSize Number of individual selected
     * @return Indices of selected individuals
     */
    public int [] KBestSelection(double [] v, int poolSize)
    {
    	
    	// index of selected individual
    	int [] indexes = new int[poolSize]; 
    	int [] ordIndex = new int[v.length];
    	for (int i =0; i < v.length; i++)
    		ordIndex[i] = i;
    	// The lower, the better. Sort in ascending order (lowest to highest)
    	optimization.tools.Utils.bubbleSort(v, ordIndex);
    	/* Shuffle the array */
    	Permutation permGen = new Permutation();
    	int [] perm = permGen.getPerm(poolSize);
    	for (int i = 0; i < poolSize; i++)
    		indexes[perm[i]-1] = ordIndex[i];
    	return indexes;
    }

    static public void main(String [] argv)
    {
    	Selection sel = new Selection();
    	double [] v = {1, 2, 3, 8,10, 6, 2.15, 4};
    	//double [] v = {0, 0, 0, 0,0, 0, 0, 0};
    	int poolSize = v.length/2;
    	System.out.println("Current pop");
    	for (int i = 0; i < v.length; i++)
    		System.out.print(v[i] + " ");
    	System.out.println();
    	
    	int [] indexes = sel.KBestSelection(v, poolSize);
    	
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
