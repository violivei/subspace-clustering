package mytest.evaluation.real;
import mytest.evaluation.*;
/**
 * Implementation of Rastrigin benchmark function. <p></p>
 * Multimodal - x [-5,5], global - 0 at x[] = 0
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Rastrigin extends FitnessFunction {
	public String getName() {return "Rastrigin";}
	/**
	 * Constructor
	 * @param initCount
	 */
	public Rastrigin(long initCount) {
		super(initCount);
		this.defaultRanges[0] = -5;
		this.defaultRanges[1] = 5;
		// TODO Auto-generated constructor stub
	}

	public double calculate(double [] inputs)
	{
		double res = 10* inputs.length;
		for (int i = 0; i < inputs.length; i++)
			res += inputs[i]*inputs[i] - 
					10* Math.cos(2*Math.PI*inputs[i]);
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

		for(i=0;i<ndim;i++){
			g[ i ] = 2 * tInputs[ i ] - 10 * -Math.sin ( 2 * Math.acos( -1 ) * tInputs[ i ] ) * 2 * Math.acos( -1 );
		}
		/* Increase number of evaluations */
		NUM_EVAL += inputs.length; 
		/* Get the true gradients */
		if (transMatrix != null)
		{
			g = transformX(g, transMatrix);
		}
	}
	public static void main(String []args)
	{
		double [] x = {-1.1622843905003563E-6, -4.718830758481159E-7, 3.0409388397008803E-6};
		Rastrigin f = new Rastrigin(0);
		f.setDefaultRanges(new double[] {-10, 10});
		System.out.println(f.getFitnessFunc(x));
	}
}
