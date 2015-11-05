package mytest.evaluation.real;
import mytest.evaluation.*;
/**
 * Implementation of Elliptic function.<p></p>
 * Elliptic - Unimodal. Global optimum f = 0 at x[] = 0
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Elliptic extends FitnessFunction {
	public String getName() {return "Elliptic";}
	/**
	 * Constructor
	 * @param initCount
	 */
	public Elliptic(long initCount) {
		super(initCount);
		this.defaultRanges[0] = -100;
		this.defaultRanges[1] = 100;
	}
	
	public double calculate(double[] x) {

		double sum = 0.0;
		double a = 1e6; // 10^6

		for (int i = 0 ; i < x.length ; i ++) {
			sum += Math.pow(a, (((double )i)/((double )(x.length-1)))) * x[i] * x[i];
		}

		return (sum);
	}
}
