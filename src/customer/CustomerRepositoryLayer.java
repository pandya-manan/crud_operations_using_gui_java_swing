package customer;
import java.sql.*;
public class CustomerRepositoryLayer {

	//Establishing the connection properties
	 private String url = CustomerDBProperties.getDbURL();
	    private String username = CustomerDBProperties.getDbUsername();
	    private String password = CustomerDBProperties.getDbPassword();
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
	public void insertNewCustomer(Integer customerId,String name,String address,String email) throws SQLException
	{
		Connection conn=getConnection();
		String insertQuery="INSERT INTO customer(c_id,name,address,email) VALUES (?,?,?,?)";
		try
		{
			PreparedStatement insertStatement=conn.prepareStatement(insertQuery);
			insertStatement.setInt(1, customerId);
			insertStatement.setString(2, name);
			insertStatement.setString(3,address);
			insertStatement.setString(4, email);
			insertStatement.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("The exception caught while trying to add a new customer is: "+e.getMessage());
		}
		
	}
	
	//Logic to read data from the database
	public ResultSet readCustomers() throws SQLException
	{
		Connection conn=getConnection();
		String query="SELECT * FROM customer";
		try
		{
			PreparedStatement readData=conn.prepareStatement(query);
			return readData.executeQuery();
		}
		catch(Exception e)
		{
			System.out.println("The exception caught while trying to read data from the customer table is: "+e.getMessage());
			return null;
		}
	}
	
	//Logic to update data in the database
	public void updateCustomer(int c_id, String name, String address, String email) {
	    String query = "UPDATE customer SET name = ?, address = ?, email = ? WHERE c_id = ?";

	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, name);
	        stmt.setString(2, address);
	        stmt.setString(3, email);
	        stmt.setInt(4, c_id);  // Setting b_id as an integer

	        int rowsUpdated = stmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Customer record updated successfully!");
	        } else {
	            System.out.println("No record found with the specified ID.");
	        }

	    } catch (SQLException e) {
	        System.err.println("Error updating customer record: " + e.getMessage());
	    }
	}

	
	//Logic to delete data in the database
	public void deleteCustomer(Integer customerId)
	{
		String deleteSQL="DELETE FROM customer WHERE c_id=?";
		try
		{
			PreparedStatement deleteStatement=toDatabase.prepareStatement(deleteSQL);
			deleteStatement.setInt(1, customerId);
			deleteStatement.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("The exception caught while trying to delete data from the customer table is: "+e.getMessage());
		}
	}

}
