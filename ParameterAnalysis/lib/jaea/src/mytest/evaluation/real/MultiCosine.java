package mytest.evaluation.real;
import mytest.evaluation.*;
/**
 * Implementation of MultiCosine function. <p></p>
 * Unimodal, optimal = 0 at x[] = 0 
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class MultiCosine extends FitnessFunction {
	public String getName() {return "MultiCosine";}
	public MultiCosine(long initCount) {
		super(initCount);
		this.defaultRanges[0] = -100;
		this.defaultRanges[1] = 100;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Simple function = sum of cos
	 */
	 public double calculate(double [] inputs)
	{
		double res = 0;
		for (int i = 0; i < inputs.length; i++)
		{
			res += (Math.abs(inputs[i])-10)*Math.cos(2*Math.PI*inputs[i]);
		}
		return res;
	}
}
