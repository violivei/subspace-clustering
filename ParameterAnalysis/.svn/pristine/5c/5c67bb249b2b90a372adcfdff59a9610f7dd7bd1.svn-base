package runtime;

import mytest.evaluation.FitnessFunction;

import org.w3c.dom.Element;

/**
 * Class to process parameters passed in Problem section, including of 
 * <p>
 * - Ackley, Elliptic, Griewank, Rastrigin, Rastrigin NonCon, Rosenbrock, 
 * Noise Sphere, Sphere, Weierstrass, Schwefel, ExpandedScaffer, etc...
 * </p>
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Problem {
	public String name = "";
	public byte problemID;
	public Problem(Element elem) {
		name = ConfigContainer.getTextValue(elem,"Name");
		if (name.compareTo("Ackley") == 0)
			problemID = FitnessFunction.F_Ackley;
		else if (name.compareTo("Elliptic") == 0)
			problemID = FitnessFunction.F_Elliptic;
		else if (name.compareTo("Equal") == 0)
			problemID = FitnessFunction.F_Equal; 
		else if (name.compareTo("Griewank") == 0)
			problemID = FitnessFunction.F_Griewank;
		else if (name.compareTo("MultiCos") == 0)
			problemID = FitnessFunction.F_MultiCos;
		else if (name.compareTo("Rastrigin") == 0)
			problemID = FitnessFunction.F_Rastrigin;
		else if (name.compareTo("Rastrigin NonCon") == 0)
			problemID = FitnessFunction.F_Rastrigin_NonCon;
		else if (name.compareTo("Rosenbrock") == 0)
			problemID = FitnessFunction.F_Rosenbrock;
		else if (name.compareTo("Noise Sphere") == 0)
			problemID = FitnessFunction.F_Sphere_Noise;
		else if (name.compareTo("Sphere") == 0)
			problemID = FitnessFunction.F_Sphere;
		else if (name.compareTo("Weierstrass") == 0)
			problemID = FitnessFunction.F_Weierstrass;
		else if (name.compareTo("Schwefel") == 0)
			problemID = FitnessFunction.F_Schwefel_102;
		else if (name.compareTo("ExpandedScaffer") == 0)
			problemID = FitnessFunction.F_ExpandedScaffer;
		else if (name.compareTo("ScaledSphere") == 0)
			problemID = FitnessFunction.F_ScaledSphere;
		else if (name.compareTo("Deceptive Core") == 0)
			problemID = FitnessFunction.F_DeceptiveCore;
		else if (name.compareTo("Deceptive Rastrigin") == 0)
			problemID = FitnessFunction.F_DeceptiveRastrigin;
		else problemID = -1;
	}
	public String toString()
	{
		return "Problem: " + name + "\n";
	}
}

