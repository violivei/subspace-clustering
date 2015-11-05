package optimization.tools;

import mytest.evaluation.*;
import mytest.evaluation.real.Sphere;

import java.util.Vector;
import optimization.sampling.MorrisSampling;

/**
 * Class to perform Morris screening to evaluate "importance"/sensitivity of 
 * each design variables in objective function. <p></p> See paper:
 * "Factorial Sampling Plans for Preliminary Computational Experiments" - Max D. Morris.
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class MorrisScreening {
	private double level = 5;
	public double getLevel() {return level;}
	public void setLevel(double val) {level = val;}
	/**
	 * 
	 * @param nDim Number of design variables
	 * @param p Number of bins/quantization in Morris sampling
	 * @param runs Number of runs
	 * @param f Function for sensitivity analysis
	 * @return Vector<Matrix>: 0 for main effect calculated, 1 for sampling points
	 */
	public Vector<Matrix> screenFunction(
			int nDim, double []lowBound, double [] highBound,
			int p, int runs, FitnessFunction f)
	{
		Vector<Matrix> resVec = new Vector<Matrix>();
		Matrix res = new Matrix(nDim, 5, Matrix.M_ZEROS);
		
		MorrisSampling mm = new MorrisSampling();
		double [][] data = mm.getMorrisData(nDim, p, runs);
		// Initial matrix
		Matrix samplePoints = new Matrix(data.length, nDim +1, Matrix.M_ZEROS);
		/* Transform to actual range */
		if (highBound != null && lowBound != null) {
			for (int i = 0; i < data.length; i++)
				for (int j = 0; j < data[0].length; j++)
				{
					data[i][j] = data[i][j]*(highBound[j]-lowBound[j])+lowBound[j];
					samplePoints.mat[i][j] = data[i][j]; 
				}
		}
		
		/* Evaluation */
		double [] eval = new double[data.length]; 
		for (int i = 0; i < data.length; i++) {
			eval[i] = f.getFitnessFunc(data[i]);
			samplePoints.mat[i][nDim] = eval[i];
		}
		
		double [][] eleEffect = new double[nDim][runs];
		int index = 0, nextIndex;
		for (int r= 0; r < runs; r++)
		{
			for (int i = 0; i < nDim; i++)
			{
				index = r*(nDim +1) + i;
				nextIndex = index + 1;
				for (int j = 0; j < nDim; j++) {
					double delta =
						data[index][j]-data[nextIndex][j];
					if (Math.abs(delta) > 1E-9)
						eleEffect[j][r] = 
							(eval[index] - eval[nextIndex])/delta; 
				}
			}
		}
		computeRes(res, eleEffect, nDim, runs);
		
		resVec.addElement(res);
		resVec.addElement(samplePoints);
		return resVec;
	}
	/**
	 * Measuring the effect
	 * @param data
	 * @param p
	 * @param runs
	 */
	public Matrix screenData(double [][] data, int p, int runs)
	{
		int nDim = data[0].length - 1;
		int evalIdx = data[0].length-1;
		Matrix res = new Matrix(nDim, 5, Matrix.M_ZEROS);
				
		double [][] eleEffect = new double[nDim][runs];
		int index = 0, nextIndex;
		for (int r= 0; r < runs; r++)
		{
			for (int i = 0; i < nDim; i++)
			{
				index = r*(nDim +1) + i;
				nextIndex = index + 1;
				for (int j = 0; j < nDim; j++) {
					double delta =
						data[index][j]-data[nextIndex][j];
					if (Math.abs(delta) > 1E-6)
						eleEffect[j][r] = 
							(data[index][evalIdx] - data[nextIndex][evalIdx])/delta; 
				}
			}
		}
		computeRes(res, eleEffect, nDim, runs);
		return res;
	}
	/**
	 * Compute main effect from data
	 * @param res
	 * @param eleEffect
	 * @param nDim
	 * @param runs
	 */
	private void computeRes(Matrix res, double[][] eleEffect, int nDim, int runs)
	{
		// Tinh theo absolute
		for (int i = 0; i < nDim; i++)
			for (int r = 0; r < runs; r++)
				eleEffect[i][r] = Math.abs(eleEffect[i][r]);
		// Calculate mean
		for (int i = 0; i < nDim; i++)
			for (int r = 0; r < runs; r++)
				res.getMat()[i][0] += eleEffect[i][r];
		for (int i = 0; i < nDim; i++)
				res.getMat()[i][0] /= runs;
		// Calculate variance
		for (int i = 0; i < nDim; i++)
			for (int r = 0; r < runs; r++)
				res.getMat()[i][1] += (eleEffect[i][r] - res.getMat()[i][0])*
										(eleEffect[i][r] - res.getMat()[i][0]);
		for (int i = 0; i < nDim; i++)
			res.getMat()[i][1] = res.getMat()[i][1]/runs;
		// Calculate standard error of mean
		for (int i = 0; i < nDim; i++)
			res.getMat()[i][2] = Math.sqrt(res.getMat()[i][1]/runs);
		// Determine if the variable is important
		for (int i = 0; i < nDim; i++)
			res.getMat()[i][3] = 
				(Math.abs(res.getMat()[i][0]) >= (level*res.getMat()[i][2])) ? 1 : 0;
		// Determine the percentage of the mean
		double temp = 0;
		for (int i = 0; i < nDim; i++)
			temp += Math.abs(res.getMat()[i][0]);
		for (int i = 0; i < nDim; i++)
			res.getMat()[i][4] = Math.abs(res.getMat()[i][0])/temp;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MorrisScreening scr = new MorrisScreening();
		//FitnessFunction f = new SphereFunc(0);
		FitnessFunction f = new Sphere(0);
		/* */
		double []lowBound =  {-1, -5, -10};
		double []highBound = {1, 5, 10};
		//f.normalize(lowBound, highBound);
		int p = 5;
		int r = 36;
		Vector<Matrix> a = 
			scr.screenFunction(3, lowBound, highBound, p, r, f);
		System.out.println("Mean, Variance, Standard Error of Mean, Good estimate, Rank");
		System.out.println(a.elementAt(0).toString());
		//System.out.println("Sample points ");
		//System.out.println(a.elementAt(1).toString());
	}
}

