package mytest.evaluation.real;
import mytest.evaluation.*;
/**
 * Implementation of HybridF8F2 benchmark function.
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class HybridF8F2 extends FitnessFunction {
	public String getName() {return "HybridF8F2";}
	/** 
	 * Constructor
	 * @param initCount
	 */
	public HybridF8F2(long initCount) {
		super(initCount);
		this.defaultRanges[0] = -3;
		this.defaultRanges[1] = 1;
		
		// TODO Auto-generated constructor stub
	}

	public double calculate(double[] x) 
	{

		double sum = 0.0;

		for (int i = 1 ; i < x.length ; i ++) {
			sum += F8(F2(x[i-1], x[i]));
		}
		sum += F8(F2(x[x.length-1], x[0]));

		return (sum);
	}
		
	/**
	 * F2: Rosenbrock's Function -- 2D version
	 */
	public double F2(double x, double y) 
	{
			double temp1 = (x * x) - y;
			double temp2 = x - 1.0;
			return ((100.0 * temp1 * temp1) + (temp2 * temp2));
	}

	/**
	 * F8: Griewank's Function -- 1D version
	 */
	public double F8(double x) {
			return (((x * x) / 4000.0) - Math.cos(x) + 1.0);
	}
}
