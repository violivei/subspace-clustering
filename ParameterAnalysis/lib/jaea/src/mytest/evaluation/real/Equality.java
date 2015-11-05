package mytest.evaluation.real;

import mytest.evaluation.*;
/**
 * Implementation of Equality function. <p></p>
 * Multi-modal, numberous global optimum = 0 at x[i]=x[j]
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Equality extends FitnessFunction {
	public String getName() {return "Equality";}
	
	public Equality(long initCount) {
		super(initCount);
		this.defaultRanges[0] = -100;
		this.defaultRanges[1] = 100;
		// TODO Auto-generated constructor stub
	}

	 public double calculate(double [] inputs)
	{
		double res = 0;
		for (int i = 0; i< inputs.length; i++)
			for (int j = 0; j <= i; j++)
			{
				res += (inputs[i]- inputs[j])*
								(inputs[i]- inputs[j]);
			}
		return res;
	}
}
