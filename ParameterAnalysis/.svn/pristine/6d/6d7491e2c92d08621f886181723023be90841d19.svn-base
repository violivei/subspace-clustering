package optimization.operator.population;
import java.util.Vector;

import optimization.searchspace.Individual;
import mytest.evaluation.FitnessFunction;

/**
 * Operator to invoke fitness function to evaluate solutions. <p></p>
 * (Population-based Operator)
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Evaluation extends OperatorTemplate{
	FitnessFunction fc = null;
	/**
	 * Constructor
	 */
	public Evaluation()
	{
		super();
	}
	/**
	 * Set fitness function to be used
	 * @param f Fitness function
	 */
	public void setFitnessFunc(FitnessFunction f)
	{
		fc = f;
	}
	public FitnessFunction getFitnessFunc()
	{
		return fc;
	}
	/**
	 * Implement of abstract function
	 * @param indivs Individuals to be evaluated
	 * @return Individuals with fitness assigned
	 */
    public Vector<Individual> doProcess(Vector<Individual> indivs){
    	Vector<Individual> res = null;
    	try {
    		for (int i = 0; i < indivs.size(); i++ )
    			fc.evaluate(indivs.elementAt(i));
    		res = indivs;
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
    	return res;
    }
}
