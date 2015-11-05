package mytest;

import mytest.evaluation.FitnessFunction;
import mytest.evaluation.real.*;
import optimization.search.Search;
import optimization.search.SimpleEA;
import runtime.ConfigContainer;

/**
 * Abstract class contains basic functions - to be extended by MAIN class/optimizer 
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public abstract class EARunTemplate {
	
	/**
	 * Get optimization algorithm instance
	 * @param c Container of runtime and algorithm parameters
	 * @param evalFunc Fitness function to be optimized
	 * @return search algorithm in Search template
	 */
	static public Search getOptimizer(ConfigContainer c, FitnessFunction evalFunc) {
		Search optimizer = null;
		optimizer = new SimpleEA(c, evalFunc);	
		return optimizer;
	}
	
	/**
	 * Get fitness function instance 
	 * @param fType Function code (FitnessFunction class)
	 * @return fitness Function in FitnessFunction type
	 */
	static public FitnessFunction getFitnessFunc(byte fType) {
		FitnessFunction res = null;
		switch (fType) {
		case FitnessFunction.F_Ackley:
			res = new Ackley(0);
			break;
		case FitnessFunction.F_Elliptic:
			res = new Elliptic(0);
			break;
		case FitnessFunction.F_Equal:
			res = new Equality(0);
			break;
		case FitnessFunction.F_Griewank:
			res = new Griewank(0);
			break;
		case FitnessFunction.F_MultiCos:
			res = new MultiCosine(0);
			break;
		case FitnessFunction.F_Rastrigin:
			res = new Rastrigin(0);
			break;
		case FitnessFunction.F_Rastrigin_NonCon:
			res = new RastriginNonCont(0);
			break;
		case FitnessFunction.F_Rosenbrock:
			res = new Rosenbrock(0);
			break;
		case FitnessFunction.F_Sphere_Noise:
			res = new SphereNoise(0);
			break;
		case FitnessFunction.F_Sphere:
			res = new Sphere(0);
			break;
		case FitnessFunction.F_Weierstrass:
			res = new Weierstrass(0);
			break;
		case FitnessFunction.F_Schwefel_102:
			res = new Schwefel(0);
			break;
		case FitnessFunction.F_ExpandedScaffer:
			res = new ExpandedScaffer(0);
			break;
		case FitnessFunction.F_ScaledSphere:
			res = new ScaledSphere(0);
			break;
		case FitnessFunction.F_DeceptiveCore:
			res = new DeceptiveCore(0);
			break;
		case FitnessFunction.F_DeceptiveRastrigin:
			res = new DeceptiveRastrigin(0);
			break;
		}
		return res;
	}
}
