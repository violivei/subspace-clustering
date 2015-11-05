package optimization.tools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Class of common used functions in optimization
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Utils {
	static RandomGenerator rd = new RandomGenerator();
	/**
	 * Write matrix of integer to file
	 * @param filename Output file name
	 * @param matrix Input matrix
	 */
	static public void IMat2File(String filename, int [][] matrix) {
		/* prepare log file */
    	PrintStream MyOutput = null;
    	try {
    	       MyOutput = new PrintStream(new FileOutputStream(filename));
    	   }
    	catch (IOException e)
    	   {
    	      System.out.println("IO Error in Mat2File");
    	   }
    	// output all population
    	int maxRow = matrix.length;
    	int maxCol = matrix[0].length;
    	MyOutput.println(maxRow);
    	MyOutput.println(maxCol);
    	for (int i = 0; i < maxRow; i++) {
    		for (int j = 0; j < maxCol; j++)
    			if (j < maxCol -1 )
    				MyOutput.print(matrix[i][j] + ",");
    			else
    				MyOutput.println(matrix[i][j]);
    	}
	}
	
	/**
	 * Write matrix of double to file
	 * @param filename Output file name
	 * @param matrix Input matrix
	 */
	static public void DMat2File(String filename, double [][] matrix) {
		/* prepare log file */
    	PrintStream MyOutput = null;
    	try {
    	       MyOutput = new PrintStream(new FileOutputStream(filename));
    	   }
    	catch (IOException e)
    	   {
    	      System.out.println("IO Error in Mat2File");
    	   }
    	// output all population
    	int maxRow = matrix.length;
    	int maxCol = matrix[0].length;
    	MyOutput.println(maxRow);
    	MyOutput.println(maxCol);
    	for (int i = 0; i < maxRow; i++) {
    		for (int j = 0; j < maxCol; j++)
    			if (j < maxCol -1 )
    				MyOutput.print(matrix[i][j] + ",");
    			else
    				MyOutput.println(matrix[i][j]);
    	}
	}
	/**
	 * Read file in CSV format to matrix of double [][]
	 */
	static public double [][] File2DoubleMat(String filename)
	{
		double [] [] LH = null;
		try {
			/* Read parameters */
			FileReader fr = new FileReader(filename);
			BufferedReader input = new BufferedReader(fr);
			String s = null;
			s = input.readLine();
			int numSamples = Integer.parseInt(s);
			s = input.readLine();
			int numDim = Integer.parseInt(s);
			LH = new double [numSamples][numDim];
			int i = 0;
			do {
				s = input.readLine();
				if (s != null) {
					String [] strPara = s.split(",");
					for (int k = 0; k < numDim; k++) {
						LH[i][k] = Double.parseDouble(strPara[k]);
					}
					i++;
				}
			} while (s!= null);
		} catch(IOException e) {}
		return LH;
	}
	/**
	 * Read file in CSV format to matrix of integer [][]
	 */
	static public int [][] File2IntMat(String filename)
	{
		int [] [] LH = null;
		try {
			/* Read parameters */
			FileReader fr = new FileReader(filename);
			BufferedReader input = new BufferedReader(fr);
			String s = null;
			s = input.readLine();
			int numSamples = Integer.parseInt(s);
			s = input.readLine();
			int numDim = Integer.parseInt(s);
			LH = new int [numSamples][numDim];
			int i = 0;
			do {
				s = input.readLine();
				if (s != null) {
					String [] strPara = s.split(",");
					for (int k = 0; k < numDim; k++) {
						LH[i][k] = Integer.parseInt(strPara[k]);
					}
					i++;
				}
			} while (s!= null);
		} catch(IOException e) {}
		return LH;
	}
    
    /**
     * Take a binary string and convert it to the long-integer format. For example, '1101' --> 13
     */
    static public long binaryStrToInt(String sBinary)
    {
        long digit, iResult = 0;

        int iLen = sBinary.length();
        for (int i = iLen - 1; i >= 0; i--)
        {
            if (sBinary.charAt(i) == '1')
                digit = 1;
            else
                digit = 0;
            iResult += (digit << (iLen - i - 1));
        }
        return (iResult);
    }
	/**
	 * Check if a file in given folder exists
	 * @param dirName Directory name
	 * @param filename File nam
	 * @return TRUE if file exists, FALSE if otherwise
	 */
	static public boolean exists(String dirName, String filename) {
        boolean exists = false;
        File dir = new File(dirName);
        File file = new File(dir, filename);
        if (file.exists())
            exists = true;
        return exists;
	}
	/**
	 * Delete directory: 1. delete files in directories, 2. delete empty directory
	 * @param dir Specfied directory in File type
	 * @return TRUE if success, FALSE if otherwise
	 */
	public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
    
        // The directory is now empty so delete it
        return dir.delete();
    }
	/**
	 * Check if "greater or equal to" condition x >= y is TRUE
	 * @param x
	 * @param y
	 * @return TRUE if x >= y, FALSE if otherwise
	 */
	public static boolean ge(double x, double y) 
	{
		boolean res = false;
		if ((x - y) > (Math.abs(y)*(-1E-16)))
			res= true;
		return res;
	}
	/**
	 * Check if "greater" condition x > y is TRUE
	 * @param x
	 * @param y
	 * @return TRUE if x > y, FALSE if otherwise
	 */
	public static boolean g(double x, double y) 
	{
		boolean res = false;
		if ((x - y) > (Math.abs(y)*(1E-16)))
			res= true;
		return res;
	}
	/**
	 * Check if "less or equal to" condition x <= y is TRUE
	 * @param x
	 * @param y
	 * @return TRUE if x <= y, FALSE if otherwise
	 */
	public static boolean le(double x, double y) 
	{
		boolean res = false;
		if ((y - x) > (Math.abs(x)*(-1E-16)))
			res= true;
		return res;
	}
	/**
	 * Check if "less" condition x < y is TRUE
	 * @param x
	 * @param y
	 * @return TRUE if x < y, FALSE if otherwise
	 */
	public static boolean l(double x, double y) 
	{
		boolean res = false;
		if ((y - x) > (Math.abs(x)*(1E-16)))
			res= true;
		return res;
	}
	/**
	 * Check if "equal" condition x == y is TRUE
	 * @param x
	 * @param y
	 * @return TRUE if x == y, FALSE if otherwise
	 */
	public static boolean eq(double x, double y) 
	{
		boolean res = false;
		if (Math.abs(x - y) < 1E-16) res = true;
		return res;
	}
	/**
	 * Bubble Sort for Integer array
	 * @param array Value of the original array
	 * @param index Index of the original array
	 * @return Array is sorted in ascending order (lowest to highest). 
	 * Together with its original index stored in index[]
	 */
	static public int[] bubbleSort(int array[], int index[])
	{
		boolean swappedOnPrevRun = true;
		while(swappedOnPrevRun)
		{
			swappedOnPrevRun = false;			
			// this variable keeps track of whether to continue sorting or exit
			for(int i = 0; i < array.length - 1; i++)	
				// loop through every element in the array,
				// except for the last one
			{
				if(array[i] > array[i + 1])		
					// if current element is greater than the next
				{
					// swap the two elements
					swappedOnPrevRun = true;	// we don't want the loop to end just yet, we're not done
					int temp = array[i];		// store element i in a temporary variable
					array[i] = array[i + 1];	// set element i+1 to where i used to be
					array[i + 1] = temp;		// release the old i from temp into i+1 slot
					
					temp = index[i];
					index[i] = index[i + 1];
					index[i + 1] = temp;
				}
			}
		}
		return array;
	}
	
	/**
	 * Bubble Sort for Double array
	 * @param array Value of the original array
	 * @param index Index of the original array
	 * @return Array is sorted in ascending order (lowest to highest). 
	 * Together with its original index stored in index[]
	 */
	static public double[] bubbleSort(double array[], int index[])
	{
		boolean swappedOnPrevRun = true;
		while(swappedOnPrevRun)
		{
			swappedOnPrevRun = false;			
			// this variable keeps track of whether to continue sorting or exit
			for(int i = 0; i < array.length - 1; i++)	
				// loop through every element in the array,
				// except for the last one
			{
				if(array[i] > array[i + 1])		
					// if current element is greater than the next
				{
					// swap the two elements
					swappedOnPrevRun = true;	// we don't want the loop to end just yet, we're not done
					double temp = array[i];		// store element i in a temporary variable
					array[i] = array[i + 1];	// set element i+1 to where i used to be
					array[i + 1] = temp;		// release the old i from temp into i+1 slot
					
					int temp1 = index[i];
					index[i] = index[i + 1];
					index[i + 1] = temp1;
				}
			}
		}
		return array;
	}
	/**
	 * Return the median of the array
	 */
	static public double median(double [] input)
	{
		double res = 0;
		int [] index = new int[input.length];
		int len = input.length;
		for (int i = 0; i < len; i++) index[i] = i;
		bubbleSort(input,index);
		
		if ((len %2) == 1)
		{
			res = input[len/2];
		} 
		else 
		{
			res = (input[len/2] + input[len/2-1])/2;
		}
		return res;
	}
	
	/**
     * Return a integer random number between 0 and < upperBound
     */
    static public int getRandom(int upperBound)
    {
        int iRandom = rd.getRandom(upperBound);
        return (iRandom);
    }

    /**
     * Return a double random number between 0 and < upperBound
     */
    static public double getRandom(double upperBound)
    {
        double dRandom = rd.getRandom(upperBound);
        return (dRandom);
    }
    /**
     * Return the mean of the double array
     */
    static public double average(double [] vals)
    {
    	double sum = 0;
    	for (int i = 0 ; i < vals.length; i++)
    		sum += vals[i];
    	sum = sum/vals.length;
    	return sum;
    }
    /**
     * Return deviation of data in double array 
     */
    static public double deviance(double [] vals)
    {
    	double mean = average(vals);
    	double var = 0;
    	if (vals.length > 1)
    	{
    		for (int i = 0; i < vals.length; i++)
    			var += (vals[i] - mean)*(vals[i] - mean);
    		var = var/(vals.length-1);
    		var = Math.sqrt(var);
    	}
    	else 
    		var = 0;
    	return var;
    }
    /**
     * Function to manually remind Garbage Collector to clean memory
     */
    static public void invokeGarbageCollector()
    {
        Runtime r = Runtime.getRuntime();
        r.gc();
    }
    public static void main(String [] avrg)
    {
    	double [] a = {1,2,7,4,3,8};
    	System.out.println("Mean: " + average(a));
    	System.out.println("Median: " + median(a));
    	System.out.println("Deviance: " + deviance(a));
    }
}
