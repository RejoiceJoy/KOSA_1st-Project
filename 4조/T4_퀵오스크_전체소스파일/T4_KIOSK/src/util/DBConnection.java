//김미리, 김학수 페어 프로그래밍_pair programming

package util;

import java.io.FileNotFoundException;/*FileReader 사용을 위한 필수 예외.
public FileReader(File file) throws FileNotFoundException Creates a new FileReader, given the File to read from.
public FileReader(String fileName) throws FileNotFoundException Creates a new FileReader, given the name of the file to read from.*/
import java.io.FileReader;/*Creates a new FileReader, given the File to read from. Creates a new FileReader, given the name of the file to read from.*/
import java.io.IOException;/*java.util.Properties 클래스의 load() 메소드 사용을 위한
void java.util.Properties.load(Reader reader) throws IOException
public void load(Reader reader) throws IOException
Reads a property list (key and element pairs) from the input character stream in a simple line-oriented format.*/
import java.io.Reader;/*java.io 객체. Abstract class for reading character streams.
The only methods that a subclass must implement are read(char[], int, int) and close().
Most subclasses, however, will override some of the methods defined here in order to provide higher efficiency, additional functionality, or both.*/
import java.sql.Connection;//JDBC API 사용을 위한 인터페이스. createStatement()/preparedStatement()/collableStatement() 메소드 사용하여 Statement/preparedStatement 개체 참조 위함.
import java.sql.DriverManager;//JDBC API 사용을 위한 클래스. getConnection() 메소드 사용 하여 Connection 개체 참조 위함.
import java.sql.SQLException;/*드라이버 메소드, 데이터베이스에 액세스하는 메소드 또는 데이터베이스 연결을 가져오려는 시도 중 하나에 발생하는 오류에서 발생. 
사용자 이름이나 암호 정보가 잘못되어 데이터 베이스에 연결할 수 없거나 데이터베이스가 오프라인일 경우, SQL 쿼리에 포함되지 않은 열 이름에 액세스를 시도할 경우*/
import java.util.Properties;//접속할 때 필요한 driver, url, userid, password 정보

public class DBConnection {

	private static Connection conn;

	private DBConnection() {
	}

	static {//static 초기화 블록 -> DBConnection의 정적 필드인 private static Connection conn;을 초기화하기 위함.
		// 환경설정 파일을 읽어오기 위한 객체 생성
		Properties properties  = new Properties();//load() 메소드 사용하여 설정파일 읽어오기 위함. The Properties class represents a persistent set of properties.
		Reader reader;//properties 객체의 load() 메소드의 인자로 사용 위함. 바이트 기반이 아닌 문자 기반의 스트림을 제공하기 위해 InputStream이 아닌 Reader를 사용.
		try {
			reader = new FileReader("lib/oracle.properties");  // 읽어올 파일 지정
			/*입력 대상 중 properties라는 File을 읽어오므로 FileReader 스트림을 개방하여 Reader형으로 다형성.
			java.lang.Object
				java.io.Reader
					java.io.InputStreamReader
						java.io.FileReader
			Fields inherited from class java.io.Reader
			lock*/
			properties.load(reader);                           // 설정 파일 로딩하기
			/*public void load(Reader reader)
          				throws IOException
			Reads a property list (key and element pairs) from the input character stream in a simple line-oriented format.*/
		} catch (FileNotFoundException e1) {//FileReader(File file) 생성자에 필수로 딸려오는 예외.
			System.out.println("예외: 지정한 파일을 찾을수없습니다 :" + e1.getMessage());
			e1.printStackTrace();
		} catch (IOException e) {//java.util.Properties 클래스의 load() 메소드 사용에 필수로 딸려오는 예외.
			e.printStackTrace();
		}//end try

		String driverName = properties.getProperty("driver");//설정파일에서 값 불러오기 위함
		/*public String getProperty(String key)
		Searches for the property with the specified key in this property list.
		If the key is not found in this property list, the default property list, and its defaults, recursively, are then checked.
		The method returns null if the property is not found.*/		
		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String pwd = properties.getProperty("password");

		try {
			Class.forName(driverName);//설정파일에서 불러온 드라이버 클래스 이름을 인자로 드라이버 클래스 반환?
			/*public static Class<?> forName(String className)
                        throws ClassNotFoundException
			Returns the Class object associated with the class or interface with the given string name.*/
			conn = DriverManager.getConnection(url, user, pwd);/*url, username, pw를 인자로 세션에 데이터베이스를 제공하는 Connection 클래스의 객체 생성.
			연결 객체가 열려 있으면 데이터베이스에 액세스하고 명령을 만들고 결과를 가져오며 데이터베이스를 조작할 수 있음.*/
			System.out.println("connection success");
		} catch (ClassNotFoundException e) {//forName() 메소드가 드라이버 클래스를 찾지 못해 예외 발생해서 catch로 대처함.
			System.out.println("예외: 드라이버로드 실패 :" + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {/*DriverManager.getConnection() 메소드로 DB에 액세스 시 발생하는 예외에 대해 catch로 대처.
		사용자 이름이나 암호 정보가 잘못되어 데이터베이스에 연결할 수 없거나 데이터베이스가 오프라인일 경우
		SQL 쿼리에 포함되지 않은 열 이름에 액세스를 시도할 경우 등*/
			System.out.println("예외: connection fail :" + e.getMessage());
			e.printStackTrace();
		}//end try
	}//end static

	//싱글턴 패턴
	public static Connection getConnection() {//DBConnection의 정적 멤버 메소드. static 필드에서 초기화한 private static Connection conn; 값을 반환하는 getter
		return conn;
	}//end getConnection
}//end DBConnection
