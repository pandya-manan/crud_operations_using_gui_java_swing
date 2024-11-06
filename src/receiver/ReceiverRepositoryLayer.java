package receiver;
import java.sql.*;
public class ReceiverRepositoryLayer {

	//Establishing the connection properties
	 private String url = ReceiverDBProperties.getDbURL();
	    private String username = ReceiverDBProperties.getDbUsername();
	    private String password = ReceiverDBProperties.getDbPassword();
	    private Connection toDatabase;

	    public void databaseConnection() throws SQLException {
	        this.toDatabase = getConnection();
	    }

	    // Connecting the application to the database
	    public Connection getConnection() throws SQLException {
	        if (toDatabase == null || toDatabase.isClosed()) {
	            toDatabase = DriverManager.getConnection(url, username, password);
	            if(toDatabase == null || toDatabase.isClosed())
	            {
	            	System.out.println("Connection not established");
	            }
	            else
	            {
	            	System.out.println("Connection established successfully");
	            }
	        }
	        return toDatabase;
	    }
	
	
	//Logic to insert data into the database
	public void insertNewBusiness(Integer receiverId,String name,String address,String email) throws SQLException
	{
		Connection conn=getConnection();
		String insertQuery="INSERT INTO receiver(r_id,name,address,email) VALUES (?,?,?,?)";
		try
		{
			PreparedStatement insertStatement=conn.prepareStatement(insertQuery);
			insertStatement.setInt(1, receiverId);
			insertStatement.setString(2, name);
			insertStatement.setString(3,address);
			insertStatement.setString(4, email);
			insertStatement.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("The exception caught while trying to add a new receiver is: "+e.getMessage());
		}
		
	}
	
	//Logic to read data from the database
	public ResultSet readBusiness() throws SQLException
	{
		Connection conn=getConnection();
		String query="SELECT * FROM receiver";
		try
		{
			PreparedStatement readData=conn.prepareStatement(query);
			return readData.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println("The exception caught while trying to read data from the receiver table is: "+e.getMessage());
			return null;
		}
	}
	
	//Logic to update data in the database
	public void updateBusiness(int r_id, String name, String address, String email) {
	    String query = "UPDATE receiver SET name = ?, address = ?, email = ? WHERE r_id = ?";

	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, name);
	        stmt.setString(2, address);
	        stmt.setString(3, email);
	        stmt.setInt(4, r_id);  // Setting b_id as an integer

	        int rowsUpdated = stmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Receiver record updated successfully!");
	        } else {
	            System.out.println("No record found with the specified ID.");
	        }

	    } catch (SQLException e) {
	        System.err.println("Error updating receiver record: " + e.getMessage());
	    }
	}

	
	//Logic to delete data in the database
	public void deleteBusiness(Integer receiverId)
	{
		String deleteSQL="DELETE FROM receiver WHERE r_id=?";
		try
		{
			PreparedStatement deleteStatement=toDatabase.prepareStatement(deleteSQL);
			deleteStatement.setInt(1, receiverId);
			deleteStatement.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("The exception caught while trying to delete data from the receiver table is: "+e.getMessage());
		}
	}

}
