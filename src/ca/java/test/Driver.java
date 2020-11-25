package ca.java.test;



import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;
import java.sql.Timestamp;




 
 public class Driver {
	 
	 private static final String ALL_userss_QUERY	= "SELECT * FROM test.user";
	 private static final String ALL_BOARD_QUERY = "SELECT * FROM test.boards";
	 private static final String ADD_COMMENTS_QUERY = "INSERT INTO test.boards(username, email, create_time, comments ) VALUES(?,?,?,?)";
	
	 
	 
	 public static void main(String[] args) throws SQLException {
		 Connection conn = null;

		 createTable();
//		 post();
		 int option = printMenu();
			try {
				conn = getConnection();
				switch(option) {
				case 1:
					addComments(conn);
					break;
				case 2:
					show();
					break;
				case 3: 
					System.out.println("What do you want to search?");
					break;
				case 4:
					System.out.println("Bye!");
					break;
				default :
					System.out.println("The option is wrong !");
				}		
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null) {
					conn.close();
				}
				
			}

	 }

	 public static Connection getConnection() {
	  try {
	   String driver = "com.mysql.cj.jdbc.Driver";
	   String url = "jdbc:mysql://localhost/test?serverTimezone = UTC";
	   String username = "";
	   String password = "";
	   Class.forName(driver);
	   
	   Connection conn = DriverManager.getConnection(url, username, password);
//	   System.out.println("Connect to Database");
	   return conn;
	   
	  } catch(Exception e) {
	   System.out.println(e);
	  
	  return null;
	 }
	 }
	 //ÉeÅ[ÉuÉãçÏê¨
	 public static void createTable() {
	  try {
	   Connection conn = getConnection();
	   PreparedStatement create = conn.prepareStatement("CREATE TABLE IF NOT EXISTS userss ("
	     + "id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT, "
	     + "firstName varchar(255), lastName varchar(255), "
	     + "email varchar(250))");
	   create.executeUpdate();
	  } catch (Exception e) {
	   System.out.println(e);
	  } finally {
	   System.out.println("Function complete");
	  }
	 }
	 
//	 public static void createBoardTable() {
//		  try {
//		   Connection con = getConnection();
//		   PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS board ("
//		     + "id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT, "
//		     + "firstName varchar(255), lastName varchar(255), "
//		     + "email varchar(250))");
//		   create.executeUpdate();
//		  } catch (Exception e) {
//		   System.out.println(e);
//		  } finally {
//		   System.out.println("Function complete");
//		  }
//		 }
	 
 
//	 public static void post() {
//	  final String firstN = "Ayumi";
//	  final String lastN = "Tanaka";
//	  final String email = "amichan2@gmail.com";
//	  
//	  try {
//	   Connection conn = getConnection();
//	   PreparedStatement posted = conn.prepareStatement("INSERT INTO user (firstName, lastName, email) VALUES (' "+firstN+" ',' "+lastN+" ', ' "+email+" ')");
//	   posted.executeUpdate();
//	  } catch(Exception e) {
//	   System.out.println(e);
//	  } finally {
//	   System.out.println(firstN + " " + lastN + " has been added to the user Table.");
//	  }
//	  
//	 }
	 //method to print menu
		public static int printMenu() {
			Scanner input = new Scanner(System.in);
			System.out.println("Choose your option :");
			System.out.println("1- Add comment");
			System.out.println("2- Show all comments");
			System.out.println("3- Search board");
			System.out.println("4- Quit now");
			return input.nextInt();
		}
		
		//add comments
		public static void addComments(Connection conn) throws SQLException {
			Scanner input = new Scanner(System.in);
			System.out.println("1- Please enter your user Name.");
			String userName = input.nextLine();
			System.out.println("2- Please enter your E-mail");
			String email = input.nextLine();
			System.out.println("3- Please enter your comments");
			String comments = input.nextLine();
			
			java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
			
			PreparedStatement prestmt = null;
			prestmt = getConnection().prepareStatement(ADD_COMMENTS_QUERY);
			prestmt.setString(1, userName);
			prestmt.setString(2, email);
			prestmt.setTimestamp(3, date);
			prestmt.setString(4, comments);
		
			prestmt.executeUpdate();
			
			 
		 System.out.println("\nThis comments has posted on the board as below!" + " Å´Å´Å´" +
				 "\nUserName: "  + userName + "\nEmail: " + email + "\ncomments: " + comments);
		}
	 
		//method to get data from DB
		public static ResultSet getData(Connection conn, String query) throws SQLException {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		}
		
		//show comments
		public static void show() throws SQLException {
			Connection conn = getConnection();
			ResultSet rsEmp = getData(conn, ALL_BOARD_QUERY);
			while(rsEmp.next()) {
				System.out.println("ID:" + rsEmp.getString("id") + "\nName: " + rsEmp.getString("userName") + "\nComments: " + rsEmp.getString("comments") + "\n************************");
			}
		}
		
 }
