package mytest.evaluation.real;
import mytest.evaluation.*;
/**
 * Implementation of Ackley benchmark function. <p></p>
 * Multi-modal, global optimum = 0 at x[] = 0
 * @author Le Minh Nghia, NTU-Singapore
 */
public class Ackley extends FitnessFunction {

	/**
	 * Constructor
	 * @param initCount
	 */
	public Ackley(long initCount) {
		super(initCount);
		this.defaultRanges[0] = -32;
		this.defaultRanges[1] = 32;
		// TODO Auto-generated constructor stub
	}
	public String getName() {return "Ackley";}
	
	public double calculate(double[] x) {

		double sum1 = 0.0;
		double sum2 = 0.0;

		for (int i = 0 ; i < x.length ; i ++) {
			sum1 += (x[i] * x[i]);
			sum2 += (Math.cos(2*Math.PI*x[i]));
		}

		return (-20.0 * Math.exp(-0.2 * Math.sqrt(sum1 / ((double )x.length))) - 
				Math.exp(sum2 / ((double )x.length)) + 20.0 + Math.E);
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
		int i, ndim = inputs.length;
		double sum1 = 0, sum2 = 0;

		for(i = 0 ; i < ndim; i++){
			sum1 += ( tInputs[ i ] * tInputs[ i ] );
			sum2 += Math.cos( 2 * Math.PI * tInputs[ i ] );
		}

		for(i=0;i<ndim;i++){
			
			if(sum1!=0)
			{
				g[i] = -20*Math.exp( -0.2 * Math.sqrt( sum1/ndim ) ) * ( -0.2 / Math.sqrt( ndim ) ) * ( 1/(2 * Math.sqrt( sum1 ))) * 2 * tInputs[i]
				- Math.exp( sum2 / ndim ) * ( 2 * Math.PI / ndim ) * ( -Math.sin ( 2 * Math.PI * tInputs[i] ) );
			}

			else
			{

				g[i] = - Math.exp( sum2 / ndim ) * ( 2 * Math.PI / ndim ) * ( -Math.sin ( 2 * Math.PI * tInputs[i] ) );
			}
			
		}
		// Increase number of evaluations
		NUM_EVAL += inputs.length; 
		// Get the true gradients 
		if (transMatrix != null)
		{
			g = transformX(g, transMatrix);
		}
	}
	public static void main(String []args)
	{
		double [] x = {-1.1622843905003563E-6, -4.718830758481159E-7, 3.0409388397008803E-6};
		Ackley f = new Ackley(0);
		System.out.println(f.calculate(x));
	}
}
