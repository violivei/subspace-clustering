package optimization.operator.individual;
import mytest.evaluation.FitnessFunction;
import mytest.evaluation.real.*;
import optimization.searchspace.Individual;
import optimization.tools.*;
/**
 * Implementation of Davidon–Fletcher–Powell (DFP) search
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class DFP extends IndivSearch {
	public final String name = "DFP";
	public static final double EPS = 1.0E-12;
	public static final double TOLX = 4*EPS; 
	/**
	 * Convergence criterion on x values.
	 */
	public static final double STPMX = 2.0; 
	/**
	 * Scaled maximum step length allowed in line searches.
	 */
	public static final double ALF = 1.0E-4;
	/**
	 * Enable debug = true to print out search trace
	 */
	public boolean debug = false;
	
	/**
	 * Constructor
	 * @param fc Fitness function
	 */
	public DFP(FitnessFunction fc) {
		super(fc);
	}
	
	/**
	 * Constructor
	 * @param fc Fitness function
	 * @param maxeval Maxinum number of fitness evaluations
	 */
	public DFP(FitnessFunction fc, int maxeval) {
		super(fc, maxeval);
	}
	
	public void search(Individual indiv) 
	{
		double [] xInit = indiv.getChromAtIndex(0).getDoubleArray();
		double yInit = indiv.getFitnessValue();
		// Search from the individual, return new fitness
		double fit = search(xInit, yInit);
		// Copy the learned result to the individual
		for (int i = 0; i < xInit.length; i++)
			indiv.getChromAtIndex(0).setGene(i, xInit[i]);
		indiv.setFitnessValue(fit); 
	}
	
	public double search(double[] xInit, double yInit)
	{
		double [] fret = new double[1];
		double [] rmsGrad = new double[1];
		if (!this.StopIfConverge) 
		{
			if (debug) {
				System.out.println("DFP at budget: " + this.evalMax + " evaluations");
			}
			this.dfpmin(xInit, yInit, xInit.length, ACC, iterMax, fret, rmsGrad);
		}
		else 
		{
			if (debug) {
				System.out.println("DFP runs until convergence");
			}
			/* This code of convergence is correct but 
			 * not working. It shows the DFP code having problem testing
			 * of converging to some point of which dF/dx != 0
			double accuracy = 1E-5;
			this.evalMax = Integer.MAX_VALUE;
			this.dfpmin(xInit, xInit.length, accuracy, iterMax, fret, rmsGrad);
			*/
			/* Check converge by phenotype - OLD convergence */
			double [] xTemp = new double[xInit.length];
			int count = 0;

			do {
				// Copy xInit to xTemp
				for (int i = 0; i < xInit.length; i++)
					xTemp[i] = xInit[i];
				// Search from xInit
				this.dfpmin(xInit, yInit, xInit.length, ACC, 
						1, fret, rmsGrad);
				// After search, xInit is updated. Now update yInit
				yInit = fret[0];
				// Calculate the length of this move
				double distance = distance(xInit, xTemp);
				
//				Strict convergence condition for finding local optimum 1.0E-8 or 1.0E-7
//				if (distance >= 1.0E-8)
//				if (distance >= 1.0E-7)
//				Convergence condition in runtime for less wasted evaluations
				if (distance >= this.CONV_ACC)
				{
					if (debug)
						System.out.println("Improved distance: " + distance);
					count = 0;
				}
				else
				{
					if (debug)
						System.out.println("Converged distance: " + distance);
					count++;
				}
			}
			while (count < this.MAX_COUNT);
			
		}
		return fret[0];
		
	}
	/**
	 * Function to calculate Euclidean distance
	 * @param x 
	 * @param y
	 * @return
	 */
	private double distance(double [] x, double [] y)
	{
		 double res = 0;
		 if (x.length != y.length) return Double.MIN_VALUE;
		 for (int i = 0;  i < x.length; i++)
		 {
			 double temp = x[i] - y[i]; 
			 res += temp * temp;
		 }
		 return Math.sqrt(res);
	}
	 
	static public void main(String [] args)
	{
		int ndim = 10;
		double [] x = new double[ndim];
//		double [] x = {2.1, 4.9, 2.1};
		FitnessFunction f = new Ackley(0);
		double [] v = {-10, 10};
		f.setDefaultRanges(v);
		
//		int [] index = new int[ndim];
//		for (index[0] = 0; index[0] < 8; index[0]++)
//			for (index[1] = 0; index[1] < 8; index[1]++)
//				for (index[2] = 0; index[2] < 4; index[2]++)
//					for (index[3] = 0; index[3] < 4; index[3]++)
//						for (index[4] = 0; index[4] < 4; index[4]++)
			{
				for (int j =0; j < x.length; j++)
						x[j] = -10 + 20* Math.random();
					double y = f.getFitnessFunc(x);
					System.out.print(y);
					System.out.println("Initial: \n" + (new Matrix(x)).toString() + "->" + y);
		
					DFP directSearch = new DFP(f, -1);
					directSearch.ACC = 1E-5;
					directSearch.StepSize = 0.5;
					directSearch.debug = true;
					
					y = directSearch.search(x, y);
					System.out.println("Final: \n" + (new Matrix(x)).toString() + "->" + y);
//					System.out.println(", " + y);
					System.out.println("Eval: " + (f.getEvalCount()-1));
			}
	}
	//Ensures sufficient decrease in function value.

	/**
	 * Line Search procedure
	 * 	\fn LOpt::lnsrch(int n, double xold[], double fold, double g[], double p[], double x[],
	 * double *fret, double stpmax, int *check)
	 * @param n Number of dimensions
	 * @param xold Initial point
	 * @param fold Initial fitness
	 * @param g Gradient of initial point
	 * @param p Initial direction
	 * @param x Output = new double[dim]
	 * @param fret f(x) of the resultant x
	 * @param stpmax Step-size max limit
	 * @param check ?
	 */
	private void lnsrch(int n, double xold[], double fold, 
			double g[], double p[], double x[],double []fret, 
			double stpmax, int []check)
	{
		int i;
		double a=0,alam=0,alam2=0,alamin=0,b=0;
		double disc=0,f2=0,rhs1=0,rhs2=0,slope=0;
		double sum=0,temp=0,test=0,tmplam=0;
		
		check[0]=0;
		for (sum=0.0,i=0;i<n;i++) sum += p[i]*p[i];
		sum=Math.sqrt(sum);

		if (sum > stpmax)
			for (i=0;i<n;i++) p[i] *= stpmax/sum; //Scale if attempted step is too big.
		for (slope=0.0,i=0; i<n; i++)
			slope += g[i]*p[i];
		if (slope >= 0.0) {};//cout<<"Roundoff problem in lnsrch.\n";
		test=0.0; //Compute min.

		for (i=0; i<n;i++) {
			temp=Math.abs(p[i])/FMAX(Math.abs(xold[i]),1.0);
			if (temp > test) test=temp;
		}
		alamin=TOLX/test;
		//alam=1.0;  //Always try full Newton step first.
		alam= this.StepSize;
		for (;;) { //Start of iteration loop.
			for (i=0;i<n;i++) 
				x[i]=xold[i]+alam*p[i];
			fret[0]=evalFunc.getFitnessFunc(x);
			//Convergence on x. For zero finding,the calling program should verify the convergence.
			if (alam < alamin) { 
				for (i=0;i<n;i++) x[i]=xold[i];
				// Nghia: update the fitness of xold instead
//				fret[0] = evalFunc.getFitnessFunc(x);
//				System.out.println("Different fold-fret = " + (fret[0]-fold));
				fret[0] = fold;
				check[0]=1;
				return;
			} 
			else if (fret[0] <= fold+ALF*alam*slope) 
				return; //Sufficient function decrease.
			else { //Backtrack.
				if (alam == 1.0)
					tmplam = -slope/(2.0*(fret[0]-fold-slope)); //First time.
				else {// Subsequent backtracks.
					rhs1 = fret[0]-fold-alam*slope;
					rhs2=f2-fold-alam2*slope;
					a=(rhs1/(alam*alam)-rhs2/(alam2*alam2))/(alam-alam2);
					b=(-alam2*rhs1/(alam*alam)+alam*rhs2/(alam2*alam2))/(alam-alam2);
					if (a == 0.0) tmplam = -slope/(2.0*b);
					else {
						disc=b*b-3.0*a*slope;
						if (disc < 0.0) tmplam=0.5*alam;
						else if (b <= 0.0) tmplam=(-b+Math.sqrt(disc))/(3.0*a);
						else tmplam=-slope/(b+Math.sqrt(disc));
					}
					if (tmplam > 0.5*alam)
						tmplam=0.5*alam; 
				}
			}
			alam2=alam;
			f2 = fret[0];
			alam=FMAX(tmplam,0.1*alam); 
		}     
	}
	
	/** 
	 * DFP core search procedure
	 * LOpt::dfpmin(double p[], int n, double gtol, int iterMax, double *fret,double *rmsGrad)
	 * @param p Local point/ pass by reference. Learn & update directly
	 * @param fp Initial Fitness of p
	 * @param n Number of variables
	 * @param gtol Accuracy test (here used for gradient < epsilon test)
	 * @param iterMaxA Maximum number of search iteration 
	 * @param fret Improved fitness of p
	 * @param rmsGrad RMS of the gradient vector
	 * @return Current number of fitness evaluation calls 
	 */
	public int dfpmin(double p[], double fp, int n, double gtol, 
			int iterMaxA, double []fret,double [] rmsGrad)
	{
		int [] check = new int[1];
		int i,its,j;
		double fac=0,fad=0,fae=0;
//		double fp=0,stpmax=0,sum=0.0;
		double stpmax=0,sum=0.0;
		double sumdg =0,sumxi=0,test=0;	

		double []dg = new double[n];
		double []g = new double[n];
		double [] hdg = new double[n];
		double [][]hessin = new double[n][n];
		double []pnew = new double[n];
		double []xi = new double[n];
		
//		fp=evalFunc.getFitnessFunc(p);  
//		Calculate starting function value and gradient,
		evalFunc.getFitnessGrad(p, fp, g);

		for (i=0;i<n;i++) { //and initialize the inverse Hessian to the unit matrix. 
			for (j=0;j<n;j++) hessin[i][j]=0.0;
			hessin[i][i]=1.0;
			xi[i] = -g[i]; //Initial line direction.
			sum += p[i]*p[i];
		}

		stpmax=STPMX;//*n;
		// Check eval count
		long startEval = evalFunc.getEvalCount();
		//for (its=1;its<=iterMaxA;its++)
		its = 1;
		while (((evalFunc.getEvalCount() < startEval + evalMax) && !this.StopIfConverge) ||
				((its <= iterMaxA) && this.StopIfConverge))
		{ 
			its++;
			//Main loop over the iterations.	
			//Nghia: lnsrch is to calculate pnew and fret 
			/*n : number of dimensions
				xold: initial point
				fold: initial fitness
				g: gradient
				p = initial direction
				x: output = new double[dim]
				fret: f(x)
				stpmax: step max
				check = ?
			 */
			lnsrch(n,p,fp,g,xi,pnew,fret,stpmax,check);

			//The new function evaluation occurs in lnsrch; save the function value in fp for the
			//next line search. It is usually safe to ignore the value of check.
			fp = fret[0];
			
			for (i=0;i<n;i++) {
				xi[i]=pnew[i]-p[i]; //Update the line direction,
				p[i]=pnew[i];  // and the current point.
			}	
			//Nghia: the fret[0] is generally the fitness of pnew in lnsrch
			//but pnew=x[i] is assigned to xold when the search converges, 
			//we need to update fret[0] as in lnsrch() function
//			double calFit = evalFunc.getFitnessFunc(p);
//			System.out.println("Fret: " + fret[0] + ", Cal: " + calFit +
//								" Diff: " + (calFit-fret[0]));	
			/*
			test=0.0; //Test for convergence on x.
			for (i=1;i<=n;i++) {
				temp=fabs(xi[i])/FMAX(fabs(p[i]),1.0);
				if (temp > test) test=temp;
			}	
			if (test < TOLX)  return its; */
			
			
			for (i=0;i<n;i++) dg[i]=g[i]; //Save the old gradient,
			evalFunc.getFitnessGrad(p, fret[0], g); //and get the new gradient.
					
			test=0.0; //Test for convergence on zero gradient.		
			for (i=0;i<n;i++) {
				test+=g[i]*g[i];
			}
			test=Math.sqrt(test/n);
			if (debug)
				System.out.println("Test..." + test);
			if (test < gtol)  {
				if (debug)
					System.out.println("Stop by converged...");
				break;		
			}
		
			for (i=0;i<n;i++) dg[i]=g[i]-dg[i]; //Compute difference of gradients,
			for (i=0;i<n;i++) 
			{ //and difference times current matrix.
				hdg[i]=0.0;
				for (j=0;j<n;j++) hdg[i] += hessin[i][j]*dg[j];
			}
			fac=fae=sumdg=sumxi=0.0; //Calculate dot products for the denominators.
			for (i=0;i<n;i++) 
			{
				fac += dg[i]*xi[i];
				fae += dg[i]*hdg[i];
				sumdg += SQR(dg[i]);
				sumxi += SQR(xi[i]);
			}
			if (fac > Math.sqrt(EPS*sumdg*sumxi)) 
			{ 
				//Skip update if fac not sufficiently positive.
				fac=1.0/fac;
				fad=1.0/fae;
	    //The vector that makes BFGS different from DFP:
				for (i=0;i<n;i++) dg[i]=fac*xi[i]-fad*hdg[i];
				for (i=0;i<n;i++) 
				{ //The BFGS updating formula:
					for (j=i;j<n;j++) 
					{
						hessin[i][j] += fac*xi[i]*xi[j] -fad*hdg[i]*hdg[j]+fae*dg[i]*dg[j];
						hessin[j][i]=hessin[i][j];
					}
				}
			}
			for (i=0;i<n;i++) 
			{ //Now calculate the next direction to go,
				xi[i]=0.0;
				for (j=0;j<n;j++) xi[i] -= hessin[i][j]*g[j];
			}
		}// and go back for another iteration.
		rmsGrad[0]=0;
		for(i=0;i<n;i++)	
			rmsGrad[0]+=SQR(g[i]);	
		rmsGrad[0]=Math.sqrt((rmsGrad[0])/n);	
			   
		return (int)evalFunc.getEvalCount();//if overrun	

	}
	private double FMAX(double A, double B) {
		return (Utils.g(A, B)? A: B);
	}
	private double SQR(double x)
	{
		return x*x;
	}
}
