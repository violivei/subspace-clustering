package mytest.evaluation.real;
import mytest.evaluation.*;
/**
 * Implementation of Sphere benchmark function. <p></p>
 * Unimodal - Global opt : 0.0 at x[] = 0
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Sphere extends FitnessFunction{
	public String getName() {return "Sphere";}
	public Sphere(long initCount) {
		super(initCount);
		this.defaultRanges[0] = -100;
		this.defaultRanges[1] = 100;
	}
	
	public double calculate(double [] inputs) 
	{
		double res = 0;
		for (int i = 0; i< inputs.length; i++)
		{
			res += inputs[i]*inputs[i];
		}
		return res;
	}
	/**
	 * Override function - Using analytical gradient function instead of approximaton
	 */
	public void getFitnessGrad(double [] inputs, double f, double [] g)
	{
		/* Origin coordinates */
		double [] sInputs;
		if (shiftVector != null)
			sInputs = shiftX(inputs, shiftVector);
		else 
			sInputs = inputs;
		
		double [] tInputs;
		if (transMatrix != null)
			tInputs = transformX(sInputs, transMatrix);
		else
			tInputs = sInputs;
		/* Exact gradient formula */
		for (int i = 0; i < inputs.length; i++)
			g[i] = tInputs[i]*2;
		/* Increase number of evaluations */
		NUM_EVAL += inputs.length; 
		/* Get the true gradients */
		if (transMatrix != null)
		{
			g = transformX(g, transMatrix);
		}
	}
	
}
