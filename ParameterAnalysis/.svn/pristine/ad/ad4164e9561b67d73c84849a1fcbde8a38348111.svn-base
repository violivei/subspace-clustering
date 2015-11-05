package optimization.sampling;

import optimization.tools.*;

/**
 * Function to check if the sampled data is well-sampled
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class SampleCheck {
	/**
	 * Check if the data is from stratified sampling
	 * @param samplePoints Matrix of sample points: row = number of samples, col = dimensions
	 * @param lowerBound Lower bounds on each dimension
	 * @param upperBound Upper bounds on each dimension
	 * @param nBin Bins for discretization
	 * @return TRUE if Stratified Samples, FALSE if otherwise
	 */
	static public boolean isStratifiedSample(double [][] samplePoints,
			double [] lowerBound,
			double [] upperBound,
			int nBin)
	{
		boolean res = true;
		int nSample = samplePoints.length;
		int nDim = samplePoints[0].length;
		double [] binWidth = new double[nDim];
		for (int i = 0; i < nDim; i++) 
		{
			if (Utils.eq(upperBound[i], lowerBound[i]))
        		return false;
			binWidth[i] = (upperBound[i] - lowerBound[i])/nBin;
		}
		int indexDim;
		
		double [][] SampleIndexes = new double[nBin][nDim];
		for (int i = 0; i < nBin; i++)
			for (int j = 0; j < nDim; j++)
				SampleIndexes[i][j] = 0;
		try {
		for (int i = 0; i < nSample; i++) {
			double [] chrom = samplePoints[i];
			for (int j = 0; j < nDim; j++) {
				indexDim = 
					(int)Math.floor((chrom[j]-lowerBound[j])/binWidth[j]);
				if (indexDim == nBin) indexDim = nBin-1;
				
				SampleIndexes[indexDim][j] += 1;
			}
		}
		}
		catch (Exception e) {
			System.err.println("Error at Stratified Sample checking... " + e.getMessage());
			System.exit(0);
		}
		//Utils.DMat2File("test.csv", SampleIndexes);
		/* Checking if all 'hypersquares' are filled */
		for (int i = 0; i < nBin; i++)
			for (int j = 0; j < nDim; j++)
				if (SampleIndexes[i][j] == 0)
					return false;
		return res;
	}
	/**
	 * Check if the data is from Latin hyper-cube sampling
	 * @param samplePoints Matrix of sample points: row = number of samples, col = dimensions
	 * @param lowerBound Lower bounds on each dimension
	 * @param upperBound Upper bounds on each dimension
	 * @return TRUE if Stratified Samples, FALSE if otherwise
	 */
	static public boolean isLHSample(double [][] samplePoints,
								double [] lowerBound,
								double [] upperBound)
	{
		boolean res = true;
		int nSample = samplePoints.length;
		int nDim = samplePoints[0].length;
		double [] binWidth = new double[nDim];
        for (int i = 0; i < nDim; i++)
        {
        	if (Utils.eq(upperBound[i], lowerBound[i]))
        		return false;
        	binWidth[i] = (upperBound[i] - lowerBound[i])/nSample;
        }
        int indexDim;
        double [][] SampleIndexes = new double[nSample][nDim];
        for (int i = 0; i < nSample; i++)
        	for (int j = 0; j < nDim; j++)
        		SampleIndexes[i][j] = 0;
    	for (int i = 0; i < nSample; i++) {
        	double [] chrom = samplePoints[i];
        	for (int j = 0; j < nDim; j++) {
        		indexDim = 
        			(int)Math.floor((chrom[j]-lowerBound[j])/binWidth[j]);
        		if (indexDim == nSample) indexDim = nSample-1;
        		SampleIndexes[indexDim][j] = i+1;
        	}
    	}
    	//Utils.DMat2File("test.csv", SampleIndexes);
    	/* Checking */
    	for (int i = 0; i < nSample; i++)
        	for (int j = 0; j < nDim; j++)
        		if (SampleIndexes[i][j] == 0)
        			return false;
		return res;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double [][] samples = 
			Utils.File2DoubleMat("p112.csv");
		int nSample = samples.length;
		int nDim = samples[0].length;
		double [] lowB = new double[nDim];
		double [] highB = new double[nDim];
		for (int i = 0; i < nDim; i++)
		{
			lowB[i] = Double.MAX_VALUE;
			highB[i] = -Double.MAX_VALUE;
		}
		for (int i = 0; i < nSample; i++)
			for (int j = 0; j < nDim; j++)
			{
				if (samples[i][j] < lowB[j])
					lowB[j] = samples[i][j];
				if (samples[i][j] > highB[j])
					highB[j] = samples[i][j];
			}
		/*
		for (int j = 0; j < nDim; j++)
		{
			highB[j] += 0.01*Math.abs(highB[j]);
		}
		*/
		
		boolean res = SampleCheck.isLHSample(samples, lowB, highB);
		if (res == true)
			System.out.println("IS LH Sampling");
		else System.out.println("IS NOT LH Sampling");
		res = SampleCheck.isStratifiedSample(samples, lowB, highB, nDim);
		if (res == true)
			System.out.println("IS Stratified Sampling");
		else System.out.println("IS NOT Stratified Sampling");
	}

}
