package mytest.evaluation.real;

import mytest.evaluation.*;
/**
 * Implementation of Rosenbrock benchmark function. <p></p>
 * Multimodal, Global optimal f= 0 at x[] = 1
 * @author Le Minh Nghia, NTU-Singapore
 */
public class Rosenbrock extends FitnessFunction {
	public String getName() {return "Rosenbrock";}
	public Rosenbrock(long initCount) {
		super(initCount);
		/* Suganthan's paper   
		this.defaultRanges[0] = -100;
		this.defaultRanges[1] = 100;
		*/
		this.defaultRanges[0] = -2.048;
		this.defaultRanges[1] = 2.048;
		/* Orthogonal paper 
		this.defaultRanges[0] = -5;
		this.defaultRanges[1] = 10;
		*/
		
		// TODO Auto-generated constructor stub
	}

	public double calculate(double[] x) {

		double sum = 0.0;
		double [] v = new double[x.length];
		for (int i = 0; i < x.length; i++) v[i] = x[i] + 1;
		for (int i = 0 ; i < (x.length-1) ; i ++) {
			double temp1 = (v[i] * v[i]) - v[i+1];
			double temp2 = v[i] - 1.0;
			sum += (100.0 * temp1 * temp1) + (temp2 * temp2);
		}

		return (sum);
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
		double [] _x = new double[ndim];

		for( i=0; i<ndim; i++) _x[i] = tInputs[i] + 1;

		g [ 0 ] = 100 * 2 * ( _x [ 0 ] * _x [ 0 ]  - _x [ 1 ] ) * 2 * _x [ 0 ] + 2 * ( _x [ 0 ] - 1);
		g [ ndim - 1 ] = 100 * 2 * ( _x [ndim - 2] * _x [ ndim - 2 ] - _x [ ndim - 1 ] ) * -1;
		for(i=1;i<=ndim-2;i++){
			g [ i ] = 100 * 2 * ( _x [ i - 1 ] * _x [ i - 1 ] - _x [ i ]) * -1
				+ 100 * 2 * ( _x [ i ] * _x [ i ] - _x [ i + 1 ] ) * 2 * _x [ i ] + 2 * ( _x [ i ] - 1);
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
