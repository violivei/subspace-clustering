package optimization.tools;

/**
 * Function to facilitate binary <-> double conversion
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class RealCoding {
	/**
	 * Convert from double to binary string
	 * @param value Real value to be converted
	 * @param lowBound Lower bound
	 * @param highBound Upper bound
	 * @param numBits Number of bits used to represent
	 * @return Binary string in boolean [] array
	 */
	static public boolean [] encodingValue(double value, double lowBound,
									double highBound, int numBits)
	{
		boolean [] res = new boolean[numBits];
		// number of steps
		long nRanges = (1<<numBits);
		int j;

		int pos = 0;
		// step size of each step
		double stepSize = (highBound - lowBound) / (nRanges);

		// discretize the phenotype[i]
		long nSteps = (long)((value - lowBound) / stepSize);
		long one = 1;
		// convert nSteps to binary
		for(j=0; j<numBits; j++)
		{
				res[pos] = ((nSteps & (one << j)) > 0);
				pos++;
		}	
		return res;
	}
	
	/**
	 * Convert from array of double values to binary string
	 * @param values Real values to be converted
	 * @param lowBound Array of lower bounds
	 * @param highBound Array of upper bounds
	 * @param numBits Number of bits used to represent
	 * @return Binary string in boolean [] array
	 */
	static public boolean [] encodingArray(double [] values, double []lowBound,
			double []highBound, int numBits)
	{
		int numVals = values.length;
		boolean [] res = new boolean[numBits * numVals];
		// number of steps
		long nRanges = (1<<numBits);
		int j;
	
		int pos = 0;
		
		for (int i = 0; i < numVals; i++)
		{
			// step size of each step
			double stepSize = (highBound[i] - lowBound[i]) / (nRanges);
			
			// discretize the phenotype[i]
			long nSteps = (long)((values[i] - lowBound[i]) / stepSize);
			long one = 1;
			// convert nSteps to binary
			for(j=0; j<numBits; j++)
			{
				res[pos] = ((nSteps & (one << j)) > 0);
				pos++;
			}	
		}
		
		return res;
	}
	/**
	 * Convert from bitstring to double value
	 * @param value Bit string in boolean array
	 * @param lowBound Lower bound
	 * @param highBound Upper bound
	 * @return Double value
	 */
	static public double decodingValue(boolean [] value, double lowBound,
								double highBound)
	{
		double res = 0;
		
		int numBits = value.length;
		int j;
		int pos = 0;
		
		long tmp = 0;
		long bitval;
		for(j=0; j<numBits; j++)
			{
				if (value[pos]) bitval = 1;
				else bitval = 0;
				tmp |= (bitval << j);
				pos++;
			}
		long nRanges = (1<<numBits);
		res = lowBound + ((highBound - lowBound) / (nRanges))*tmp;
		
		return res;
	}
	/**
	 * Convert from bitstring to double value
	 * @param value Bit string in boolean array
	 * @param lowBound Array of lower bounds
	 * @param highBound Array of upper bounds
	 * @return Array of double values
	 */
	static public double [] decodingArray(boolean [] value, double[] lowBound,
			double[] highBound)
	{
		int numVals = lowBound.length;
		int numBits = value.length/numVals;
		
		double [] res = new double[numVals];
		int j;
		int pos = 0;
		
		long bitval;
		long nRanges = (1<<numBits);
		
		for (int i = 0 ; i < numVals; i++)
		{
			long tmp = 0;
			for(j=0; j<numBits; j++)
			{
				if (value[pos]) bitval = 1;
				else bitval = 0;
				tmp |= (bitval << j);
				pos++;
			}
			res[i] = lowBound[i] + ((highBound[i] - lowBound[i]) / (nRanges))*tmp;
		}
		return res;
	}
	/**
	 * Convert binary string to positive integer value 
	 */
	static public int decodingArray(boolean [] value)
	{
		int numBits = value.length;
		
		int j, pos = 0;
		
		int bitval;
		
		int tmp = 0;
		for(j=0; j<numBits; j++)
		{
			if (value[pos]) bitval = 1;
			else bitval = 0;
			tmp |= (bitval << j);
			pos++;
		}		
		return tmp;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double []value = {0};
		double []low = {0};
		double []high = {7};
		int numBits = 3;
		boolean [] res = encodingArray(value, low, high, numBits);
		for (int i = 0; i < numBits*value.length; i++)
			if (res[i]) 
				System.out.print("1");
			else 
				System.out.print("0");
		System.out.println();
		
		System.out.println("Decode to: ");
		double [] val = decodingArray(res, low,high);
		for (int i = 0; i < value.length; i++)
			System.out.print(val[i] + " ");
		System.out.println();
		
		System.out.println("Binary decode to: ");
		int v = decodingArray(res);
		System.out.print(v + " ");
		System.out.println();
	}

}
