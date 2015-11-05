package optimization.sampling;

import optimization.tools.Permutation;

/**
 * Simple algorithm to generate Latin-hypercube sampling 
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class SimpleLH {
	/**
	 * Generate matrix (nSamples x nDim) so that each column is a permutation of 1->N 
	 * @param N Number of samples
	 * @param dim Number of dimensions
	 */
	public int [][] getSimpleLH(int N, int dim) {
		int [] [] LH = new int [N][dim];
		Permutation permGen = new Permutation();
		for (int c = 0; c < dim; c++) {
			int [] perm = permGen.getPerm(N);
			for (int r = 0; r < N; r++) {
				LH[r][c] = perm[r];
			}
		}
		return LH;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N = 10;
		int dim = 2;
		SimpleLH LHGen = new SimpleLH();
		int [][] aLH = LHGen.getSimpleLH(N, dim);
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < dim; c++)
				System.out.print(aLH[r][c] + " ");
			System.out.println();
		}
	}
}
