package optimization.tools;

import java.util.Random;

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
        Random rand = null;

        public Permutation(){
            rand = new Random();
        }
        
        public void setSeed(long seed) {
            rand = new Random(seed);
        }
        
        public Permutation(long seed) {
            rand = new Random(seed);
        }
                
	public int [] getPerm(int N) {
		int [] res = new int[N];
		int i, k;
		// initialization
		for (i = 0; i < N; i++)
			res[i] = i + 1;
		// permutation
		for (i = 0; i < N-1; i++)
		{
			 k = i + (int)(rand.nextDouble()*(N-i));
			 int temp = res[i];
			 res[i] = res[k];
			 res[k] = temp;
		}
		return res;
	}
        
        /**
	 * Get permutation from 1:MAX
	 * @param MAX
	 * @return Array of permutated indices from 1 to MAX
	 */
	public double [] getFloatPerm(double min, double max, int N) {
                double step = (max - min)/N;
                
                double [] res = new double[N];
		int i, k;
		// initialization
		for (i = 0; i < N; i++)
			res[i] = min + i*step + rand.nextDouble()*step;
		// permutation
		for (i = 0; i < N-1; i++)
		{
			 k = i + (int)(rand.nextDouble()*(N-i));
			 double temp = res[i];
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
		Permutation permGen = new Permutation(10000);
		int N = 20;
		int [] perm = permGen.getPerm(N);
		for (int i = 0; i < perm.length; i++)
			System.out.print(perm[i] + ", ");
	}

}

