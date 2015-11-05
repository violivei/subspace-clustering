package optimization.operator.population;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Calendar;

import optimization.tools.MySQLdbase;

/**
 * Class to record & calculate statistics of search performance
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Performance {
	
	/** statistic-- best so far solution found */
    public double [] bestFound;
    /** statistic-- current best found solution */
    public double [] currentBestFound;
    /** statistic-- threshold use to decide class in learning */
    public double [] threshold;
    /** record of actions */
    public byte [] currAction;
    /** current number of evalutions used */
    public long [] evals;
    /**
     * Max evaluation used
     */
    public long evaluation;
    /**
     * Number of generations in one search
     */
    public int maxGens;
    /**
     * Best fitness found in one search
     */
    public double bestFoundValue;
    /**
     * TRUE to record statistic at generation-level, FALSE if otherwise
     */
    private boolean recordActivate = false;
    /**
     * Constructor
     * @param maxRuns Number of iterations allowed
     */
	public Performance(int maxRuns, boolean keepGenRecord)
	{
		// best found solution at this generation (evaluation)
		this.recordActivate = keepGenRecord;
		this.maxGens = 0;
		if (recordActivate)
		{
	    	this.bestFound = new double[maxRuns+1];
	    	// current best found solution
	    	this.currentBestFound = new double[maxRuns+1];
	    	// current action use in the search
	    	this.currAction = new byte[maxRuns+1];
	    	// current evaluations used
	    	this.evals = new long[maxRuns+ 1];
	    	
	    	this.threshold = new double[maxRuns + 1];
	    	for (int i= 0; i <= maxRuns; i++)
	    		threshold[i] = Double.MAX_VALUE;
		}
		else
			System.out.println("Recording per generation is not activated...");
	}
	public double getBestFound()
	{
		return this.bestFoundValue;
	}
	public void setBestFound(double val)
	{
		this.bestFoundValue = val;
	}
	/**
	 * Write the records to file
	 * @param filename
	 */
	public void toFile(String filename) 
	{
		if (!recordActivate) {
			System.out.println("ERROR: Archiving records is not activated...");
			return;
		}
		PrintStream MyOutput = null;
    	try {
    	       MyOutput = new PrintStream(new FileOutputStream(filename));
    	   }
    	catch (IOException e)
    	   {
    	      System.out.println("OOps");
    	   }
    	MyOutput.println("Iter,Evals,Act,CurrBest,BestF,(Threshold)");
    	for (int i = 0; i < maxGens; i++)
    	{
    		MyOutput.print(i + ",");
    		MyOutput.print(evals[i] + ",");
    		MyOutput.print(currAction[i] + ",");
    		MyOutput.print(currentBestFound[i] + ",");
    		MyOutput.print(bestFound[i] + ",");
    		MyOutput.print(threshold[i] + "\n");
    		MyOutput.flush();
    	}
    	MyOutput.close();
	}
	/**
	 * Write the records to MySQL database (not supported now)
	 * @param description
	 */
	public int toDatabase(String description)
	{
		if (!recordActivate) {
			System.out.println("ERROR: Archiving records is not activated...");
			return 0;
		}
		int res = 0;
		try {
			Connection aCon = 
				MySQLdbase.CreateConnection("localhost", "testdata", "test", "test");
			if (aCon == null) return -1;
//			 get max RunID in the run table
			String sqlString = "select max(runID) from run";
			int maxRunID = -1;
			ResultSet rs = MySQLdbase.QueryResultSet(aCon, sqlString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			if (rs != null) {
				if (rs.next())
					maxRunID = rs.getInt("max(runID)");
			}
			
			// access TABLES in database
			ResultSet run = MySQLdbase.RetrieveResultSet(aCon,"run",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet results = MySQLdbase.RetrieveResultSet(aCon,"results",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
						
			// update database
			maxRunID++;
			// insert into runs database
			Calendar cl = Calendar.getInstance();
			
			run.moveToInsertRow();
			run.updateString("description", description);
			run.updateInt("runID", maxRunID);
			run.updateLong("evaluations", evaluation);
			run.updateString("time", cl.getTime().toString());
			run.updateInt("maxGens", maxGens);
			run.insertRow();
			// insert into result database
			for (int i = 0; i < maxGens; i++)
	    	{
				results.moveToInsertRow();
				results.updateInt("runID", maxRunID);
				results.updateInt("iteration", i);
				results.updateInt("action", currAction[i]);
				results.updateDouble("currBest", currentBestFound[i]);
				results.updateDouble("bestFound", bestFound[i]);
				results.updateDouble("evals", evals[i]);
				results.insertRow();
	    	}
	    	
			// close connection
			aCon.close();
			
		} catch (Exception e)
		{
			System.out.println("Database errors...Exit");
			return -1;
		}
		return res;
	}
	
	public static void main(String[] args) {
		Performance perf = new Performance(2, true);
		perf.toDatabase("Test");
	}
}
