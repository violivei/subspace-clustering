package mytest.evaluation.real;
import mytest.evaluation.*;
/**
 * Implementation of Griewank benchmark function. <p></p> 
 * Multi-modal, global optimum = 0 at x[] = 0
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Griewank extends FitnessFunction {
	public String getName() {return "Griewank";}
	/**
	 * Constructor
	 * @param initCount
	 */
	public Griewank(long initCount) {
		super(initCount);
		this.defaultRanges[0] = -600;
		this.defaultRanges[1] = 600;
		// TODO Auto-generated constructor stub
	}

	public double calculate(double[] x) {

		double sum = 0.0;
		double product = 1.0;

		for (int i = 0 ; i < x.length ; i ++) {
			sum += ((x[i] * x[i]) / 4000.0);
			product *= Math.cos(x[i] / Math.sqrt(i+1));
		}

		return (sum - product + 1.0);
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
		double prod;
		int i,j, ndim = inputs.length;

		for(i=0;i<ndim;i++){
			g[i] = tInputs[i]/2000;
			prod = 1;
			for(j=0;j<i;j++){
				prod *= Math.cos ( tInputs[ j ] / Math.sqrt ( j+1 ) );
			}
			for(j=i+1;j<ndim;j++){
				prod *= Math.cos ( tInputs[j] / Math.sqrt( j+1 ) );
			}
			prod *= ( 1 / Math.sqrt(i+1) ) * -Math.sin ( tInputs[ i ] / Math.sqrt( i+1 ) );
			g[i] = g[i]-prod;
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
		double [] x = {1,2,3,1.432, 4.111,6,9.4};
		Griewank f = new Griewank(0);
		System.out.println(f.calculate(x));
	}
}
