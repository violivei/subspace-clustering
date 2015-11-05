package mytest.evaluation.real;

import mytest.evaluation.FitnessFunction;


/**
 * Implementation of deceptive function with local valley models similar to Rastrigin function. <p></p>
 * Multimodal - x [-5,5], global - 0 at x[] = 0
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class DeceptiveRastrigin extends FitnessFunction{
	public String getName() {return "Deceptive Rastrigin";}
	private DeceptiveCore core;
	/**
	 * Constructor
	 * @param initCount
	 */
	public DeceptiveRastrigin(long initCount) {
		super(initCount);
		core = new DeceptiveCore(initCount);
		this.defaultRanges[0] = -10;
		this.defaultRanges[1] = 10;
		// TODO Auto-generated constructor stub
	}

	public double calculate(double [] inputs)
	{
		double res = 10* inputs.length;
		res += core.calculate(inputs);
		for (int i = 0; i < inputs.length; i++)
			res += - 10* Math.cos(2*Math.PI*inputs[i]);
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
		int i, ndim = inputs.length;	
		core.getFitnessGrad(tInputs, f, g);
		for(i=0;i<ndim;i++){
			g[ i ] += - 10 * -Math.sin ( 2 * Math.acos( -1 ) * tInputs[ i ] ) * 2 * Math.acos( -1 );
		}
		// Increase number of evaluations 
		NUM_EVAL += inputs.length;
		/* Get the true gradients */
		if (transMatrix != null)
		{
			g = transformX(g, transMatrix);
		}
	}
}
