package mytest.evaluation.real;
import mytest.evaluation.*;
/**
 * Implementation of Expanded Scaffer's F6 benchmark function <p></p>
 * Global = 0 at x[] = 0
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class ExpandedScaffer extends FitnessFunction {
	public String getName() {return "Expanded Scaffer";}
	/**
	 * Constructor
	 * @param initCount
	 */
	public ExpandedScaffer(long initCount) {
		super(initCount);
		this.defaultRanges[0] = -100;
		this.defaultRanges[1] = 100;
		// TODO Auto-generated constructor stub
	}

	 public double calculate(double[] x) {

		double sum = 0.0;

		for (int i = 1 ; i < x.length ; i ++) {
			sum += ScafferF6(x[i-1], x[i]);
		}
		sum += ScafferF6(x[x.length-1], x[0]);

		return (sum);
	}
	
	 /**
	  * Core function of ScafferF6 
	  * @param x Scalar value x
	  * @param y Scalar value y
	  * @return Fitness contribution 
	  */
	 public double ScafferF6(double x, double y) {
		double temp1 = x*x + y*y;
		double temp2 = Math.sin(Math.sqrt(temp1));
		double temp3 = 1.0 + 0.001 * temp1;
		return (0.5 + ((temp2 * temp2 - 0.5)/(temp3 * temp3)));
	}

	/**
	 * Non-Continuous Expanded Scaffer's F6 function
	 * @param x Vector of decision variables x[]
	 */
	 public double EScafferF6NonCont(double[] x) {

		double sum = 0.0;
		double prevX, currX;

		currX = myXRound(x[0]);
		for (int i = 1 ; i < x.length ; i ++) {
			prevX = currX;
			currX = myXRound(x[i]);
			sum += ScafferF6(prevX, currX);
		}
		prevX = currX;
		currX = myXRound(x[0]);
		sum += ScafferF6(prevX, currX);

		return (sum);
	}
}
