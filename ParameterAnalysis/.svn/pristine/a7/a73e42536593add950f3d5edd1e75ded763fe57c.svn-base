package optimization.operator.individual;

import java.util.Vector;

import mytest.evaluation.FitnessFunction;
import mytest.evaluation.real.*;
import optimization.searchspace.Individual;
import optimization.tools.*;
/**
 * Implementation of Davies, Swann, and Campey 
 * with Gram-Schmidt orthogonalization (DSCG) search
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class DSCG extends IndivSearch {
	public final String name = "DSCG";
	public boolean debug = false;
	/**
	 * Constructor
	 * @param fc Fitness function
	 */
	public DSCG(FitnessFunction fc) {
		super(fc);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Constructor
	 * @param fc Fitness function
	 * @param maxeval Maxinum number of fitness evaluations
	 */
	public DSCG(FitnessFunction fc, int maxeval) {
		super(fc, maxeval);
	}
	
	public double search(double[] xInit, double y)
	{
		double [] xTemp = new double [xInit.length]; 
		double [] yTemp = new double [1];
		
		if (!this.StopIfConverge) 
		{
			if (debug) {
				System.out.println("DSCG at budget: " + this.evalMax + " evaluations");
			}
			double s = this.StepSize;
			this.move(xInit, y, s, 
					this.evalFunc, this.evalMax, 
					this.ACC, xTemp, yTemp);
		}
		else 
		{	
			if (debug) {
				System.out.println("DSCG runs until convergence");
			}
//			Strict convergence condition for finding local optimum 1.0E-8 or 1.0E-7
//			double accuracy = 1E-8;
//			Convergence condition in runtime for less wasted evaluations
//			double accuracy = 1E-4;
			this.evalMax = Integer.MAX_VALUE;
			double s = this.StepSize;
			this.move(xInit, y, s, 
					this.evalFunc, this.evalMax, 
					this.CONV_ACC, xTemp, yTemp);
			/* Converge by phenotype 
			double [] xT = new double[xInit.length];
			double yT;
			// Copy xTemp
			for (int i = 0; i < xInit.length; i++)
				xT[i] = xInit[i];
			yT = y;
			int count = 0;
			do {
				double s = DSCG.StepSize;
				this.move(xT, yT, s, 
						this.evalFunc, 100000, 
						DSCG.ACC, xTemp, yTemp);
				double distance = MathExt.distance(xT, xTemp);
				Matrix.copyArray(xT, xTemp);
				yT = yTemp[0];
				if (distance >= 1.0E-8)
				//if (distance >= 1.0E-7)
				{
					System.out.println("Improved distance: " + distance);
					count = 0;
				}
				else
				{
					System.out.println("Converged distance: " + distance);
					count++;
				}
			}
			while (count < 10);
			*/
		}
		Matrix.copyArray(xInit, xTemp);
		return yTemp[0];	
	}
	
	public void search(Individual indiv) 
	{
		double [] xInit = indiv.getChromAtIndex(0).getDoubleArray();
		double yInit = indiv.getFitnessValue();
		double fit = search(xInit, yInit);
		for (int i = 0; i < xInit.length; i++)
			indiv.getChromAtIndex(0).setGene(i, xInit[i]);
		indiv.setFitnessValue(fit); 
	}
	/**
	 * Move operator in DSCG, including of line search, coordinate rotation & quadratic search
	 * @param x Starting location
	 * @param y Fitness of starting location
	 * @param s Initial step size
	 * @param f Fitness function
	 * @param maxevals Maximum number of function evaluations allowed
	 * @param epsilon Accuracy required or Stopping condition when stepsize < epsilon
	 * @param optx Resultant location after learning 
	 * @param opty Resultant fitness after learning
	 */
	public void move(double [] x,
							double y,
							double s,
							FitnessFunction f,
							int maxevals,
							double epsilon,
							double [] optx,
							double [] opty)
	{
		int n = x.length;
		Matrix v = new Matrix(n,n,Matrix.M_DIAG);
		double [] d = new double[n];
		for (int i = 0; i < n; i++) d[i] = 0;
		// Initial point of each iteration
		double [] x0 = new double[n];
		Matrix.copyArray(x0, x);
		
		Matrix.copyArray(optx, x);
		opty[0] = y;
		double [] xtemp = new double[n];
		double [] ytemp = new double[1];
		double [] dtemp = new double[1];
		int startDirection = 0;
		int i;
		long startEval = f.getEvalCount();
		
		while (s >= epsilon)
		{
			
			for (i = startDirection; i < n; i++)
			{
				if (debug) {
					System.out.println();
					System.out.println("Step length:" + s);
				}
				// Need to update max evaluation 
				linesearch.search(optx, opty[0], 
						s, v.getFlatCol(i), f, xtemp, ytemp, dtemp);
				if (debug) {
					System.out.print("Starting P: " + (new Matrix(optx)).toString());
					System.out.print("Ending P: " + (new Matrix(xtemp)).toString());
				}
				
				Matrix.copyArray(optx, xtemp);
				opty[0] = ytemp[0];
				d[i] = dtemp[0];
				
				if (debug)
					System.out.println("d[" + i + "] = " + d[i]);
				if (f.getEvalCount() >= startEval + maxevals) 
					return;
				
			}
			
			double [] z = new double[n];
			for (int count = 0; count < n; count++)
				z[count] = optx[count] - x0[count];
			double l = MathExt.norm(z);
			
			if (l >= MathExt.EPS64)
			{
				double [] xt = new double[n];
				double [] yt = new double[1];
				double [] dt = new double[1];
				
				double [] zTemp = new double[n];
				for (int count = 0; count < n; count++)
					zTemp[count] = z[count]/l;
				// Update max evaluation
				if (debug) {
					System.out.println();
					System.out.println("Step length:" + s);
				}
				linesearch.search(optx, opty[0], s, zTemp, f, xt, yt, dt);
				if (debug) {
					System.out.print("Starting P: " + (new Matrix(optx)).toString());
					System.out.print("Ending P: " + (new Matrix(xt)).toString());
				}
				if (debug)
					System.out.println("dt = " + dt[0]);
				// Check max evaluation
				if (f.getEvalCount() >= startEval + maxevals)
				{
					Matrix.copyArray(optx, xt);
					opty[0] = yt[0];
					return;
				}
				for (int count = 0; count < n; count++)
					z[count] = xt[count] - x0[count];
				l = MathExt.norm(z);
				// Check appropriateness of step length
				if (l >= s) 
				{
					Vector<Integer> p = find(d, epsilon);
					Vector<Integer> diffset = new Vector<Integer>();
					for (int count = 0; count < d.length; count++)
						diffset.addElement(new Integer(count));
					for (int count = 0; count < p.size(); count++)
						diffset.removeElement(p.elementAt(count));
					int lengthP = p.size();
					if (debug)
						System.out.println("Index p: " + p.toString());
					
					if (lengthP > 1)
					{
						// Rearrange vector d and matrix d
						double [] dT = new double[n];
						Matrix vT = new Matrix(n,n);
						// Copy vector V - non zeros d[i]
						for (int count = 0; count < p.size(); count++)
						{
							dT[count] = d[p.elementAt(count).intValue()];
							Matrix.copyCols(vT, new int[] {count}, 
									v, new int [] {p.elementAt(count).intValue()});
						}
						for (int count = 0; count < diffset.size(); count++)
						{
							dT[lengthP + count] = 
								d[diffset.elementAt(count).intValue()];
							Matrix.copyCols(vT, new int[] {(lengthP + count)}, 
									v, new int [] {diffset.elementAt(count).intValue()});
						}
						
						d = dT;
						v = vT;
						if (debug) {
							System.out.print("Improvement vector:");
							for (int ii = 0; ii < d.length;ii++)
								System.out.print(d[ii] + " ");
							System.out.println();
						}
						double [] [] v_ = gramschmidt.execute(v.getMat(), d, lengthP);
						v = new Matrix(v_);
						if (debug)
							System.out.println("Direction matrix: \n" + v.toString());
						Matrix.copyArray(x0, optx);
						Matrix.copyArray(optx, xt);
						opty[0] = yt[0];
						d[0] = dt[0];
						
						startDirection = 1;
						continue;
					}
				}
				Matrix.copyArray(optx, xt);
				opty[0] = yt[0];
			}
			s = 0.1 * s;
			Matrix.copyArray(x0, optx);
			startDirection = 0;
		}
	}
	/**
	 * Find indexes of vector d[] at which |value| is larger than epsilon
	 * @param d
	 * @param epsilon
	 * @return
	 */
	static private Vector<Integer> find(double [] d, double epsilon)
	{
		Vector<Integer> res = new Vector<Integer>();
		for (int i = 0; i < d.length; i++)
		{
			if (Math.abs(d[i]) > epsilon)
			{
				res.addElement(new Integer(i));
			}
		}
		return res;
	}
	
	
	public static void main(String[] args) {
		
		int ndim = 30;
		double [] x = new double[ndim];
//		double [] x = {4, 4};
		
		FitnessFunction f = new Rastrigin(0);
		double [] v = {-6, 6};
		f.setDefaultRanges(v);
		
		int [] index = new int[ndim];
		for (index[0] = 0; index[0] < 100; index[0]++)
//			for (index[1] = 0; index[1] < 5; index[1]++)
//				for (index[2] = 0; index[2] < 5; index[2]++)
//					for (index[3] = 0; index[3] < 4; index[3]++)
//						for (index[4] = 0; index[4] < 4; index[4]++)
			{
				for (int j =0; j < x.length; j++)
						x[j] = -6 + 12* Math.random();
					double y = f.getFitnessFunc(x);
//					System.out.print(y);
//					System.out.println("Initial: \n" + (new Matrix(x)).toString() + "->" + y);
		
					DSCG directSearch = new DSCG(f, -1);
					directSearch.ACC = 1E-5;
					directSearch.StepSize = 1;
					directSearch.debug = false;
					
					y = directSearch.search(x, y);
//					System.out.println("Final: \n" + (new Matrix(x)).toString() + "->" + y);
					System.out.print("Final: " + "->" + y);
					System.out.println(" Re-eval y=" + f.getFitnessFunc(x));
//					System.out.println("Eval: " + (f.getEvalCount()-1));
					System.out.println();
			}
	}
	
}
