package optimization.tools;

import java.util.Random;
/**
 * Class for generating uniformly random numbers, using Random class (Java)
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class RandomGenerator {
	Random rd = null;
	Random booleanRd = null;
	/**
	 * Constructor
	 */
	public RandomGenerator()
	{
		rd = new Random(System.currentTimeMillis());
		booleanRd = new Random(System.currentTimeMillis());
	}
	/**
     * Return a INTEGER random number between 0 and < upperBound
     */
    public int getRandom(int upperBound)
    {
        int iRandom = rd.nextInt(upperBound);
        return (iRandom);
    }

    /**
     * return a DOUBLE random number between 0 and < upperBound
     * @param upperBound
     * @return double
     */
    public double getRandom(double upperBound)
    {
        double dRandom = rd.nextDouble()* upperBound;
        return (dRandom);
    }
    /**
     * "Coin Toss" distribution - Return boolean value with probability P for TRUE, 1-P for FALSE
     */
    public boolean coinToss(double P)
    {
    	if (booleanRd.nextDouble() < P) 
    		return true;
    	else 
    		return false;
    }
}
