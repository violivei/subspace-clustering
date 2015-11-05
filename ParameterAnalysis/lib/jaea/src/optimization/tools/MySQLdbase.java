package optimization.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Class of functions to facilitate connnection to MySQL database for process/storing data
 * @author Le Minh Nghia, NTU-Singapore
 *
 */
public class MySQLdbase {

	public static java.sql.Connection CreateConnection(String host, String db, String user, String pwd){
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + host + ":3306/" + db;
			Connection con = DriverManager.getConnection(url,user,pwd);
			
			return con;
			
		}
		catch(Exception e){
			System.out.print(e.getLocalizedMessage() + "\n");
			System.out.println(e.getCause().toString());
			return null;
		}
	}
	
	public static boolean InsertRecord(Connection con, String query){
		
		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate(query);
			return true;
		}
		catch(Exception e){
			return false;
		}
		
	}
	
	public static ResultSet RetrieveResultSet(Connection con, String tableName,int resultSetType, int resultSetConcurrency){
		
		try{
			Statement stmt;
			stmt = con.createStatement(resultSetType,resultSetConcurrency);

		    ResultSet rs;
			rs = stmt.executeQuery("SELECT * from " + tableName);
			
			return rs;
		}
		catch(Exception e){
			return null;
		}
	}
	
	public static ResultSet QueryResultSet(Connection con, String query,int resultSetType, int resultSetConcurrency){
		
		try{
			Statement stmt;
			stmt = con.createStatement(resultSetType,resultSetConcurrency);

		    ResultSet rs;
			rs = stmt.executeQuery(query);
			
			return rs;
		}
		catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 
	 * @param con
	 * @param query
	 * @param resultSetType ResultSet.TYPE_SCROLL_INSENSITIVE --> not case-sensitive (table name, field name, etc are not case sensitive)
	 * @param resultSetConcurrency ResultSet.CONCUR_UPDATABLE --> control concurrency in update/delete recordset
	 */
	public static boolean ExecuteUpdate(Connection con, String query,int resultSetType, int resultSetConcurrency){
		try{
			Statement stmt;
			stmt = con.createStatement(resultSetType,resultSetConcurrency);

		    int rs = stmt.executeUpdate(query);	
			if (rs != 0) return true;
			else return false;
		}
		catch(Exception e){
			return false;
		}
	}
}
