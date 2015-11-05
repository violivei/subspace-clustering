package optimization.sampling;

/**
 * Function to create Latin-hyper cube sampling with objective
 * to maximize the sparseness between samples (i.e. defined as MIN distance)
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class SweepCWLH {
	SimpleLH sLHGen = new SimpleLH();
	/**
	 * Main function
	 * @param N Number of samples
	 * @param dim Number of dimensions
	 * @return Matrix of sampling: each row is the indices of discretized 'hypersquare'
	 */
	public int [][] getSweepCWLH(int N, int dim) {
		int [] distInfo = new int[3];
		// First generate initial matrix
		int [] [] LH = sLHGen.getSimpleLH(N, dim);
		int i, j;
		// Create square matrix
		int [] [] SQR = new int[N][N];
		for (i=1; i <= N; i++)
			for (j= i; j <=N; j++) {
				if (i!= j) {
					int x = (i-j)*(i-j);
					SQR[i-1][j-1] = x;
					SQR[j-1][i-1] = x;
				}
				else 
					SQR[i-1][i-1] = 0;
			}
		// Get min distance
		distInfo = calMinDist(LH,SQR);
		System.out.println("Initial min dist: "+ distInfo[2]);
		// Then maximize the MIN-Distance
		// Sweep column-wise
		for (i = 0; i < dim; i++)
		{
			int bestR1=0, bestR2=0;
			int r1, r2;
			int temp;
			int [] distInfoTmp = new int[3];
			boolean improved = false;
			// Sweep through each row (r1,r2)
			for (r1 =0; r1 < N-1; r1++)
				for (r2 = r1+1; r2 < N; r2++)
				{
					// Swap 2 elements at row r1, r2
					temp = LH[r1][i];
					LH[r1][i] = LH[r2][i];
					LH[r2][i] = temp;
					// Calculate new min distance
					distInfoTmp  = calMinDist(LH, SQR);
					// Check if min is maximum, then store r1,r2
					if (distInfoTmp[2] > distInfo[2])
					{
						bestR1 = r1;
						bestR2 = r2;
						improved = true;
					}
					// Swap back rows r1,r2
					temp = LH[r1][i];
					LH[r1][i] = LH[r2][i];
					LH[r2][i] = temp;
				}
			// Swap bestR1, bestR2
			if (improved) {
				temp = LH[bestR1][i];
				LH[bestR1][i] = LH[bestR2][i];
				LH[bestR2][i] = temp;
				distInfo = calMinDist(LH, SQR);
			}
		}
		System.out.println("Initial min dist: "+ distInfo[2]);
		return LH;
	}
	/**
	 * 
	 * @param LH
	 * @param SQR: squared array a(i,j) = (i-j)^2
	 * @return
	 */
	private int [] calMinDist(int [] [] LH, int [][] SQR)
	{
		// res in order Point1 - Point 2 - Distance
		int [] res = new int[3];
		int N = LH.length;
		int i,j;
		int minDist = Integer.MAX_VALUE;
		int r1=0,r2=0;
		// Get minimum distance d(xi,xj)
		for (i = 0; i < N-1; i++)
			for (j = i + 1; j < N; j++) {
				int a = calDistance(LH[i], LH[j], SQR);
				if (minDist > a) { 
					minDist = a;
					r1= i; r2 = j;
				}
			}
		// System.out.println(r1 + ", " + r2);
		res[0] = r1; res[1] = r2; res[2] = minDist;
		return res;
	}
	private int calDistance(int [] u, int [] v, int [][] SQR)
	{
		int res = 0;
		for (int i = 0; i < u.length; i++)
			res += SQR[u[i]-1][v[i]-1];
		return res;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N = 10;
		int dim = 3;
		SweepCWLH LHGen = new SweepCWLH();
		int [][] aLH = LHGen.getSweepCWLH(N, dim);
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < dim; c++)
				System.out.print(aLH[r][c] + " ");
			System.out.println();
		}
	}
}

