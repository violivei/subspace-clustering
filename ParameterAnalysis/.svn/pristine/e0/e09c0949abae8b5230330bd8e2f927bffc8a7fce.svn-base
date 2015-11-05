package optimization.operator.population;

import java.util.Random;
import java.util.Vector;

import optimization.searchspace.Chromosome;
import optimization.searchspace.Individual;
import optimization.tools.Utils;
/**
 * Operator to perform mutation with adaptation strategy 
 * in Evolution Strategy on a vector of Individuals . <p></p>
 * (Population-based Operator)
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class ESMutation extends OperatorTemplate {

	/** Random generator */
	Random rd = null;
	Random [] rdGen = null;
	/** Parameters */
	double a, b;
	int chromDim;
	/** 
	 * Constructor of operator
	 */
	public ESMutation(int nDim) {
		super();
		chromDim = nDim;
        rd = new Random(System.currentTimeMillis());
        /* 
        rdGen = new Random[nDim];
        for (int i = 0; i < nDim; i++)
        	rdGen[i] = new Random(System.currentTimeMillis());
        	*/
	}
	
    public Vector<Individual> doProcess(Vector<Individual> indivs)
    {
    	// Setting parameters
    	a = 1/Math.sqrt(2*Math.sqrt(chromDim));
    	b = 1/Math.sqrt(2*chromDim);
        // Do mutation
    	Vector<Individual> res = new Vector<Individual>();
    	Individual temp = null;
    	int populationDim = indivs.size();
    	
    	for (int i = 0; i < populationDim; i++)
    	{
    		temp = doESMutation(indivs.elementAt(i));
    		res.addElement(temp);
    	}
    	
    	return res;
    }
	/**
	 * Perform ES Mutation on the individual. 
	 * No crossover on chromosome of mutation variances 
	 */
    public Individual doESMutation(Individual indiv)
    {
    	Individual res = null;
    	res = new Individual(indiv);
    	res.evaluated = false;
    	Chromosome Chrom = res.genome.elementAt(0);
    	Chromosome Variance = res.getChromAtIndex(1);
    	
    	int chromosomeDim = Chrom.n_Dim;
        
        /* Update variance value */
        double rand = rd.nextGaussian();
        for (int iGene = 0; iGene < chromosomeDim; iGene++)
        {
        	//double randI = rdGen[iGene].nextGaussian();
        	double randI = rd.nextGaussian();
        	double value = Math.exp(b*rand + a*randI);
        	double oldVar = Variance.getGeneAsDouble(iGene);
        	Variance.setGene(iGene, value*oldVar);
        }
        /* Update chromosome value */
    	for (int iGene = 0; iGene < chromosomeDim; iGene++) {
    		//double randI = rdGen[iGene].nextGaussian();
    		double range;
    		double rNewGene = Chrom.getGeneAsDouble(iGene);
    		do {
    			double randI = rd.nextGaussian();
    			range = randI * Variance.getGeneAsDouble(iGene);
    		} while(Utils.g(rNewGene+range, Chrom.uBound[iGene]) ||
					Utils.l(rNewGene+range, Chrom.lBound[iGene]));
    		
    		rNewGene += range;
    		Chrom.setGene(iGene, rNewGene);
    	}
        return res;
    }
}
