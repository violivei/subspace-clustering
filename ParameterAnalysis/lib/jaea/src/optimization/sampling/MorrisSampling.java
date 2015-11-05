package optimization.sampling;

import optimization.tools.*;

/**
 * Create Morris Sampling matrix. Each row differs from prev. row in one column
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class MorrisSampling {
	/**
	 * 
	 * @param elements Number of samples required (+1)
	 * @param p Number of features as well as Number of division in each dimension [0 - 1] 
	 * @param runs Number of experiment runs
	 * @return Sampling matrix
	 */
	public double [][] getMorrisData(int elements, int p, 
			int runs)
	{
		double [][] res = new double[runs*(elements +1)][elements];
		for (int i = 0; i <runs; i++)
		{
			double [][] oneRun = getMorrisMatrix(elements,p);
			int row = oneRun.length;
			int col = oneRun[0].length;;
			for (int r = 0; r < row; r++)
				for (int c =0; c < col; c++)
					res[i*row + r][c] = oneRun[r][c];
		}
		return res;
	}
	/**
	 * Get Morris Matrix for each run
	 * @param elements Number of samples required (+1)
	 * @param p Number of features as well as Number of division in each dimension [0 - 1]
	 * @return Sampling matrix
	 */
	public double [][] getMorrisMatrix(int elements, int p)
	{
		Permutation permGen = new Permutation();
		Matrix BI = 
			new Matrix(elements + 1, elements, Matrix.M_ZEROS);
		/* Get initial matrix B */
		for(int a = 1; a < elements +1; a++)
			for (int b = 0; b < a ; b++)
			{
				BI.getMat()[a][b] = 1;
			}
		/* Get matrix J, (k+1)xk of 1's */
		Matrix Jstar = 
			new Matrix(elements + 1, elements, Matrix.M_ONES);
		/* Get matrix D*, k-dim diagonal matrix with
		 * diagonal elements = 1 or -1 with equal prob 
		 * */
		Matrix D = 
			new Matrix(elements, elements, Matrix.M_DIAG);
		int [] perm = permGen.getPerm(elements);
		for (int c = 0; c < elements; c++)
			if (perm[c] % 2 == 1)
				D.getMat()[c][c] = -1;
		/* Get matrix P*, kxk random permutation matrix 
		 * with column 00..1..0
		 * */
		Matrix P = 
			new Matrix(elements, elements, Matrix.M_ZEROS);
		int [] rr = permGen.getPerm(elements);
		for (int e = 0; e < elements; e++)
			P.getMat()[e][rr[e]-1]=1;
		/* Get the range of x value, 0 to 1-delta */
		Matrix xi = new Matrix(1, elements);
		double delta = (1.0 * p)/(2*(p-1));
		int mm = (int)Math.floor((1-delta)*(p-1));
		double [] xvalue = new double[mm + 1];
		for (int m = 0; m <= mm; m++)
			xvalue[m] = m * 1.0/(p-1);
		for (int m = 0; m < elements; m++)
		{
			// get index: 0:(mm+1)
			int index = Utils.getRandom(mm+1);
			xi.getMat()[0][m] = xvalue[index];
		}
		/* Transform and get the orient matrix B*,
		 * one elementary effect per input
		 * BO = (term1 + term2)* P
		 */
		Matrix J1 = 
			new Matrix(elements + 1,1,Matrix.M_ONES);
		Matrix term1 = Matrix.multiply(J1, xi);
		Matrix temp = Matrix.multiply(
				Matrix.substract(Matrix.multiply(BI, 2), 
						Jstar), D);
		Matrix term2 = Matrix.multiply(
				Matrix.add(temp, Jstar), 
				delta/2);
		Matrix BO = Matrix.multiply(
				Matrix.add(term1, term2), P);
		return BO.getMat();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MorrisSampling ms = new MorrisSampling();
		Matrix res = new Matrix(ms.getMorrisData(5, 4, 1));
		System.out.println(res.toString());
	}

}
