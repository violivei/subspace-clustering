package optimization.tools;
/**
 * Math Functions (Extra)
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class MathExt {
	public static final double EPS64 = 2.2204E-016;
	/**
	 * Hyperbolic Secant
	 */
	static public double sech(double x)
	{
		double res = 0;
		res = 1/Math.cosh(x);
		return res;
	}
	/**
	 * Norm(x) = sum(x_{i}^2): distance to the origin
	 */
	static public double norm(double [] input)
	{
		double l = 0;
		for (int k = 0; k < input.length; k++)
			l += input[k]*input[k];
		l = Math.sqrt(l);
		return l;
	}
	/**
	 * Euclidean distance between point x and y
	 */
	static public double distance(double [] x, double [] y)
	{
		 double res = 0;
		 if (x.length != y.length) return Double.MIN_VALUE;
		 for (int i = 0;  i < x.length; i++)
		 {
			 double temp = x[i] - y[i]; 
			 res += temp * temp;
		 }
		 return Math.sqrt(res);
	}
}
