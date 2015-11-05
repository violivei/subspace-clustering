package mytest.evaluation.real;
import mytest.evaluation.*;
/**
 * Implementation of RastriginNonCont benchmark function. 
 * @author Le Minh Nghia, NTU-Singapore
 */
public class RastriginNonCont extends FitnessFunction {
	public String getName() {return "Rastrigin Non Continuous";}
	/**
	 * Constructor
	 * @param initCount
	 */
	public RastriginNonCont(long initCount) {
		super(initCount);
		this.defaultRanges[0] = -5;
		this.defaultRanges[1] = 5;
		// TODO Auto-generated constructor stub
	}

	 public double calculate(double[] x) {

		double sum = 0.0;
		double currX;

		for (int i = 0 ; i < x.length ; i ++) {
			currX = myXRound(x[i]);
			sum += (currX * currX) - (10.0 * Math.cos(2*Math.PI * currX)) + 10.0;
		}

		return (sum);
	}
}
