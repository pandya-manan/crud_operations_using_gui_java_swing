package business;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class BusinessGUI extends JFrame{
	
	private JTextField nameField, addressField, emailField, idField;
	private JButton addButton, updateButton, deleteButton, loadButton;
	private JTable studentTable;
	private DefaultTableModel tableModel;
	private BusinessRepositoryLayer dbManager;
	public BusinessGUI() {
	dbManager = new BusinessRepositoryLayer();
	setTitle("Overseas Transport Management System");
	setLayout(new BorderLayout());
	// Input panel
	JPanel inputPanel = new JPanel(new GridLayout(5, 2));
	inputPanel.add(new JLabel("B_ID:"));
	idField = new JTextField();
	inputPanel.add(idField);
	inputPanel.add(new JLabel("Name:"));
	nameField = new JTextField();
	inputPanel.add(nameField);
	inputPanel.add(new JLabel("Address:"));
	addressField = new JTextField();
	inputPanel.add(addressField);
	inputPanel.add(new JLabel("Email:"));
	emailField = new JTextField();
	inputPanel.add(emailField);
	// Button panel
	JPanel buttonPanel = new JPanel();
	addButton = new JButton("Add");
	updateButton = new JButton("Update");
	deleteButton = new JButton("Delete");
	loadButton = new JButton("Load");
	buttonPanel.add(addButton);
	buttonPanel.add(updateButton);
	buttonPanel.add(deleteButton);
	buttonPanel.add(loadButton);
	// Table
	tableModel = new DefaultTableModel(new String[]{"B_ID", "Name", "Address", "Email"}, 0);
	studentTable = new JTable(tableModel);
	JScrollPane scrollPane = new JScrollPane(studentTable);
	// Add components to the frame
	add(inputPanel, BorderLayout.NORTH);
	add(scrollPane, BorderLayout.CENTER);
	add(buttonPanel, BorderLayout.SOUTH);
	// Action listeners
	addButton.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
	Integer b_id=Integer.parseInt(idField.getText());
	String name = nameField.getText();
	String address = addressField.getText();
	String email=emailField.getText();
	try {
		dbManager.insertNewBusiness(b_id,name,address,email);
	} catch (SQLException e1) {
		
		System.out.println("The exception while trying to insert new business is: "+e1.getMessage());
	}
	loadStudents();
	}
	});
	updateButton.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
	Integer b_id=Integer.parseInt(idField.getText());
	String name = nameField.getText();
	String address = addressField.getText();
	String email=emailField.getText();
	dbManager.updateBusiness(b_id, name, address, email);
	loadStudents();
	}
	});
	deleteButton.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
	Integer b_id=Integer.parseInt(idField.getText());
	dbManager.deleteBusiness(b_id);
	loadStudents();
	}
	});
	loadButton.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
	loadStudents();
	}
	});
	setSize(600, 400);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
	}
	
	// Load students into the table
	private void loadStudents() {
	try {
	ResultSet resultSet = dbManager.readBusiness();
	tableModel.setRowCount(0); // Clear existing data
	while (resultSet != null && resultSet.next()) {
	int b_id = resultSet.getInt("b_id");
	String name = resultSet.getString("name");
	String address=resultSet.getString("address");
	String email=resultSet.getString("email");
	tableModel.addRow(new Object[]{b_id,name,address,email});
	}
	} catch (SQLException e) {
	e.printStackTrace();
	}
	}

	public static void main(String[] args) {
		new BusinessGUI();

	}

}
