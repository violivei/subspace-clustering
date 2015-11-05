package mytest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import optimization.search.*;
import mytest.evaluation.*;
import optimization.searchspace.Chromosome;
import optimization.searchspace.Population;
import optimization.tools.*;
import runtime.ConfigContainer;
/**
 * Real-coded standard EA optimization test-bed
 * @author  Le Minh Nghia, NTU-Singapore
 *
 */
public class EARun extends EARunTemplate{

	public static void main(String[] args) {
		// Get runtime parameters from command-line parameter
//		ConfigContainer profile = new ConfigContainer("gaconf.xml");
		ConfigContainer profile = new ConfigContainer(args[0]);
		profile.parseParams();

		int numOfRuns = profile.runtime.numRuns;
		double [] bestFounds = new double[numOfRuns];
		double [] numEvals = new double[numOfRuns];
		int [] numGens = new int[numOfRuns];
		Population f;
		int [] chromDim;
		byte [] chromType;
		
		chromDim = new int[1]; chromDim[0] = profile.method.chromosomeDim;
		chromType= new byte[1]; chromType[0] = Chromosome.FLOAT;
	
		/* Setting input/output stream */
		boolean success = (new File(profile.runtime.savedDir)).mkdir();
	    if (success) {
	        System.out.println("Directory: " + profile.runtime.savedDir + " created");
	    } 
	    /* Record Message and Error to files */
	    PrintStream outputStream= null;
		PrintStream errStream= null;
	    if (profile.runtime.writeIO2File) {
	    	String outputFilename = profile.runtime.savedDir + "/" + 
	    							profile.runtime.savedDir + ".out";
    		String errFilename = profile.runtime.savedDir + "/" +
    							 profile.runtime.savedDir + ".err";
    		try {
    			outputStream = new PrintStream(new FileOutputStream(outputFilename));
    			errStream = new PrintStream(new FileOutputStream(errFilename));
    		}
    		catch (IOException e) {
    			System.out.println("OOps");
    		}
    	
    		/* Redirect Output message to file */
    		//System.setOut(outputStream);
    		/* Redirect Error message to file */
	    	System.setErr(errStream);
	    }
	    /* Start run the search */
    	Search optimizer = null;
    	
    	/* f: population variable */
		f = new Population(chromDim, chromType, profile.method.populationDim);
		// Get fitness function or use your own fitness function
		if (profile.problem.problemID < 0)
		{
			System.out.println("Missing problem description...");
			System.exit(0);
		}
		FitnessFunction evalFunc = getFitnessFunc(profile.problem.problemID);
		
		if (f != null)
		{
			/*
			 * Two ways to use use fitness functions: 
			 * 1. Use default search on [0,1]^n space ->
			 * 		Create fitness function. Then normalize it to the actual range
			 * 2. Use search on specified range in f.initPopulation()
			 */
			// Boolean variable: (true) for Method 1, (false) for Method 2
			boolean normalize = false;
			/* Method 1 */
			// Normalize if the range is different from [0,1]
			int nDim = profile.method.chromosomeDim;
			if (normalize) {
				double lowBound [] = new double[nDim];
				double highBound [] = new double[nDim];
				for (int i = 0; i <nDim; i++) {
					//lowBound[i] = -RuntimeConfiguration.maxPopRange;
					//highBound[i] = RuntimeConfiguration.maxPopRange;
					lowBound[i] = -(i+1)*10;
					highBound[i] = (i+1)*10;
				}
				evalFunc.normalize(lowBound, highBound);
			}
			/* If not normalize, (optional) randomly shift fitness function
			 * for debugging purpose */
			else {
				double [] s = new double[nDim];
				for (int i = 0; i < nDim; i++)
				{
					//s[i] = Math.random();
					s[i] = 0.0;
					System.out.print(s[i]+ " ");
				}
				// Set the shift vector
				System.out.println();
				evalFunc.setShiftVector(s);
				// Set the range vector
				if (profile.method.maxPopRange > 0)
				{
					double [] fRanges = 
						{-profile.method.maxPopRange, profile.method.maxPopRange};
					evalFunc.setDefaultRanges(fRanges);
				}
				// Validate function at optimum
				System.out.println("Evaluated: " + 
						evalFunc.getFitnessFunc(s));
			}
			
			// Start optimizing
			for (int i = 0; i < numOfRuns; i++)
			{
				/* Clean overall memory after each run */
		        System.out.println("Clean memory after one run...");
		        Utils.invokeGarbageCollector();
		        System.out.println("Finish clean memory.");
				// Reset evaluation count for different runs
				evalFunc.resetEvalCount();
				evalFunc.setWriteOption(profile.runtime.writeEvaluation2File);
				evalFunc.setOutputFile(i, profile.runtime.savedDir,
										profile.runtime.evalFilename);
				// Get optimizer
				optimizer = getOptimizer(profile, evalFunc);
				// initialize function
				if (normalize)
					f.initPopulation();
				else {
					/* Method 2
					double lowerBound [] = {-5, -5, -0.5};
					double upperBound [] = {5, 5, 0.5};
					*/
					double lowerBound [] = new double[nDim];
					double upperBound [] = new double[nDim];
					for (int j = 0; j <nDim; j++) {
						/*   */
						if (profile.method.maxPopRange > 0) 
						{
							lowerBound[j] = -profile.method.maxPopRange;
							upperBound[j] = profile.method.maxPopRange;
						}
						else {
							lowerBound[j] = evalFunc.getDefaultRanges()[0];
							upperBound[j] = evalFunc.getDefaultRanges()[1];
						}
					}
					f.initPopulation(lowerBound, upperBound);
				}
				
				//f.initPopulation(lowerBound, upperBound, "p0.txt");
			   
		        try
		        {	
		        	// do search
		        	optimizer.search(f);
		        	if (f.measure != null) {
		        		f.measure.evaluation = evalFunc.getEvalCount();
		        		if (profile.runtime.writeResults2File)
		        			f.measure.toFile(profile.runtime.savedDir + "/" + 
		        					profile.runtime.evalFilename + "-" + i + ".csv");
		        	}
		        	bestFounds[i] = f.measure.getBestFound();
		        	numGens[i] = f.measure.maxGens;
		        	numEvals[i] = evalFunc.getEvalCount();
		        	System.out.println("Number of eval: "
		        			+ evalFunc.getEvalCount());
		        }
		        catch (Exception gae)
		        {
		            System.out.println(gae.getMessage() + "\n" + gae.toString()
		            		+ "\n" + gae.getStackTrace().toString());
		        }
		        /* Close file to store fitness evaluation */
		        evalFunc.close();
			}
			/* Print out final results */
			System.out.println("/********* RESULTS *********/");
			System.out.println(optimizer.toString());
			System.out.println("Test function: " + evalFunc.toString());
			
			//f.toFile("finalPop.txt"); 
			System.out.println(f.getPopInfo());
			
			// Calculate performance statistics: mean & variance 
			System.out.println("Best solutions found: ");
			double [][] a = new double[3][numOfRuns];
			for (int i = 0; i < numOfRuns; i++) 
			{
//				System.out.println(bestFounds[i]);
				a[0][i] = bestFounds[i];
			}
			for (int i = 0; i < numOfRuns; i++) 
			{
//				System.out.println(numEvals[i]);
				a[1][i] = numEvals[i];
			}
			for (int i = 0; i < numOfRuns; i++) 
			{
//				System.out.println(numGens[i]);
				a[2][i] = numGens[i];
			}
			// Output best solutions found/function evaluations/generations to file  
			Matrix matA = new Matrix(a);
			Matrix Trans_MatA = Matrix.transpose(matA);
			System.out.println(Trans_MatA.toString());

			Matrix.DMat2File(profile.runtime.savedDir + "/" + 
					evalFunc.getName() + "_" + nDim + "D.csv", Trans_MatA);
			// Best solutions found			
			System.out.println("Average fitness: " + Utils.average(a[0]) 
					+ " +/- " + Utils.deviance(a[0]) 
					+ " | Median: " + Utils.median(a[0]));
			// Function evaluations
			System.out.println("Average number of evaluations: " + Utils.average(a[1]) 
					+ " +/- " + Utils.deviance(a[1]) 
					+ " | Median: " + Utils.median(a[1]));
			// Number of generations
			System.out.println("Average number of generations: " + Utils.average(a[2]) 
									+ " +/- " + Utils.deviance(a[2]) 
									+ " | Median: " + Utils.median(a[2]));
		}
		if (profile.runtime.writeIO2File) {
			outputStream.close();
			errStream.close();
		}
	}
}
