package mytest.evaluation.real;
import mytest.evaluation.*;
import optimization.tools.MathExt;
/**
 * Implementation of smooth deceptive function which is
 * a composite of 'up' & 'down' sphere function
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class DeceptiveCore extends FitnessFunction{
	public String getName() {return "Deceptive Core";}
	/**
	 * R: range of the valley
	 */
	public double R = 2;
	/**
	 * S: steepness of the valley
	 */
	public double S = 50;
	/**
	 * Constructor
	 * @param initCount
	 */
	public DeceptiveCore(long initCount) {
		super(initCount);
		this.defaultRanges[0] = -10;
		this.defaultRanges[1] = 10;
	}
	
	/**
	 * Negative correlation function
	 * @param x
	 * @return
	 */
	private double g1(double [] x)
	{
		double res = 0;
		for (int i = 0; i< x.length; i++)
		{
			res += x[i]*x[i];
		}
		return res;
	}
	/**
	 * Positive correlation function
	 * @param x
	 * @return
	 */
	private double g2(double [] x)
	{	
		// Offset value
		double C = 50;
		int nDim = x.length;
		double [] x0 = new double[x.length];
		for (int i = 0; i < nDim; i++)
			x0[i] = this.defaultRanges[1];
		
		double res = g1(x0) + C - g1(x);
		return res;
	}
	/**
	 * DeceptiveCore function - Unimodal, composite of g1 & g2 function
	 * Global opt : 0.0 at x[] = 0
	 */
	public double calculate(double [] inputs) 
	{
		double res = 0;
		int ndim = inputs.length;
		double [] xR = new double[ndim];
		for (int i = 0; i < ndim; i++)
			xR[i] = this.R;
		double d = g1(inputs);
		double range = g1(xR);
		double test = 0.5 * (1 + Math.tanh(S*(d-range))*Math.tanh(S*(d+range)));
		res = g2(inputs)*test + (g2(xR)/g1(xR))*g1(inputs)*(1-test);
		return res;
	}
	/**
	 * Override function - Using analytical gradient function instead of approximaton
	 */
	public void getFitnessGrad(double [] inputs, double f, double [] g)
	{
		// Origin coordinates 
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
		// Exact gradient formula
		int ndim = tInputs.length;
		double [] xR = new double[ndim];
		for (int i = 0; i < ndim; i++)
			xR[i] = this.R;
		double d = g1(tInputs);
		double range = g1(xR);
		// Constant use in derivatives of Test(x)
		double temp = MathExt.sech(d+range);
		double dTest = Math.tanh(d-range)*temp*temp;
		temp =  MathExt.sech(d-range);
		dTest += Math.tanh(d+range)*temp*temp;
		// Calculate Test(x)
		double test = 0.5 * (1 + Math.tanh(S*(d-range))*Math.tanh(S*(d+range)));
		// Calculate derivatives
		for (int i = 0; i < tInputs.length; i++)
		{
			g[i] = (-2*tInputs[i]*test + g2(tInputs)*dTest*tInputs[i]) +
			(g2(xR)/g1(xR))*(2*tInputs[i]*(1-test) - g1(tInputs)*dTest*tInputs[i]);
		}
		// Increase number of evaluations 
		NUM_EVAL += inputs.length; 
		// Get the true gradients 
		if (transMatrix != null)
		{
			g = transformX(g, transMatrix);
		}
	}
	 
}
