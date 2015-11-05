package mytest.evaluation.real;
import mytest.evaluation.*;
/**
 * Implementation of Schwefel benchmark function. <p></p>
 * Schwefel's problem 1.2 - Unimodal
 * Global optimum: f = 0 at x[] = 0
 * @author Le Minh Nghia, NTU-Singapore
 */
public class Schwefel extends FitnessFunction {
	public String getName() {return "Schwefel";}
	public Schwefel(long initCount) {
		super(initCount);
		this.defaultRanges[0] = -Math.PI;
		this.defaultRanges[1] = Math.PI;
	}

	public double calculate(double[] x) {

		double prev_sum, curr_sum, outer_sum;

		curr_sum = x[0];
		outer_sum = (curr_sum * curr_sum);

		for (int i = 1 ; i < x.length ; i ++) {
			prev_sum = curr_sum;
			curr_sum = prev_sum + x[i];
			outer_sum += (curr_sum * curr_sum);
		}

		return (outer_sum);
	}
}
