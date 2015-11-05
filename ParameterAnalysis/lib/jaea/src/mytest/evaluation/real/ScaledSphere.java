package mytest.evaluation.real;
import mytest.evaluation.*;
/**
 * Implementation of Scaled Sphere benchmark function. <p></p>
 * Unimodal, Global opt : 0.0 at x[] = 0
 * @author Le Minh Nghia, NTU-Singapore
 */
public class ScaledSphere extends FitnessFunction{
	double coef = 2;
	double [] cache = new double[50];
	public String getName() {return "Scaled Sphere";}
	/**
	 * Constructor
	 * @param initCount
	 */
	public ScaledSphere(long initCount) {
		super(initCount);
		this.defaultRanges[0] = -5;
		this.defaultRanges[1] = 5;
		
		for (int i = 0; i < cache.length; i++)
		{
			cache[i]=1;
		}
		cache[0] = 400;
	}
	
	public double calculate(double [] inputs) 
	{
		double res = 0;
		double temp=1;
		if (inputs.length < 50) {
			// res += x[i]^2
			for (int i = 0; i< inputs.length; i++)
			{
				res += (cache[i])*inputs[i]*inputs[i];
			}
		}
		else {
			// res += 2^i * x[i]^2
			for (int i = 0; i< inputs.length; i++)
			{
				temp = coef * temp;
				res += (temp)*inputs[i]*inputs[i];
			}
		}
		return res;
	}
	
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
		double temp = 1;
		for (int i = 0; i < inputs.length; i++)
		{
			temp = coef * temp;
			g[i] = tInputs[i]*2*(temp);
		}
		/* Increase number of evaluations */
		NUM_EVAL += inputs.length; 
		/* Get the true gradients */
		if (transMatrix != null)
		{
			g = transformX(g, transMatrix);
		}
	}
	
}
