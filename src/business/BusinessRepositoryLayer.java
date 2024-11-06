package business;
import java.sql.*;
public class BusinessRepositoryLayer {

	//Establishing the connection properties
	 private String url = BusinessDBProperties.getDbURL();
	    private String username = BusinessDBProperties.getDbUsername();
	    private String password = BusinessDBProperties.getDbPassword();
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
	public void insertNewBusiness(Integer businessId,String name,String address,String email) throws SQLException
	{
		Connection conn=getConnection();
		String insertQuery="INSERT INTO business(b_id,name,address,email) VALUES (?,?,?,?)";
		try
		{
			PreparedStatement insertStatement=conn.prepareStatement(insertQuery);
			insertStatement.setInt(1, businessId);
			insertStatement.setString(2, name);
			insertStatement.setString(3,address);
			insertStatement.setString(4, email);
			insertStatement.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("The exception caught while trying to add a new business is: "+e.getMessage());
		}
		
	}
	
	//Logic to read data from the database
	public ResultSet readBusiness() throws SQLException
	{
		Connection conn=getConnection();
		String query="SELECT * FROM business";
		try
		{
			PreparedStatement readData=conn.prepareStatement(query);
			return readData.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println("The exception caught while trying to read data from the business table is: "+e.getMessage());
			return null;
		}
	}
	
	//Logic to update data in the database
	public void updateBusiness(int b_id, String name, String address, String email) {
	    String query = "UPDATE business SET name = ?, address = ?, email = ? WHERE b_id = ?";

	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, name);
	        stmt.setString(2, address);
	        stmt.setString(3, email);
	        stmt.setInt(4, b_id);  // Setting b_id as an integer

	        int rowsUpdated = stmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Business record updated successfully!");
	        } else {
	            System.out.println("No record found with the specified ID.");
	        }

	    } catch (SQLException e) {
	        System.err.println("Error updating business record: " + e.getMessage());
	    }
	}

	
	//Logic to delete data in the database
	public void deleteBusiness(Integer businessId)
	{
		String deleteSQL="DELETE FROM business WHERE b_id=?";
		try
		{
			PreparedStatement deleteStatement=toDatabase.prepareStatement(deleteSQL);
			deleteStatement.setInt(1, businessId);
			deleteStatement.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("The exception caught while trying to delete data from the business table is: "+e.getMessage());
		}
	}

}
