package Weight;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FoodDAO implements DAO<Integer>{
	private Connection myConn;

	public FoodDAO() throws Exception {
		
		// get db properties
		Properties props = new Properties();
		props.load(new FileInputStream("prop.properties"));
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		// connect to database
		myConn = DriverManager.getConnection(dburl, user, password);
	}
	
	public List<Integer> getAllids(LocalDate date) throws Exception {
		List<Integer> list = new ArrayList<>();
		
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String dateString =date.toString();
		try {
			myStmt = myConn.prepareStatement("select id from food where food_date = ?");
			myStmt.setString(1, dateString);
			myRs = myStmt.executeQuery();
			while (myRs.next()) {
				list.add(myRs.getInt("id"));
			}
			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Food> getAllFoods(LocalDate date) throws Exception {
		List<Food> list = new ArrayList<>();
		
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String dateString = date.toString();
		try {
			myStmt = myConn.prepareStatement("select * from food where food_date = ?");
			myStmt.setString(1, dateString);
			myRs = myStmt.executeQuery();
			while (myRs.next()) {
				Food tempFood = convertRowToFood(myRs);
				list.add(tempFood);
			}
			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public int getSumKcalDate(LocalDate date) throws SQLException {
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String dateString =date.toString();
		try {
			myStmt = myConn.prepareStatement("select SUM(kcal) from food where food_date = ?");
			myStmt.setString(1, dateString);
			myRs = myStmt.executeQuery();
			int result = 0;
			while (myRs.next()) {
				result = myRs.getInt(1);
			}
			return result;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public void addFood(Food food, LocalDate date) throws Exception {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("insert into food"
					+ " (food_date, food_name, amount, kcal, protein, carbs, fat)"
					+ " values (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			// set params
			myStmt.setString(1, date.toString());
			myStmt.setString(2, food.getFood().toString());
			myStmt.setDouble(3, food.getAmount());
			myStmt.setInt(4, food.getKcal());
			myStmt.setDouble(5, food.getProteinAmount());
			myStmt.setDouble(6, food.getCarbsAmount());
			myStmt.setDouble(7, food.getFatAmount());
			
			// execute SQL
			myStmt.executeUpdate();	
		}
		finally {
			close(myStmt, null);
		}
		
	}
	
	public void remove(Integer id) throws Exception {
		PreparedStatement myStmt = null;
			int id1 = id.intValue();
		try {
			// prepare statement
			myStmt = myConn.prepareStatement("delete from food where id = ?");
			
			// set params
			myStmt.setInt(1, id1);		
			// execute SQL
			myStmt.executeUpdate();	
		}
		finally {
			close(myStmt, null);
		}
		
	}
	
	private Food convertRowToFood(ResultSet myRs) throws SQLException {
			String foodName = myRs.getString("food_name");
			double amount = myRs.getDouble("amount");
			int kcal = myRs.getInt("kcal");
			double protein = myRs.getDouble("protein");
			double carbs = myRs.getDouble("carbs");
			double fat = myRs.getDouble("fat");
			
			Food tempFood = new Food(foodName, amount, kcal, protein, carbs, fat);
			return tempFood;
		}
	
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
			throws SQLException {

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			myStmt.close();
		}
		
		if (myConn != null) {
			myConn.close();
		}
		
	}
	private void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);		
	}
}
