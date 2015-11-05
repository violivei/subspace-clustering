package mytest.evaluation.real;
import mytest.evaluation.*;
import java.util.Random;
/**
 * Implementation of Sphere with Noise benchmark function. <p></p>
 * Sphere function with Gaussian noise. Global optimum: f = 0 at x[] = 0
 * @author Le Minh Nghia, NTU-Singapore
 */
public class SphereNoise extends FitnessFunction{
	public String getName() {return "Sphere with Noise";}
	public SphereNoise(long initCount) {
		super(initCount);
		this.defaultRanges[0] = -100;
		this.defaultRanges[1] = 100;
	}

	public double calculate(double[] x) {

		double sum = 0.0;
		Random random = new Random();
		
		for (int i = 0 ; i < x.length ; i ++) {
			sum += x[i] * x[i];
		}

		// NOISE
		// Comment the next line to remove the noise
		sum *= (1.0 + 0.1 * Math.abs(random.nextGaussian()));

		return (sum);
	}
}
