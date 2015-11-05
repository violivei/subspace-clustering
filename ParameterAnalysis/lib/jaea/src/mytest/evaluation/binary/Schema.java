package mytest.evaluation.binary;

/**
 * Model of schemata for binary search space
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Schema {
	int [] loci;
	byte [] allele;
	public int order;
	/**
	 * Constructor
	 * @param o Order of schema, i.e., defined as a number of bit 0|1
	 * @param l Loci index of defined bit
	 * @param a Value/allele at the loci
	 */
	public Schema(int o, int [] l, byte [] a)
	{
		if (o != l.length || o != a.length) {
			System.err.println("Schema: order disagreement...");
			System.exit(0);
		}
		order = o;
		loci = new int[order];
		allele = new byte[order];
		for (int i = 0; i < order; i++)
		{
			loci[i] = l[i];
			allele[i] = a[i];
		}
	}
	/**
	 * Check if the solution string matches with the schema
	 * @param indiv
	 * @return TRUE if matched, FALSE if otherwise
	 */
	public boolean isInSchema(byte [] indiv)
	{
		boolean res = true;
		for (int i = 0; i < loci.length; i++)
		{
			if (indiv[loci[i]] != allele[i])
				res = false;
		}
		return res;
	}
	
	public String toString(int nDim)
	{
		String res = "";
		byte [] temp = new byte[nDim];
		for (int i = 0; i < nDim; i++)
		{
			temp[i] = -1;
		}
		for (int i = 0; i < order; i++)
		{
			temp[loci[i]] = allele[i];
		}
		for (int i = 0; i < nDim; i++)
			if (temp[i] < 0) res += "*";
			else res += temp[i];
		return res;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [] l = {1,2,3};
		byte [] a = {0, 0, 0};
		byte [] indiv = {1, 0, 0, 1, 1, 1, 1};
		Schema s = new Schema(l.length, l , a);
		System.out.println((s.isInSchema(indiv) ? "Is in" : "Is NOT in"));
	}

}
