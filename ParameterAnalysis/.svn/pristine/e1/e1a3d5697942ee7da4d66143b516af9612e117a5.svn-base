package optimization.operator.individual;

import mytest.evaluation.FitnessFunction;
import mytest.evaluation.real.*;
import optimization.tools.*;
/**
 * Implementation of standard line search (used in DSCG)
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class linesearch {
	static private boolean debug = false;
	/**
	 * Standard line search procedure
	 * linesearch.search(optx, opty[0], 
						s, v.getFlatCol(i), f, xtemp, ytemp, dtemp);
	 * @param x Starting point
	 * @param y Starting fitness
	 * @param s Initial step size
	 * @param v Initial search direction
	 * @param f Fitness function
	 * @param optx Resultant point after search
	 * @param opty Resultant fitness
	 * @param d Magnitude of improvement
	 */
	public static void search(double [] x,
							   double y,
							   double s,
							   double [] v,
							   FitnessFunction f,
							   double [] optx, 
							   double [] opty, double [] d)
	{
		boolean success = true;
		int n = x.length;
		d[0] = 0;
		Matrix tmpx, tmpy;
		Matrix xMat = new Matrix(x);
		Matrix vMat = new Matrix(v);
		
		// Debug
		if (linesearch.debug)
		{
			System.out.println("X(old): " + xMat.toString());
			System.out.println("V: " + vMat.toString());
			System.out.println("s: " + s);
		}
		// End debug
		Matrix xrMat = Matrix.add(xMat, 
				Matrix.multiply(vMat, s));
		double yr = f.getFitnessFunc(xrMat.getFlatRow(0));
		// Debug
		if (linesearch.debug)
		{
			System.out.println("X(new): " + xrMat.toString());
			System.out.println("Eval " + f.getEvalCount() + ": " + yr);
		}
		// End debug
		if (yr > y)
		{
			s = -s;
			// Debug
			if (linesearch.debug)
			{
				System.out.println("X(old): " + xMat.toString());
				System.out.println("V: " + vMat.toString());
				System.out.println("s: " + s);
			}
			// End debug
			Matrix xlMat = Matrix.add(xMat, 
					Matrix.multiply(vMat, s));
			double yl = f.getFitnessFunc(xlMat.getFlatRow(0));
			// Debug
			if (linesearch.debug)
			{
				System.out.println("X(new): " + xlMat.toString());
				System.out.println("Eval " + f.getEvalCount() + ": " + yl);
			}
			// End debug
			if (yl > y)
			{
				success = false;
				//s = -s;
				tmpx = new Matrix(n,3);
				tmpy = new Matrix(1,3);
				Matrix.copyCols(tmpx, new int[] {0}, 
						Matrix.transpose(xrMat), new int[] {0});
				Matrix.copyCols(tmpx, new int[] {1}, 
						Matrix.transpose(xMat), new int[] {0});
				Matrix.copyCols(tmpx, new int[] {2}, 
						Matrix.transpose(xlMat), new int[] {0});
				tmpy.mat[0][0] = yr;
				tmpy.mat[0][1] = y;
				tmpy.mat[0][2] = yl;
				// Move to interpolation
			}
			else
			{
				tmpx = new Matrix(n,4, Matrix.M_ZEROS);
				Matrix.copyCols(tmpx, new int[] {1}, 
						Matrix.transpose(xMat), new int[] {0});
				Matrix.copyCols(tmpx, new int[] {3}, 
						Matrix.transpose(xlMat), new int[] {0});
				tmpy = new Matrix(1,4, Matrix.M_ZEROS);
				tmpy.mat[0][1] = y;
				tmpy.mat[0][3] = yl;
			}
		}
		else
		{
			tmpx = new Matrix(n,4, Matrix.M_ZEROS);
			Matrix.copyCols(tmpx, new int[] {1}, 
					Matrix.transpose(xMat), new int[] {0});
			Matrix.copyCols(tmpx, new int[] {3}, 
					Matrix.transpose(xrMat), new int[] {0});
			tmpy = new Matrix(1,4, Matrix.M_ZEROS);
			tmpy.mat[0][1] = y;
			tmpy.mat[0][3] = yr;
		}
		
		if (success)
		{
			while (tmpy.mat[0][1] >= tmpy.mat[0][3])
			{
				//d[0] = d[0] + s;
				Matrix.copyCols(tmpx, new int[] {0, 1}, 
						tmpx, new int[] {1, 3});
				Matrix.copyCols(tmpy, new int[] {0, 1}, 
						tmpy, new int[] {1, 3});
				
				s = 2 * s;
				Matrix temp = tmpx.getCol(1);
				Matrix sv = Matrix.multiply(vMat, s);
				temp = Matrix.add(temp, Matrix.transpose(sv));
				Matrix.copyCols(tmpx, new int[] {3},
						temp, new int[] {0});
				tmpy.mat[0][3] = 
					f.getFitnessFunc(tmpx.getFlatCol(3));
			}
			s = 0.5 * s;
			Matrix temp = tmpx.getCol(1);
			Matrix sv = Matrix.multiply(vMat, s);
			temp = Matrix.add(temp, Matrix.transpose(sv));
			Matrix.copyCols(tmpx, new int[] {2},
					temp, new int[] {0});
			tmpy.mat[0][2] = 
				f.getFitnessFunc(tmpx.getFlatCol(2));
			
			if (tmpy.mat[0][1] >= tmpy.mat[0][2])
			{
				//d[0] = d[0] +s;
				Matrix.copyCols(tmpx, new int[] {0, 1, 2}, 
						tmpx, new int[] {1, 2, 3});
				Matrix.copyCols(tmpy, new int[] {0, 1, 2}, 
						tmpy, new int[] {1, 2, 3});
			}
		}
		double tmp = 2 * (tmpy.mat[0][0] - 2* tmpy.mat[0][1] + tmpy.mat[0][2]);
		Matrix optXMat;
		if (Math.abs(tmp) >= MathExt.EPS64)
		{
			double w = (tmpy.mat[0][0] - tmpy.mat[0][2])/tmp * s;
			optXMat = tmpx.getCol(1);
			Matrix sv = Matrix.multiply(vMat, w);
			optXMat = Matrix.add(optXMat, Matrix.transpose(sv));
			opty[0] = 
				f.getFitnessFunc(optXMat.getFlatCol(0));
			
			if (opty[0] > tmpy.mat[0][1])
			{
				optXMat = tmpx.getCol(1);
				opty[0] = tmpy.mat[0][1];
			}
			else
			{
				//d[0] = d[0] + s;
			}
		}
		else
		{
			optXMat = tmpx.getCol(1);
			opty[0] = tmpy.mat[0][1];
		}
		double [] temp = optXMat.getFlatCol(0);
		for (int i = 0; i < n; i++) {
			optx[i] = temp[i];
			temp[i] -= x[i];
		}

		for (int i =0; i < v.length; i++)
		{
			if (Math.abs(v[i]) > MathExt.EPS64) {
				d[0] = temp[i]/v[i];
				return;
			}
		}

	}
	public static void main(String [] args)
	{
		double [] x = {1, 1, 1, 1};
		FitnessFunction f = new Griewank(0);
		double y = f.getFitnessFunc(x);
		double [] v = {1, 1, 1, 1};
		double [] optx= new double[x.length];
		double [] opty = new double[1];
		double [] d = new double[1];
		double s = 0.8;
		linesearch.search(x, y, s, v, f, optx, opty, d);
		System.out.println((new Matrix(optx)).toString() + "->" 
				+ opty[0] + " | d = " + d[0]);
	}
}
