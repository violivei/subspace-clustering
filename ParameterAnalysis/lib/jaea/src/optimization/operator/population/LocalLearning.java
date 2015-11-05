package optimization.operator.population;

import java.util.Vector;

import optimization.searchspace.Individual;
import optimization.operator.individual.*;

/**
 * Operator to perform individual learning on set/subset of individuals.<p></p>
 * (Population-based Operator)
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class LocalLearning extends OperatorTemplate{
	Selection selector = new Selection();
	IndivSearch learningOperator;
	/**
	 * Set learning procedure used
	 * @param val
	 */
	public void setLearningOpt(IndivSearch val)
	{
		learningOperator = val;
	}
	/**
	 * Number of individuals undergo learning
	 */
	int poolsize;
	public void setPoolSize(int val)
	{
		poolsize = val;
	}
	/**
	 * Scheme to selection individuals that undergo learning
	 */
	int selectionType;
	public void setSelectionType(int val)
	{
		this.selectionType = val;
	}
	/**
	 * Constructor
	 */
	public LocalLearning()
	{
		super();
	}
	
    public Vector<Individual> doProcess(Vector<Individual> indivs){
    	Vector<Individual> res = null;
    	/* Local learning */
        double [] fits = new double[indivs.size()];
        for (int i = 0; i < indivs.size(); i++)
        {
    		fits[i] = indivs.elementAt(i).getFitnessValue();
        }
        // Select individuals for learning
        int [] selectedIndivs;
        switch (selectionType) {
        case Selection.RouletteWheel:
        	Scaling.scaling4selection(fits, Scaling.MINIMIZE);
        	selectedIndivs = selector.RWSelection(fits, poolsize);
        	break;
        case Selection.SUS:
        	Scaling.scaling4selection(fits, Scaling.MINIMIZE);
        	selectedIndivs = selector.SUSelection(fits, poolsize);
        	break;
        case Selection.Tournament:
        	//Scaling.scaling4selection(fits, Scaling.MAXIMIZE);
        	selectedIndivs = selector.TournamentSelection(fits, poolsize);
        	break;
        case Selection.KBest:
        	//Scaling.scaling4selection(fits, Scaling.MAXIMIZE);
        	selectedIndivs = selector.KBestSelection(fits, poolsize);
        	break;
        default:
        	// Copy whole population
        	selectedIndivs = new int[fits.length];
        	for (int i = 0; i < fits.length; i++)
        		selectedIndivs[i] = i;
        	break;
        }
        // Start learning 
        for (int i = 0; i < selectedIndivs.length; i++) {
//        	System.out.print("Before: " 
//            		+ learningOperator.evalFunc.getEvalCount());
        	
        	learningOperator.search(indivs.elementAt(selectedIndivs[i]));
        
//        	System.out.println("/ After: " 
//            		+ learningOperator.evalFunc.getEvalCount());
        }
        res = indivs;
    	return res;
    }
}
