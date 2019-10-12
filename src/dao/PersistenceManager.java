package dao;

import com.sun.source.tree.ArrayAccessTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class PersistenceManager {
	
	private static final String PROPS_FILE = "./resources/db.properties";
	
	private static Connection connection;
	
	private PersistenceManager(){}//Prevents initialization

	/**
	 * 
	 * @return la connexion a la base de donnees
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {

		if (connection == null || !connection.isValid(2)) {

			Properties props = new Properties();
			try (FileInputStream fis = new FileInputStream(PROPS_FILE)) {
				props.load(fis);
			}

			String driverClass = "org.mariadb.jdbc.Driver";
			String dbURL = props.getProperty("DB_URL");
			String dbLogin = props.getProperty("DB_LOGIN");
			String dbPWD = props.getProperty("DB_PASSWORD");

			
			
			//dbURL="jdbc:mariadb://localhost:3306/SegaBank?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
			//dbLogin="admin";
			//dbPWD="patate";
				
					
			//Class.forName(driverClass);

			Enumeration<Driver> drivers= DriverManager.getDrivers();
			while (drivers.hasMoreElements()){
				System.out.println(drivers.nextElement().getClass().getName());
			}
			connection = DriverManager.getConnection(dbURL, dbLogin, dbPWD);
		}
		return connection;
	}

	/**
	 * Ferme la connexion a las base de donnees
	 * @throws SQLException
	 */
	public static void closeConnection() throws SQLException {
		if (connection != null && connection.isValid(2)) {
			connection.close();
		}
	}

}
