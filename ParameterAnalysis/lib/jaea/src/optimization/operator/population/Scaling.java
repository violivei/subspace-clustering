package optimization.operator.population;

import java.text.DecimalFormat;
import optimization.tools.Utils;
/**
 * Class Scaling generate the probability of being selected of an individual
 * based on its NON-NEGATIVE fitness function by <p></p>
 * 1. Scale (linear scaling) to statisfy some constraints to avoid pre-convergence<p></p>
 * 2. Normalize to get probability, based on MAX or MIN optimization.<p></p>
 * 3. Standard scheme: the HIGHER fitness value a solution has, the BETTER it is<p></p>
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Scaling {
	/**
	 * Scaling scheme for minimization problem (the lower fitness, the better) so that
	 * the higer scaled fitness, the better
	 */
	public static final int MINIMIZE = 0;
	/**
	 * Scaling scheme for maximization problem (the higher fitness, the better)
	 */
	public static final int MAXIMIZE = 1;
	
	/** This tuned value C = 5 worked best for Sphere, Rastrigin, Griewank kind problem
	static private double C = 5;
	*/
	/** This tuned value C = 2 is used in GA-Lib */
	static private double C = 2;
	
	/**
	 * Given fitness f(x), the scaled NON-NEGATIVE function: g=af+b. <p></p>
	 * Use heuristics to obtain values for a and b: <p></p>
	 * 1) maintaining average fitness value before and after scaling g_{avg} = f_{avg}. <p></p>
	 * 2) g_{max}=C * f_{avg} to restrict the fittest candidate to have atmost 2 samples 
	 * in the mating pool by setting C=2. <p></p>
	 * 3) a = f_{avg}(C-1) / (f_{max}-f_{avg}) and b = f_{avg}(1-a).<p></p>
	 * @param v Original fitness vector. Update scaled value directly on v
	 */
    static public void scaling_linear(double [] v)
    {
    	double sum = 0;
    	double vmax = v[0];
    	double vmin = v[0];

    	for(int i=0; i<v.length; i++)
    	{
    		vmax = (Utils.ge(vmax, v[i])) ? vmax : v[i];
    		vmin = (Utils.le(vmin, v[i])) ? vmin : v[i];
    		sum += v[i];
    	}
    	// now we are to make sure that all the value in v is > 0
    	// 1. if all v[i] < 0, flip fitness v over the x-axis 
        if (vmax <= 0)
        {
                for(int j=0; j<v.length; j++) 
                	v[j] = v[j] - vmax - vmin;
                double tmp = vmax;
                vmax = -vmin;
                vmin = -tmp;
                sum = sum + v.length *(vmax + vmin);
        }
        // calculate scaling coefficient
    	double vavg = sum / v.length;
    	double a, b;
    	if (Utils.eq(vavg, vmax)) {
    		a = 1.0;
    		b = 0;
    	}
    	else {
    		// a = f_{avg}(C-1) / (f_{max}-f_{avg}) b = f_{avg}(1-a)
    		a = vavg*(C-1)/(vmax - vavg);
    		b = vavg*(1-a);

	    	if (a*vmin + b < 0) 
	    	{
	    		a = vavg/(vavg-vmin);
	    		b = vavg*(1-a);
	    	}
    	}
    	for(int i=0; i<v.length; i++)
    	{
    		v[i] = v[i]*a + b;
    		if (Utils.l(v[i],0)) v[i] = 0;
    	}
    }
    /**
     * Normalize non-negative fitness (default: the higher, the better) 
     * to probability of being selected: f' = f/(sum(f)/n)
     * @param v Non-negative fitness vectors
     */
    static public void scaling_to_prob(double [] v)
    {
    	double sum = 0;

    	for(int i=0; i<v.length; i++)
    	{
    		sum += v[i];
    	}	
    	double vavg = sum / v.length;
    	
    	// If average fitness = 0
    	if (Utils.eq(vavg, 0))
    	{
    		/* Equal chance*/
    		double temp = 1.0/v.length;
    		for (int i = 0; i< v.length; i++)
    			v[i] = temp;
    	}
    	else
    	{
    		for(int i=0; i<v.length; i++)
    		{
    			v[i] = v[i]/vavg;
    		}
    	}
    }
    /**
     * Scaling non-negative fitnesses v (default: the higher, the better)
     * to standard fitness range [0, 1]
     * @param v
     */
	static public void scaling_01(double [] v)
    {
    	double vmax = v[0];
    	double vmin = v[0];

    	for(int i=0; i<v.length; i++)
    	{
    		vmax = (Utils.ge(vmax, v[i])) ? vmax : v[i];
    		vmin = (Utils.le(vmin, v[i])) ? vmin : v[i];
    	}
    	if (Utils.eq(vmin, vmax))
    	{
    		/* Equal chance*/
    		double temp = 1.0/v.length;
    		for (int i = 0; i< v.length; i++)
    			v[i] = temp;
    	}
    	else
    	{
	    	for(int i=0; i<v.length; i++)
	    	{
	    		v[i] = (v[i]-vmin)/(vmax-vmin);
	    	}
    	}
    }
	/**
	 * Scaling scheme for original problem to the "standard" scheme 
	 * (the higher, the better). For minimization problem, the conversion is:
	 * v' = vmax - v' (negate)
	 * @param v Original fitness vector
	 * @param code Specify original problems is minimize or maximize
	 * @param mode 0 for fitness-proportional scaling, 1 for [0, 1] range
	 */
	static public void scalingSelNegate(double [] v, 
			int code, int mode)
	{
		/* If MINIMIZE problem (the lower, the better), shift fitness value to 
		 * non-negative and the higher, the better 
		 */
		if (code == Scaling.MINIMIZE)
		{
			double vmax = v[0];
			for(int i=0; i<v.length; i++)
	    	{
	    		vmax = (Utils.ge(vmax, v[i])) ? vmax : v[i];
	    	}
			for (int i = 0; i < v.length; i++)
				v[i] = - v[i] + vmax;
		}
		if (mode == 0)
		{
			scaling_to_prob(v);
		}
		else {
			scaling_01(v);
		}
	}
	/**
	 * DEFAULT SCHEME - Scaling scheme for original problem to the "standard" scheme 
	 * (the higher, the better). <p></p> For minimization problem, the conversion is:
	 * v' = (vmax + vmin - v')/(sum) (inverse)
	 * @param v Original fitness vector
	 * @param code Specify original problems is minimize or maximize
	 */
	static public void scalingSelInverse(double [] v, int code)
	{
		double sum = 0;
    	double vmax = -Double.MAX_VALUE;
    	double vmin = Double.MAX_VALUE;

    	/* Linear scale to non-negative values while maintain the original order */
    	scaling_linear(v);
    	/* Create the probability of selecting */
    	for(int i=0; i<v.length; i++)
    	{
    		vmax = (Utils.ge(vmax, v[i])) ? vmax : v[i];
    		vmin = (Utils.le(vmin, v[i])) ? vmin : v[i];
    		sum += v[i];
    	}
    	if (Utils.eq(vmin, vmax))
    	{
    		/* Equal chance*/
    		double temp = 1.0/v.length;
    		for (int i = 0; i< v.length; i++)
    			v[i] = temp;
    	}
    	else {
    		// if MAXIMIZATION, it already conforms to "the higher, the better"
    		if (code == Scaling.MAXIMIZE) 
    		{
    			for (int i = 0; i < v.length; i++)
    				v[i] = v[i]/sum;
    		}
    		// if MINIMIZATION fitness, then inverse fitness v' = (vmax + vmin - v')/(sum)
    		// so that the higher, the better is maintained.
    		else {
    			double temp = -sum + v.length* vmax + v.length*vmin;
    			for (int i = 0; i < v.length; i++)
    				v[i] = (-v[i] + vmax + vmin)/temp;
    		}
    	}
	}
   /**
    * Scaling scheme on original fitness for selection purpose with 
    * the "standard" scheme (the higher, the better). 
    * @param v
    * @param code
    */
    static public void scaling4selection(double [] v, int code)
    {
    	// mode: 0 - inverse, 1 - negate for minimization
    	boolean mode = true;
    	// default is inverse mode or fitness proportional scale 
    	if (mode) {
    		scalingSelInverse(v, code);
    	}
    	else
    		// 0: prob scale, 1: 0-1 scale
    		scalingSelNegate(v, code,1);
    }
    static public void main(String [] argv)
    {
    	double [] v = {1, 5, 6, 8,10, 3, 2.15, 4};
    	System.out.println("Current value");
    	for (int i = 0; i < v.length; i++)
    		System.out.print(v[i] + " ");
    	System.out.println();
    	
    	scaling4selection(v, Scaling.MINIMIZE);
    	DecimalFormat df1 = new DecimalFormat("####.000");
    	System.out.println("Value for minimize");
    	for (int i = 0; i < v.length; i++)
    		System.out.print(df1.format(v[i]) + " ");
    	System.out.println();
    }
}
