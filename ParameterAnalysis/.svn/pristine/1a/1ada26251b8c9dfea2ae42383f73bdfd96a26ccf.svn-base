package optimization.searchspace;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;

import optimization.tools.Utils;
/**
 * Chromosome class - basic representation of a part of solution string
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Chromosome {
	/**
	 * Indicate solution string in real-coded 
	 */
	public static byte FLOAT = 0;
	/**
	 * Indicate solution string in binary
	 */
	public static byte BINARY = 1;
	
	/**
	 * Number of dimensions/design variables
	 */
	public int n_Dim = 0;
	/**
	 * Type of string representation: binary or real-coded
	 */
	public byte type;
	// public so that all methods are available
	@SuppressWarnings("unchecked")
	/**
	 * String representation in Vector type 
	 */
	public Vector chromosome = null; 
	/**
	 * Upper bounds for each dimensions (for real-coded)
	 */
	public double [] uBound = null;
	/**
	 * Lower bounds for each dimensions (for real-coded)
	 */
	public double [] lBound = null;
	
	/**
	 * Constructor, by default value of chromosome is in [0,1]
	 * @param type Binary or Real-coded
	 * @param dim Number of design variabls
	 * @param doInit TRUE to randomly initialze solution, FALSE if otherwise. Default values is 0 
	 * for both types
	 */
	@SuppressWarnings("unchecked")
	public Chromosome(byte type, int dim, boolean doInit) {
		this.type = type;
		
		if (type == Chromosome.FLOAT) {
			chromosome = new Vector<Double>();
			n_Dim = dim;
			uBound = new double[dim];
			lBound = new double[dim];
			for (int i = 0; i < dim; i++) {
				uBound[i] = 1;
				lBound[i] = 0;
			}
			for (int i = 0; i < n_Dim; i++)
				chromosome.addElement(new Double(0));
		}
		else if (type == Chromosome.BINARY) {
			chromosome = new Vector<Byte>();
			n_Dim = dim; 
			for (int i = 0; i < n_Dim; i++)
				chromosome.addElement(new Byte("0"));
		}
		/* initialize */
		if (doInit)
			initialize();
	}

	/**
	 * Constructors with lower and upper bound specified
	 * @param type Binary or Real-coded
	 * @param dim Number of design variabls
	 * @param doInit TRUE to randomly initialze solution, FALSE if otherwise
	 * @param u Array of upper bounds (NOT USED, default is 0, 1)
	 * @param l Array of lower bounds (NOT USED, default is 0, 1)
	 */
	@SuppressWarnings("unchecked")
	public Chromosome(byte type, int dim, double [] l, double [] u, boolean doInit) {
		this.type = type;
		
		if (type == Chromosome.FLOAT) {
			chromosome = new Vector<Double>();
			n_Dim = dim;
			uBound = u;
			lBound = l;
			for (int i = 0; i < n_Dim; i++)
				chromosome.addElement(new Double(0));
		}
		else if (type == Chromosome.BINARY) {
			chromosome = new Vector<Byte>();
			n_Dim = dim; 
			for (int i = 0; i < n_Dim; i++)
				chromosome.addElement(new Byte("0"));
		}
		/* initialize */
		if (doInit)
			initialize();
	}
	/**
	 * Constructor for real-coded chromosome with the same initial values 
	 * @param type Valid value = Chromosome.FLOAT
	 * @param val Initial values
	 * @param l Array of lower bounds
	 * @param u Array of upper bounds
	 */
	@SuppressWarnings("unchecked")
	public Chromosome(byte type, double [] val, double [] l, double [] u) {
		this.type = type;
		
		if (type == Chromosome.FLOAT) {
			chromosome = new Vector<Double>();
			n_Dim = val.length;
			uBound = u;
			lBound = l;
			for (int i = 0; i < n_Dim; i++)
				chromosome.addElement(new Double(val[i]));
		}
	}
	/**
	 * Constructor for binary chromosome with the same initial values 
	 * @param type Valid value = Chromosome.BINARY
	 * @param val Initial values
	 * @param l Array of lower bounds (NOT USED, default is 0, 1)
	 * @param u Array of upper bounds (NOT USED, default is 0, 1)
	 */
	@SuppressWarnings("unchecked")
	public Chromosome(byte type, byte [] val, double [] l, double [] u) {
		this.type = type;
		
		if (type == Chromosome.BINARY) {
			chromosome = new Vector<Byte>();
			n_Dim = val.length; 
			for (int i = 0; i < n_Dim; i++)
				chromosome.addElement(new Byte(val[i]));
		}
	}
	/**
	 * Initialize chromosome with uniform distribution
	 */
	@SuppressWarnings("unchecked")
	public void initialize() {
		for (int i = 0; i < this.n_Dim; i++) {
			if (this.type == Chromosome.FLOAT) {
				double temp = lBound[i] + Utils.getRandom(uBound[i] - lBound[i]);
				chromosome.setElementAt(new Double(temp), i);
			}
			else if (this.type == Chromosome.BINARY) {
				 
				byte temp;
				if (Utils.getRandom(1.0) > 0.5) 
					temp = 1;
				else 
					temp = 0;
				chromosome.setElementAt(new Byte(temp), i);
			}
		}
	}
	/** 
	 * Return real-coded chromsome in double [] format. Only apply for FLOAT type
	 * @return Solution string in double []
	 */
	public double [] getDoubleArray() {
		double [] res = null;
		if (this.type == Chromosome.FLOAT) {
			res = new double[this.n_Dim];
			for (int i = 0; i < n_Dim; i++)
				res[i] = ((Double) chromosome.elementAt(i)).doubleValue();
		}
		return res;
	}
	/** 
	 * Return binary chromsome in byte [] format. Only apply for BINARY type
	 * @return Solution string in byte []
	 */
	public byte [] getByteArray() {
		byte [] res = null;
		if (this.type == Chromosome.BINARY) {
			res = new byte[this.n_Dim];
			for (int i = 0; i < n_Dim; i++)
				res[i] = ((Byte) chromosome.elementAt(i)).byteValue();
		}
		return res;
	}
	/**
	 * toString() function
	 */
	public String toString() {
		String resStr="";
		for (int i = 0; i < this.n_Dim; i++)
			if (i == n_Dim - 1)
				resStr += chromosome.elementAt(i);
			else
				resStr += chromosome.elementAt(i) + ", ";
		
		return resStr;
	}
	/**
	 * Copy-by-value genes from input chromosome
	 * @param srcGene Input chromosome
	 */
	@SuppressWarnings("unchecked")
	public void copyChrom(Chromosome srcGene) {
		this.n_Dim = srcGene.n_Dim;
		this.chromosome = new Vector();
		this.type = srcGene.type;
		if (srcGene.type == Chromosome.FLOAT) {
			uBound = new double[n_Dim];
			lBound = new double[n_Dim];
			for (int i = 0; i < srcGene.chromosome.size(); i++) {
				double temp = ((Double) srcGene.chromosome.elementAt(i)).doubleValue();
				chromosome.addElement(new Double(temp));
				this.lBound[i] = srcGene.lBound[i];
				this.uBound[i] = srcGene.uBound[i];
			}
		}
		else if (srcGene.type == Chromosome.BINARY) {
			for (int i = 0; i < srcGene.chromosome.size(); i++) {
				byte temp = ((Byte) srcGene.chromosome.elementAt(i)).byteValue();
				chromosome.addElement(new Byte(temp));
			}
		}
	}
	/**
	 * Constructor - cloning a chromosome
	 * @param srcChrom Input chromosome
	 */
	public Chromosome(Chromosome srcChrom) {
		copyChrom(srcChrom);
	}
	/**
	 * Change double value in chromosome (applicable for REAL-CODED chromosome)
	 * @param index Gene location/loci
	 * @param value Value to be assigned
	 */
	@SuppressWarnings("unchecked")
	public void setGene(int index, double value) {
		if (index < this.n_Dim) {
			// Range checking
			double range = uBound[index] - lBound[index];
			if (value > uBound[index]) value = uBound[index] - range*Math.random();
			else if (value < lBound[index])
				value = lBound[index] + range* Math.random();
			// Assign
			this.chromosome.setElementAt(new Double(value), index);
		}
	}
	/**
	 * Change binary value in chromosome (applicable for BINARY chromosome)
	 * @param index Gene location/loci
	 * @param value Value to be assigned
	 */
	@SuppressWarnings("unchecked")
	public void setGene(int index, byte value) {
		if (index < this.n_Dim)
			this.chromosome.setElementAt(new Byte(value), index);
	}
	/**
	 * Get gene value at specified location (applicable for REAL-CODED chromosome)
	 * @param index Gene location/loci
	 * @return Gene value
	 */
	public double getGeneAsDouble(int index)
	{
		return ((Double) chromosome.elementAt(index)).doubleValue();
	}
	/**
	 * Get gene value at specified location (applicable for BINARY chromosome)
	 * @param index Gene location/loci
	 * @return Gene value
	 */
	public byte getGeneAsByte(int index)
	{
		return ((Byte) chromosome.elementAt(index)).byteValue();
	}
	/**
     * Export chromosome to format used in YALE analysis package (OLD FUNCTION)
     * @param filenameNoExtension
     */
    public void toYaleFiles(String filenameNoExtension) {
    	String fileAML = filenameNoExtension + ".aml";
    	String fileDAT = filenameNoExtension + ".dat";
    	// generate AML file to describe list of attributes
    	PrintStream MyOutput = null;
    	try {
    	       MyOutput = new PrintStream(new FileOutputStream(fileAML));
    	   }
    	catch (IOException e)
    	   {
    	      System.out.println("OOps");
    	   }

    	// List out all attributes
    	if (MyOutput != null) {
    		MyOutput.println("<attributeset default_source=\""+
    				fileDAT + "\">");
    		for (int i = 0; i < n_Dim; i++) {
    			MyOutput.println("<attribute");
    			MyOutput.println("name =\"" + i + "\"");
    			MyOutput.println("sourcecol =\"" + (i+1) + "\"");
    			MyOutput.println("valuetype  =\"real\"");
    			MyOutput.println("blocktype  =\"single_value\"");
    			MyOutput.println("/>");
    		}
    		MyOutput.println("<label");
    		MyOutput.println("name =\"class\"");
    		MyOutput.println("sourcecol =\""+ (n_Dim+1)+"\"");
    		MyOutput.println("valuetype =\"nominal\"");
    		MyOutput.println("blocktype  =\"single_value\"");
    		MyOutput.println("classes =\"\"");
    		MyOutput.println("/>");
    		
    		MyOutput.println("</attributeset>");
    	    MyOutput.close();
    	} else {
    		System.out.println("No output file written");
    	}
    }
	public static void main(String[] args) {
		Chromosome a = new Chromosome(Chromosome.BINARY,3,true);
		System.out.println(a.toString());
		Chromosome b = new Chromosome(Chromosome.BINARY,2, true);
		b.copyChrom(a);
		System.out.println(b.toString());
	}
}
