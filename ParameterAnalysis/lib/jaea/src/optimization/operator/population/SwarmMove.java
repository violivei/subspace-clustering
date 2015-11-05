package optimization.operator.population;

import java.util.Vector;

import optimization.searchspace.Individual;
import optimization.tools.*;
/**
 * Operator implementing the move of population in Particle Swarm Optimization. <p></p>
 * (Population-based Operator)
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class SwarmMove extends OperatorTemplate {
	/** inertia to incorporate the previous velocity */
	double inertia = 0.05;
	/**
	 * Set function
	 * @param val Inertia value. Default is 0.05
	 */
	public void setInertia(double val) {
		this.inertia = val;
	}
	/** learning rate on best experience */
	double localLearnRate = 2;
	/**
	 * Set function
	 * @param val Learning Rate on Individual. Default is 2.
	 */
	public void setLocalLearnRate(double val)
	{
		this.localLearnRate = val;
	}
	/** learning rate on best global experience */
	double neighborLearnRate = 2;
	/**
	 * Set function 
	 * @param val Neighborhood Learning Rate. Default is 2.
	 */
	public void setNeighborLearnRate(double val)
	{
		this.neighborLearnRate = val;
	}
	/**
	 * Global best individual/Current best solution found
	 */
	public Individual nBest;
	/**
	 * Individual best 'experience'/Individuals' best found solutions
	 */
	public Vector<Individual> pBest = new Vector<Individual>();
	/**
	 * Current velocity of moving of each individual
	 */
	public Vector<Individual> velocity = new Vector<Individual>();
	/** uniform random generator */
	RandomGenerator uniRnd = null;
	/**
	 * Constructor of default parameters
	 *
	 */
	public SwarmMove() {
		uniRnd = new RandomGenerator();
	}
	/**
	 * Constructor with specified parameters
	 * @param i Inertia
	 * @param l LocalLearnRate
	 * @param n NeighborLearnRate
	 */
	public SwarmMove( double i, double l, double n) {
		this.inertia = i;
		this.localLearnRate = l;
		this.neighborLearnRate = n;
	}
	
    public Vector<Individual> doProcess(Vector<Individual> pop) {
    	Vector<Individual> res = new Vector<Individual>();
    	int populationDim = pBest.size();
    	/** Determine individual best-so-far */
    	for (int i = 0; i < populationDim; i++)
    		if (pop.elementAt(i).getFitnessValue() 
    				< pBest.elementAt(i).getFitnessValue())
    		{
    			/* Copy new experience */
    			Individual indiv = pBest.elementAt(i);
    			indiv.copyIndividual(pop.elementAt(i));
    		}
    		
    	/** Generate next generation based on direction */

    	for (int i = 0; i < populationDim; i++)
    	{
    		Individual p = pBest.elementAt(i);
    		Individual v = velocity.elementAt(i);
    		Individual x = new Individual(pop.elementAt(i));
    		for (int j = 0; j < pop.elementAt(0).getChromAtIndex(0).n_Dim; j++)
    		{
    			double newV, currX;
    			// generate new valid velocity
    			do {
    				// v' = intertia * v + A * (xBest-x) + B * (xLBest - x')
    				newV = inertia * ((Double) v.getChromAtIndex(0).getGeneAsDouble(j) 
    							+ localLearnRate*uniRnd.getRandom(1.0)*
    							(p.getChromAtIndex(0).getGeneAsDouble(j) - x.getChromAtIndex(0).getGeneAsDouble(j))
    							+ neighborLearnRate*uniRnd.getRandom(1.0)*(nBest.getChromAtIndex(0).getGeneAsDouble(j) 
    									- x.getChromAtIndex(0).getGeneAsDouble(j)));
    				currX = x.getChromAtIndex(0).getGeneAsDouble(j);
    			} while(Utils.g(newV+currX, x.getChromAtIndex(0).uBound[j]) ||
    					Utils.l(newV+currX, x.getChromAtIndex(0).lBound[j]));
    			// update new location
    			v.getChromAtIndex(0).setGene(j, newV);
    			x.getChromAtIndex(0).setGene(j, currX + newV);
    			x.evaluated = false;
    		}
    		res.addElement(x);
    	}
    	return res;
    }
}
