package optimization.search;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import mytest.evaluation.FitnessFunction;
import optimization.operator.individual.*;
import optimization.operator.population.Crossover;
import optimization.operator.population.ESMutation;
import optimization.operator.population.Evaluation;
import optimization.operator.population.LocalLearning;
import optimization.operator.population.Merging;
import optimization.operator.population.Mutation;
import optimization.operator.population.OperatorTemplate;
import optimization.operator.population.Performance;
import optimization.operator.population.Selection;
import optimization.operator.population.SwarmMove;
import optimization.searchspace.Chromosome;
import optimization.searchspace.Individual;
import optimization.searchspace.Population;
import runtime.ConfigContainer;
import runtime.operator.CrossoverOption;
import runtime.operator.LocalLearnOption;
import runtime.operator.MergeOption;
import runtime.operator.MutationOption;
import runtime.operator.Operator;
import runtime.operator.SelectionOption;
import runtime.operator.SwarmOption;
/**
 * Search strategy - default designed for MINIMIZATION problem
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class SimpleEA extends Search {
	@SuppressWarnings("unchecked")
	/**
	 * Work-flow list of population-based search operators
	 */
	ArrayList operators = new ArrayList();
	/**
	 * Parametric & runtime configuration of the search 
	 */
	public ConfigContainer profile = null;
	/**
	 * TRUE to print the search trace. FALSE if otherwise
	 */
	private boolean debug = true;
	/**
	 * Variable to control recording: best_found, current_found, evaluations
	 * per generation to memory
	 */
	private boolean keepGenRecord = false;
	/**
	 * Setting to debug mode
	 * @param val
	 */
	public void setDebugMode(boolean val) {
		debug = val;
	}
	/**
	 * Constructors
	 * @param config
	 * @param fc
	 */
	public SimpleEA(ConfigContainer config, FitnessFunction fc) 
	{
		super(fc);
		profile = config;
	}
	@SuppressWarnings("unchecked")
	/**
	 * Initiate the operators in the work-flow list of the search
	 */
	public void initOperators()
	{
		operators.clear();
		ArrayList template = (ArrayList) profile.method.operators;
		for (int i = 0; i < template.size(); i++)
		{
			Operator opt = (Operator)template.get(i);
			System.out.println(opt.Name);
			if (opt.Name.compareTo("Crossover")==0)
			{
				Crossover crossover = new Crossover();
				CrossoverOption option = (CrossoverOption) opt;
				crossover.setXType(option.crossoverType);
				crossover.setXProb(option.crossoverProb);
				crossover.setPoolSize(profile.method.populationDim);
				operators.add(crossover);
			}
			else if (opt.Name.compareTo("Mutation")== 0)
			{
				Mutation mutation = new Mutation();
				MutationOption option = (MutationOption) opt;
				mutation.setMutProb(option.mutationProb);
				mutation.setMutType(option.mutationType);
				mutation.setMutRadius(option.mutationRadius);
				operators.add(mutation);
			}
			else if (opt.Name.compareTo("Evaluation")==0)
			{
				Evaluation evaluation = new Evaluation();
				evaluation.setFitnessFunc(evalFunc);
				operators.add(evaluation);
			}
			else if (opt.Name.compareTo("Selection")==0)
			{
				Selection selector = new Selection();				
				SelectionOption option = (SelectionOption) opt;
				selector.setType(option.selectionType);
				if (option.poolsize < 0)
					// default setting
					selector.setPoolSize(profile.method.populationDim);
				else
					selector.setPoolSize(option.poolsize);
				operators.add(selector);
			}	
			else if (opt.Name.compareTo("Merging")==0)
			{
				Merging merger = new Merging();
				MergeOption option = (MergeOption) opt;
				merger.setType(option.selectionType);
				if (option.poolsize < 0)
					// default setting
					merger.setPoolSize(profile.method.populationDim);
				else
					merger.setPoolSize(option.poolsize);
				operators.add(merger);
			}
			else if (opt.Name.compareTo("ES Mutation")==0)
			{
				ESMutation mutation = new ESMutation(profile.method.chromosomeDim);
				operators.add(mutation);
			}
			else if (opt.Name.compareTo("SwarmMove")==0)
			{
				SwarmMove swarm = new SwarmMove();
				SwarmOption option = (SwarmOption) opt;
				if (option.inertia > 0)
					swarm.setInertia(option.inertia);
				if (option.neighborLearnRate > 0)
					swarm.setNeighborLearnRate(option.neighborLearnRate);
				if (option.localLearnRate > 0)
					swarm.setLocalLearnRate(option.localLearnRate);
				
				// Set up prelimChrom used by PSO
				swarm.pBest.clear();
				swarm.velocity.clear();
				for (int k = 0; k < profile.method.populationDim; k++) {
		    		Vector<Chromosome> temp = new Vector<Chromosome>();
		    		temp.addElement(new Chromosome(Chromosome.FLOAT, profile.method.chromosomeDim, false));
		    		
		    		Individual tempInd = new Individual(temp);
		    		tempInd.setFitnessValue(Double.MAX_VALUE);
		    		swarm.pBest.addElement(tempInd);
		    		Individual velocityVector = new Individual(temp);
		    		swarm.velocity.addElement(velocityVector);
		    	}
				operators.add(swarm);
			}
			else if (opt.Name.compareTo("Local Learning")==0)
			{
				LocalLearning teacher = new LocalLearning();
				LocalLearnOption option = (LocalLearnOption) opt;
				// set poolsize
				if (option.poolsize < 0)
					teacher.setPoolSize(profile.method.populationDim);
				else 
					teacher.setPoolSize(option.poolsize);
				// set selection
				teacher.setSelectionType(option.selectionType);
				// set local learning
				String learningScheme = option.learningScheme;
				IndivSearch iSearch = null;
				if (learningScheme.compareTo("DFP")==0)
						iSearch = new DFP(evalFunc,option.intensity);
				else if (learningScheme.compareTo("RMHC")==0)
					iSearch = new RMHC(evalFunc, option.intensity);
				else if (learningScheme.compareTo("DSCG")==0)
					iSearch = new DSCG(evalFunc, option.intensity);
				// Set stepsize and accuracy
				if (iSearch != null) {
					iSearch.ACC = option.accuracy;
					iSearch.StepSize = option.stepsize;
				}
				teacher.setLearningOpt(iSearch);
				operators.add(teacher);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	/**
	 * Start one complete EA search
	 * @param pop Initial population (haven't evaluated fitness)
	 */
	public void search(Population pop) 
	{
		int iGen;
        double best_so_far = Double.MAX_VALUE;
        Individual bestSol = null;
        
        System.out.println("Search by " + profile.method.searchMethod + "...");
        this.initOperators();
        // init Performance Record
        pop.measure = new Performance(profile.runtime.maxGenerations, keepGenRecord);
        
        System.out.println("EA start time: " + new Date().toString());

        iGen = -1;
        boolean stopCond = false;
        double currBest; 

        
        evalFunc.evaluate(pop);
        pop.computeRankings(); // since chromosomes are just copied, the fitness are available
        
        /* USE if DELTA_CLOSE stopping condition is used 
        double initBest = pop.getFittestIndividualsFitness();
        */
        
        /* Start iterations */
        while (!stopCond)
        {	    
        	iGen++;
        	if (profile.runtime.writePop2File)
        		pop.toFile(profile.runtime.savedDir + "/p"+iGen+".txt");
        	
        	currBest = pop.getFittestIndividualsFitness();
        	/*   */
        	if (this.debug) {
        		System.out.println(iGen+ " generation: " + evalFunc.getEvalCount() 
    								+ "\t" + currBest);
        	}
    		
        	if (currBest <  best_so_far) {
        		best_so_far = currBest;
        		bestSol = new Individual(pop.getFittestIndividual());
        	}
        	
        	// record
        	pop.measure.setBestFound(best_so_far);
        	pop.measure.maxGens++;
        	if (keepGenRecord)
        	{
	        	pop.measure.bestFound[iGen] = best_so_far;
	        	pop.measure.currentBestFound[iGen] = currBest;
	        	pop.measure.evals[iGen] = evalFunc.getEvalCount();
        	}
        	
        	Vector<Individual> temp = pop.individuals;
        	/* Run the pipeline/workflow */
        	ArrayList template = (ArrayList) profile.method.operators;
			for (int i = 0; i < template.size(); i++)
			{
				Operator opt = (Operator) template.get(i);
				if (opt.Name.compareTo("Merging")==0)
				{
					Merging merger = (Merging) operators.get(i);
					merger.setParents(pop.individuals);
				}
				else if (opt.Name.compareTo("SwarmMove")== 0)
				{
					SwarmMove swarm = (SwarmMove) operators.get(i);
					swarm.nBest = new Individual(pop.getFittestIndividual());
				}
				temp = ((OperatorTemplate) operators.get(i)).doProcess(temp);
				//System.out.println("Temp size: " + temp.size());
			}
        	
            pop.individuals = temp;
            pop.computeRankings();
            
            /* Check stop condition */
            //if ((evalFunc.delta_close(currBest, initBest)
            /* Uncomment to enable DELTA_CLOSE - stopping condition 
            if (evalFunc.delta_close(best_so_far, initBest, profile.runtime.globalOptimum)
        			< profile.runtime.deltaThreshold)
        	{
        		stopCond = true;
				System.out.println("Delta-closed reached at " + iGen + " generation...");
				System.out.println("Stop running!");
				break;
        	}
        	*/
            /* Stopping condition by limited number of fitness evaluations */
            if ((profile.runtime.maxEvaluation > 0) &&
            		(evalFunc.getEvalCount() > profile.runtime.maxEvaluation))
            {
            	stopCond = true;
            	System.out.println("Max evaluation reached at " + iGen + " generation...");
            	System.out.println("Stop running!");
            	break;
            }
            /* Stopping condition by fitness threshold (MINIMIZATION problem)*/
            if (best_so_far <= profile.runtime.fitThreshold)
            {
            	stopCond = true;
            	System.out.println("Below fitness threshold. Solution found...");
            	System.out.println("Stop running!");
            	break;
            }
            /* Stopping condition by limited number of generations/iterations */
        	if (iGen >= (profile.runtime.maxGenerations -1)) 
			{
    			System.out.println("Use up all iterations at " + iGen + " generation...");
				System.out.println("Stop running!");
    			stopCond = true;	
			}
            
        }
        
        // computeFitnessRankings();
        if (profile.runtime.writePop2File)
    		pop.toFile(profile.runtime.savedDir + "/p"+(iGen+1)+".txt");
        Individual curBestIndividual = pop.individuals.elementAt(pop.bestFitnessChromIndex);
        
		if (curBestIndividual.getFitnessValue() < best_so_far) {
			best_so_far = curBestIndividual.getFitnessValue();
			bestSol.copyIndividual(curBestIndividual);
		}
		pop.bestpool.add(bestSol);
    	System.out.println("Best-found solutions: ");
    	for (int i = 0; i < pop.bestpool.size(); i++)
    	{
    		System.out.println(i+ " solution :" + pop.bestpool.elementAt(i).toString());
			System.out.println("Fittest value: " 
					+ pop.bestpool.elementAt(i).getFitnessValue());
    	}
        
    	// record last item
    	pop.measure.setBestFound(best_so_far);
        pop.measure.maxGens++;
    	if (this.keepGenRecord)
    	{
	        pop.measure.bestFound[iGen+1] = best_so_far;
	        pop.measure.currentBestFound[iGen+1] = curBestIndividual.getFitnessValue();
	        pop.measure.evals[iGen+1] = evalFunc.getEvalCount();
    	}
    	// measure.toFile("gaPerf.txt");
        System.out.println("GA end time: " + new Date().toString());
	}
	
	public String toString() 
	{
		String res = "";
		res = profile.method.toString();
		return res;
	}
}
