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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.event.SwingPropertyChangeSupport;
import java.sql.Timestamp;

 public class Driver {
	
	 private static final String ALL_BOARD_QUERY = "SELECT * FROM test.boards";
	 private static final String ADD_COMMENTS_QUERY = "INSERT INTO test.boards(username, email, create_time, topic, comments) VALUES(?,?,?,?,?)";
	 public static String searchWordResult;
	 private static final String SEARCH_FROM_BOARD = "SELECT * FROM test.boards WHERE topic LIKE";
	
	
	
	 public static void main(String[] args) throws SQLException {
		 Connection conn = null;
		 Boolean menuContenue = false;
		 System.out.println("\nWelcome to AY Bulletin Board!!!");
		 	
		 	while(menuContenue != true) {
		 	
				try {
					int option = printMenu();
					conn = getConnection();
					
					switch(option) {
					case 1:
						addComments(conn);
						System.out.println();
						break;
					case 2:
						show();
						System.out.println();
						break;
					case 3: 					
						showSearchTopic();
						System.out.println();						
						break;
					case 4:
						System.out.println("Bye!");
						menuContenue = true;
						break;
					default :
						System.err.println("The option is wrong! Please enter the menu number again.");
							
						}
						
							
				} catch (SQLException e) {
					e.printStackTrace();
					
				} finally {
					if (conn != null) {
						conn.close();
					}
				}
			}
		 	
	 }
	
	 //connection to database
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
//	 public static void createTable() {
//		  try {
//		   Connection conn = getConnection();
//		   PreparedStatement create = conn.prepareStatement("CREATE TABLE IF NOT EXISTS userss ("
//		     + "id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT, "
//		     + "firstName varchar(255), lastName varchar(255), "
//		     + "email varchar(250))");
//		   create.executeUpdate();
//		  } catch (Exception e) {
//		   System.out.println(e);
//		  } finally {
//		   System.out.println("Function complete");
//		  }
//	 }
	
	 //method to print menu
		public static int printMenu() {
			Scanner input = new Scanner(System.in);
			System.out.println("===========================");
			System.out.println("Choose your option :");
			System.out.println("1- Add comment on the board");
			System.out.println("2- Show all comments");
			System.out.println("3- Search topic");
			System.out.println("4- Quit now");
			System.out.println("===========================");
			//while loop
			while(!input.hasNextInt()) {
				System.err.println("The option is wrong! Please enter the menu number again.");
				input.next();
				System.out.println("===========================");
				System.out.println("Choose your option :");
				System.out.println("1- Add comment on the board");
				System.out.println("2- Show all comments");
				System.out.println("3- Search topic");
				System.out.println("4- Quit now");
				System.out.println("===========================");
			}
			return input.nextInt();
		}
		
		//add comments on the board
		public static void addComments(Connection conn) throws SQLException {
			Scanner input = new Scanner(System.in);
			System.out.println("1- Please enter your user Name.");
			String userName = input.nextLine();
			System.out.println("2- Please enter your E-mail");
			String email = input.nextLine();
						
				while(!Check.checkEmail(email)) {
					System.err.println("Email Not Accepted!");
					System.out.println("2- Please enter your E-mail again.");
					email = input.nextLine();
					
				}
			
			System.out.println("3- Please Enter the Topic");
			String topic = input.nextLine();
			System.out.println("4- Please enter your comments");
			String comments = input.nextLine();
			
//			java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
			Timestamp date = new Timestamp(System.currentTimeMillis());
			
			PreparedStatement prestmt = null;
			prestmt = getConnection().prepareStatement(ADD_COMMENTS_QUERY);
			prestmt.setString(1, userName);
			prestmt.setString(2, email);
			prestmt.setTimestamp(3, date);
			prestmt.setString(4, topic);
			prestmt.setString(5, comments);
		
			prestmt.executeUpdate();
			
			
		 System.out.println("\nThis comments has posted on the board as below!" + " Å´Å´Å´" +
				 "\nUserName: "  + userName + "\nEmail: " + email + "\nTopic: " + topic  + "\ncomments: " + comments);
		}
	
		//method to get data from DB
		public static ResultSet getData(Connection conn, String query) throws SQLException {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		}
		
		
		//Show all comments
		public static void show() throws SQLException {
			Connection conn = getConnection();
			ResultSet rsEmp = getData(conn, ALL_BOARD_QUERY);
			while(rsEmp.next()) {
				System.out.println("Date:" + rsEmp.getString("create_time") + "\nID:" + rsEmp.getString("id") +
								"\nName: " + rsEmp.getString("userName") + "\nComments: " + rsEmp.getString("comments") +
								"\n************************");
			}
		}
		
		//Search topic from DB and show that board
		public static void showSearchTopic() throws SQLException {
			Scanner input = new Scanner(System.in);
			Connection conn = getConnection();
			ResultSet rsEmp = getData(conn, (SEARCH_FROM_BOARD + "'"+ searchWordResult + "'"));
			
			Boolean find = true;
			int cnt = 0;
			
			System.out.println("Do you want to search? "
								+ "\n1- Continue to search."
								+ "\n2-Return menu");
			
			while(find == true) {
				while(!input.hasNextInt()) {
					System.err.println("The option is wrong! Please enter 1 or 2 again.");
					input.next();
				}
				int answer = input.nextInt();
				switch (answer) {
					case 1:
						System.out.println("What topic do you want to search?");
						searchWordResult = input.next();
						rsEmp = getData(conn, (SEARCH_FROM_BOARD + "'%"+ searchWordResult + "%'"));
						
							while(rsEmp.next()) {
								
								System.out.println("Searched by " + searchWordResult + " are... \n" +
										"************************" +
										"\nDate:" + rsEmp.getString("create_time") +
										"\nID:" + rsEmp.getString("id") +
										"\nName: " + rsEmp.getString("userName") +
										"\nTopic: " + rsEmp.getString("topic") +
										"\nComments: "  + rsEmp.getString("comments") +
										"\n************************");
								find =  false;
								cnt++;
							}
							if (cnt == 0) {
								System.out.println("There is no " + searchWordResult + " topic yet!"
													+ "\nEnter 1 or 2 again!");
								find = true;
							}
						
						break;
						
					case 2 :
						find = false;
						break;
						
					default :
						System.err.println("The option is wrong! Please enter 1 or 2 again.");
	//					input.next();
					}
			}
		}
		
		
 }