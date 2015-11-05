package mytest.evaluation.real;
import mytest.evaluation.*;
/**
 * Implementation of Weierstrass benchmark function. <p></p>
 * Multi-modal, global optimum = 0 at x[] = 0
 * @author Le Minh Nghia, NTU-Singapore
 */
public class Weierstrass extends FitnessFunction {
	public String getName() {return "Weierstrass";}
	double [] pa = new double[100];
	double [] pb = new double[100];
	double initsum;
	double a;
	double b;
	double pi;
	double k;
	/**
	 * Constructor
	 * @param initCount
	 */
	public Weierstrass(long initCount) {
		super(initCount);
		this.defaultRanges[0] = -0.5;
		this.defaultRanges[1] = 0.5;
		// TODO Auto-generated constructor stub
		init();
	}
	public static void main(String [] args)
	{
		double [] x = {1, 2.1, 1.3, 1.4, 5};
		double [] v = {-10, 10};
		FitnessFunction fc = new Weierstrass(0);
		fc.setDefaultRanges(v);
		double currFit = fc.getFitnessFunc(x);
		System.out.println(x[0] +" " + x[1] + "-> " + currFit );
	}
	
	public double calculate(double[] x) {
		//return (weierstrass(x, 0.5, 3.0, 20));
		return (evaluate_(x));
	}
	private void init()
	{
		a = 0.5;
		b = 3;
		k = 20;
		initsum = 0;
		pi = Math.PI;

		for( int j = 0; j <= k; j++ ) {
	        	pa[j] =  Math.pow( a, j );
			pb[j] = Math.pow( b, j );
			initsum += pa[j] * Math.cos(pi*pb[j]);
	    }
	}

	private double evaluate_( double []x )
	{
		int i, j;
		double sum1;
		int ndim = x.length;
		sum1 = 0;
		double sum2 = initsum* ndim;
		for( i = 0; i < ndim; i++ ) {
			for( j = 0; j <= k; j++ ) {
				sum1 += (  pa[j] * Math.cos( 2 * pi * pb[j] * (x[ i ] + 0.5) ) );
			}
		}

		return sum1 - sum2;
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
		int i, ndim = inputs.length,j;	

		for ( i = 0 ; i < ndim ; i++ ){
			
			double sum = 0;

			for ( j = 0 ; j<= k; j++ ){

				sum += Math.pow ( a , j ) * -Math.sin ( 2 * Math.acos( -1 ) * Math.pow ( b, j) * ( tInputs[ i ] + 0.5 ) ) 
					* 2 * Math.acos( - 1 ) * Math.pow ( b , j );
			}

			g [ i ] = sum;
		}
		/* Increase number of evaluations */
		NUM_EVAL += inputs.length; 
		/* Get the true gradients */
		if (transMatrix != null)
		{
			g = transformX(g, transMatrix);
		}
	}

/** Old code of Weierstrass */
//	public double weierstrass(double[] x, double a, double b, int Kmax) {
//
//		double sum1 = 0.0;
//		for (int i = 0 ; i < x.length ; i ++) {
//			for (int k = 0 ; k <= Kmax ; k ++) {
//				sum1 += Math.pow(a, k) * Math.cos(2*Math.PI * Math.pow(b, k) * (x[i] + 0.5));
//			}
//		}
//
//		double sum2 = 0.0;
//		for (int k = 0 ; k <= Kmax ; k ++) {
//			sum2 += Math.pow(a, k) * Math.cos(2*Math.PI * Math.pow(b, k) * (0.5));
//		}
//
//		return (sum1 - sum2*((double )(x.length)));
//	}
}
