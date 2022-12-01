package dbconnection;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//(11/18윤준호 작성)
public class DBConnection {

	private static Connection conn;
	
	private DBConnection() {
	}
	
	static {
		//환경 설정(db접속용) 파일을 읽어오기 위한 객체 생성
		Properties properties = new Properties();
		Reader reader;
		try {
			reader = new FileReader("lib/oracle.properties");
			properties.load(reader);
		} catch (FileNotFoundException e1) {
			System.out.println("예외: 지정한 파일을 찾을 수 없습니다. :" + e1.getMessage());
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//end try
		
		String driverName = properties.getProperty("driver");
		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String pwd = properties.getProperty("password");
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, pwd);
			System.out.println("connection success");
		} catch (ClassNotFoundException e) {
			System.out.println("예외: 드라이버로드실패 :" + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("예외: connection fail :" + e.getMessage());
			e.printStackTrace();
		}//end try
		}//end static

		//싱글턴 패턴 (한 번 접속되면 다른데서 사용 X)
		public static Connection getConnection() {
			return conn;
		}//end getConnection
}//end DBConnection