class TestFunc extends FitnessFunction{
	public String getName() {return "TestFunc";}
	public TestFunc(long initCount) {
		super(initCount);
		/*
		double []lowBound = new double[4];
		double []highBound = new double[4];
		
		for (int i = 0; i < 4; i++) {
			lowBound[i] = -1;
			highBound[i] = 1;
		}
		this.normalize(lowBound, highBound);
		*/
	}
	/**
	 * Sphere function - Unimodal
	 * Global opt : 0.0 at x[] = 0
	 */
	public double calculate(double [] inputs) 
	{
		double res = 0;
		for (int i = 0; i< inputs.length; i++)
		{
			if (i == 0)
				res += 10* inputs[i]*inputs[i];
			else if (i == 1)
				res += 5*inputs[i]*inputs[i];
			else
				res += inputs[i]*inputs[i];
		}
		return res;
	}
}

class TestFunc2 extends FitnessFunction{
	public String getName() {return "TestFunc2";}
	public TestFunc2(long initCount) {
		super(initCount);
	}
	
	public double calculate(double [] x) 
	{
		double res = 0;
		if (x.length == 20)
		{
			res += 5*x[11]/(1+x[0]);
			res += 5*(x[3]-x[19])*(x[3]-x[19]);
			res += x[4];
			res += 40*x[18]*x[18]*x[18];
			res += -5*x[18];
			res += 0.05*x[1] + 0.08*x[2] - 0.03*x[5] + 0.03*x[6];
			res += -0.09*x[8] - 0.01*x[9] - 0.07*x[10];
			res += 0.25*x[12]*x[12] - 0.04*x[13] +0.06*x[14]-0.01*x[16] -0.03*x[17];
		}
		return res;
	}
}