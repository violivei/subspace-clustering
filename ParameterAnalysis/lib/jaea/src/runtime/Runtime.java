package runtime;

import org.w3c.dom.Element;
/**
 * Class to process parameters passed in Runtime section
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Runtime {
	public String savedDir = "";
	public String evalFilename = "";
	// Number of runs per execution
	public int numRuns = 50;
	// Option to change the output stream to files
	public boolean writeIO2File = false;
	// Option to write best fitness/evaluation to file
	public boolean writeEvaluation2File = false;
	// Option to write population (fitness) to text file
	public boolean writePop2File = false;
	// Option to write records to file
	public boolean writeResults2File = false;
	// Option to write ruleset to file - read by Matlab to visualize
	public boolean writeRBox2File = false;
	// Option to write records to MySQL database
	public boolean writeResults2DB = false;
	// Option to enable visualization in 2D - Not suggested in operation
	public boolean enableVisualization = false;
	// Option to write ruleset to file (in readable format)
	public boolean writeRules2File = false;	
	
	// Option to write labelled population (fitness) to text file
	public boolean writeClassifiedPop2File = false;
	
	/* Stop criteria *************************/
	// Maximum allowed evaluations - Computational budget
	public long maxEvaluation = 300000;
	// Improvement threshold made by searching
	public double deltaThreshold = Double.MIN_VALUE;
	public double fitThreshold =  -128;
	// Max generation allowed
	public int maxGenerations = 10000;
	public double globalOptimum = 0.0;
	public Runtime(Element elem) {
		savedDir = ConfigContainer.getTextValue(elem, "savedDir");
		evalFilename = ConfigContainer.getTextValue(elem, "evalFilename");
		// Number of runs per execution
		numRuns = ConfigContainer.getIntValue(elem,"numRuns");
		// Option to change the output stream to files
		writeIO2File = ConfigContainer.getBoolValue(elem,"writeIO2File");
		// Option to write best fitness/evaluation to file
		writeEvaluation2File = ConfigContainer.getBoolValue(elem,"writeEvaluation2File");
		// Option to write population (fitness) to text file
		writePop2File = ConfigContainer.getBoolValue(elem,"writePop2File");
		// Option to write records to file
		writeResults2File = ConfigContainer.getBoolValue(elem,"writeResults2File");
		// Option to write ruleset to file - read by Matlab to visualize
		writeRBox2File = ConfigContainer.getBoolValue(elem,"writeRBox2File");
		// Option to write records to MySQL database
		writeResults2DB = ConfigContainer.getBoolValue(elem,"writeResults2DB");
		// Option to enable visualization in 2D - Not suggested in operation
		enableVisualization = ConfigContainer.getBoolValue(elem,"enableVisualization");
		// Option to write ruleset to file (in readable format)
		writeRules2File = ConfigContainer.getBoolValue(elem,"writeRules2File");	
		
		// Option to write labelled population (fitness) to text file
		writeClassifiedPop2File = ConfigContainer.getBoolValue(elem,"writeClassifiedPop2File");	
		
		/* Stop criteria *************************/
		// Maximum allowed evaluations - Computational budget
		maxEvaluation = ConfigContainer.getLongValue(elem, "maxEvaluation");
		// Improvement threshold made by searching
		deltaThreshold = ConfigContainer.getDoubleValue(elem, "deltaThreshold");
		fitThreshold = ConfigContainer.getDoubleValue(elem,"fitThreshold");
		// Max generation allowed
		maxGenerations = ConfigContainer.getIntValue(elem, "maxGenerations");
		globalOptimum = ConfigContainer.getDoubleValue(elem,"globalOptimum");
	}
	public String toString()
	{
		String res = "";
		res += "numRuns = " + numRuns + "\n";
		// Option to change the output stream to files
		res += "writeIO2File = " + writeIO2File + "\n";
		// Option to write best fitness/evaluation to file
		res += "writeEvaluation2File = " + writeEvaluation2File + "\n";
		// Option to write population (fitness) to text file
		res += "writePop2File = " + writePop2File + "\n";
		// Option to write records to file
		res += "writeResults2File = "+ writeResults2File + "\n";
		// Option to write ruleset to file - read by Matlab to visualize
		res += "writeRBox2File = " + writeRBox2File + "\n";
		// Option to write records to MySQL database
		res += "writeResults2DB = " + writeResults2DB + "\n";
		// Option to enable visualization in 2D - Not suggested in operation
		res += "enableVisualization = " + enableVisualization + "\n";
		// Option to write ruleset to file (in readable format)
		res += "writeRules2File = " + writeRules2File+ "\n";	
		
		// Option to write labelled population (fitness) to text file
		res += "writeClassifiedPop2File = " + writeClassifiedPop2File+ "\n";	
		
		/* Stop criteria *************************/
		// Maximum allowed evaluations - Computational budget
		res += "maxEvaluation = " + maxEvaluation + "\n";
		// Improvement threshold made by searching
		res += "deltaThreshold = "+ deltaThreshold +"\n";
		res += "fitThreshold = " + fitThreshold + "\n";
		// Max generation allowed
		res += "maxGenerations = " + maxGenerations + "\n";
		res += "globalOptimum = " + globalOptimum + "\n";
		return res;
	}
}
