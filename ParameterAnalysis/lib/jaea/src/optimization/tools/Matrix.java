package optimization.tools;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
/**
 * Matrix class with standard matrix manipulation functions
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class Matrix {
	/**
	 * 2-D array to store matrix values
	 */
	public double [][] mat;
	/**
	 * Dimensions
	 */
	public int row, col;
	/**
	 * Indicate matrix with 0's
	 */
	final static public int M_ZEROS = 0;
	/**
	 * Indicate matrix with 1's
	 */
	final static public int M_ONES = 1;
	/**
	 * Indicate diagonal matrix
	 */
	final static public int M_DIAG = 2;
	/**
	 * Get function
	 */
	public double [][] getMat() {
		return mat;
	}
	/**
	 * Constructor
	 * @param r Number of rows
	 * @param c Number of columns
	 */
	public Matrix(int r, int c)
	{
		row = r;
		col = c;
		mat = new double[row][col];
	}
	/**
	 * Constructor
	 * @param r Number of rows
	 * @param c Number of columns
	 * @param code Initialize matrix with 0's, 1's or diagonal
	 */
	public Matrix(int r, int c, int code)
	{
		row = r;
		col = c;
		mat = new double[row][col];
		
		switch (code) {
		case Matrix.M_ZEROS:
			for (int i = 0; i < row; i ++)
				for (int j = 0; j < col; j++)
					mat[i][j] = 0;
			break;
		case Matrix.M_ONES:
			for (int i = 0; i < row; i ++)
				for (int j = 0; j < col; j++)
					mat[i][j] = 1;
			break;
		case Matrix.M_DIAG:
			for (int i = 0; i < row; i ++)
				for (int j = 0; j < col; j++)
					mat[i][j] = 0;
			int a = (row < col) ? row : col; 
			for (int i = 0; i < a; i ++)
					mat[i][i] = 1;
			break;
		default:
			break;
		}
	}
	/**
	 * Constructor as matrix 'wrapper' for double [][] 2D array (copy by reference)
	 */
	public Matrix(double [][] srcMat)
	{
		mat = srcMat;
		row = mat.length;
		col = mat[0].length;
	}
	/**
	 * Constructor as matrix 'wrapper' for double [] 1D array (copy by value)
	 */
	public Matrix(double [] srcMat)
	{
		row = 1;
		col = srcMat.length;
		mat = new double[row][col];
		for (int i = 0; i < col; i++)
			mat[0][i] = srcMat[i];
	}
	/**
	 * Transpose function
	 * @param inMat Input matrix
	 * @return Transposed matrix
	 */
	public static Matrix transpose(Matrix inMat)
	{
		double [] [] temp = new double[inMat.col][inMat.row];
		for (int i = 0; i < inMat.row; i++)
			for (int j = 0; j < inMat.col; j++)
				temp[j][i] = inMat.mat[i][j];
		return new Matrix(temp);
	}
	/**
	 * Get i-th row as 1D array 
	 * @param index Row index
	 */
	public double [] getFlatRow(int index)
	{
		double [] rowMat = new double[this.col];
		for (int i = 0; i < this.col; i++)
			rowMat[i] = this.mat[index][i];
		return rowMat;
	}
	/**
	 * Get i-th row as Matrix
	 * @param index Row index
	 */
	public Matrix getRow(int index)
	{
		double [] rowMat = new double[this.col];
		for (int i = 0; i < this.col; i++)
			rowMat[i] = this.mat[index][i];
		return new Matrix(rowMat);
	}
	/**
	 * Get i-th column as 1D array
	 * @param index Column index
	 */
	public double [] getFlatCol(int index)
	{
		double []colMat = new double [this.row];
		for (int i = 0; i < this.row; i++)
			colMat[i] = this.mat[i][index];
		return colMat;
	}
	/**
	 * Get i-th column as Matrix
	 * @param index Column index
	 */
	public Matrix getCol(int index)
	{
		double [][] colMat = new double [this.row][1];
		for (int i = 0; i < this.row; i++)
			colMat[i][0] = this.mat[i][index];
		return new Matrix(colMat);
	}
	/**
	 * Copy value from source to dest array
	 * @param dest Destination 1D array
	 * @param src Source 1D array
	 */
	static public void copyArray(double [] dest, double [] src)
	{
		if (dest.length != src.length) {
			System.err.println("Array mismatched...");
			return;
		}
		for (int i = 0; i < dest.length; i++)
		{
			dest[i] = src[i];
		}
	}
	/**
	 * Multiply matrices
	 * @param A Input matrix
	 * @param B Input matrix
	 * @return Matrix C = A*B
	 */
	public static Matrix multiply(Matrix A, Matrix B)
	{
		double [][] res = new double [A.row][B.col];
		for (int i = 0; i < A.row; i++)
			for (int j = 0; j < B.col; j++)
			{
				double temp = 0;
				for (int k = 0; k < A.col; k++)
					if (Math.abs(A.mat[i][k]) > 1E-9 && 
							Math.abs(B.mat[k][j]) > 1E-9)
						temp += A.mat[i][k]*B.mat[k][j];
				res[i][j] = temp;
			}
		return (new Matrix(res));
	}
	/**
	 * Multiply matrix with scalar
	 * @param A Input matrix
	 * @param d Scalar value
	 * @return Matrix C = d*A
	 */
	public static Matrix multiply(Matrix A, double d)
	{
		double [][] res = new double [A.row][A.col];
		for (int i = 0; i < A.row; i++)
			for (int j = 0; j < A.col; j++)
			{
				res[i][j] = d*A.mat[i][j];
			}
		return (new Matrix(res));
	}
	/**
	 * Add matrices
	 * @param A Input matrix
	 * @param B Input matrix
	 * @return Matrix C = A + B
	 */
	public static Matrix add(Matrix A, Matrix B)
	{
		double [][] res = new double [A.row][A.col];
		for (int i = 0; i < A.row; i++)
			for (int j = 0; j < A.col; j++)
			{
				res[i][j] = A.mat[i][j] + B.mat[i][j];
			}
		return (new Matrix(res));
	}
	/**
	 * Add matrix and scalar
	 * @param A Input matrix
	 * @param d Scalar value
	 */
	public static Matrix add(Matrix A, double d)
	{
		double [][] res = new double [A.row][A.col];
		for (int i = 0; i < A.row; i++)
			for (int j = 0; j < A.col; j++)
			{
				res[i][j] = A.mat[i][j] + d;
			}
		return (new Matrix(res));
	}
	/**
	 * Generate a m-by-n tiling of copies of input matrix
	 * @param matIn Input matrix
	 * @param m  
	 * @param n
	 */
	public static Matrix repmat(Matrix matIn, int m, int n)
	{
		int r = matIn.row;
		int c = matIn.col;
		double [] [] res = new double [m*r] [n*c] ;
		for (int outR = 0; outR < m; outR++)
			for (int outC = 0; outC < n; outC++)
			{
				int indexR = outR * r;
				int indexC = outC * c;
				for (int i = 0; i < r; i++)
					for (int j = 0; j < c; j++)
						res[indexR + i][indexC +j] = matIn.mat[i][j];
			}
		Matrix resM = new Matrix(res);
		return resM;
	}
	/**
	 * Return lower triangular part of matrix
	 * @param matIn Input matrix
	 * @return Lower triangular matrix
	 */
	public static Matrix tril(Matrix matIn)
	{
		int r = matIn.row;
		int c = matIn.col;
		int n = (r <= c) ? r : c;
		double [][] res = new double[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (j <= i)
					res[i][j] = matIn.mat[i][j];
				else
					res[i][j] = 0;
		Matrix resM = new Matrix(res);
		return resM;
	}
	/**
	 * Copy rows from srcMat to rows in destMat
	 * @param destMat Destination matrix
	 * @param destInd Row indices of destination matrix 
	 * @param srcMat Source matrix
	 * @param srcInd Row indices of source matrix to be copied from
	 */
	public static void copyRows(Matrix destMat, int [] destInd,
			Matrix srcMat, int [] srcInd)
	{
		if (srcInd.length != destInd.length)
		{
			System.err.println("Index mismatched...");
			return;
		}
		if (destMat.col != srcMat.col)
		{
			System.err.println("Matrix mismatched...");
			return;
		}
		int nRows = destInd.length;
		for (int n = 0; n < nRows; n++)
		{
			for (int c = 0; c < srcMat.col; c++)
				destMat.mat[destInd[n]][c] = 
					srcMat.mat[srcInd[n]][c];
		}
	}
	/**
	 * Copy columns from srcMat to columns in destMat
	 * @param destMat Destination matrix
	 * @param destInd Column indices of destination matrix
	 * @param srcMat Source matrix
	 * @param srcInd Column indices of source matrix
	 */
	public static void copyCols(Matrix destMat, int [] destInd,
			Matrix srcMat, int [] srcInd)
	{
		if (srcInd.length != destInd.length)
		{
			System.err.println("Index mismatched...");
			return;
		}
		if (destMat.row != srcMat.row)
		{
			System.err.println("Matrix mismatched...");
			return;
		}
		int nCols = destInd.length;
		for (int n = 0; n < nCols; n++)
		{
			for (int r = 0; r < srcMat.row; r++)
				destMat.mat[r][destInd[n]] = 
					srcMat.mat[r][srcInd[n]];
		}
	}
	/**
	 * Subtraction operator
	 * @param A Input matrix
	 * @param B Input matrix
	 * @return Matrix C = A - B
	 */
	public static Matrix substract(Matrix A, Matrix B)
	{
		double [][] res = new double [A.row][A.col];
		for (int i = 0; i < A.row; i++)
			for (int j = 0; j < A.col; j++)
			{
				res[i][j] = A.mat[i][j] - B.mat[i][j];
			}
		return (new Matrix(res));
	}
	/**
	 * Write matrix to file in CSV format
	 * @param filename Output file name
	 * @param matC Matrix
	 */
	static public void DMat2File(String filename, Matrix matC) {
		double [][] matrix = matC.mat;
		/* prepare log file */
    	PrintStream MyOutput = null;
    	try {
    	       MyOutput = new PrintStream(new FileOutputStream(filename));
    	   }
    	catch (IOException e)
    	   {
    	      System.out.println("IO Error in Mat2File");
    	   }
    	// output all population
    	int maxRow = matC.row;
    	int maxCol = matC.col;
    	MyOutput.println(maxRow);
    	MyOutput.println(maxCol);
    	for (int i = 0; i < maxRow; i++) {
    		for (int j = 0; j < maxCol; j++)
    			if (j < maxCol -1 )
    				MyOutput.print(matrix[i][j] + ",");
    			else
    				MyOutput.println(matrix[i][j]);
    	}
	}
	/**
	 * Read matrix from file in CSV format [row \n col \n matrix]
	 */
	static public Matrix File2DMat(String filename) {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader input = new BufferedReader(fr);
			String s = "";
			/* Pass 2 first lines */
			s = input.readLine();
			int N = Integer.parseInt(s);
			s = input.readLine();
			int dim = Integer.parseInt(s);
			double [][] matrix = new double[N][dim];
			int count = 0;
			do {
				s = input.readLine();
				if (s != null) {
					String [] params = s.split(",");
					for (int k = 0; k < params.length; k++)
						matrix[count][k] = Double.parseDouble(params[k]);
					count++;	       
				}
			} 
			while (s != null);
			fr.close();
			Matrix res = new Matrix(matrix);
			return res;
		}
		catch (IOException e)
		{
			System.out.println(e.toString());
			return null;
		}
	}
	
	public String toString()
	{
		String res = "";
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
				if (j == col - 1)
					res += mat[i][j]+ "\n";
				else
					res += mat[i][j]+ ", ";
		}
		return res;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Matrix A = new Matrix(4,4,Matrix.M_ONES);
//		Matrix B = new Matrix(4,4,Matrix.M_DIAG);
//		Matrix C = Matrix.multiply(Matrix.add(A, B),B);
//		System.out.println("A: \n" + A.toString());
//		System.out.println("B: \n" + B.toString());
//		System.out.println("C: \n" + C.toString());
//		Matrix.DMat2File("C.csv", C);
		Matrix T = Matrix.File2DMat("C.csv");
		System.out.println(T.toString());
	}

}
