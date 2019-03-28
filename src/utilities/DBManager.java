package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

	private Connection con = null;
	
	private Statement stmt = null;
	ResultSet Result;
	
	public void DBConnection(String DBPath,String username,String password) throws SQLException{
		
		
		DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
		System.out.println("Registered Driver");
		
		con = DriverManager.getConnection(DBPath,username,password);
		
		System.out.println("Database connection done");
		
		stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		
		System.out.println("statement created");
		
	}
	
	
	public ResultSet getResultset(String DBQuery) throws SQLException{
		try {
		
		Result = stmt.executeQuery(DBQuery);
		
		
		
	} catch (Exception e){
		System.out.println(e.getMessage());
	
	}
	
		return Result;	
	}
	
	public void SetData(String ClaimId, String documentId, String doccategoryid, String docstatusId, String docname,
			String docurl,String createdon, String author,String tableName) throws SQLException {

		int isProcessed = 1;
		int isActive = 1;

		try {
			String datam = "(" + ClaimId + ",'" + documentId + "','" + doccategoryid + "','" + docstatusId + "','" + docname + "','"
					+ docurl + "','" + isActive + "','" + createdon + "','" + author +"');";

			System.out.println("DataM " + datam);

		} catch (Exception e) {
			System.out.println(e);
		}
		
		System.out.println("INSERT INTO " + tableName
				+ "(ClaimId, DocumentID, DocCategoryID, DocStatusID,DocsName, DocumentUrl, IsActive, CreatedOn, CreatedBy) values ('"
				+ ClaimId + "','" + documentId + "','" + doccategoryid + "','" + docstatusId + "','" + docname + "','" + docurl
				+ "','" + isActive + "','" + createdon + "','" + author + "');");
		stmt.executeUpdate("INSERT INTO " + tableName
				+ "(ClaimId, DocumentID, DocCategoryID, DocStatusID,DocsName, DocumentUrl, IsActive, CreatedOn, CreatedBy) values ('"
				+ ClaimId + "','" + documentId + "','" + doccategoryid + "','" + docstatusId + "','" + docname + "','" + docurl
				+ "','" + isActive + "','" + createdon + "','" + author + "');");
		System.out.println("data inserted in table");

		tear();

	}
	
	public void tear() {
		con = null;
	}
	

}
