package Weight;
import java.util.*;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
public class WeightDAO implements DAO<LocalDate>{
	
private Connection myConn;

	public WeightDAO() throws Exception {
		
		// get db properties
		Properties props = new Properties();
		props.load(new FileInputStream("prop.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		// connect to database
		myConn = DriverManager.getConnection(dburl, user, password);
		
		System.out.println("DB connection successful to: " + dburl);
	}
	
	public List<Weight> getAllWeights() throws Exception {
		List<Weight> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from weight");
			while (myRs.next()) {
				Weight tempWeight = convertRowToWeight(myRs);
				list.add(tempWeight);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Weight> searchWeightByDate(LocalDate date, YearMonth yearMonth, Year year) throws Exception {
		List<Weight> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String dateStr =null;
		try {
			if(yearMonth== null && year==null) {
				dateStr = date.toString()+"%" ; //
			} else if(date == null && year ==null) {
				dateStr = yearMonth.toString()+"%" ; //
			}else if(date == null && yearMonth==null) {
				dateStr = year.toString()+"%";
			}else {
				throw new IllegalArgumentException();
			}

			myStmt = myConn.prepareStatement("select * from weight where date_weight like ?");
			
			myStmt.setString(1, dateStr);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Weight tempWeight = convertRowToWeight(myRs);
				list.add(tempWeight);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public void addWeight(Weight weight) throws Exception {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("insert into weight"
					+ " (date_weight, weight, kcal, gym)"
					+ " values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			// set params
			myStmt.setString(1, weight.getDate().toString());
			myStmt.setDouble(2, weight.getWeight());
			myStmt.setInt(3, weight.getKcal());
			myStmt.setString(4, weight.getGym());
			
			// execute SQL
			myStmt.executeUpdate();	
		}
		finally {
			close(myStmt, null);
		}
		
	}
	
	public void updateKcal(LocalDate date, int kcal) throws Exception {
		PreparedStatement myStmt = null;
		String dateString = date.toString();
		try {
			// prepare statement
			myStmt = myConn.prepareStatement("update weight set kcal =? where date_weight=?");
			
			// set params
			myStmt.setInt(1, kcal);
			myStmt.setString(2, dateString);
			
			
			// execute SQL
			myStmt.executeUpdate();	
		}
		finally {
			close(myStmt, null);
		}
		
	}
	
	public void remove(LocalDate ld) throws Exception {
		PreparedStatement myStmt = null;
		PreparedStatement myStmt2 = null;

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("delete from weight where date_weight = ?");
			myStmt2 = myConn.prepareStatement("delete from food where food_date = ?");
			
			// set params
			myStmt.setString(1, ld.toString());
			myStmt2.setString(1, ld.toString());	
			// execute SQL
			myStmt.executeUpdate();	
			myStmt2.executeUpdate();	
		}
		finally {
			close(myStmt, null);
			close(myStmt2, null);
		}
		
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
	
	private Weight convertRowToWeight(ResultSet myRs) throws SQLException {
		
		LocalDate date = LocalDate.parse(myRs.getString("date_weight"));
		double weight = myRs.getDouble("weight");
		int kcal = myRs.getInt("kcal");
		String gym = myRs.getString("gym");
		
		Weight tempWeight = new Weight(date, weight, kcal, gym);
		
		return tempWeight;
	}
}
