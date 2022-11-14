package br.com.amaral.dao.generics.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author leandro.amaral
 *
 */

public class ConnectionFactory {

	private static Connection connection;

	private ConnectionFactory(Connection connection) {

	}

	public static Connection getConnection() throws SQLException {
		if (connection == null) {
			connection = initConnection();
			return connection;
		} else if (connection.isClosed()) {
			connection = initConnection();
			return connection;
		} else {
			return connection;
		}
	}

	private static Connection initConnection() {
		try {

//			String con = "jdbc:postgresql://" + Config.server + "/" + Config.database + ",";
//
			return DriverManager.getConnection(
					"jdbc:postgresql://localhost:15432/sales_test", "postgres", "108278");
			//con, Config.user, Config.password);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}

