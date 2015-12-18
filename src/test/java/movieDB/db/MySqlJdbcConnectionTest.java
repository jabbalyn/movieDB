package movieDB.db;

import org.junit.Test;
import java.util.Date;

//DO NOT import com.mysql.jdbc.* -> will create problems

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySqlJdbcConnectionTest {

	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs1 = null;
	ResultSet rs2 = null;
	
	int visited;
	
	@Test
	public void testConnectionToMySQLDatabaseviaJDBC() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost/test?" +
				                                   "user=root&password=$hollywood1945");


			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
										ResultSet.CONCUR_UPDATABLE);

			rs1 = stmt.executeQuery("select * from users");
		
			while (rs1.next()) {
				int userID = rs1.getInt(1);
				String firstName = rs1.getString(2);
				String lastName = rs1.getString(3);
				Date birth = rs1.getDate(4);
				int children = rs1.getInt(5);
				String ethnicity = rs1.getString(6);
				visited = rs1.getInt(7);
				rs1.updateInt(7, visited + 1);
				rs1.updateRow();
				visited = rs1.getInt(7);
				
				System.out.println(userID + ": User - " + firstName + " " + lastName
						+ ", " + birth + ", " + children + " children"
						+ ", ethnicity: " + ethnicity + ", visited: " + visited);
			}
			

			pstmt = conn.prepareStatement(
					"SELECT USERID, FIRST, LAST, VISITED FROM USERS where first = ? or last = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_UPDATABLE);

			pstmt.setString(1, "Juan");
			pstmt.setString(2,  "Greystone");
			rs2 = pstmt.executeQuery();

			while (rs2.next()) {
				// for an updatable result set, need to include the primary key in the query even
				// if we don't use it
				int userID = rs2.getInt(1);
				String first = rs2.getString(2);
				String last = rs2.getString(3);
				visited = rs2.getInt(4);
				
				rs2.updateInt(4, visited + 1);
				rs2.updateRow();
				visited = rs2.getInt(4);
				
				System.out.println("Match found: " + "userID: " + userID +"," + first + " " + last 
						+ ", visited: " + visited);
			}
			
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} catch (Exception ex) {
			System.out.println("error while specifying which JDBC Driver to use: "
					+ ex.getMessage());
			throw ex;
		}
	
		// release all resources
		
		if (rs1 != null) {
			try {
				rs1.close();
			} catch (SQLException ex) {
				// ignore for now
			}
		}

		if (rs2 != null) {
			try {
				rs2.close();
			} catch (SQLException ex) {
				// ignore for now
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				// ignore for now
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException ex) {
				// ignore for now
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				// ignore for now
			}
		}
	}
}
