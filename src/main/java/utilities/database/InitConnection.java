package utilities.database;

import org.testng.annotations.Test;

import java.sql.*;

import static utilities.account.AccountTest.*;

public class InitConnection {
    public java.sql.Connection createConnection() throws SQLException {
        String connectionUrl = "jdbc:postgresql://%s:%s/%s?user=%s&password=%s&loginTimeout=30".formatted(DB_HOST, DB_PORT, DB_DATABASE, DB_USER, DB_PASS);
        return DriverManager.getConnection(connectionUrl);
    }

	/**
	 * Get OTP code sent to a particular phone number in Database
	 * @param number: phone number
	 * @param typeOfOTP: either activate or reset
	 * Example usage: String opt_code = obj.getOTPCode("+500:8942531099", "activate")
	 */
    public String getOTPCode(String number, String typeOfOTP) throws SQLException {
        Connection connection = new InitConnection().createConnection();
        String query = "select * from \"gateway-services\".jhi_user ju where login = '%s'".formatted(number);
        ResultSet resultSet = connection.prepareStatement(query).executeQuery();
        String OTP_CODE = null;
        while (resultSet.next()) {
            OTP_CODE = resultSet.getString("activation_key");
        }
        return OTP_CODE;
    }     
    
    @Test
    public void test() throws SQLException {
        java.sql.Connection connection = createConnection();
        String query = "select activation_key from \"gateway-services\".jhi_user ju where login = '+500:8942531099'";
        ResultSet resultSet = connection.prepareStatement(query).executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("activation_key"));
        }
    }
}
