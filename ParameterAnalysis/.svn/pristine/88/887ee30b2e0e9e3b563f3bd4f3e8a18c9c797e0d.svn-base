package optimization.tools;

/**
 * Class to create random permutation (for shuffling)
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Permutation {
	/**
	 * Get permutation from 1:N
	 * @param N
	 * @return Array of permutated indices from 1 to N
	 */
	public int [] getPerm(int N) {
		int [] res = new int[N];
		int i, k;
		// initialization
		for (i = 0; i < N; i++)
			res[i] = i + 1;
		// permutation
		for (i = 0; i < N-1; i++)
		{
			 k = i + (int)(Math.random()*(N-i));
			 int temp = res[i];
			 res[i] = res[k];
			 res[k] = temp;
		}
		return res;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Permutation permGen = new Permutation();
		int N = 5;
		int [] perm = permGen.getPerm(N);
		for (int i = 0; i < perm.length; i++)
			System.out.print(perm[i] + ", ");
	}

}

