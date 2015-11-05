package optimization.operator.individual;
import optimization.tools.*;
/**
 * Implementation for Gram-Schmidt orthogonalization
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class gramschmidt {
	/**
	 * Gram-Schmidt orthogonalization
	 * @param v Initial direction matrix
	 * @param d Target direction
	 * @param p Number of direction you want to rotate
	 * @return Resultant direction matrix
	 */
	public static double [][] execute(double [][] v,
										double [] d,
										int p)
	{
		int n = v[0].length;
		Matrix orthoMat = new Matrix(n,n, Matrix.M_ZEROS);
		Matrix dMat = new Matrix(d);
		Matrix vMat = new Matrix(v);
		Matrix tempMat = Matrix.tril(
				Matrix.repmat(Matrix.transpose(dMat),1, n));
		Matrix aMat = Matrix.multiply(vMat, tempMat);
		// Copy columns
		int [] idx = new int[p];
		for (int i = 0; i < p; i++) idx[i] = i;
		Matrix.copyCols(orthoMat, idx, aMat, idx);
		idx = new int[n-p];
		for (int i = p; i < n; i++) idx[i-p] = i;
		Matrix.copyCols(orthoMat, idx, vMat, idx);
		// Calculate colume 0->p
		for (int i = 0; i < p; i++)
		{
			Matrix cOrthoI = orthoMat.getCol(i);
			for (int j = 0; j <= (i-1); j++)
			{
				Matrix cOrthoJ = orthoMat.getCol(j);
				Matrix cA = Matrix.transpose(aMat.getCol(i));
				Matrix temp = Matrix.multiply(cOrthoJ,
						Matrix.multiply(cA, cOrthoJ));
				//System.out.println(temp.toString());
				Matrix temp2 = Matrix.substract(cOrthoI, temp);
				cOrthoI = temp2;
			}
			// Normalize column I
			double l = 0;
			for (int k = 0; k < cOrthoI.row; k++)
				l += cOrthoI.mat[k][0]*cOrthoI.mat[k][0];
			l = Math.sqrt(l);
			
			if (l > MathExt.EPS64)
				cOrthoI = Matrix.multiply(cOrthoI, 1/l);
			// Copy to the matrix
			Matrix.copyCols(orthoMat, new int[] {i}, 
							cOrthoI, new int[] {0});
		}
		return orthoMat.mat;
	}
	
	
	public static void main(String [] args)
	{
		System.out.println("Input");
		double [][] v = {{1, 1, 0.3},{0, 1, 1}, {0.5, 0, 1}};
		double [] d = {0.8, 0.8, 0.8};
		int p = 3;
		double [][] output = gramschmidt.execute(v, d, p);
		Matrix res = new Matrix(output);
		
//		double [][] mat = {{1, 1}, {0.5, 1}};
//		Matrix matIn = new Matrix(mat);
//		System.out.println(matIn.toString());
//		System.out.println("Output");
//		Matrix res = Matrix.tril(matIn);
//		Matrix res = Matrix.repmat(matIn, 3, 4);
		System.out.println(res.toString());
	}
}
